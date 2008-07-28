/*
 * DialogLogin.java
 *
 * Created on 3 de junio de 2008, 10:28 AM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.semanticwb.openoffice.*;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogLogin extends javax.swing.JDialog
{

    private int numTry = 0;
    private boolean canceled = true;
    private UserInfo userInfo;
    private URI webAddress;
    private String loggin,  password;
    ConfigurationListURI configurationListURI = new ConfigurationListURI();

    /** Creates new form DialogLogin */
    public DialogLogin(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        this.jComboBoxWebAddress.removeAllItems();
        for ( URI uri : configurationListURI.getAddresses() )
        {
            this.jComboBoxWebAddress.addItem(uri);
        }

    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public String getLogin()
    {
        return loggin;
    }

    public String getPassword()
    {
        return password;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public URI getWebAddress()
    {
        return webAddress;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonCancel = new javax.swing.JButton();
        jButtonAccept = new javax.swing.JButton();
        jLabelWebAddress = new javax.swing.JLabel();
        jComboBoxWebAddress = new javax.swing.JComboBox();
        jButtonAvanced = new javax.swing.JButton();
        jLabelClave = new javax.swing.JLabel();
        jTextFieldClave = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        jCheckBoxSaveConfiguration = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jButtonCancel.setText("Cancelar");
        jButtonCancel.setNextFocusableComponent(jButtonAvanced);
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAccept.setText("Aceptar");
        jButtonAccept.setNextFocusableComponent(jButtonCancel);
        jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptActionPerformed(evt);
            }
        });

        jLabelWebAddress.setText("Dirección Web:");

        jComboBoxWebAddress.setEditable(true);
        jComboBoxWebAddress.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxWebAddress.setAutoscrolls(true);
        jComboBoxWebAddress.setName("WebAddress"); // NOI18N
        jComboBoxWebAddress.setNextFocusableComponent(jTextFieldClave);
        jComboBoxWebAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWebAddressActionPerformed(evt);
            }
        });

        jButtonAvanced.setText("Avanzado");
        jButtonAvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvancedActionPerformed(evt);
            }
        });

        jLabelClave.setText("Clave de acceso:");

        jTextFieldClave.setNextFocusableComponent(jPassword);

        jLabelPassword.setText("Contraseña de acceso:");

        jPassword.setNextFocusableComponent(jButtonAccept);

        jCheckBoxSaveConfiguration.setText("Guardar Configuración");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxSaveConfiguration)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAvanced)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addComponent(jButtonCancel)
                        .addGap(5, 5, 5)
                        .addComponent(jButtonAccept))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPassword)
                            .addComponent(jLabelWebAddress)
                            .addComponent(jLabelClave))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldClave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(jPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addComponent(jComboBoxWebAddress, javax.swing.GroupLayout.Alignment.TRAILING, 0, 251, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelWebAddress)
                    .addComponent(jComboBoxWebAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelClave)
                    .addComponent(jTextFieldClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jCheckBoxSaveConfiguration)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonAccept)
                    .addComponent(jButtonAvanced))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButtonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAcceptActionPerformed

        numTry++;
        if ( numTry < 3 )
        {
            if ( this.jComboBoxWebAddress.getSelectedItem() == null )
            {
                JOptionPane.showMessageDialog(null, "Debe indicar una dirección Web", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                this.jComboBoxWebAddress.requestFocus();
                return;
            }
            String sUri = this.jComboBoxWebAddress.getSelectedItem().toString();
            try
            {
                URI uri = new URI(sUri);
                if ( this.jTextFieldClave.getText().isEmpty() )
                {
                    JOptionPane.showMessageDialog(null, "Debe indicar la clave de acceso", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                    this.jTextFieldClave.requestFocus();
                    return;
                }
                if ( this.jPassword.getPassword().length == 0 )
                {
                    JOptionPane.showMessageDialog(null, "Debe indicar la contraseña de acceso", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                    this.jPassword.requestFocus();
                    return;
                }
                configurationListURI.addUserConfiguration(uri, this.jTextFieldClave.getText());
                this.webAddress=uri;
            }
            catch ( URISyntaxException use )
            {
                JOptionPane.showMessageDialog(null, "La dirección Web no es válida", this.getTitle(), JOptionPane.ERROR_MESSAGE);
                this.jComboBoxWebAddress.requestFocus();
                return;
            }            
            this.setVisible(false);
            this.canceled = false;
        }
        else
        {
            this.setVisible(false);
            this.canceled = true;
        }
    // TODO: Agregar logica de acceso
    }//GEN-LAST:event_jButtonAcceptActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.setVisible(false);
        this.canceled = true;
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAvancedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvancedActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAvancedActionPerformed

    private void jComboBoxWebAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxWebAddressActionPerformed
        if ( this.jComboBoxWebAddress.getSelectedItem() != null )
        {
            String sUri = this.jComboBoxWebAddress.getSelectedItem().toString();
            try
            {
                URI uri = new URI(sUri);
                String login = configurationListURI.getLogin(uri);
                this.jTextFieldClave.setText(login);
            }
            catch ( URISyntaxException use )
            {
                JOptionPane.showMessageDialog(this, "Error al escribir la dirección web",this.getTitle(),JOptionPane.ERROR);
            }
        }
    }//GEN-LAST:event_jComboBoxWebAddressActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAccept;
    private javax.swing.JButton jButtonAvanced;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JCheckBox jCheckBoxSaveConfiguration;
    private javax.swing.JComboBox jComboBoxWebAddress;
    private javax.swing.JLabel jLabelClave;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelWebAddress;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JTextField jTextFieldClave;
    // End of variables declaration//GEN-END:variables
}
