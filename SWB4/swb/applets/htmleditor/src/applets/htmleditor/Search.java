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
 * Search.java
 *
 * Created on 10 de enero de 2005, 04:22 PM
 */

package applets.htmleditor;

import java.util.Locale;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class Search extends javax.swing.JDialog
{
    
    int ret=0;
    static boolean mcase=false;
    static boolean mwhole=false;
    
    Locale locale=new Locale("es");       
    
    Object editor=null;
    
    /** Creates new form Search */
    public Search(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        //setSize(300,120);
        setLocation(200,200);        
        jTextField1.setText("");
        setTitle("Find");
        //setFocusableWindowState(false);
        setFocusable(false);
        jCheckBox1.setSelected(mcase);
        jCheckBox2.setSelected(mwhole);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        butFind = new javax.swing.JButton();
        butClose = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Webbuilder");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("findwhat"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jLabel1, gridBagConstraints);

        jTextField1.setText("jTextField1");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                jTextField1KeyTyped(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(jTextField1, gridBagConstraints);

        butFind.setFont(new java.awt.Font("Dialog", 0, 12));
        butFind.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("find"));
        butFind.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butFindActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butFind, gridBagConstraints);

        butClose.setFont(new java.awt.Font("Dialog", 0, 12));
        butClose.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("close"));
        butClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                butCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 5);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        getContentPane().add(butClose, gridBagConstraints);

        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 11));
        jCheckBox1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("matchCase"));
        jCheckBox1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBox1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jCheckBox1, gridBagConstraints);

        jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 11));
        jCheckBox2.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/Replace",locale).getString("WholeWords"));
        jCheckBox2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jCheckBox2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBox2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 5, 0);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(jCheckBox2, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextField1KeyTyped
    {//GEN-HEADEREND:event_jTextField1KeyTyped
        // Add your handling code here:
        if(evt.getKeyChar()=='\n')
        {
            submit();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBox2ActionPerformed
    {//GEN-HEADEREND:event_jCheckBox2ActionPerformed
        // Add your handling code here:
        mwhole=jCheckBox2.isSelected();        
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBox1ActionPerformed
    {//GEN-HEADEREND:event_jCheckBox1ActionPerformed
        // Add your handling code here:
        mcase=jCheckBox1.isSelected();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void butFindActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFindActionPerformed
    {//GEN-HEADEREND:event_butFindActionPerformed
        // Add your handling code here:
        submit();
    }//GEN-LAST:event_butFindActionPerformed

    private void submit()
    {
        // Add your handling code here:
        if(editor instanceof TemplateEditor)
        {
            ((TemplateEditor)editor).findStr(getText(),mcase,mwhole);
        }
        ret=0;
        dispose();
        setVisible(false);        
    }
    
    
    private void butCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butCloseActionPerformed
    {//GEN-HEADEREND:event_butCloseActionPerformed
        // Add your handling code here:
        setVisible(false);
        dispose();    
        ret=2;
    }//GEN-LAST:event_butCloseActionPerformed
    
    public int returnCode()
    {
        return ret;
    }
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)//GEN-FIRST:event_closeDialog
    {
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    public void setText(String text)
    {
        jTextField1.setText(text);
    }
    
    public String getText()
    {
        return jTextField1.getText();
    }
    
    public boolean matchCase()
    {
        return jCheckBox1.isSelected();
    }
    
    public boolean matchWhole()
    {
        return jCheckBox2.isSelected();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        new Search(new javax.swing.JFrame(), true).show();
    }
    
    /**
     * Getter for property editor.
     * @return Value of property editor.
     */
    public java.lang.Object getEditor()
    {
        return editor;
    }    
    
    /**
     * Setter for property editor.
     * @param editor New value of property editor.
     */
    public void setEditor(java.lang.Object editor)
    {
        this.editor = editor;
    }
    
    /**
     * Getter for property locale.
     * @return Value of property locale.
     */
    public java.util.Locale getLocale()
    {
        return locale;
    }
    
    /**
     * Setter for property locale.
     * @param locale New value of property locale.
     */
    public void setLocale(java.util.Locale locale)
    {
        this.locale = locale;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butClose;
    private javax.swing.JButton butFind;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}
