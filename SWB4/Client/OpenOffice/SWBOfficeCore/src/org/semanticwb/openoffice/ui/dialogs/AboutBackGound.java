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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AboutBackGound.java
 *
 * Created on 10/02/2009, 05:45:49 PM
 */

package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Image;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author victor.lorenzana
 */
public class AboutBackGound extends javax.swing.JPanel {

    private Image imgFondo=ImageLoader.images.get("splash").getImage();
    Dialog parent;
    private static final String version="4.0.1.1";
    /** Creates new form AboutBackGound */
    public AboutBackGound(Dialog parent) {
        this.parent=parent;        
        initComponents();
        this.jLabelVersionAPI.setText(this.jLabelVersionAPI.getText()+IOfficeApplication.version);
        this.jLabelVersion.setText(this.jLabelVersion.getText()+version);
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonClose = new javax.swing.JButton();
        jButtonCloseLicense = new javax.swing.JButton();
        jLabelVersionAPI = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(500, 300));

        jButtonClose.setBackground(new java.awt.Color(51, 102, 153));
        jButtonClose.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButtonClose.setText("Cerrar");
        jButtonClose.setBorder(null);
        jButtonClose.setBorderPainted(false);
        jButtonClose.setDoubleBuffered(true);
        jButtonClose.setMargin(new java.awt.Insets(0, 14, 2, 14));
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonCloseLicense.setBackground(new java.awt.Color(51, 102, 153));
        jButtonCloseLicense.setFont(new java.awt.Font("Tahoma", 0, 14));
        jButtonCloseLicense.setText("Ver Licencia");
        jButtonCloseLicense.setBorder(null);
        jButtonCloseLicense.setBorderPainted(false);
        jButtonCloseLicense.setDoubleBuffered(true);
        jButtonCloseLicense.setMargin(new java.awt.Insets(0, 14, 2, 14));
        jButtonCloseLicense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseLicenseActionPerformed(evt);
            }
        });

        jLabelVersionAPI.setText("API server versión: ");

        jLabelVersion.setText("Version:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVersion)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabelVersionAPI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButtonCloseLicense, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(171, Short.MAX_VALUE)
                .addComponent(jLabelVersion)
                .addGap(18, 18, 18)
                .addComponent(jLabelVersionAPI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCloseLicense, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        parent.setVisible(false);
}//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonCloseLicenseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseLicenseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseLicenseActionPerformed
        DialogSeeLicense dialogSeeLicense=new DialogSeeLicense();
        dialogSeeLicense.setSize(500, 400);
        dialogSeeLicense.setVisible(true);
}//GEN-LAST:event_jButtonCloseLicenseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCloseLicense;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JLabel jLabelVersionAPI;
    // End of variables declaration//GEN-END:variables

   
    @Override
    public void paintComponent(Graphics g)
    {
        if (imgFondo != null)
        {
            g.drawImage(imgFondo, 0, 0, 500, 300, this);
        }
    }
}
