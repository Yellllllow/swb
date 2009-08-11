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
 * SelectNode.java
 *
 * Created on 3 de noviembre de 2004, 04:27 PM
 */

package applets.workflowadmin;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Clase que permite denir un nodo que puede ser seleccionado.
 * @author Victor Lorenzana
 */
public class SelectNode 
   extends DefaultMutableTreeNode { 
      ArrayList listeners=new ArrayList();
      boolean selected=false;
      Role role;
      JCheckBox c=new JCheckBox();
      public SelectNode(Role role) {
         super(role.getName());                  
         selected = false;
         c.setText(role.getName());         
         c.setBackground(Color.WHITE);
         this.role=role;
         
      }
      public String getText()  {
         return role.getName();
      }
      //can set and get the checked state
      public JCheckBox getCheckBox()
      {
          c.setSelected(selected);
          return c;
      }
      public void setCheckBox(JCheckBox c)
      {          
          this.c=c;
      }
      public void setChecked(boolean b) {
         selected=b;
         c.setSelected(b);                         
         c.updateUI();         
      }
      public boolean getChecked() {
          return selected;
      }  
      public Role getRole()
      {
          return role;
      }
}

