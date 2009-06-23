/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.admin.admresources;


import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBContainerFE;
import org.w3c.dom.*;


/**
 * Objeto que administra un elemento de tipo Map en una forma de html.
 * <p>
 * Object that administers an html Map element in a form
 * @author  Jorge Alberto Jim�nez
 */

public class MapFE extends WBContainerFE
{
    private static Logger log = SWBUtils.getLogger(MapFE.class);
    
    private String accesskey=null;
    private String name=null;
    private String xmltag=null;
    protected Node tag=null;

   
    /** Creates a new instance of AppletFE */
    public MapFE(String name) {
        this.name=name;
    }
    
    /** Creates a new instwance with the default parameters */
    public MapFE(Node tag){
        this.tag=tag;
        setAttributes();
    }
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
   
    public void setName(String name){
        this.name=name;
    }
    
    /**determines de xml tag name the form element will be added in a resource.*/
    public void setXmlTag(String xmltag){
        this.xmltag=xmltag;
    }
    
    //gets
    public String getAccessKey(){
        return this.accesskey;
    }
    
    public String getName(){
        return this.name;
    }
    
     /**determines de xml tag name the form element will be added in a resource.*/
    public String getXmlTag(){
        return this.xmltag;
    }

    public void add(Object obj){
       super.add(obj);
       setMapFEName(obj);
    }
    
    private void setMapFEName(Object obj){
       if(obj instanceof AreaMapFE){
           AreaMapFE objareafe=(AreaMapFE)obj;
           objareafe.setMapFEObj(this);
         }
    }
   
    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */  
    public String getHtml(){
        String xml="";
        try 
        {
            Document dom=SWBUtils.XML.getNewDocument(); 
            if(dom!=null)
            {
                Element child=dom.createElement("map");
                if(accesskey!=null) child.setAttribute("accesskey",accesskey);
                if(id!=null) child.setAttribute("id",id);
                if(name!=null) {
                    child.setAttribute("name",name);
                    if(id==null) child.setAttribute("id",name);
                }
                if(moreattr!=null) child.setAttribute("moreattr",moreattr);
                
                Document area=SWBUtils.XML.xmlToDom("<map>"+show()+"</map>");
                NodeList ndl = area.getElementsByTagName("area");
                for(int i=0;  i < ndl.getLength();i++)
                {
                    child.appendChild(dom.importNode(ndl.item(i), true));
                }
                dom.appendChild(child);
                
                xml=SWBUtils.XML.domToXml(dom, "ISO-8859-1", true);
                if(xml!=null && !"".equals(xml.trim())) xml=xml.substring(xml.indexOf("<map"));
                else xml="";
            }
        } 
        catch(Exception e) { log.error(e); }
        return xml;
    }
    
    
    /**
    * Set attributes to class according with the xml tag element
    */
    public void setAttributes(){
        if(tag!=null){
            NamedNodeMap nnodemap=tag.getAttributes();
            if(nnodemap.getLength()>0){
                for(int i=0;i<nnodemap.getLength();i++){
                    String attrName=nnodemap.item(i).getNodeName();
                    String attrValue=nnodemap.item(i).getNodeValue();
                    if(attrValue!=null && !attrValue.equals("")){
                        //defecto
                        if(attrName.equalsIgnoreCase("name")) name=attrValue;

                        else if(attrName.equalsIgnoreCase("style")) style=attrValue;
                        else if(attrName.equalsIgnoreCase("class")) styleclass=attrValue;
                        else if(attrName.equalsIgnoreCase("moreattr")) moreattr=attrValue;
                        //propios
                        else if(attrName.equalsIgnoreCase("accesskey")) accesskey=attrValue;
                        else if(attrName.equalsIgnoreCase("name")) name=attrValue;
                    }
                }
            }
        }
    }
}
