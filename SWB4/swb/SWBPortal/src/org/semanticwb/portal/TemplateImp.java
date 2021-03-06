/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal;

import com.arthurdo.parser.HtmlException;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Template;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequestImp;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceModes;
import org.semanticwb.portal.util.SWBIFMethod;
import org.semanticwb.portal.util.SWBMethod;
import org.semanticwb.servlet.SWBHttpServletRequestWrapper;
import org.semanticwb.servlet.SWBHttpServletResponseWrapper;
import org.semanticwb.servlet.internal.DistributorParams;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateImp.
 */
public class TemplateImp extends Template
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(TemplateImp.class);
    
    /** The parts. */
    private ArrayList parts;
    
    /** The objects. */
    private HashMap objects = new HashMap();

    /** The web path. */
    private String webPath;
    
    /** The work path. */
    private String workPath;
    
    /** The act path. */
    private String actPath;
    
    /** The act work path. */
    private String actWorkPath;
    
    /** The act rel work path. */
    private String actRelWorkPath;

    /** The content type. */
    private String contentType=null;
    
    /**
     * Instantiates a new template imp.
     * 
     * @param base the base
     */
    public TemplateImp(Template base)
    {
        super(base.getSemanticObject());
        
        //System.out.println("TemplateImp:"+isActive()+" "+isDeleted());
        
        webPath = SWBPortal.getWebWorkPath()+super.getWorkPath();
        actPath = webPath+ "/" + getActualVersion().getVersionNumber() + "/";
        workPath = SWBPortal.getWorkPath()+super.getWorkPath();
        actWorkPath=workPath + "/" + getActualVersion().getVersionNumber();
        actRelWorkPath=super.getWorkPath()+ "/" + getActualVersion().getVersionNumber();

        objects.put("template", this);
        objects.put("user", new User(null));
        objects.put("topic", new WebPage(null));
        objects.put("webpage", new WebPage(null));
        objects.put("request", HttpServletRequest.class);
        objects.put("response", HttpServletResponse.class);
        objects.put("webpath", SWBPlatform.getContextPath());
        objects.put("distpath", SWBPortal.getDistributorPath());
        objects.put("templatepath", actPath);
        objects.put("if:user", SWBIFMethod.class);
        objects.put("if:topic", SWBIFMethod.class);
        objects.put("if:webpage", SWBIFMethod.class);
        objects.put("if:website", SWBIFMethod.class);
        objects.put("if:template", SWBIFMethod.class);
        

        if (isActive() && !isDeleted())
        {
            parts = parse(getFileName(getActualVersion()));
        } else
        {
            parts = new ArrayList();
        }
    }

    /**
     * Identify object.
     * 
     * @param val the val
     * @param objects the objects
     * @return the object
     */
    public Object identifyObject(String val, HashMap objects)
    {
        try
        {
            if (val.startsWith("{") && val.endsWith("}"))
            {
                int sar = val.indexOf('@');
                if (sar == -1)
                {
                    String sobj = val.substring(1, val.length() - 1).toLowerCase();
                    return objects.get(sobj);
                } else
                {
                    String sobj = val.substring(1, sar).toLowerCase();
                    if(objects.get(sobj)==null)return null;
                    String smto = val.substring(sar + 1, val.length() - 1);
                    Method mto = objects.get(sobj).getClass().getMethod(smto, (Class[])null);
                    return new SWBMethod(mto, objects.get(sobj), null, this);
                }
            }
        } catch (Exception e)
        {
            log.error(e);
        }
        return val;
    }

    /**
     * Replace objects values.
     * 
     * @param value the value
     * @param auxparts the auxparts
     * @param parts the parts
     * @param objects the objects
     * @return the string buffer
     */
    public StringBuffer replaceObjectsValues(String value, StringBuffer auxparts, ArrayList parts, HashMap objects)
    {
        //System.out.println("value:"+value);
        //ArrayList p = split(value, "\\{(.+?)[^\\}]@(.+?)\\}");
        ArrayList p = SWBUtils.TEXT.regExpSplit(value, "\\{([^\\{]+?)\\}");
        Iterator it = p.iterator();
        while (it.hasNext())
        {
            String sp = (String) it.next();
            Object obj = identifyObject(sp, objects);
            //System.out.println("sp:"+sp);
            //System.out.println("obj:"+obj);
            if(obj==null)
            {
                if(sp.startsWith("/work/"))
                {
                    auxparts.append(SWBPortal.getWebWorkPath());
                    auxparts.append(sp.substring(5));
                }else
                {
                    auxparts.append(sp);
                }
            }else
            {
                if (obj instanceof String)
                {
                    String str=(String)obj;
                    if(str.startsWith("/work/"))
                    {
                        auxparts.append(SWBPortal.getWebWorkPath());
                        auxparts.append(str.substring(5));
                    }else
                    {
                        auxparts.append(str);
                    }
                } else
                {
                    if (auxparts.length() > 0)
                    {
                        parts.add(auxparts.toString());
                        parts.add(obj);
                        auxparts = new StringBuffer();
                    }
                }
            }
        }
        return auxparts;
    }
    
    /**
     * Gets the resource type.
     * 
     * @param type the type
     * @return the resource type
     */
    private ResourceType getResourceType(String type)
    {
        ResourceType rt=null;
        if(type!=null)
        {
            //busca el tipo de recurso en el topicmap del template
            rt=getWebSite().getResourceType(type);
            //busca el tipo de recurso en el topicmap global
            //if(rt==null)rt=SWBContext.getGlobalWebSite().getResourceType(type);
        }
        return rt;
    }
    
    /*
     * Regresa ID (numero) del subtipo de recurso como string y concatena 
     * subtypemap en el caso de no coincidir con el topicmap de la plantilla
     */    
    /**
     * Gets the sub type.
     * 
     * @param type the type
     * @param stype the stype
     * @return the sub type
     */
    public ResourceSubType getSubType(String type, String stype)
    {
        //busca el tipo de recurso en el topicmap del template
        //ResourceSubType rt=getWebSite().getResourceSubType((type+"_"+stype));
        ResourceSubType rt=getWebSite().getResourceSubType(stype);
        if(rt==null)
        {
            ResourceType t=getWebSite().getResourceType(type);
            if(t!=null)
            {
                Iterator<ResourceSubType> it=t.listSubTypes();
                while (it.hasNext())
                {
                    ResourceSubType resourceSubType = it.next();
                    if(resourceSubType.getTitle().equalsIgnoreCase(stype))
                    {
                        rt=resourceSubType;
                        break;
                    }
                }
            }
        }
        //busca el tipo de recurso en el topicmap global
        //if(rt==null)rt=SWBContext.getGlobalWebSite().getResourceSubType((type+"_"+stype));
        return rt;
    }
    

    /**
     * Parses the.
     * 
     * @param filename the filename
     * @return the array list
     * @return
     */
    public ArrayList parse(String filename)
    {
        ArrayList parts = new ArrayList();
        LinkedList<SWBIFMethod> ifs = new LinkedList();
        try
        {
            //FileInputStream in= new FileInputStream(AFUtils.getInstance().getWorkPath()+"/templates/"+recTemplate.getId()+"/"+recTemplate.getActualversion()+"/"+filename);
            HtmlStreamTokenizer tok = null;
            if (filename.startsWith("/"))
                tok = new HtmlStreamTokenizer(SWBPortal.getFileFromWorkPath(filename));
            else
                tok = new HtmlStreamTokenizer(SWBPortal.getFileFromWorkPath(actRelWorkPath + "/" + filename));
            StringBuffer auxpart = new StringBuffer();
            //HtmlTag opentag=null;                       //tag inicial
            boolean textpart = false;
            boolean objectpart = false;
            boolean resourcepart = false;
            boolean contentpart = false;
            boolean scriptpart = false;
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                //System.out.println("parse element:"+tok.getStringValue());
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG)
                {
                    HtmlTag tag = new HtmlTag();
                    tok.parseTag(tok.getStringValue(), tag);
//                    if(!tag.isEndTag())
//                    {
//                        //System.out.println("tok:"+tok.getStringValue()+" "+tag);
//                        opentag=tag;
//                    }
                    if (tag.getTagString().toLowerCase().equals("resource"))
                    {
                        if (textpart)
                        {
                            if (!resourcepart)
                                parts.add(auxpart.toString());
                            //System.out.print(auxpart.toString());
                            auxpart = new StringBuffer();
                            textpart = false;
                        }
                        SWBResourceMgr obj = SWBPortal.getResourceMgr();
                        try
                        {
                            //System.out.println(tag.getTagString()+" type:"+tag.getTagType()+" endtag:"+tag.isEndTag()+" empty:"+tag.isEmpty()+" "+tag.getParamCount());
                            if (!tag.isEndTag())
                            {
                                if (!tag.isEmpty()) resourcepart = true;

                                HashMap params = new HashMap();
                                Enumeration en = tag.getParamNames();
                                while (en.hasMoreElements())
                                {
                                    String name = (String) en.nextElement();
                                    params.put(name.toLowerCase(), tag.getParam(name));
                                }
                                ResourceType type=getResourceType(tag.getParam("type"));
                                ResourceSubType stype=getSubType(tag.getParam("type"),tag.getParam("stype"));
                                Object args[] = {type, params, stype};
                                parts.add(new SWBMethod(null, obj, args, this));
                                //System.out.print(cls.getName()+":"+"getResources");
                            } else
                            {
                                resourcepart = false;
                            }
                        } catch (Exception e)
                        {
                            log.error("Error to parse Template:" + tag.getParam("type") + " ->Template:" + getTitle(),e);
                        }
                    } else if (tag.getTagString().toLowerCase().equals("wbobject"))
                    {
                        if (textpart)
                        {
                            if(!tag.isEndTag())parts.add(auxpart.toString());
                            auxpart = new StringBuffer();
                            textpart = false;
                        }
                        try
                        {
                            if (!tag.isEndTag())
                            {
                                ResourceType rec=getResourceType(tag.getParam("name"));
                                String objclass = rec.getResourceClassName();
                                Class cls = Class.forName(objclass);
                                objects.put(tag.getParam("name").toLowerCase(), cls.newInstance());
                            }
                        } catch (Exception e)
                        {
                            log.error("Error in Template, resource not found" + getTitle());
                        }
                    } else if (objects.containsKey(tag.getTagString().toLowerCase()))
                    {
                        Object obj = objects.get(tag.getTagString().toLowerCase());
                        if(obj instanceof String)
                        {
                            if (!textpart) textpart = true;
                            auxpart.append(obj.toString());
                        }else if(obj==SWBIFMethod.class)
                        {
                            if (!tag.isEndTag())
                            {
                                if (!textpart) textpart = true;
                                parts.add(auxpart.toString());
                                auxpart = new StringBuffer();
                                //Guardar partes y crear temporal
                                SWBIFMethod ifp=new SWBIFMethod(tag, parts, this);
                                parts.add(ifp);
                                ifs.add(ifp);
                                parts=ifp.getParts();
                            }else
                            {
                                parts.add(auxpart.toString());
                                auxpart = new StringBuffer();
                                SWBIFMethod ifp=ifs.removeLast();
                                parts=ifp.getBaseParts();
                            }
                        }else
                        {
                            if (textpart)
                            {
                                if (!objectpart)
                                    parts.add(auxpart.toString());
                                auxpart = new StringBuffer();
                                textpart = false;
                            }
                            try
                            {
                                if (!tag.isEndTag())
                                {
                                    if (!tag.isEmpty()) objectpart = true;
                                    Class cls = null;
                                    if (obj instanceof Class)
                                        cls = (Class) obj;
                                    else
                                        cls = obj.getClass();

                                    HashMap params = new HashMap();
                                    Enumeration en = tag.getParamNames();
                                    boolean objArgs = false;
                                    while (en.hasMoreElements())
                                    {
                                        String name = (String) en.nextElement();
                                        String val = tag.getParam(name);
                                        if (val.startsWith("{") && val.endsWith("}"))
                                        {
                                            objArgs = true;
                                            int sar = val.indexOf('@');
                                            if (sar == -1)
                                            {
                                                String sobj = val.substring(1, val.length() - 1).toLowerCase();
                                                params.put(name.toLowerCase(), objects.get(sobj));
                                            } else
                                            {
                                                String sobj = val.substring(1, sar).toLowerCase();
                                                String smto = val.substring(sar + 1, val.length() - 1);
                                                Method mto = objects.get(sobj).getClass().getMethod(smto, (Class[])null);
                                                params.put(name.toLowerCase(), new SWBMethod(mto, objects.get(sobj), null, this));
                                            }
                                        } else
                                            params.put(name.toLowerCase(), val);
                                    }
                                    //System.out.println(cls.getName()+":"+tag.getParam("method"));
                                    if (params.size() > 1)
                                    {
                                        Object arg[] = {params};
                                        Class argumentTypes[] = {new HashMap().getClass()};
                                        Method mto = cls.getMethod(tag.getParam("method"), argumentTypes);
                                        SWBMethod wbmto = new SWBMethod(mto, obj, arg, this);
                                        wbmto.setObjArgs(objArgs);
                                        parts.add(wbmto);
                                    } else
                                    {
                                        Method mto = cls.getMethod(tag.getParam("method"), (Class[])null);
                                        parts.add(new SWBMethod(mto, obj, null, this));
                                    }
                                } else
                                {
                                    objectpart = false;
                                }
                            } catch (Exception e)
                            {
                                log.error("Error Method not found: " + tag.getParam("method") + " ->Template:" + getTitle());
                            }
                        }
                    } else if (tag.getTagString().toLowerCase().equals("content"))
                    {
                        if (textpart)
                        {
                            if (!contentpart)
                                parts.add(auxpart.toString());
                            //System.out.print(auxpart.toString());
                            auxpart = new StringBuffer();
                            textpart = false;
                        }
                        SWBResourceMgr obj = SWBPortal.getResourceMgr();
                        try
                        {
                            if (!tag.isEndTag())
                            {
                                if (!tag.isEmpty()) contentpart = true;
                                HashMap params = new HashMap();
                                Enumeration en = tag.getParamNames();
                                while (en.hasMoreElements())
                                {
                                    String name = (String) en.nextElement();
                                    params.put(name.toLowerCase(), tag.getParam(name));
                                }
                                ResourceType type=getResourceType(tag.getParam("type"));
                                Object args[] = {"content", params, type};
                                parts.add(new SWBMethod(null, obj, args, this));
                                //System.out.print(cls.getName()+":"+"getResources");
                            } else
                            {
                                contentpart = false;
                            }
                        } catch (Exception e)
                        {
                            log.error("Component not found: "+ tag.getParam("class") + " ->Template:" + getTitle());
                        }
                    } else if (tag.getTagString().toLowerCase().equals("include"))
                    {
                        if (textpart)
                        {
                            if(!tag.isEndTag())parts.add(auxpart.toString());
                            auxpart = new StringBuffer();
                            textpart = false;
                        }
                        try
                        {
                            if (!tag.isEndTag())
                            {
                                String src=tag.getParam("src");
                                if(tag!=null)
                                {
                                    if(!(src.endsWith(".jsp")||src.contains(".jsp?")||src.endsWith(".php")||src.contains(".php?")||src.endsWith(".py")||src.contains(".py?")||src.endsWith(".groovy")||src.contains(".groovy?")))
                                    {
                                        ArrayList arr = parse(tag.getParam("src"));
                                        parts.addAll(arr);
                                    }else
                                    {
                                        HashMap params = new HashMap();
                                        Enumeration en = tag.getParamNames();
                                        while (en.hasMoreElements())
                                        {
                                            String name = (String) en.nextElement();
                                            params.put(name.toLowerCase(), tag.getParam(name));
                                        }
                                        Object args[] = {src, params};
                                        parts.add(new SWBMethod(null, "include", args, this));
                                    }
                                }
                            }
                        } catch (Exception e)
                        {
                            log.error("Component not found: ->Template:" + getTitle());
                        }
                    } else if (!resourcepart && !contentpart && !objectpart &&
                            (tag.getTagString().toLowerCase().equals("img")
                            || tag.getTagString().toLowerCase().equals("script")
                            || tag.getTagString().toLowerCase().equals("applet")
                            || tag.getTagString().toLowerCase().equals("tr")
                            || tag.getTagString().toLowerCase().equals("td")
                            || tag.getTagString().toLowerCase().equals("table")
                            || tag.getTagString().toLowerCase().equals("body")
                            || tag.getTagString().toLowerCase().equals("input")
                            || tag.getTagString().toLowerCase().equals("a")
                            || tag.getTagString().toLowerCase().equals("link")
                            || tag.getTagString().toLowerCase().equals("form")
                            || tag.getTagString().toLowerCase().equals("area")
                            || tag.getTagString().toLowerCase().equals("meta")
                            || tag.getTagString().toLowerCase().equals("bca")
                            || tag.getTagString().toLowerCase().equals("embed")
                            )
                    )
                    {
                        if (tag.getTagString().toLowerCase().equals("script"))
                        {
                            if (!tag.isEndTag())
                            {
                                if (!tag.isEmpty()) scriptpart = true;
                            } else
                            {
                                scriptpart = false;
                            }
                        }
                        if (!textpart) textpart = true;
                        if (!tag.isEndTag())
                        {
                            auxpart.append("<");
                            auxpart.append(tag.getTagString());
                            auxpart.append(" ");
                            Enumeration en = tag.getParamNames();
                            while (en.hasMoreElements())
                            {
                                String name = (String) en.nextElement();
                                String value = tag.getParam(name);
                                
                                //System.out.println("name:"+name);
                                //System.out.println("value:"+name);
                                //System.out.println("actPath:"+actPath);
                                //System.out.println("auxpart1:"+auxpart);
                                
                                auxpart.append(name);
                                auxpart.append("=\"");
                                if ((name.toLowerCase().equals("src")
                                        || name.toLowerCase().equals("background")
                                        || name.toLowerCase().equals("href"))
                                        && !value.startsWith("/")
                                        && !value.startsWith("{")
                                        && !value.startsWith("http://")
                                        && !value.startsWith("https://")
                                        && !value.startsWith("mailto:")
                                        && !value.startsWith("ftp://")
                                        && !value.startsWith("telnet://")
                                        && !value.startsWith("javascript")
                                        && !value.startsWith("#")
                                )
                                {
                                    auxpart.append(actPath);
                                }
                                if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick"))
                                {
                                    String out = findImagesInScript(value, ".gif'");
                                    out = findImagesInScript(out, ".jpg'");
                                    auxpart = replaceObjectsValues(out, auxpart, parts, objects);
                                    //auxpart.append(out);
                                } else
                                {
                                    auxpart = replaceObjectsValues(value, auxpart, parts, objects);
                                    //auxpart.append(value);
                                }
                                auxpart.append("\" ");
                                
                                //System.out.println("auxpart2:"+auxpart);
                                
                            }
                            if(tag.isEmpty())auxpart.append("/");
                            auxpart.append(">");
                        } else
                            auxpart.append(tok.getRawString());
                    } else if (!resourcepart && !contentpart && !objectpart &&
                            tag.getTagString().toLowerCase().equals("param"))
                    {
                        if (!textpart) textpart = true;
                        if (!tag.isEndTag())
                        {
                            if (tok.getRawString().toLowerCase().indexOf("movie") > 0)
                            {
                                auxpart.append("<");
                                auxpart.append(tag.getTagString());
                                auxpart.append(" ");
                                Enumeration en = tag.getParamNames();
                                while (en.hasMoreElements())
                                {
                                    String name = (String) en.nextElement();
                                    String value = tag.getParam(name);
                                    auxpart.append(name);
                                    auxpart.append("=\"");
                                    if (name.toLowerCase().equals("value")
                                            && !value.startsWith("/")
                                            && !value.startsWith("{")
                                            && !value.startsWith("http://")
                                            && !value.startsWith("https://")
                                            && !value.startsWith("mailto:")
                                            && !value.startsWith("ftp://")
                                            && !value.startsWith("telnet://")
                                            && !value.startsWith("javascript")
                                            && !value.startsWith("#")
                                    )
                                    {
                                        auxpart.append(actPath);
                                    }
                                    if (name.toLowerCase().equals("onmouseover") || name.toLowerCase().equals("onload") || name.toLowerCase().equals("onmouseout") || name.toLowerCase().equals("onclick"))
                                    {
                                        String out = findImagesInScript(value, ".gif'");
                                        out = findImagesInScript(out, ".jpg'");
                                        auxpart = replaceObjectsValues(out, auxpart, parts, objects);
                                        //auxpart.append(out);
                                    } else
                                    {
                                        auxpart = replaceObjectsValues(value, auxpart, parts, objects);
                                        //auxpart.append(value);
                                    }
                                    auxpart.append("\" ");
                                }
                                if(tag.isEmpty())auxpart.append("/");
                                auxpart.append(">");

                            } else
                                auxpart.append(tok.getRawString());
                        } else
                            auxpart.append(tok.getRawString());
                    } else if (!resourcepart && !contentpart && !objectpart)
                    {
                        if (!textpart) textpart = true;
                        auxpart = replaceObjectsValues(tok.getRawString(), auxpart, parts, objects);
                        //auxpart.append(tok.getRawString());
                    }
                } else if (!resourcepart && !contentpart && !objectpart && ttype == HtmlStreamTokenizer.TT_TEXT)
                {
                    if (!textpart) textpart = true;
                    auxpart.append(tok.getRawString());
                } else if (ttype == HtmlStreamTokenizer.TT_COMMENT)
                {
                    if (scriptpart) auxpart.append(tok.getRawString());
                    //System.out.println("Comment:"+tok.getRawString());
                } else
                {
                    if (!textpart) textpart = true;
                    auxpart.append(tok.getRawString());
                    //System.out.println("else:"+tok.getRawString());
                }
            }
            if (textpart)
            {
                parts.add(auxpart.toString());
                //System.out.println(auxpart.toString());
                textpart = false;
            }
        } catch (SWBException e)
        {
            log.error("Template:parse",e);
            //throw new AFException(e.getMessage(),"Template:parse");
        } catch (IOException e)
        {
            log.error("Template:parse",e);
            //throw new AFException(ioe.getMessage(),"Template:parse");
        } catch (HtmlException e)
        {
            log.error("Template:parse",e);
            //throw new AFException(ioe.getMessage(),"Template:parse");
        }

        return parts;
    }

    /**
     * Find images in script.
     * 
     * @param value the value
     * @param ext the ext
     * @return the string
     */
    private String findImagesInScript(String value, String ext)
    {
        StringBuffer aux = new StringBuffer(value.length());
        int off = 0;
        int f = 0;
        int i = 0;
        do
        {
            f = value.indexOf(ext, off);
            i = value.lastIndexOf("'", f);
            if (f > 0 && i >= 0)
            {
                i++;
                if (value.startsWith("/", i) || value.startsWith("http://", i))
                {
                    if(value.startsWith("/work/"))
                    {
                        aux.append(SWBPortal.getWebWorkPath());
                        aux.append(value.substring(off+5, f + ext.length()));
                    }else
                    {
                        aux.append(value.substring(off, f + ext.length()));
                    }
                } else
                {
                    aux.append(value.substring(off, i) + actPath + value.substring(i, f + ext.length()));
                }
                off = f + ext.length();
            }
        } while (f > 0 && i > 0);
        aux.append(value.substring(off));
        return aux.toString();
    }

    /**
     * Find image in script.
     * 
     * @param value the value
     * @param ext the ext
     * @return the string
     */
    private String findImageInScript(String value, String ext)
    {
        int f = value.indexOf(ext);
        int i = value.lastIndexOf("'", f);
        if (f > 0 && i >= 0)
        {
            i++;
            if (value.startsWith("/", i) || value.startsWith("http://", i))
            {
                if(value.startsWith("/work/"))
                {
                    return SWBPortal.getWebWorkPath()+value.substring(5);
                }else               
                {
                    return value;
                }
            } else
            {
                return value.substring(0, i) + actPath + value.substring(i, value.length());
            }
        }
        return value;
    }
    
    /**
     * Include.
     * 
     * @param src the src
     * @param request the request
     * @param response the response
     * @param user the user
     * @param topic the topic
     * @param params the params
     * @return the string
     */
    public String include(String src, HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap params)
    {
        String ret="";
        try
        {
            if(!(src.startsWith("/") || src.startsWith("http://")))
            {
                src=getActualPath()+src;
            }
            //System.out.println("Include src:"+src);
            SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
            request.setAttribute("topic", topic);
            request.setAttribute("webpage", topic);
            request.setAttribute("user", user);
            request.setAttribute("params", params);
            request.setAttribute("template", this);
            RequestDispatcher dispatcher = request.getRequestDispatcher(src);
            dispatcher.include(request, res);
            ret=res.toString();
        }catch(Exception e)
        {
            log.error(e);
        }finally
        {
            if(request!=null)
            {
                request.removeAttribute("topic");
                request.removeAttribute("webpage");
                request.removeAttribute("user");
                request.removeAttribute("params");            
                request.removeAttribute("template"); 
            }
        }
        return ret;
    }

    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param user the user
     * @param topic the topic
     */
    public void build(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic) 
    {
        build(request, response, user, topic, false);
    }

    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param user the user
     * @param topic the topic
     * @param savelog the savelog
     */
    public void build(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, boolean savelog)
    {
        try
        {
            PrintWriter out = response.getWriter();
            build(request, response, out, user, topic, savelog,null,null);
            out.flush();
            out.close();
        } catch (Exception e)
        {
            log.error("Error Template Build...",e);
        }
    }

    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param out the out
     * @param user the user
     * @param topic the topic
     * @param savelog the savelog
     */
    public void build(HttpServletRequest request, HttpServletResponse response, PrintWriter out, User user, WebPage topic, boolean savelog) 
    {
        build(request, response, out, user, topic, savelog, null,null);
    }

    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param out the out
     * @param user the user
     * @param topic the topic
     * @param savelog the savelog
     * @param content the content
     */
    public void build(HttpServletRequest request, HttpServletResponse response, PrintWriter out, User user, WebPage topic, boolean savelog, String content) 
    {
        build(request, response, out, user, topic, savelog, content,null);
    }

    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param out the out
     * @param user the user
     * @param topic the topic
     * @param savelog the savelog
     * @param content the content
     * @param distparams the distparams
     */
    public void build(HttpServletRequest request, HttpServletResponse response, PrintWriter out, User user, WebPage topic, boolean savelog, String content, DistributorParams distparams) 
    {
        //System.out.println("Enter Builder...");
        StringBuffer logbuf = null;
        StringBuffer resbuf = null;
        long tini = 0;
        if (savelog)
        {
            tini = System.currentTimeMillis();
            resbuf = new StringBuffer(300);
            logbuf = new StringBuffer(300);
            logbuf.append("log|");
            logbuf.append(request.getRemoteAddr());
            logbuf.append("|");
            logbuf.append(SWBPortal.getMessageCenter().getAddress());
            //logbuf.append("_");
            logbuf.append("|");
            String sess=request.getSession().getId();
            if(sess!=null)
            {
                sess=""+sess.hashCode();
            }else sess="_";
            logbuf.append(sess);
            logbuf.append("|");
            logbuf.append(topic.getWebSiteId());
            logbuf.append("|");
            //logbuf.append(topic.getSId());
            logbuf.append(topic.getId());
            logbuf.append("|");
            logbuf.append(user.getUserRepository().getId());
            logbuf.append("|");
            if (user.getLogin() != null)
                logbuf.append(user.getLogin());
            else
                logbuf.append("_");
            logbuf.append("|");
            logbuf.append(user.getSemanticObject().getSemanticClass().getClassId());
            logbuf.append("|");
            if(user.getDevice()!=null)
                logbuf.append(user.getDevice().getId());
            else
                logbuf.append("_");
            logbuf.append("|");
            if (user.getLanguage() != null && user.getLanguage().length() > 0)
                logbuf.append(user.getLanguage());
            else
                logbuf.append("_");
        }
        log.debug("<!-- Template1:"+ (System.currentTimeMillis()-tini) +"ms -->");
        build(request, response, out, user, topic, savelog, content, distparams, parts, resbuf);
        if (savelog)
        {
            long tfin = System.currentTimeMillis() - tini;            
            SWBPortal.getMessageCenter().sendMessage(logbuf.toString()+"|"+tfin+resbuf.toString());
        }
        log.debug("<!-- TemplateFin:"+ (System.currentTimeMillis()-tini) +"ms -->");
    }



    /**
     * Builds the.
     * 
     * @param request the request
     * @param response the response
     * @param out the out
     * @param user the user
     * @param topic the topic
     * @param savelog the savelog
     * @param content the content
     * @param distparams the distparams
     * @param parts the parts
     * @param resbuf the resbuf
     */
    public void build(HttpServletRequest request, HttpServletResponse response, PrintWriter out, User user, WebPage topic, boolean savelog, String content, DistributorParams distparams, ArrayList parts, StringBuffer resbuf)
    {
        if(parts==null)return;
        HashMap antresrc = new HashMap(5);                    //recursos evaluados anteriormente
        try
        {
            //PrintWriter out=response.getWriter();
            Iterator en = parts.iterator();
            while (en.hasNext())
            {
                Object obj = en.next();
                if (obj instanceof String)
                    out.print(obj);
                else if (obj instanceof SWBMethod)
                {
                    SWBMethod wbm = (SWBMethod) obj;
                    if (wbm.getObj() instanceof User)
                        out.print(wbm.invoke(user, topic, user, request,response));
                    else if (wbm.getObj() instanceof WebPage)
                        out.print(wbm.invoke(topic, topic, user, request,response));
                    else if (wbm.getObj() == HttpServletRequest.class)
                        out.print(wbm.invoke(request, topic, user, request,response));
                    else if (wbm.getObj() == HttpServletResponse.class)
                        out.print(wbm.invoke(response, topic, user, request,response));
                    else if (wbm.getObj() instanceof SWBResourceMgr)
                    {
                        HashMap args = (HashMap) wbm.getArguments(1);
                        if ("content".equals(wbm.getArguments(0)))   //es contenido
                        {
                            if (content != null)
                            {
                                out.println(content);
                                if (savelog && distparams!=null)
                                {
                                    resbuf.append("|");
                                    //System.out.println("distparams:"+distparams);
                                    //System.out.println("topic:"+topic);
                                    if(!topic.getWebSiteId().equals(distparams.getAccResourceTMID()))
                                        resbuf.append("0");
                                    resbuf.append(distparams.getAccResourceID());
                                }
                            } else
                            {
                                boolean first = true;

                                //cambio de topico en el contenido
                                String resTopic=(String)args.get("topic");
                                WebPage auxTopic=null;
                                if(resTopic!=null)
                                {
                                    auxTopic=topic.getWebSite().getWebPage(resTopic);
                                }

                                Iterator it;
                                if(auxTopic!=null)
                                    it = (Iterator) wbm.invoke((SWBResourceMgr) wbm.getObj(), user, auxTopic);
                                else
                                    it = (Iterator) wbm.invoke((SWBResourceMgr) wbm.getObj(), user, topic);
                                int con=-1;
                                while (it.hasNext())
                                {
                                    con++;
                                    SWBResource wbres = (SWBResource) it.next();
                                    //System.out.println("tpl id:"+wbres.getResourceBase().getId()+" prt:"+wbres.getResourceBase().getPriority()+wbres.getResourceBase().getRandPriority());

                                    String resTitle=(String)args.get("gettitle");
                                    if(resTitle!=null)
                                    {
                                        int rt=-1;
                                        try
                                        {
                                            rt=Integer.parseInt(resTitle);
                                        }catch(Exception e){log.error(e);}
                                        if(rt==con)out.print(wbres.getResourceBase().getTitle());
                                        continue;
                                    }

                                    String resDesc=(String)args.get("getdescription");
                                    if(resDesc!=null)
                                    {
                                        int rt=-1;
                                        try
                                        {
                                            rt=Integer.parseInt(resDesc);
                                        }catch(Exception e){log.error(e);}
                                        if(rt==con)out.print(wbres.getResourceBase().getDescription());
                                        continue;
                                    }
/*
                                    String resMUrl=(String)args.get("getmaximizedurl");
                                    if(resMUrl!=null)
                                    {
                                        int rt=-1;
                                        try
                                        {
                                            rt=Integer.parseInt(resMUrl);
                                        }catch(Exception e){AFUtils.log(e);}
                                        if(rt==con)
                                        {
                                            long rid=wbres.getResourceBase().getId();
                                            javax.servlet.http.HttpServletRequest req=null;
                                            if(distparams!=null)
                                            {
                                                req=distparams.getInternalRequest(request,rid);
                                            }else
                                            {
                                                req=new WBHttpServletRequestWrapper(request);
                                            }
                                            WBParamRequestImp resParams = new WBParamRequestImp(req,wbres.getResourceBase(),topic,user);
                                            out.print(resParams.getRenderUrl().setWindowState(WBResourceURL.WinState_MAXIMIZED));
                                        }
                                        continue;
                                    }
                                    String resDUrl=(String)args.get("getdirecturl");
                                    if(resDUrl!=null)
                                    {
                                        int rt=-1;
                                        try
                                        {
                                            rt=Integer.parseInt(resDUrl);
                                        }catch(Exception e){AFUtils.log(e);}
                                        if(rt==con)
                                        {
                                            long rid=wbres.getResourceBase().getId();
                                            javax.servlet.http.HttpServletRequest req=null;
                                            if(distparams!=null)
                                            {
                                                req=distparams.getInternalRequest(request,rid);
                                            }else
                                            {
                                                req=new WBHttpServletRequestWrapper(request);
                                            }
                                            WBParamRequestImp resParams = new WBParamRequestImp(req,wbres.getResourceBase(),topic,user);
                                            out.print(resParams.getRenderUrl().setCallMethod(WBResourceURL.Call_DIRECT));
                                        }
                                        continue;
                                    }
*/
                                    //System.out.println("tpl id:"+wbres.getResourceBase().getId()+" prt:"+wbres.getResourceBase().getIndex());
                                    //if (!(!first && wbres.getResourceBase().getIndex() <= 0))
                                    if(wbres.getResourceBase().getIndex()>0 || (first && wbres.getResourceBase().getIndex()==0 && !it.hasNext()))
                                    {
                                        String resCont=(String)args.get("getcontent");
                                        if(resCont!=null)
                                        {
                                            int rt=-1;
                                            try
                                            {
                                                rt=Integer.parseInt(resCont);
                                            }catch(Exception e){log.error(e);}
                                            if(rt!=con)continue;
                                        }

                                        //System.out.println("tpl ok");
                                        //out.print(SWBResourceMgr.getInstance().getResourceTraceMgr().getHtmlTraced(wbres, request, response, user, topic, args));
                                        String rid=wbres.getResourceBase().getId();
                                        //String rid=wbres.getResourceBase().getSId();
                                        String mdo=null;
                                        String wst=null;
                                        String act=null;
                                        WebPage vtopic=null;
                                        String extParams=null;
                                        if(distparams!=null)
                                        {
                                            vtopic=distparams.getVirtWebPage();
                                            HashMap resp=distparams.getResourceURI(rid);
                                            if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                                            {
                                                mdo=(String)resp.get(DistributorParams.URLP_MODE);
                                                wst=(String)resp.get(DistributorParams.URLP_WINSTATE);
                                                act=(String)resp.get(DistributorParams.URLP_ACTION);
                                            }
                                            extParams=distparams.getNotAccResourceURI(rid);
                                        }

                                        //System.out.println("rid:"+rid);
                                        //System.out.println("distparams.getAccResourceID():"+distparams.getAccResourceID());

                                        SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
                                        javax.servlet.http.HttpServletRequest req=null;
                                        if(distparams!=null)
                                        {
                                            req=distparams.getInternalRequest(request,rid);
                                        }else
                                        {
                                            req=new SWBHttpServletRequestWrapper(request);
                                        }

                                        SWBParamRequestImp resParams = new SWBParamRequestImp(req,wbres.getResourceBase(),topic,user);
                                        resParams.setArguments(args);
                                        resParams.setExtURIParams(extParams);
                                        resParams.setCallMethod(SWBResourceModes.Call_CONTENT);
                                        if(act!=null)resParams.setAction(act);
                                        if(mdo!=null)resParams.setMode(mdo);
                                        if(wst!=null)resParams.setWindowState(wst);
                                        if(vtopic!=null)
                                        {
                                            resParams.setVirtualTopic(vtopic);
                                        }

                                        SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res, resParams);
                                        out.print(res.toString());

//                                        byte arr[]=res.toByteArray();
//                                        String r=res.toString();
//                                        int ri=r.indexOf("Jei ");
//                                        String f=r.substring(ri,ri+10);
//                                        System.out.println("res:"+f);
//                                        for(int x=0;x<f.length();x++)
//                                        {
//                                            System.out.println(" "+(int)f.charAt(x)+" "+arr[ri+x]);
//                                        }
//                                        //System.out.println("res:"+new String(res.toByteArray(),"UTF-8"));

                                        String intraBR=(String)args.get("intrabr");
                                        if(it.hasNext() && (intraBR==null || intraBR.equalsIgnoreCase("true")))
                                        {
                                            out.println("<br/>");
                                        }
                                        if(savelog)
                                        {
                                            resbuf.append("|");
                                            if(!wbres.getResourceBase().getWebSiteId().equals(topic.getWebSiteId()))
                                                resbuf.append("0");
                                            //resbuf.append(wbres.getResourceBase().getSId());
                                            resbuf.append(wbres.getResourceBase().getId());
                                        }
                                        first = false;
                                    }
                                }
                            }
                        } else                                        //es estrategia
                        {
                            String id = "" + args.get("type") + args.get("stype");       //id para guardar recurso en anteriores
                            if (antresrc.get(id) == null)
                            {
                                //System.out.println("id:"+id+":"+0);
                                Iterator it = (Iterator) wbm.invoke((SWBResourceMgr) wbm.getObj(), user, topic);
                                if (it.hasNext())
                                {
                                    SWBResource wbres = (SWBResource) it.next();
                                    //System.out.println("rec:"+wbres.getResourceBase().getId()+" typemap="+wbres.getResourceBase().getTopicMapId());

                                    //String rid=wbres.getResourceBase().getSId();
                                    String rid=wbres.getResourceBase().getId();
                                    String mdo=null;
                                    String wst=null;
                                    String act=null;
                                    WebPage vtopic=null;
                                    String extParams=null;
                                    if(distparams!=null)
                                    {
                                        vtopic=distparams.getVirtWebPage();
                                        HashMap resp=distparams.getResourceURI(rid);
                                        if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                                        {
                                            mdo=(String)resp.get(DistributorParams.URLP_MODE);
                                            wst=(String)resp.get(DistributorParams.URLP_WINSTATE);
                                            act=(String)resp.get(DistributorParams.URLP_ACTION);
                                        }
                                        extParams=distparams.getNotAccResourceURI(rid);
                                    }

                                    //out.print(SWBResourceMgr.getInstance().getResourceTraceMgr().getHtmlTraced(wbres, request, response, user, topic, args));
                                    SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
                                    javax.servlet.http.HttpServletRequest req=null;
                                    if(distparams!=null)
                                    {
                                        req=distparams.getInternalRequest(request,rid);
                                    }else
                                    {
                                        req=new SWBHttpServletRequestWrapper(request);
                                    }

                                    SWBParamRequestImp resParams = new SWBParamRequestImp(request,wbres.getResourceBase(),topic,user);
                                    resParams.setArguments(args);
                                    resParams.setExtURIParams(extParams);
                                    resParams.setCallMethod(SWBResourceModes.Call_STRATEGY);
                                    if(act!=null)resParams.setAction(act);
                                    //resParams.setCallMethod(mto);
                                    if(mdo!=null)resParams.setMode(mdo);
                                    if(wst!=null)resParams.setWindowState(wst);
                                    if(vtopic!=null)
                                    {
                                        resParams.setVirtualTopic(vtopic);
                                    }

                                    SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res, resParams);
                                    out.print(res.toString());

                                    //System.out.println("Salida:"+wbres.getResourceBase().getId()+":"+res.toString());

                                    if (savelog)
                                    {
                                        resbuf.append("|");
                                        if(!wbres.getResourceBase().getWebSiteId().equals(topic.getWebSiteId()))
                                            resbuf.append("0");
                                        //resbuf.append(wbres.getResourceBase().getSId());
                                        resbuf.append(wbres.getResourceBase().getId());
                                    }
                                }
                                if (it.hasNext()) antresrc.put(id, it);
                            } else
                            {
                                //System.out.println("id:"+id+":n");
                                Iterator it = (Iterator) antresrc.get(id);
                                SWBResource wbres = (SWBResource) it.next();

                                //String rid=wbres.getResourceBase().getSId();
                                String rid=wbres.getResourceBase().getId();
                                String mdo=null;
                                String wst=null;
                                String act=null;
                                WebPage vtopic=null;
                                String extParams=null;
                                if(distparams!=null)
                                {
                                    vtopic=distparams.getVirtWebPage();
                                    HashMap resp=distparams.getResourceURI(rid);
                                    if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                                    {
                                        mdo=(String)resp.get("_mod");
                                        wst=(String)resp.get("_wst");
                                        act=(String)resp.get("_act");
                                    }
                                    extParams=distparams.getNotAccResourceURI(rid);
                                }
                                //out.print(SWBResourceMgr.getInstance().getResourceTraceMgr().getHtmlTraced(wbres, request, response, user, topic, args));
                                SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
                                javax.servlet.http.HttpServletRequest req=null;
                                if(distparams!=null)
                                {
                                    req=distparams.getInternalRequest(request,rid);
                                }else
                                {
                                    req=new SWBHttpServletRequestWrapper(request);
                                }

                                SWBParamRequestImp resParams = new SWBParamRequestImp(request,wbres.getResourceBase(),topic,user);
                                resParams.setArguments(args);
                                resParams.setExtURIParams(extParams);
                                resParams.setCallMethod(SWBResourceModes.Call_STRATEGY);

                                if(act!=null)resParams.setAction(act);
                                //resParams.setCallMethod(mto);
                                if(mdo!=null)resParams.setMode(mdo);
                                if(wst!=null)resParams.setWindowState(wst);
                                if(vtopic!=null)
                                {
                                    resParams.setVirtualTopic(vtopic);
                                }

                                SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res, resParams);
                                out.print(res.toString());

                                if (!it.hasNext()) antresrc.remove(id);
                                if (savelog)
                                {
                                    resbuf.append("|");
                                    if(!wbres.getResourceBase().getWebSiteId().equals(topic.getWebSiteId()))
                                        resbuf.append("0");
                                    //resbuf.append(wbres.getResourceBase().getSId());
                                    resbuf.append(wbres.getResourceBase().getId());
                                }
                            }
                        }
                    }else if (wbm.getObj() instanceof String)
                    {
                        String aux=(String)wbm.getObj();
                        if(aux.equals("include"))
                        {
                            HashMap args = (HashMap) wbm.getArguments(1);
                            out.print(include((String)wbm.getArguments(0),request,response,user,topic,args));
                        }
                    } else
                    {
                        out.print(wbm.invoke(topic, user, request,response));
                    }
                }else if (obj instanceof SWBIFMethod)
                {
                    ArrayList subparts=((SWBIFMethod)obj).eval(user, topic, this);
                    //System.out.println("subparts:"+subparts);
                    build(request, response, out, user, topic, savelog, content, distparams, subparts, resbuf);
                }
            }
        } catch (Exception e)
        {
            log.error("Error Template Build...",e);
        }
    }

    
    /**
     * Builds the contents.
     * 
     * @param request the request
     * @param response the response
     * @param out the out
     * @param distparams the distparams
     * @param savelog the savelog
     * @param content the content
     */
    public static void buildContents(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, PrintWriter out, DistributorParams distparams, boolean savelog, String content) 
    {
        StringBuffer logbuf = null;
        StringBuffer resbuf = null;
        
        WebPage topic=distparams.getWebPage();
        User user=distparams.getUser();
        
        long tini = 0;
        if (savelog)
        {
            tini = System.currentTimeMillis();
            resbuf = new StringBuffer(300);
            logbuf = new StringBuffer(300);
            logbuf.append("log|");
            logbuf.append(request.getRemoteAddr());
            logbuf.append("|");
            logbuf.append(SWBPortal.getMessageCenter().getAddress());
            //logbuf.append("_");
            logbuf.append("|");
            String sess=request.getSession().getId();
            if(sess!=null)
            {
                int p=sess.length()-10;
                if(p>-1)sess=sess.substring(p);
            }else sess="_";
            logbuf.append(sess);
            logbuf.append("|");
            logbuf.append(topic.getWebSiteId());
            logbuf.append("|");
            //logbuf.append(topic.getSId());
            logbuf.append(topic.getId());
            logbuf.append("|");
            logbuf.append(user.getUserRepository().getId());
            logbuf.append("|");
            if (user.getLogin() != null)
                logbuf.append(user.getLogin());
            else
                logbuf.append("_");
            logbuf.append("|");
            logbuf.append(user.getSemanticObject().getSemanticClass().getClassId());
            logbuf.append("|");
            logbuf.append(user.getDevice().getId());
            logbuf.append("|");
            if (user.getLanguage() != null && user.getLanguage().length() > 0)
                logbuf.append(user.getLanguage());
            else
                logbuf.append("_");
        }
        
        try
        {
            if (content != null)
            {
                out.println(content);
                if (savelog)
                {
                    resbuf.append("|");
                    if(!distparams.getAccResourceTMID().equals(topic.getWebSiteId()))
                        resbuf.append("0");
                    resbuf.append(distparams.getAccResourceID());
                }
            }else
            {
                boolean first = true;
                Iterator it = SWBPortal.getResourceMgr().getContents(distparams.getUser(), distparams.getWebPage(), null,null);
                while (it.hasNext())
                {
                    SWBResource wbres = (SWBResource) it.next();
                    //System.out.println("tpl id:"+wbres.getResourceBase().getId()+" prt:"+wbres.getResourceBase().getIndex());
                    if(wbres.getResourceBase().getIndex()>0 || (first && wbres.getResourceBase().getIndex()==0 && !it.hasNext()))
                    //if (!(!first && wbres.getResourceBase().getIndex() <= 0))
                    {
                        //System.out.println("tpl ok");
                        //out.print(SWBResourceMgr.getInstance().getResourceTraceMgr().getHtmlTraced(wbres, request, response, user, topic, args));
                        //String rid=wbres.getResourceBase().getSId();
                        String rid=wbres.getResourceBase().getId();
                        String mdo=null;
                        String wst=null;
                        String act=null;
                        WebPage vtopic=null;
                        String extParams=null;
                        if(distparams!=null)
                        {
                            vtopic=distparams.getVirtWebPage();
                            HashMap resp=distparams.getResourceURI(rid);
                            if(resp!=null && distparams.getResourceTMID(rid).equals(wbres.getResourceBase().getWebSiteId()))
                            {
                                mdo=(String)resp.get(DistributorParams.URLP_MODE);
                                wst=(String)resp.get(DistributorParams.URLP_WINSTATE);
                                act=(String)resp.get(DistributorParams.URLP_ACTION);
                            }
                            extParams=distparams.getNotAccResourceURI(rid);
                        }

                        //System.out.println("rid:"+rid);
                        //System.out.println("distparams.getAccResourceID():"+distparams.getAccResourceID());

                        SWBHttpServletResponseWrapper res=new SWBHttpServletResponseWrapper(response);
                        javax.servlet.http.HttpServletRequest req=null;
                        if(distparams!=null)
                        {
                            req=distparams.getInternalRequest(request,rid);
                        }else
                        {
                            req=new SWBHttpServletRequestWrapper(request);
                        }
                        
                        SWBParamRequestImp resParams = new SWBParamRequestImp(req,wbres.getResourceBase(),distparams.getWebPage(),distparams.getUser());
                        resParams.setArguments(new HashMap());
                        resParams.setExtURIParams(extParams);
                        resParams.setCallMethod(SWBResourceModes.Call_CONTENT);
                        if(act!=null)resParams.setAction(act);
                        if(mdo!=null)resParams.setMode(mdo);
                        if(wst!=null)resParams.setWindowState(wst);                                        
                        if(vtopic!=null)
                        {
                            resParams.setVirtualTopic(vtopic);
                        }

                        SWBPortal.getResourceMgr().getResourceTraceMgr().renderTraced(wbres, req, res, resParams);
                        out.print(res.toString());

                        //String intraBR=(String)args.get("intrabr");
                        //if(it.hasNext() && (intraBR==null || intraBR.equalsIgnoreCase("true")))
                        if(it.hasNext())
                        {
                            out.println("<br/>");
                        }
                        if (savelog)
                        {
                            resbuf.append("|");
                            if(!wbres.getResourceBase().getWebSiteId().equals(topic.getWebSiteId()))
                                resbuf.append("0");
                            //resbuf.append(wbres.getResourceBase().getSId());
                            resbuf.append(wbres.getResourceBase().getId());
                        }
                        first = false;
                    }
                }
            }
        }catch (Exception e)
        {
            log.error("Error Template Build...",e);
        }
        
        if (savelog)
        {
//            long tfin = System.currentTimeMillis() - tini;            
//            WBMessageCenter.getInstance().sendMessage(logbuf.toString()+"|"+tfin+resbuf.toString());
        }        
    }
