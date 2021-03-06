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


package applets.mapsadm;



/*
 * WBTopicMap.java
 *
 * Created on 8 de agosto de 2002, 18:07
 */

import applets.commons.*;

/**
 *
 * @author  Administrador
 * @version 
 */
public class WBTopicMap {

    WBTreeNode node;
    private String id;
    private String title;
    private String home;
    private String lang="IDM_WBes";
    private boolean active=false;
    private boolean borrado=false;
    
    /** Creates new WBTopicMap */
    public WBTopicMap(WBTreeNode node) {
        this.node=node;
        init();
    }
    
    private void init()
    {
        for(int x=0;x<node.getNodesSize();x++)
        {
            String name=node.getNode(x).getName();
            if(name.equals("id"))
            {
                id=node.getNode(x).getFirstNode().getText();
            }
            else if(name.equals("title"))
            {
                title=node.getNode(x).getFirstNode().getText();
            }
            else if(name.equals("home"))
            {
                home=node.getNode(x).getFirstNode().getText();
            }
            else if(name.equals("language"))
            {
                lang=node.getNode(x).getFirstNode().getText();
            }
            else if(name.equals("active"))
            {
                if(node.getNode(x).getFirstNode().getText().equals("1"))
                active=true;
            }
        }
    }
    
    public String toString()
    {
        return title;
    }

    /** Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId() {
        return id;
    }
    
    /** Getter for property title.
     * @return Value of property title.
     */
    public java.lang.String getTitle() {
        return title;
    }
    
    /** Getter for property home.
     * @return Value of property home.
     */
    public java.lang.String getHome() {
        return home;
    }
    
    /** Getter for property lang.
     * @return Value of property lang.
     */
    public java.lang.String getLang() {
        return lang;
    }
    
    /** Getter for property active.
     * @return Value of property active.
     */
    public boolean isActive()
    {
        return active;
    }    
  
    /** Setter for property active.
     * @param active New value of property active.
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }    
   
    /** Getter for property borrado.
     * @return Value of property borrado.
     */
    public boolean isBorrado()
    {
        return borrado;
    }
    
    /** Setter for property borrado.
     * @param borrado New value of property borrado.
     */
    public void setBorrado(boolean borrado)
    {
        this.borrado = borrado;
    }
    
}
