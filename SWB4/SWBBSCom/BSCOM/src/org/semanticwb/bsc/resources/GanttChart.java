/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


/**
 * Genera el c&oacute;digo HTML para presentar una gr&aacute;fica de Gantt con los datos 
 * de los entregables activos de la iniciativa cuyo uri se recibe.
 * @author Jose Jimenez
 */
public class GanttChart extends GenericResource {

    
    /** Realiza operaciones en la bitacora de eventos. */
    private static final Logger log = SWBUtils.getLogger(GanttChart.class);
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        SemanticObject semanticObj = SemanticObject.createSemanticObject(suri);
        StringBuilder output = new StringBuilder(512);
        StringBuilder dataOut = new StringBuilder(512);

        if (semanticObj != null) {
            GenericObject genericObj = semanticObj.createGenericInstance();
            if (genericObj instanceof Initiative) {
                Initiative initiative = (Initiative) genericObj;
                Date today = new Date();
                int countDeliverable = 0;
                
                GenericIterator<Deliverable> iterator = initiative.listDeliverables();
                dataOut.append("[");
                if (initiative.getPlannedStart() != null && initiative.getPlannedEnd() != null) {
                    dataOut.append("{");
                    dataOut.append("  taskName : \"");
                    dataOut.append(initiative.getTitle());
                    dataOut.append(paramRequest.getLocaleString("lbl_planned"));
                    dataOut.append("\",\n");
                    dataOut.append("  startDate : new Date(" );
                    dataOut.append(initiative.getPlannedStart().getTime());
                    dataOut.append("),\n");
                    dataOut.append("  endDate : new Date(");
                    dataOut.append(initiative.getPlannedEnd().getTime());
                    dataOut.append("),\n");
                    dataOut.append("  status : \"#33B5E5\"");
                    dataOut.append("},\n");
                    if (initiative.getActualStart() != null) {
                        dataOut.append("{");
                        dataOut.append("  taskName : \"");
                        dataOut.append(initiative.getTitle());
                        dataOut.append(paramRequest.getLocaleString("lbl_actual"));
                        dataOut.append("\",\n");
                        dataOut.append("  startDate : new Date(" );
                        dataOut.append(initiative.getActualStart().getTime());
                        dataOut.append("),\n");
                        dataOut.append("  endDate : new Date(");
                        if (initiative.getActualEnd() != null) {
                            dataOut.append(initiative.getActualEnd().getTime());
                        } else {
                            dataOut.append(today.getTime());
                        }
                        dataOut.append("),\n");
                        dataOut.append("  status : ");
                        //En base al status asignar color
                        if (initiative.getState() != null) {
                            dataOut.append("\"");
                            dataOut.append(initiative.getState().getColorHex());
                            dataOut.append("\"");
                        } else {
                            dataOut.append("\"#CCCCCC\"");
                        }
                        dataOut.append("},\n");
                    }
                }
                while (iterator != null && iterator.hasNext()) {
                    Deliverable deli = iterator.next();
                    if (deli.isActive()) {
                        boolean anotherDeliverable = false;
                        try {
                            if (deli.getPlannedStart() != null && deli.getPlannedEnd() != null) {
                                if (countDeliverable > 0) {
                                    dataOut.append(",\n");
                                }
                                dataOut.append("{");
                                dataOut.append("  taskName : \"");
                                dataOut.append(deli.getTitle());
                                dataOut.append(paramRequest.getLocaleString("lbl_planned"));
                                dataOut.append("\",\n");
                                dataOut.append("  startDate : new Date(" );
                                dataOut.append(deli.getPlannedStart().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                dataOut.append(deli.getPlannedEnd().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  status : \"#33B5E5\"");
                                dataOut.append("}");
                                countDeliverable++;
                                anotherDeliverable = true;
                            }
                            if (deli.getActualStart() != null) {
                                if (anotherDeliverable) {
                                    dataOut.append(",\n");
                                }
                                dataOut.append("{");
                                dataOut.append("  taskName : \"");
                                dataOut.append(deli.getTitle());
                                dataOut.append(paramRequest.getLocaleString("lbl_actual"));
                                dataOut.append("\",\n");
                                dataOut.append("  startDate : new Date(" );
                                dataOut.append(deli.getActualStart().getTime());
                                dataOut.append("),\n");
                                dataOut.append("  endDate : new Date(");
                                if (deli.getActualEnd() != null) {
                                    dataOut.append(deli.getActualEnd().getTime());
                                } else {
                                    dataOut.append(today.getTime());
                                }
                                dataOut.append("),\n");
                                dataOut.append("  status : ");
                                //En base al status asignar color
                                if (deli.getAutoStatus() != null) {
                                    dataOut.append(deli.getAutoStatus().getColorHex());
                                } else {
                                    dataOut.append("\"#CCCCCC\"");
                                }
                                dataOut.append("}\n");
                            }
                        } catch (SWBResourceException e) {
                            GanttChart.log.error("Al generar estructura de datos", e);
                        }
                    }
                }
                dataOut.append("]");
                
                output.append("");
                output.append("<div id=\"ganttChart\">\n");
                output.append("  <script type=\"text/javascript\" src=\"");
                output.append(SWBPlatform.getContextPath());
                output.append("/swbadmin/js/gantt-chart-d3.js\"></script>\n");
                output.append("</div>\n");
                output.append("  <script type=\"text/javascript\">\n");
                output.append("    var tasks = ");
                output.append(dataOut.toString());
                output.append(";\n");
                output.append("  var taskNames = new Array();\n");
                output.append("  for (i = 0; i < tasks.length; i++) {\n");
                output.append("    if (taskNames.indexOf(tasks[i].taskName) == -1) {\n");
                output.append("      taskNames.push(tasks[i].taskName);\n");
                output.append("    }\n");
                output.append("  }\n");
                output.append("  tasks.sort(function(a, b) {\n");
                output.append("    return a.endDate - b.endDate;\n");
                output.append("  });\n");
                output.append("  var maxDate = tasks[tasks.length - 1].endDate;\n");
                output.append("  tasks.sort(function(a, b) {\n");
                output.append("    return a.startDate - b.startDate;\n");
                output.append("  });\n");
                output.append("  var minDate = tasks[0].startDate;\n");
                output.append("  var format = \"%d/%m/%Y\";\n");
                output.append("  \n");
                output.append("  var gantt = d3.gantt().taskTypes(taskNames).tickFormat(format).timeDomainMode(\"fit\");\n");
                output.append("  gantt(tasks);\n");
                output.append("  \n");
                output.append("  \n");
                output.append("  </script>\n");
                output.append("");
            }
        }
        out.println(output.toString());
    }
    
    
}