//
//    public String getPreview()
//    {
//        StringBuffer out = new StringBuffer();
//        try
//        {
//            Iterator en = parts.iterator();
//            while (en.hasNext())
//            {
//                Object obj = en.next();
//                if (obj instanceof String)
//                    out.append(obj);
//                else if (obj instanceof WBMethod)
//                {
//                    out.append("<table bgcolor=#bbbbbb><tr><td>\n");
//                    out.append("<font color=red fase=arial size=2>\n");
//                    WBMethod wbm = (WBMethod) obj;
//                    //if(wbm.getObj() instanceof User)out.append("<font color=red fase=arial size=2>User</font>");//out.append(wbm.invoke(user));
//                    //else if(wbm.getObj() instanceof WebPage)out.append("<font color=red fase=arial size=2>WebPage</font>");//out.append(wbm.invoke(topic));
//                    if (wbm.getObj() instanceof SWBResourceMgr)
//                    {
//                        HashMap args = (HashMap) wbm.getArguments(1);
//                        if (wbm.getArguments(0) instanceof String)   //es contenido
//                        {
//                            out.append("Contenido:");
//                        } else
//                        {
//                            out.append("Recurso:");
//                        }
//                        Iterator it = args.keySet().iterator();
//                        while (it.hasNext())
//                        {
//                            Object aux = it.next();
//                            if (aux.toString().equalsIgnoreCase("stype"))
//                            {
//                                //TODO: revisar objetos de otros sitios
//                                String stype = DBCatalogs.getInstance().getSubType(this.getTopicMapId(),Integer.parseInt(args.get(aux).toString())).getTitle();
//                                out.append("<BR>&nbsp;&nbsp;&nbsp;" + aux + "=\"" + stype + "\"\n");
//                            } else
//                            {
//                                out.append("<BR>&nbsp;&nbsp;&nbsp;" + aux + "=\"" + args.get(aux) + "\"\n");
//                            }
//                        }
//                    } else
//                    {
//                        //out.append(" "+wbm.getObj().getClass().getName());
//                        String mto = wbm.getMethod().toString();
//                        int k = mto.lastIndexOf('(');
//                        int i = mto.lastIndexOf('.', k);
//                        int j = mto.lastIndexOf('.', i - 1);
//                        String method = mto.substring(i + 1);
//                        String name = mto.substring(j + 1, i);
//                        out.append("Objeto:" + "\n");
//                        out.append("<BR>&nbsp;&nbsp;&nbsp;" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "usrmsg_Template_getPreview_class") + ":" + name + "\n");
//                        out.append("<BR>&nbsp;&nbsp;&nbsp;" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "usrmsg_Template_getPreview_method") + ":" + method + "\n");
//                        if (mto.length() - k > 2)
//                        {
//                            if (wbm.getArguments(0) instanceof HashMap)
//                            {
//                                out.append("<BR>&nbsp;&nbsp;&nbsp;" + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "usrmsg_Template_getPreview_args") + ":\n");
//                                HashMap args2 = (HashMap) wbm.getArguments(0);
//                                Iterator it = (args2).keySet().iterator();
//                                while (it.hasNext())
//                                {
//                                    Object aux = it.next();
//                                    out.append("<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + aux + "=\"" + args2.get(aux) + "\"\n");
//                                }
//                            }
//                        }
//                    }
//                    out.append("</font>\n");
//                    out.append("</td</tr></table>\n");
//                }
//            }
//        } catch (Exception e)
//        {
//            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Template_getPreview"), true);
//        }
//        return out.toString();
//    }
//
//    /**
//     * @return  */
//    public java.util.ArrayList getParts()
//    {
//        return parts;
//    }

