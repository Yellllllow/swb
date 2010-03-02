/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogEditActivity.java
 *
 * Created on 1/03/2010, 03:13:52 PM
 */

package org.semanticwb.publishflow;

import applets.commons.WBConnection;
import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;
import java.awt.Frame;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author victor.lorenzana
 */
public class DialogEditActivity extends javax.swing.JDialog {

    String name;
    Locale locale=new Locale("es");
    WBConnection con;
    String[] roles;
    String[] users;
    /** Creates new form DialogEditActivity */
    public DialogEditActivity(String name,WBConnection con) {
        super((Frame) null, true);
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ue)
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
        
        this.con=con;
        if(con!=null && con.getApplet()!=null && con.getApplet().getParameter("locale")!=null && !con.getApplet().getParameter("locale").trim().equals(""))
        {
            this.locale = new Locale(con.getApplet().getParameter("locale"));
        }
        

        initComponents();
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.jTextFieldName.grabFocus();
        this.name=name;
        //loadRoles();
    }

    
    public void loadRoles()
    {
        jTableRolesModel model=new jTableRolesModel(locale);
        this.jTableRoles.setModel(model);
        String tm=con.getApplet().getParameter("tm");
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatRoles</cmd><tm>"+ tm +"</tm></req>";
        xml=con.getData(xml);
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse(xml);
        Iterator eusers=exml.getFirstNode().getNodesbyName("role");
        while(eusers.hasNext())
        {
            WBTreeNode wuser=(WBTreeNode)eusers.next();
            String id=wuser.getAttribute("id");
            String oname=wuser.getAttribute("name");
            String repository=wuser.getAttribute("repository");
            Role role=new Role(id,oname,repository);
            model.addRole(role);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelPropiedades = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelDescription = new javax.swing.JLabel();
        jPanelDescription = new javax.swing.JPanel();
        jTextAreaDescription = new javax.swing.JTextArea();
        jPanelDuracion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldDias = new javax.swing.JTextField();
        jTextFieldHours = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBoxDuration = new javax.swing.JCheckBox();
        jPanelRoles = new javax.swing.JPanel();
        jScrollPaneRoles = new javax.swing.JScrollPane();
        jTableRoles = new javax.swing.JTable();
        jPanelUsuarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Propiedades de la actividad");

        jPanel10.setPreferredSize(new java.awt.Dimension(10, 40));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity",locale); // NOI18N
        jButtonAceptar.setText(bundle.getString("aceptar")); // NOI18N
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonAceptar);

        jButtonCancelar.setText(bundle.getString("cancelar")); // NOI18N
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonCancelar);

        getContentPane().add(jPanel10, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseMoved(evt);
            }
        });

        jPanelPropiedades.setLayout(null);

        jLabelName.setText(bundle.getString("nombre")); // NOI18N
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(10, 10, 70, 14);
        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(110, 10, 280, 19);

        jLabelDescription.setText(bundle.getString("description")); // NOI18N
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(10, 40, 90, 14);

        jPanelDescription.setLayout(new java.awt.BorderLayout());
        jPanelDescription.add(jTextAreaDescription, java.awt.BorderLayout.CENTER);

        jPanelPropiedades.add(jPanelDescription);
        jPanelDescription.setBounds(110, 40, 280, 80);

        jPanelDuracion.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("duracion"))); // NOI18N
        jPanelDuracion.setEnabled(false);
        jPanelDuracion.setLayout(null);

        jLabel3.setText(bundle.getString("dias")); // NOI18N
        jPanelDuracion.add(jLabel3);
        jLabel3.setBounds(110, 20, 30, 14);

        jTextFieldDias.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldDias.setText("0");
        jTextFieldDias.setEnabled(false);
        jTextFieldDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDiasKeyPressed(evt);
            }
        });
        jPanelDuracion.add(jTextFieldDias);
        jTextFieldDias.setBounds(60, 20, 40, 19);

        jTextFieldHours.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldHours.setText("0");
        jTextFieldHours.setEnabled(false);
        jPanelDuracion.add(jTextFieldHours);
        jTextFieldHours.setBounds(230, 20, 40, 19);

        jLabel6.setText(bundle.getString("horas")); // NOI18N
        jPanelDuracion.add(jLabel6);
        jLabel6.setBounds(280, 20, 40, 14);

        jPanelPropiedades.add(jPanelDuracion);
        jPanelDuracion.setBounds(10, 160, 380, 60);

        jCheckBoxDuration.setText(bundle.getString("hasDuration")); // NOI18N
        jCheckBoxDuration.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxDurationItemStateChanged(evt);
            }
        });
        jPanelPropiedades.add(jCheckBoxDuration);
        jCheckBoxDuration.setBounds(10, 130, 140, 23);

        jTabbedPane1.addTab("Propiedades", jPanelPropiedades);

        jPanelRoles.setLayout(new java.awt.BorderLayout());

        jTableRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneRoles.setViewportView(jTableRoles);

        jPanelRoles.add(jScrollPaneRoles, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Roles", jPanelRoles);

        jPanelUsuarios.setLayout(new java.awt.BorderLayout());

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableUsuarios);

        jPanelUsuarios.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Usuarios", jPanelUsuarios);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAceptarActionPerformed
    {//GEN-HEADEREND:event_jButtonAceptarActionPerformed
        // Add your handling code here:
        if(this.jTextFieldName.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("DialogEditActivity",locale).getString("Favor_de_indicar_el_nombre_de_la_activitdad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(this.jTextAreaDescription.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("DialogEditActivity",locale).getString("Favor_de_indicar_la_descripcion_dela_actividad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }

        



        jTableRolesModel rolemodel=(jTableRolesModel)this.jTableRoles.getModel();
        jTableUserModel usermodel=(jTableUserModel)this.jTableUsuarios.getModel();


        boolean selected=false;
        for(int i=0;i<rolemodel.getRowCount();i++)
        {
            if(rolemodel.getRole(i).isChecked())
            {
                selected=true;
            }
        }


        /*for(int i=0;i<usermodel.getRowCount();i++)
        {
            if(usermodel.getUser(i).isChecked())
            {
                selected=true;
            }
        }*/
        if(!selected)
        {
            this.jTabbedPane1.setSelectedIndex(1);
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("DialogEditActivity",locale).getString("No_selecciono_rol_o_usuario_que_tenga_permisos_sobre_esta_actividad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(this.jCheckBoxDuration.isSelected())
        {
            if(this.jTextFieldDias.getText().equals(""))
            {
                this.jTextFieldDias.setText("0");
            }
            int dias=Integer.parseInt(this.jTextFieldDias.getText());
            //int minutos=Integer.parseInt(this.jTextFieldMinutos.getText());
            //int segundos=Integer.parseInt(this.jTextFieldSegundos.getText());
            int horas=Integer.parseInt(this.jTextFieldHours.getText());
            if(dias==0 && horas==0)
            {
                this.jTabbedPane1.setSelectedIndex(0);
                this.jTextFieldDias.grabFocus();
                JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("DialogEditActivity",locale).getString("La_duracion_de_la_actividad_es_cero,_favor_de_indicar_una_durecion_mayor_a_cero"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
                return;
            }
            //activity.setDuraction(dias,horas);
        }
        else
        {
            //activity.setDuraction(false);
        }

        //this.activity.getLinks().clear();


        /*jTableLinkModel model=(jTableLinkModel)this.jTableAutorizacion.getModel();
        Iterator links=model.getLinks().iterator();
        while(links.hasNext())
        {
            Link link=(Link)links.next();
            this.activity.addLink(link);
        }*/

        /*model=(jTableLinkModel)this.jTableRechazo.getModel();
        links=model.getLinks().iterator();
        while(links.hasNext())
        {
            Link link=(Link)links.next();
            this.activity.addLink(link);
        }*/



        /*this.activity.getRoleModel().clear();
        this.activity.getUserModel().clear();*/



        /*int iroles=rolemodel.getRowCount();
        for(int i=0;i<iroles;i++)
        {
            Role role=rolemodel.getRole(i);
            if(role.isChecked())
            {
                this.activity.getRoleModel().addRole(role);
            }
        }

        this.activity.setName(this.jTextFieldName.getText().trim());
        this.activity.setDescription(this.jTextAreaDescription.getText().trim());

        for(int i=0;i<usermodel.getRowCount();i++)
        {
            User user=(User)usermodel.getUser(i);
            if(user.isChecked())
            {
                this.activity.getUserModel().addUser(user);
            }
        }
        System.out.println(add);
        if(add)
        {
            this.model.addActivity(this.activity);
            if(this.jRadioButtonSi.isSelected())
            {
                this.model.addFirstActivity(this.activity);
            }
            else
            {
                this.model.addActivity(this.activity);
            }
        }*/

        setVisible(false);
        this.dispose();
}//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelarActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelarActionPerformed
        // Add your handling code here:
        setVisible(false);
        this.dispose();
}//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldDiasKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldDiasKeyPressed
    {//GEN-HEADEREND:event_jTextFieldDiasKeyPressed
        // Add your handling code here:
    }//GEN-LAST:event_jTextFieldDiasKeyPressed

    private void jCheckBoxDurationItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_jCheckBoxDurationItemStateChanged
    {//GEN-HEADEREND:event_jCheckBoxDurationItemStateChanged
        if(this.jCheckBoxDuration.isSelected())
        {
            this.jPanelDuracion.setEnabled(true);
            this.jTextFieldDias.setEnabled(true);
            //this.jTextFieldMinutos.setEnabled(true);
            this.jTextFieldHours.setEnabled(true);
            //this.jTextFieldSegundos.setEnabled(true);
        }
        else
        {
            this.jPanelDuracion.setEnabled(false);
            this.jTextFieldDias.setEnabled(false);
            //this.jTextFieldMinutos.setEnabled(false);
            this.jTextFieldHours.setEnabled(false);
            //this.jTextFieldSegundos.setEnabled(false);

        }
}//GEN-LAST:event_jCheckBoxDurationItemStateChanged

    private void jTabbedPane1MouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPane1MouseMoved
    {//GEN-HEADEREND:event_jTabbedPane1MouseMoved

}//GEN-LAST:event_jTabbedPane1MouseMoved

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JCheckBox jCheckBoxDuration;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanelDescription;
    private javax.swing.JPanel jPanelDuracion;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JPanel jPanelRoles;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneRoles;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldDias;
    private javax.swing.JTextField jTextFieldHours;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables

}
