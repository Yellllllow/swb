/*
 * DialogAddCategory.java
 *
 * Created on 5 de diciembre de 2008, 12:57 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Cursor;
import java.awt.Frame;
import javax.swing.JOptionPane;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogAddCategory extends javax.swing.JDialog
{

    /** Creates new form DialogAddCategory */
    private boolean cancel = true;
    private final String repository;
    private final String categoryId;

    public DialogAddCategory(String repository, String categoryid)
    {
        super((Frame)null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        this.repository = repository;
        this.setTitle("Agregar categoria a repositorio " + repository);
        this.setLocationRelativeTo(null);
        this.categoryId = categoryid;
    }

    public DialogAddCategory(String repository)
    {
        this(repository, null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jTextFieldName = new javax.swing.JTextField(50);
        jButtonAccept = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Agregar Categoria");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Descripción");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(10);
        jScrollPane1.setViewportView(jTextAreaDescription);

        jButtonAccept.setText("Aceptar");
        jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAcceptActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancelar");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAccept)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonAccept))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    this.setVisible(false);
}//GEN-LAST:event_jButtonCancelActionPerformed

private void jButtonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAcceptActionPerformed
    if (jTextFieldName.getText().isEmpty())
    {
        JOptionPane.showMessageDialog(this, "¡Debe indicar el nombre de la categoria!", "Error al capturar el nombre de la categoria", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        jTextFieldName.requestFocus();
        return;
    }
    if (jTextAreaDescription.getText().isEmpty())
    {
        JOptionPane.showMessageDialog(this, "¡Debe indicar la descripción de la categoria!", "Error al capturar descripción de la categoria", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        jTextAreaDescription.requestFocus();
        return;
    }
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    boolean existe = false;
    try
    {
        for (CategoryInfo category : OfficeApplication.getOfficeApplicationProxy().getCategories(repository))
        {
            if (category.title.equalsIgnoreCase(jTextFieldName.getText()))
            {
                existe = true;
                break;
            }
        }
    }
    catch (Exception e)
    {
        e.printStackTrace(System.out);
    }
    if (existe)
    {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        JOptionPane.showMessageDialog(this, "¡Ya existe una categoria con ese nombre!", "Error al capturar nombre", JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        jTextFieldName.requestFocus();
        return;
    }
    else
    {
        if (categoryId != null)
        {
            try
            {
                OfficeApplication.getOfficeApplicationProxy().createCategory(repository, categoryId,this.jTextFieldName.getText(), jTextAreaDescription.getText());
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        else
        {
             try
            {
                OfficeApplication.getOfficeApplicationProxy().createCategory(repository, this.jTextFieldName.getText(), jTextAreaDescription.getText());
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }

    }
    cancel = false;
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    this.setVisible(false);
}//GEN-LAST:event_jButtonAcceptActionPerformed

    public boolean isCancel()
    {
        return cancel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAccept;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables

}
