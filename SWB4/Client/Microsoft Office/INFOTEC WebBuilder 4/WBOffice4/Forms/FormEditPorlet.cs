﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Xml;
using System.Windows.Forms;
using WBOffice4.Forms;
using WBOffice4.Interfaces;
using WBOffice4.Utils;
namespace WBOffice4.Forms
{
    public partial class FormEditPorlet : Form
    {
        private Object obj;
        private PropertyGrid grid = new PropertyGrid();
        private ResourceInfo pageInformation;
        private String repositoryName, contentID;
        List<CalendarInfo> added = new List<CalendarInfo>();
        public FormEditPorlet(ResourceInfo pageInformation, String repositoryName, String contentID)
        {
            InitializeComponent();
            this.pageInformation = pageInformation;
            this.repositoryName = repositoryName;
            this.contentID = contentID;
            this.textBoxTitle.Text = pageInformation.title;
            this.textBoxDescription.Text = pageInformation.description;
            this.checkBoxActive.Checked = pageInformation.active;
            this.labelSite.Text = pageInformation.page.site.title;
            this.labelPage.Text = pageInformation.page.title;            
            loadCalendars();
            VersionInfo info = new VersionInfo();
            info.nameOfVersion = "*";

            comboBoxVersiones.Items.Add(info);
            foreach (VersionInfo versionInfo in OfficeApplication.OfficeDocumentProxy.getVersions(repositoryName, contentID))
            {
                comboBoxVersiones.Items.Add(versionInfo);
            }
            VersionInfo selected = new VersionInfo();
            selected.nameOfVersion = pageInformation.version;
            if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(pageInformation))
            {
                this.toolTip1.SetToolTip(this.checkBoxActive, "Necesita enviar a flujo el contenido para activarlo");
            }
            if (OfficeApplication.OfficeDocumentProxy.isInFlow(pageInformation))
            {
                this.toolTip1.SetToolTip(this.checkBoxActive, "El contenido se encuentra en proceso de ser autorizado, para activarlo, necesita terminar este proceso");
            }
            comboBoxVersiones.SelectedItem = selected;
            loadProperties();
        }
        private void loadProperties()
        {
            grid.ToolbarVisible = false;
            grid.Dock = DockStyle.Fill;
            PropertyInfo[] props = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
            obj = TypeFactory.getObject(props, "Propiedades de presentación");
            foreach (PropertyInfo prop in props)
            {
                String value = OfficeApplication.OfficeDocumentProxy.getViewPropertyValue(this.pageInformation, prop);
                TypeFactory.setValue(prop, obj, value);
            }
            this.grid.SelectedObject = obj;
            tabPageProperties.Controls.Add(grid);
        }
        private void loadCalendars()
        {
            this.listViewCalendar.Items.Clear();

            foreach (CalendarInfo info in OfficeApplication.OfficeDocumentProxy.getCalendars(pageInformation))
            {
                ListViewItem item = new ListViewItem(info.title);
                item.Tag = info;
                item.SubItems.Add(info.active.ToString());
                listViewCalendar.Items.Add(item);
            }
            foreach (CalendarInfo info in added)
            {
                ListViewItem item = new ListViewItem(info.title);
                item.Tag = info;
                item.SubItems.Add(info.active.ToString());
                listViewCalendar.Items.Add(item);
            }


        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }



        private void toolStripButtonAdd_Click(object sender, EventArgs e)
        {
            FrmPeriodicidad frmPeriodicidad = new FrmPeriodicidad(listViewCalendar);
            frmPeriodicidad.ShowDialog(this);
            if (frmPeriodicidad.DialogResult == DialogResult.OK)
            {
                XmlDocument xmlCalendar = frmPeriodicidad.Document;
                CalendarInfo cal = new CalendarInfo();
                cal.xml = xmlCalendar.OuterXml;
                cal.title = frmPeriodicidad.textBoxTitle.Text;
                ListViewItem item = new ListViewItem(cal.title);
                item.SubItems.Add("sí");
                listViewCalendar.Items.Add(item);
            }
        }

        private void toolStripButtonEdit_Click(object sender, EventArgs e)
        {
            if (this.listViewCalendar.SelectedItems.Count > 0)
            {
                CalendarInfo cal = (CalendarInfo)this.listViewCalendar.SelectedItems[0].Tag;
                FrmPeriodicidad frmPeriodicidad = new FrmPeriodicidad(listViewCalendar);
                XmlDocument doc = new XmlDocument();
                doc.LoadXml(cal.xml);
                frmPeriodicidad.textBoxTitle.Text = cal.title;
                frmPeriodicidad.Document = doc;
                frmPeriodicidad.ShowDialog(this);
            }
        }

