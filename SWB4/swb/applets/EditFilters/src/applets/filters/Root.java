/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


/*
 * Root.java
 *
 * Created on 23 de noviembre de 2004, 12:55 PM
 */

package applets.filters;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
/**
 * Clase que representa un nodo simple, normalmente mostrado como ra?z dentro del
 * ?rbol de secciones.
 * @author Victor Lorenzana
 */
public class Root extends DefaultMutableTreeNode implements Node{
    
    /** Creates a new instance of Root */
    boolean selected=false;
    JLabel label=new JLabel();
    public Root(String text,ImageIcon img) {
        label.setOpaque(true);
        this.label.setBackground(Color.WHITE);
        label.setText(text);
        label.setIcon(img);
    }
    
    public java.awt.Component getComponent() {
        return label;
    }
    
    public javax.swing.JLabel getJLabel() {
        return label;
    }
    public JLabel getLabel()
    {
        return this.label;
    }
    public void setSelected(boolean selected) {
       /* if(selected)
        {
            this.label.setBackground(new Color(204,204,204));
        }
        else
        {
            this.label.setBackground(Color.WHITE);
        }*/
        this.selected=selected;
    }
    
}
