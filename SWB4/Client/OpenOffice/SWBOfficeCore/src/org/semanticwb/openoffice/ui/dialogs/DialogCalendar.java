/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogCalendar.java
 *
 * Created on 12/02/2009, 03:11:52 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.DOMOutputter;

/**
 *
 * @author victor.lorenzana
 */
public class DialogCalendar extends java.awt.Dialog
{

    DialogRegularPeriods dialogRegularPeriods = new DialogRegularPeriods(new Frame(), true);
    boolean isCanceled = true;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final SimpleDateFormat DATE_SIMPLEFORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat TIME_SIMPLEFORMAT = new SimpleDateFormat(TIME_FORMAT);

    /** Creates new form DialogCalendar */
    public DialogCalendar(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        this.jSpinnerInitDate.setEditor(new JSpinner.DateEditor(jSpinnerInitDate, DATE_FORMAT));
        this.jSpinnerEndDate.setEditor(new JSpinner.DateEditor(jSpinnerEndDate, DATE_FORMAT));
        
        this.jSpinnerInitTime.setEditor(new JSpinner.DateEditor(jSpinnerInitTime, TIME_FORMAT));
        this.jSpinnerEndTime.setEditor(new JSpinner.DateEditor(jSpinnerEndTime, TIME_FORMAT));
        this.setLocationRelativeTo(null);
    }