//    /** Verifica si el usuario tiene permisos de acceso al recurso.
//     * @return boolean
//     */
//    public boolean haveAccess(User user)
//    {
//        return checkRoles(user) && checkRules(user);
//    }    
//    
//    public boolean checkRules(User user)
//    {
//        boolean passrule = true;
//        boolean criterial_or=true;
//        
//        String cnf_rule=this.getAttribute("CNF_WBRule","");
//        if(cnf_rule.equals(WebPage.CONFIG_DATA_AND_CRITERIAL))criterial_or=false;
//        
//        //System.out.println("haveAccess:"+getId()+" crit:"+criterial_or);
//        Iterator ru = this.getRules();
//        while (ru.hasNext())
//        {
//            int nrule = ((Integer) ru.next()).intValue();
//            //System.out.println("rule:"+nrule);
//            if (RuleMgr.getInstance().eval(user, nrule, this.getTopicMapId()))
//            {
//                passrule = true;
//                if(criterial_or)break;
//            } else
//            {
//                passrule = false;
//                if(!criterial_or)break;
//            }
//        }        
//        //System.out.println("haveAccess ret:"+passrule);
//        return passrule;
//    }
//    
//    public boolean checkRoles(User user)
//    {
//        boolean pass = true;
//        boolean criterial_or=true;
//        
//        String cnf_rule=this.getAttribute("CNF_WBRole","");
//        if(cnf_rule.equals(WebPage.CONFIG_DATA_AND_CRITERIAL))criterial_or=false;
//        
//        //System.out.println("haveAccess:"+getId()+" crit:"+criterial_or);
//        Iterator ru = this.getRoles();
//        while (ru.hasNext())
//        {
//            int nrole = ((Integer) ru.next()).intValue();
//            //System.out.println("role:"+nrole);
//            if (user.haveRole(nrole))
//            {
//                pass = true;
//                if(criterial_or)break;
//            } else
//            {
//                pass = false;
//                if(!criterial_or)break;
//            }
//        }        
//        //System.out.println("haveAccess ret:"+pass);
//        return pass;
//    }  
    
    
    /**
     * Regresa ruta web de la version actual de la paltilla.
     * @return Value of property actPath.
     */
    public java.lang.String getActualPath()
    {
        return actPath;
    }    

    /**
     * Regresa ruta fisica de la version actual de la paltilla.
     * @return Value of property actPath.
     */
    public java.lang.String getActualWorkPath()
    {
        return actWorkPath;
    }

    /**
     * Regresa ruta fisica de la de la paltilla.
     * @return Value of property actPath.
     */
    @Override
    public java.lang.String getWorkPath()
    {
        return workPath;
    }


    /**
     * Gets the file name.
     * 
     * @param version the version
     * @return the file name
     */
    public String getFileName(VersionInfo version)
    {
        return version.getVersionFile();
    }
    
    //UTILIDADES DE TEMPLATES
    /**
     * Sets the headers.
     * 
     * @param map the map
     * @return the string
     */
    public String setHeaders(HashMap map)
    {
        try
        {
            HttpServletResponse response=(HttpServletResponse)map.get("response");
            //System.out.println(response);
            if(response!=null && response instanceof HttpServletResponse)
            {
                Iterator<String> it=map.keySet().iterator();
                while(it.hasNext())
                {
                    String key=it.next();
                    Object val=map.get(key);
                    if(val instanceof String && !key.equals("method"))
                    {
                        String value=(String)val;
                        if(key.equalsIgnoreCase("content-Type"))
                        {
                            contentType=value;
                        }else
                        {
                            try
                            {
                                //System.out.println(AFUtils.toUpperCaseFL(key)+":"+value);
                                response.setHeader(SWBUtils.TEXT.toUpperCaseFL(key),value);
                            }catch(Exception e){log.error(e);}
                        }
                    }
                } 
            }
        }catch(Exception e){log.error(e);}
        return "";
    }
    
    /**
     * Gets the request parameter.
     * 
     * @param map the map
     * @return the request parameter
     */
    public String getRequestParameter(HashMap map)
    {
        try
        {
            HttpServletRequest request=(HttpServletRequest)map.get("request");
            //System.out.println(response);
            if(request!=null && request instanceof HttpServletRequest)
            {
                String name=(String)map.get("name");
                String defvalue=(String)map.get("defaultvalue");
                String val=request.getParameter(name);
                if(val==null)return defvalue;
                return val;
            }
        }catch(Exception e){log.error(e);}
        return "";        
    }

    /**
     * Write text.
     * 
     * @param map the map
     * @return the string
     */
    public String writeText(HashMap map)
    {
        try
        {
            String ret=(String)map.get("text");
            if(ret==null)ret="";
            return ret;
        }catch(Exception e){log.error(e);}
        return "";        
    }    
    
    /**
     * Write gt.
     * 
     * @return the string
     */
    public String writeGT()
    {
        return "<";        
    }        
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.Template#reload()
     */
    /**
     * Reload.
     */
    @Override
    public void reload()
    {
        SWBPortal.getTemplateMgr().reloadTemplate(this);
    }

    /**
     * Gets the content type.
     * 
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     * 
     * @param contentType the new content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDate(HashMap map)
    {
        String ret=null;
        try
        {
            String lang=(String)map.get("language");
            String format=(String)map.get("format");
            if(format==null)
            {
                ret=SWBUtils.TEXT.getStrDate(new Date(), lang);
            }else
            {
                ret=SWBUtils.TEXT.getStrDate(new Date(), lang, format);
            }
            return ret;
        }catch(Exception e){log.error(e);}
        return "";
    }
    
}
