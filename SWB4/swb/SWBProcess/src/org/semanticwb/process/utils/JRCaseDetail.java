/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

package org.semanticwb.process.utils;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBResourceURL;

import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;
import org.semanticwb.portal.admin.resources.reports.beans.IncompleteFilterException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.semanticwb.process.model.ProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class JRCaseDetail implements JRDataSourceable {

    SWBResourceURL url = null;
    Iterator<ProcessInstance> itpinst = null;

    /**
     * Instantiates a new jR case access data detail.
     *
     * @param filterReportBean the filter report bean
     */
    public JRCaseDetail(Iterator<ProcessInstance> itpinst, SWBResourceURL url) {
        this.url = url;
        this.itpinst = itpinst;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#orderJRReport()
     */
    public JRBeanCollectionDataSource orderJRReport() throws IncompleteFilterException {
        List dataList = execute();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        return dataSource;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#prepareJRReport()
     */
    public void prepareJRReport() throws JRException {
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable#exportReport(javax.servlet.http.HttpServletResponse)
     */
    public void exportReport(HttpServletResponse response) throws java.io.IOException, JRException {
    }

    private List execute() {
        List ejbs = new ArrayList();
        EJBProcessInstance ejb = null;
        while (itpinst.hasNext()) {
            ejb = new EJBProcessInstance(itpinst.next());
            ejbs.add(ejb);
        }
        return ejbs;
    }
}
