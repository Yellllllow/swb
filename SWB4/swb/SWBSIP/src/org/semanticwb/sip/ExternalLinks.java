/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.sip;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author martha.jimenez
 */
public class ExternalLinks extends GenericResource{

    private static Logger log = SWBUtils.getLogger(ExternalLinks.class);
    ArrayList links = new ArrayList();

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try{
            RequestDispatcher dis= request.getRequestDispatcher(SWBPlatform.getContextPath()+"/swbadmin/jsp/sip/externalLinks/ExternalLinks.jsp");
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        }catch(Exception e){
            log.error(e);
        }

    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = paramRequest.getResourceBase();
        PrintWriter out = response.getWriter();
        SWBResourceURL addLink = paramRequest.getRenderUrl();
        SWBResourceURL urltitle = paramRequest.getActionUrl();
        SWBResourceURL urlremov = paramRequest.getActionUrl();
        SWBResourceURL urlact=paramRequest.getActionUrl();
        Iterator<String> it = base.getAttributeNames();
        String title="";
        addLink.setMode("addLink");
        urlremov.setAction("removLink");
        urltitle.setAction("addTitle");
        urlact.setMode("actLink");
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        out.println(" dojo.require(\"dijit.form.Button\");");
        out.println("function valida() {");
        out.println("    var val = true;");
        out.println("    if(document.getElementById(\"title\").value==\"\") {");
        out.println("        alert('¡Debe ingresar el T&iacute;tulo del Bloque!');");
        out.println("        document.getElementById(\"title\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    return val");
        out.println("}");
        out.println("</script>");
        while(it.hasNext()){
            String attr = it.next();
            if(attr.equals("title")){
                title=base.getAttribute(attr);
            }
        }
        out.println("<form name=\"frmtitle\" id=\"frmtitle\" class=\"swbform\" method=\"post\" action=\""+urltitle+"\" onsubmit=\"return valida(this);\" onsubmit=\"return valida(this);\">");
        out.println("<p>");
        out.println("         <label for=\"title\">T&iacute;tulo del Bloque: </label>");
        out.println("         <input name=\"title\" id=\"title\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" value=\""+title+"\">");
        out.println("</p>");
        out.println("        <input type=\"submit\" name=\"Cambiar titulo\" >");
        
        out.println("<a href=\""+addLink+"\">Agregar Links</a><br />");
        it = base.getAttributeNames();
        out.println("<table dojoType=\"dojox.Grid\">");
        out.println("<th>Url </th>");
        out.println("<th> Nombre de la Url </th>");
        while(it.hasNext()){
            String attr = it.next();
            if(attr.startsWith("name")){
                String x = attr.substring(4);
                out.println("<tr>");
                String name = base.getAttribute(attr);
                String url=base.getAttribute("link"+x);
                urlremov.setParameter("name", name);
                out.println("<td>"+url+"</td>");
                out.println("<td></td>");
                out.println("<td>"+name+"</td>");
                out.println("<td><a href=\""+urlremov+"\">Eliminar</a></td>");
                out.println("<td><a href=\""+urlact+"\">Actualizar</a></td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("</form>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(mode.equals("addLink"))
            addLink(request, response, paramRequest);
        else if(mode.equals("actLink"))
            doEdit(request, response, paramRequest);
        else
            super.processRequest(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.doEdit(request, response, paramRequest);
    }


    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = response.getResourceBase();
        if(action.equals("addsLink"))
        {
            int indice;
            try {
                indice = Integer.parseInt(base.getAttribute("indice", "0"));
            }catch(NumberFormatException er) {
                indice=0;
            }
            String name = "name"+indice;
            String nlink = "link"+indice;
            base.setAttribute("indice", Integer.toString(++indice));
            base.setAttribute(name, request.getParameter("nameLink"));
            base.setAttribute(nlink, request.getParameter("link"));
            try{
                base.updateAttributesToDB();
            }catch(SWBException e){
                log.error(e);
            }
        }else if(action.equals("addTitle"))
        {
            try{
                base.setAttribute("title", request.getParameter("title"));
            }catch(Exception e){
            }
        }else if(action.equals("removLink")){
            String attrib = request.getParameter("name");//getAttribute("name").toString();
            
        }
        response.setMode(response.Mode_ADMIN);
    }


    public void addLink(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL addLinks = paramRequest.getActionUrl();
        addLinks.setAction("addsLink");
        out.println("<script type=\"text/javascript\">");
        out.println(" dojo.require(\"dijit.form.ValidationTextBox\");");
        out.println(" dojo.require(\"dijit.form.Button\");");
        out.println("function validaForma() {");
        out.println("    var val = true;");
        out.println("    if(document.getElementById(\"nameLink\").value==\"\") {");
        out.println("        alert('¡Debe ingresar el nombre de la Liga!');");
        out.println("        document.getElementById(\"nameLink\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    if(document.getElementById(\"link\").value==\"\") {");
        out.println("        alert('¡Debe ingresar la Liga!');");
        out.println("        document.getElementById(\"link\").focus();");
        out.println("        val false;");
        out.println("    }");
        out.println("    return val");
        out.println("}");
        out.println("</script>");
        out.println("<form name=\"frmNewLink\" id=\"frmNewLink\" class=\"swbform\" method=\"post\" action=\""+addLinks+"\" onsubmit=\"return validaForma(this);\">");
        out.println("    <input type=\"hidden\" name=\"title\" value=\""+request.getParameter("title")+"\"/>");
        out.println("    <fieldset>");
        out.println("        <legend>Añadir Link</legend>");
        out.println("        <p>");
        out.println("         <label for nameLink>Nombre de la Liga: </label><br/>");
        out.println("         <input name=\"nameLink\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\">");
        out.println("        </p>");
        out.println("        <p>");
        out.println("         <label for link>Liga: </label><br/>");
        out.println("         <input name=\"link\" type=\"text\" size=\"45\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\">");
        out.println("        </p>");
        out.println("        <input type=\"submit\" name=\"Enviar\" value=\"Enviar\">");
        out.println("    </fieldset>");
        out.println("</form>");
    }

}
