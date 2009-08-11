/**  
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
**/ 
 
/*
 * XFTextArea.java
 *
 * Created on 1 de julio de 2008, 06:58 PM
 */

package org.semanticwb.xforms.ui;

import org.semanticwb.xforms.lib.XformsBaseImp;
import org.semanticwb.xforms.drop.RDFElement;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;

/**
 *
 * @author  jorge.jimenez
 */
public class XFTextArea extends XformsBaseImp 
{
    
    private static Logger log=SWBUtils.getLogger(XFTextArea.class);
    private String accesskey=null;
    private int cols=-1;
    private int rows=-1;
    private boolean isdisabled=false;
    private boolean isreadonly=false;
    private int width=-1;
    private int height=-1;
    private String wrap=null;
    private String value=null;
    protected String alert=null;
    protected String constraint=null;
    protected boolean incremental=false;
    protected String mediatype=null;
    protected RDFElement rdfElement=null;
    
    public XFTextArea(RDFElement rdfElement){
        this.rdfElement=rdfElement;
        setRDFAttributes();
    }
    
    // Sets
    
    public void setAccessKey(String accesskey){
        this.accesskey=accesskey;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    
    public void setHeight(int height){
        this.height=height;
    }
    
    public void setValue(String value){
        this.value=value;
    }
    
    public void setDisabled(boolean isdisabled){
        this.isdisabled=isdisabled;
    }
    
    public void setReadOnly(boolean isreadonly){
        this.isreadonly=isreadonly;
    }
    
    public void setConstraint(String constraint) {
        this.constraint=constraint;
    }
    
    public void setIncremental(boolean incremental) {
        this.incremental=incremental;
    }
    
    public void setCols(int cols) {
        this.cols=cols;
    }
    
    public void setRows(int rows) {
        this.rows=rows;
    }
    
    public void setWrap(String wrap) {
        this.wrap=wrap;
    }
    
    // Gets
    
    public String getAccessKey(){
        return accesskey;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public String getValue(){
        return value;
    }
    
    private boolean getDisabled(){
        return isdisabled;
    }
    
    public boolean getReadOnly(){
        return isreadonly;
    }
    
    public String getConstraint() {
        return constraint;
    }
    
    public boolean isIncremental() {
        return incremental;
    }
    
    public int getCols() {
        return cols;
    }
    
    public int getRows() {
        return rows;
    }
    
    public String getWrap() {
        return wrap;
    }
    
    public void setRDFAttributes(){
        if(rdfElement.getId()!=null) {
            id=rdfElement.getId();
        }
        if(rdfElement.getLabel()!=null) {
            label=rdfElement.getLabel();
        }
        isrequired=rdfElement.isRequired();
        if(rdfElement.getSType()!=null) {
            subType=rdfElement.getSType();
        }
        if(rdfElement.getConstraint()!=null) {
            constraint=rdfElement.getConstraint();
        }
        if(rdfElement.getHelp()!=null) {
            help=rdfElement.getHelp();
        }
        incremental=rdfElement.isIncremental();
        if(rdfElement.getCols()>0) {
            cols=rdfElement.getCols();
        }
        if(rdfElement.getRows()>0) {
            rows=rdfElement.getRows();
        }
        if(rdfElement.getWrap()!=null) {
            wrap=rdfElement.getWrap();
        }
        if(rdfElement.getMediatype()!=null) {
            mediatype=rdfElement.getMediatype();
        }
        if(rdfElement.getValue()!=null) {
            value=rdfElement.getValue();
        }
       
        
        if(rdfElement.getAlert()!=null) {
            alert=rdfElement.getAlert();
        }
        if(rdfElement.getHint()!=null) {
            hint=rdfElement.getHint();
        }
    }
    
    @Override
    public String getXmlBind() {
        StringBuffer strbXml=new StringBuffer();
        strbXml.append("<bind id=\"bind_"+id+"\" nodeset=\""+id+"\"");
        if(isrequired) {
            strbXml.append(" required=\"true()\" ");
        }
        if(constraint!=null) {
            strbXml.append(" constraint=\""+constraint+"\" ");
        }
        if(subType!=null) {
            strbXml.append(" type=\""+subType+"\" ");
        }
        strbXml.append("/>");
        return strbXml.toString();
    }
    
    @Override
    public String getXml() {
        StringBuffer strbXml=new StringBuffer();
        try {
            strbXml.append("<textarea id=\""+id+"\" bind=\"bind_"+id+"\"");
            
            if(cols>0)
            {
                strbXml.append(" rows=\""+rows+"\"");
            }
            
            if(rows>0)
            {
                strbXml.append(" cols=\""+cols+"\"");
            } 
            
            if(wrap!=null)
            {
                strbXml.append(" wrap=\""+wrap+"\"");
            } 
            
            if(incremental)
            {
                strbXml.append(" incremental=\""+incremental+"\"");
            }
            
            if(mediatype!=null)
            {
                strbXml.append(" mediatype=\""+mediatype+"\"");
            }
            
            strbXml.append(">");
                        
            if(label!=null) {
                strbXml.append("<label>");
                strbXml.append(label.trim());
                strbXml.append("</label>");
            }
            if(alert!=null) {
                strbXml.append("<alert>");
                strbXml.append(alert.trim());
                strbXml.append("</alert>");
            }
            if(hint!=null) {
                strbXml.append("<hint>");
                strbXml.append(hint.trim());
                strbXml.append("</hint>");
            }
            strbXml.append("</textarea>");
        }
        catch(Exception e) {log.error(e); }
        return strbXml.toString();
    }
    
    @Override
    public void setXml(String xml) {
        this.xml=xml;
    }
}