        private void buttonOK_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.textBoxTitle.Text))
            {
                MessageBox.Show(this, "¡Debe indicar el título!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxTitle.Focus();
                return;
            }
            if (String.IsNullOrEmpty(this.textBoxDescription.Text))
            {
                MessageBox.Show(this, "¡Debe indicar la descripción!", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.textBoxDescription.Focus();
                return;
            }
            pageInformation.title=this.textBoxTitle.Text;
            pageInformation.description=this.textBoxDescription.Text;
            pageInformation.version = ((VersionInfo)this.comboBoxVersiones.SelectedItem).nameOfVersion;
            OfficeApplication.OfficeDocumentProxy.updatePorlet(pageInformation);
            if (this.checkBoxActive.Checked)
            {
                if (this.pageInformation.active)
                {
                    OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
                }
                else
                {
                    if (OfficeApplication.OfficeDocumentProxy.needsSendToPublish(pageInformation))
                    {
                        this.checkBoxActive.Checked = pageInformation.active;
                        DialogResult res = MessageBox.Show(this, "El documento requiere una autorización para activarse\r\n¿Desea envíar a publicar el contenido?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                        if (res == DialogResult.Yes)
                        {
                            FormSendToAutorize formSendToAutorize = new FormSendToAutorize(pageInformation);
                            formSendToAutorize.ShowDialog();
                            if (formSendToAutorize.DialogResult == DialogResult.OK)
                            {
                                OfficeApplication.OfficeDocumentProxy.sendToAuthorize(pageInformation, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                            }
                            else
                            {
                                MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);                                
                            }
                        }
                        else
                        {
                            MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);                            
                        }
                    }
                    else if (OfficeApplication.OfficeDocumentProxy.isInFlow(pageInformation))
                    {
                        this.checkBoxActive.Checked = pageInformation.active;
                        MessageBox.Show(this, "El contenido se encuentra en proceso de ser autorizado.\r\nPara activarlo necesita terminar el proceso de autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);                                
                    }
                    else if (OfficeApplication.OfficeDocumentProxy.isAuthorized(pageInformation))
                    {

                        OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
                    }
                    else
                    {
                        this.checkBoxActive.Checked = pageInformation.active;
                        DialogResult res=MessageBox.Show(this, "El contenido fue rechazado.\r\nPara activarlo necesita enviarlo a autorización de nuevo\r\n¿Desea enviarlo a autorización?", this.Text, MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                        FormSendToAutorize formSendToAutorize = new FormSendToAutorize(pageInformation);
                        formSendToAutorize.ShowDialog();
                        if (formSendToAutorize.DialogResult == DialogResult.OK)
                        {
                            OfficeApplication.OfficeDocumentProxy.sendToAuthorize(pageInformation, formSendToAutorize.pflow, formSendToAutorize.textBoxMessage.Text);
                        }
                        else
                        {
                            MessageBox.Show(this, "El contenido no se activo, ya que se requiere una autorización", this.Text, MessageBoxButtons.OK, MessageBoxIcon.Information);
                        }
                    }
                }
            }
            else
            {
                OfficeApplication.OfficeDocumentProxy.activateResource(pageInformation, this.checkBoxActive.Checked);
            }
            PropertyInfo[] properties = null;
            String[] values = null;
            if (obj != null)
            {
                properties = OfficeApplication.OfficeDocumentProxy.getResourceProperties(repositoryName, contentID);
                values = TypeFactory.getValues(properties, obj);
                try
                {
                    OfficeApplication.OfficeDocumentProxy.validateViewValues(repositoryName, contentID, properties, values);
                }
                catch (Exception ue)
                {
                    MessageBox.Show(this, ue.Message, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
                    tabPageProperties.Focus();
                    return;
                }
            }
            try
            {
                this.Cursor = Cursors.WaitCursor;
                if (properties != null && values != null)
                {
                    int i = 0;
                    foreach (PropertyInfo prop in properties)
                    {
                        String value = values[i];
                        OfficeApplication.OfficeDocumentProxy.setViewPropertyValue(this.pageInformation, prop, value);
                        i++;
                    }
                }
            }
            catch (Exception ue)
            {
                MessageBox.Show(this, ue.Message, this.Text, MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

            this.DialogResult = DialogResult.OK;
        }
    }
}
