/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogDocumentsAtuhorize.java
 *
 * Created on 2/03/2009, 03:28:19 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Frame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.semanticwb.office.interfaces.FlowContentInformation;
import org.semanticwb.office.interfaces.PortletInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author victor.lorenzana
 */
public class DialogDocumentsAtuhorize extends java.awt.Dialog
{

    /** Creates new form DialogDocumentsAtuhorize */
    public DialogDocumentsAtuhorize()
    {
        super((Frame) null, ModalityType.TOOLKIT_MODAL);
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        initComponents();
        this.setLocationRelativeTo(null);
        ListSelectionModel listSelectionModel = jTableContents.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener()
        {

            public void valueChanged(ListSelectionEvent e)
            {
                jButtonAuthorize.setEnabled(false);
                jButtonSee.setEnabled(false);
                if (e.getFirstIndex() != -1)
                {
                    jButtonAuthorize.setEnabled(true);
                    jButtonSee.setEnabled(true);
                }
            }
        });
        loadSites();
        loadContents();
    }

    private void loadSites()
    {
        this.jComboBoxSites.removeAllItems();
        try
        {
            for (WebSiteInfo site : OfficeApplication.getOfficeApplicationProxy().getSites())
            {
                jComboBoxSites.addItem(site);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadContents()
    {
        DefaultTableModel model = (DefaultTableModel) jTableContents.getModel();
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            for (FlowContentInformation flowContentInformation : OfficeApplication.getOfficeApplicationProxy().getContentsForAuthorize())
            {
                PortletInfo portletInfo = flowContentInformation.portletInfo;
                String version = flowContentInformation.portletInfo.version;
                if (version.equals("*"))
                {
                    version = "Mostrar la última version";
                }
                Object[] rowData =
                {
                    portletInfo, portletInfo.page.site.title, portletInfo.page.title, version
                };
                model.addRow(rowData);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonSee = new javax.swing.JButton();
        jButtonAuthorize = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jComboBoxSites = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableContents = new javax.swing.JTable();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonClose);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonSee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/see.png"))); // NOI18N
        jButtonSee.setToolTipText("Ver Contenido");
        jButtonSee.setEnabled(false);
        jButtonSee.setFocusable(false);
        jButtonSee.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSee.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonSee);

        jButtonAuthorize.setText("Autorizar");
        jButtonAuthorize.setToolTipText("Autorizar contenido");
        jButtonAuthorize.setEnabled(false);
        jButtonAuthorize.setFocusable(false);
        jButtonAuthorize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAuthorize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonAuthorize);

        jButton1.setText("Rechazar");
        jButton1.setToolTipText("Rechazar contenido");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jPanel2.add(jToolBar1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(100, 30));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Todos");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Mis contenidos");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Por autorizar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addGap(42, 42, 42))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        jTableContents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título", "Página", "Paso", "Versión"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableContents);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAuthorize;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonSee;
    private javax.swing.JComboBox jComboBoxSites;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableContents;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