    private void init(Document document,String title)
    {
        this.jTextFieldTitle.setText(title);
        DOMOutputter out = new DOMOutputter();
        try
        {
            org.w3c.dom.Document xml = out.output(document);
            try
            {
                if (xml.getElementsByTagName("starthour").getLength() > 0 && xml.getElementsByTagName("endhour").getLength() > 0)
                {
                    this.jCheckBoxByTime.setSelected(true);
                    this.jCheckBoxByTimeStateChanged(null);
                    if (xml.getElementsByTagName("starthour").getLength() > 0)
                    {
                        Date date=TIME_SIMPLEFORMAT.parse(((org.w3c.dom.Element) xml.getElementsByTagName("starthour").item(0)).getTextContent());
                        this.jSpinnerInitTime.setValue(date);
                    }
                    if (xml.getElementsByTagName("endhour").getLength() > 0)
                    {
                        Date date=TIME_SIMPLEFORMAT.parse(((org.w3c.dom.Element) xml.getElementsByTagName("endhour").item(0)).getTextContent());
                        this.jSpinnerEndTime.setValue(date);
                    }
                }
                if (xml.getElementsByTagName("inidate").getLength() > 0)
                {
                    Date date=DATE_SIMPLEFORMAT.parse(((org.w3c.dom.Element) xml.getElementsByTagName("inidate").item(0)).getTextContent());
                    this.jSpinnerInitDate.setValue(date);
                }
                if (xml.getElementsByTagName("enddate").getLength() == 0)
                {
                    jRadioButtonNotEndDate.setSelected(true);
                    jSpinnerEndTime.setEnabled(false);
                    jRadioButtonEndSelect.setSelected(false);
                }
                else
                {
                    Date date=DATE_SIMPLEFORMAT.parse(((org.w3c.dom.Element) xml.getElementsByTagName("enddate").item(0)).getTextContent());
                    this.jSpinnerEndDate.setValue(date);
                    jRadioButtonNotEndDate.setSelected(false);
                    jSpinnerEndTime.setEnabled(true);
                    jRadioButtonEndSelect.setSelected(true);

                }
                if (xml.getElementsByTagName("iterations").getLength() > 0)
                {
                    this.jButtonRegularPeriods.setText("Ver periodos regualres activados");
                }
            }
            catch (ParseException pe)
            {
                pe.printStackTrace();
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

        buttonGroupendDate = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButtonRegularPeriods = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanelByTime = new javax.swing.JPanel();
        jCheckBoxByTime = new javax.swing.JCheckBox();
        jSpinnerInitTime = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSpinnerEndTime = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButtonNotEndDate = new javax.swing.JRadioButton();
        jRadioButtonEndSelect = new javax.swing.JRadioButton();
        jSpinnerEndDate = new javax.swing.JSpinner();
        jSpinnerInitDate = new javax.swing.JSpinner();

        setResizable(false);
        setTitle("Calendarización");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonRegularPeriods.setText("Configurar por periodos regulares");
        jButtonRegularPeriods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegularPeriodsActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonRegularPeriods);

        jButtonOk.setText("Aceptar");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonOk);

        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonClose);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel1.setText("Título:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanelByTime.setBorder(javax.swing.BorderFactory.createTitledBorder("Horario de Presentación"));

        jCheckBoxByTime.setText("Por Hora");
        jCheckBoxByTime.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxByTimeStateChanged(evt);
            }
        });

        jSpinnerInitTime.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.SECOND));
        jSpinnerInitTime.setEnabled(false);

        jLabel2.setText("Hora de inicio:");

        jLabel3.setText("Hora final:");

        jSpinnerEndTime.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.SECOND));
        jSpinnerEndTime.setEnabled(false);

        javax.swing.GroupLayout jPanelByTimeLayout = new javax.swing.GroupLayout(jPanelByTime);
        jPanelByTime.setLayout(jPanelByTimeLayout);
        jPanelByTimeLayout.setHorizontalGroup(
            jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelByTimeLayout.createSequentialGroup()
                .addGroup(jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxByTime)
                    .addGroup(jPanelByTimeLayout.createSequentialGroup()
                        .addGroup(jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerInitTime, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanelByTimeLayout.setVerticalGroup(
            jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelByTimeLayout.createSequentialGroup()
                .addComponent(jCheckBoxByTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerInitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanelByTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinnerEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Intervalo de Dias"));
        jPanel6.setPreferredSize(new java.awt.Dimension(500, 100));

        jLabel4.setText("Feha de inicio:");

        buttonGroupendDate.add(jRadioButtonNotEndDate);
        jRadioButtonNotEndDate.setText("Sin fecha de finalización");
        jRadioButtonNotEndDate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButtonNotEndDateStateChanged(evt);
            }
        });

        buttonGroupendDate.add(jRadioButtonEndSelect);
        jRadioButtonEndSelect.setSelected(true);
        jRadioButtonEndSelect.setText("Finaliza el:");
        jRadioButtonEndSelect.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButtonEndSelectStateChanged(evt);
            }
        });

        jSpinnerEndDate.setModel(new javax.swing.SpinnerDateModel());

        jSpinnerInitDate.setModel(new javax.swing.SpinnerDateModel());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonNotEndDate)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonEndSelect)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinnerEndDate, 0, 0, Short.MAX_VALUE)
                            .addComponent(jSpinnerInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSpinnerInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonEndSelect)
                    .addComponent(jSpinnerEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jRadioButtonNotEndDate)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanelByTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jPanelByTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButtonRegularPeriodsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRegularPeriodsActionPerformed
    {//GEN-HEADEREND:event_jButtonRegularPeriodsActionPerformed
        dialogRegularPeriods.setVisible(true);
    }//GEN-LAST:event_jButtonRegularPeriodsActionPerformed

    private void jCheckBoxByTimeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jCheckBoxByTimeStateChanged
    {//GEN-HEADEREND:event_jCheckBoxByTimeStateChanged
        if (this.jCheckBoxByTime.isSelected())
        {
            this.jSpinnerInitTime.setEnabled(true);
            this.jSpinnerEndTime.setEnabled(true);
        }
        else
        {
            this.jSpinnerInitTime.setEnabled(false);
            this.jSpinnerEndTime.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBoxByTimeStateChanged

    private void jRadioButtonNotEndDateStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jRadioButtonNotEndDateStateChanged
    {//GEN-HEADEREND:event_jRadioButtonNotEndDateStateChanged
        if (jRadioButtonNotEndDate.isSelected())
        {
            jSpinnerEndDate.setEnabled(false);
        }
        else
        {
            jSpinnerEndDate.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButtonNotEndDateStateChanged

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        isCanceled = true;
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOkActionPerformed
    {//GEN-HEADEREND:event_jButtonOkActionPerformed
        if(jTextFieldTitle.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "¡Debe indicar el título!",this.getTitle(),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            jTextFieldTitle.requestFocus();
            return;
        }
        isCanceled = false;
        this.setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jRadioButtonEndSelectStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jRadioButtonEndSelectStateChanged
    {//GEN-HEADEREND:event_jRadioButtonEndSelectStateChanged
        jRadioButtonNotEndDateStateChanged(null);
    }//GEN-LAST:event_jRadioButtonEndSelectStateChanged
    public Document getDocument()
    {
        Document doc = new Document();

        Element resource=new Element("Resource");
        doc.setRootElement(resource);

        Element inidate=new Element("inidate");
        String date=DATE_SIMPLEFORMAT.format(((Date)jSpinnerInitDate.getValue()));
        inidate.setText(date);
        resource.addContent(inidate);
        if(jRadioButtonEndSelect.isSelected())
        {
            date=DATE_SIMPLEFORMAT.format(((Date)jSpinnerEndDate.getValue()));
            Element enddate=new Element("enddate");
            enddate.setText(date);
            resource.addContent(enddate);
        }
        if(jCheckBoxByTime.isSelected())
        {
            Element starthour=new Element("starthour");
            date=TIME_SIMPLEFORMAT.format(((Date)jSpinnerInitTime.getValue()));
            starthour.setText(date);
            resource.addContent(starthour);

            Element endhour=new Element("endhour");
            date=TIME_SIMPLEFORMAT.format(((Date)jSpinnerEndTime.getValue()));
            endhour.setText(date);

            resource.addContent(endhour);

        }
        Element periods = this.dialogRegularPeriods.getElement();
        if (periods != null)
        {
            resource.addContent(periods);
        }
        return doc;
    }

    public void setDocument(Document document,String title)
    {
        init(document,title);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupendDate;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonRegularPeriods;
    private javax.swing.JCheckBox jCheckBoxByTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelByTime;
    private javax.swing.JRadioButton jRadioButtonEndSelect;
    private javax.swing.JRadioButton jRadioButtonNotEndDate;
    private javax.swing.JSpinner jSpinnerEndDate;
    private javax.swing.JSpinner jSpinnerEndTime;
    private javax.swing.JSpinner jSpinnerInitDate;
    private javax.swing.JSpinner jSpinnerInitTime;
    public javax.swing.JTextField jTextFieldTitle;
    // End of variables declaration//GEN-END:variables
}
