/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.text.Collator;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;

/**
 *
 * @author juan.fernandez
 */
public class SWBASearchUsers extends GenericResource {

    private Logger log=SWBUtils.getLogger(SWBASearchUsers.class);
    
    public SWBASearchUsers() { 
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode("search");
        Iterator<UserRepository> itur = SWBContext.listUserRepositorys();
        ret.append("  <form id=\"SWBASearchUsers\" name=\"SWBASearchUsers\" method=\"post\" action=\""+url+"\">\n");
        ret.append("<fieldset>\n");
       // ret.append("  <legend>Busqueda usuarios</legend>\n");
        ret.append("<table>\n");
        ret.append("    <tr><td>Repositorio de usuarios</td>\n");
        ret.append("    <td><select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" name=\"userRepository\" id=\"userRepository\">\n");
        while(itur.hasNext()){
            UserRepository ur = itur.next();
            ret.append("        <option value=\""+ur.getId()+"\">"+ur.getTitle()+"</option>\n"); //todo Add Language
        }
        ret.append("    </select>\n");
        ret.append("    </td></tr>\n");
        ret.append("    <tr><td>Correo Electrónico</td>\n");
        ret.append("    <td><input type=\"text\" name=\"usrEMail\" id=\"usrEMail\" dojoType=\"dijit.form.TextBox\" />\n");
        ret.append("    </td></tr>\n");
        ret.append("    <tr><td>Nombre(s)</td>\n");
        ret.append("    <td><input type=\"text\" name=\"usrFirstName\" id=\"usrFirstName\" dojoType=\"dijit.form.TextBox\" />\n");
        ret.append("    </td></tr>\n");
        ret.append("    <tr><td>Primer Apellido</td>\n");
        ret.append("    <td><input type=\"text\" name=\"usrLastName\" id=\"usrLastName\" dojoType=\"dijit.form.TextBox\" />\n");
        ret.append("    </td></tr>\n");
        ret.append("    <tr><td>Segundo Apellido</td>\n");
        ret.append("    <td><input type=\"text\" name=\"usrSecondLastName\" id=\"usrSecondLastName\" dojoType=\"dijit.form.TextBox\" />\n");
        ret.append("    </td></tr>\n");
        Iterator<Role>itroles = SWBContext.getDefaultRepository().listRoles();
        ret.append("    <tr><td>Roles</td>\n");
        ret.append("    <td><select name=\"userRoles\" id=\"userRoles\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" >\n");
        while(itroles.hasNext()){
            Role role = itroles.next();
            ret.append("        <option value=\""+role.getId()+"\">"+role.getTitle()+"</option>\n"); //todo Add Language
        }
        ret.append("    </select>\n");
        ret.append("    </td></tr>\n");
        Iterator<UserGroup>itgroup = SWBContext.getDefaultRepository().listUserGroups();
        ret.append("    <tr><td>Grupos de usuarios</td>\n");
        ret.append("    <td><select name=\"userGroups\" id=\"userGroups\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" >\n");
        while(itgroup.hasNext()){
            UserGroup group = itgroup.next();
            ret.append("        <option value=\""+group.getURI()+"\">"+group.getId()+"</option>\n"); //todo Add Language
        }
        ret.append("    </select>\n");
        ret.append("    </td></tr>\n");
        ret.append("</table>");
        ret.append("</fieldset>\n");
        ret.append("<fieldset>\n");
        ret.append("    <input name=\"search\" type=\"submit\" value=\"Buscar\" />\n");
        ret.append("</fieldset>\n");
        ret.append("  </form>\n");
        response.getWriter().write(ret.toString());
    }

    public void doSearch(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException
    {
        StringBuffer ret = new StringBuffer("");
        String usrep  = request.getParameter("userRepository");
        String usrFirstName = request.getParameter("usrFirstName");
        String usrLastName = request.getParameter("usrLastName");
        String usrSecondLastName = request.getParameter("usrSecondLastName");
        String usrEmail = request.getParameter("usrEMail");
        UserRepository ur  =SWBContext.getUserRepository(usrep);
        Iterator<String> itst = ur.searchUsersBy(usrFirstName, usrLastName, usrSecondLastName, usrEmail);
        ret.append("<table><tr><td>login</td><td>Nombre(s)</td><td>Primer Apellido</td><td>Segundo Apellido</td><td>Correo electr&oacute;nico</td></tr>");
        while (itst.hasNext()){
            String[] valores = itst.next().split("\\|\\|");
           ret.append("<tr><td><a href=\"javascript:addNewTab('"+valores[0]+"', '"+valores[5]+"');\">");
           ret.append(valores[5]);
           ret.append("</a></td><td>");
           ret.append(valores[1]);
           ret.append("</td><td>");
           ret.append(valores[2]);
           ret.append("</td><td>");
           ret.append(valores[3]);
           ret.append("</td><td>");
           ret.append(valores[4]);
           ret.append("</td></tr>");

        }
        ret.append("</table>");
        response.getWriter().write(ret.toString());
    }
//
//    @Override
//    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
//    
//        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : response.getAction();
//        try
//        {
//            User wbuser = response.getUser();
//            UserSrv srv=new UserSrv();
//            String msg=response.getLocaleString("msgUndefinedOperation");
//            if(action.equals("update"))
//            { 
//                boolean bOk=true;
//                String[] users = null != request.getParameter("deactivate") && !"".equals(request.getParameter("deactivate").trim()) ? request.getParameter("deactivate").trim().split(",") : null;
//                if(users!=null)
//                {
//                    if(!srv.activateUsers(request.getParameter("rep"), users, 0, wbuser.getId())) bOk=false;
//                }
//                users=request.getParameterValues("_active");
//                if(users!=null && users.length > 0)
//                {
//                    if(!srv.activateUsers(request.getParameter("rep"), users, 1, wbuser.getId())) bOk=false;
//                }
//                if(bOk) msg=response.getLocaleString("msgOkUpdate");
//                else msg=response.getLocaleString("msgErrUpdate");
//            }
//            if(action.equals("remove"))
//            { 
//                if(srv.removeUsers(request.getParameter("rep"), request.getParameterValues("_remove"), wbuser.getId()))
//                    msg=response.getLocaleString("msgOkRemove");
//                else msg=response.getLocaleString("msgErrRemove");
//            }
//            response.setRenderParameter("_msg", msg);
//        }
//        catch(Exception e){ log.error(e); }
//        response.setMode(response.Mode_VIEW);
//    }
//
//    @Override
//    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        StringBuffer ret = new StringBuffer("");
//        User user=paramRequest.getUser();
//        String rep = null != request.getParameter("rep") && !"".equals(request.getParameter("rep").trim()) ? request.getParameter("rep").trim() : SWBContext.getDefaultRepository().getId();
//        String firstname =  null != request.getParameter("firstname") && !"".equals(request.getParameter("firstname").trim()) ? request.getParameter("firstname").trim() : "";
//        String lastname =  null != request.getParameter("lastname") && !"".equals(request.getParameter("lastname").trim()) ? request.getParameter("lastname").trim() : "";
//        String middlename =  null != request.getParameter("middlename") && !"".equals(request.getParameter("middlename").trim()) ? request.getParameter("middlename").trim() : "";
//        String email =  null != request.getParameter("email") && !"".equals(request.getParameter("email").trim()) ? request.getParameter("email").trim() : "";
//        String usertype = null != request.getParameter("usertype") && !"".equals(request.getParameter("usertype").trim()) ? request.getParameter("usertype").trim() : "";
//        String role = null != request.getParameter("role") && !"".equals(request.getParameter("role").trim()) ? request.getParameter("role").trim() : "";
//        String active = null != request.getParameter("active") && !"".equals(request.getParameter("active").trim()) ? request.getParameter("active").trim() : "2";
//        String seg = null != request.getParameter("seg") && !"".equals(request.getParameter("seg").trim()) ? request.getParameter("seg").trim() : "10";
//
//        if(request.getParameter("_msg")!=null)
//        {
//            ret.append(
//                "<script language=\"JavaScript\">\n"+
//                "   alert('"+request.getParameter("_msg")+"');\n"+
//                "</script>\n");
//        }        
//        ret.append("<div class=\"box\"> \n");
//        ret.append("<form name=\"frmSearch\" method=\"post\"> \n");
//        ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgRepository")+"</td> \n");
//        ret.append("<td class=\"valores\"> \n");
//        ret.append("<select name=\"rep\" onChange=\"this.form.submit();\"> \n");
//        ret.append("<option value=\"\">" + paramRequest.getLocaleString("msgOption") + "</option>");
//        Iterator<UserRepository> it=SWBContext.listUserRepositorys();
//        while(it.hasNext())
//        {
//            UserRepository userep=it.next();
//            //userep=DBUser.getInstance(userep.getName());
//            if(userep!=null && 2 == AdmFilterMgr.getInstance().haveAccess2UserRep(user, userep.getTitle()))
//            {
//                ret.append("<option value=\""+userep.getTitle()+"\"");
//                if(userep.getTitle().equals(rep)) ret.append(" selected");
//                ret.append(">"+userep.getTitle()+"</option> \n");
//            }
//        }
//        ret.append("</select> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgName")+":</td> \n");
//        ret.append("<td class=\"valores\"><input name=\"firstname\" type=\"text\" size=30 maxlength=50 value=\""+firstname+"\"></td> \n");
//        ret.append("</tr> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgLastName")+"</td> \n");
//        ret.append("<td class=\"valores\"><input name=\"lastname\" type=\"text\" size=30 maxlength=50 value=\""+lastname+"\"></td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgMiddleName")+"</td> \n");
//        ret.append("<td class=\"valores\"><input name=\"middlename\" type=\"text\" size=30 maxlength=50 value=\""+middlename+"\"></td> \n");
//        ret.append("</tr> \n");        
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgEmail")+":</td> \n");
//        ret.append("<td class=\"valores\"><input name=\"email\" type=\"text\" size=30 maxlength=99 value=\""+email+"\"></td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgUserTypes")+"</td> \n");
//        ret.append("<td class=\"valores\">\n");
//        ret.append("<select name=\"usertype\" size=\"5\" multiple>\n");
//        ret.append("<option value=''>"+paramRequest.getLocaleString("msgOption")+"</option>");
//        TreeMap map =  getUserTypes(rep, paramRequest.getUser().getLanguage());
//        /*
//        for(int i = 0; i < usrtypes.size(); i++)
//        {
//            String type = (String) usrtypes.get(Integer.toString(i));
//            ret.append("<option value=\""+ type + "\"");
//            if(usertype.equals(type)) ret.append(" selected");
//            ret.append(">" + type + "</option>");
//        }
//        */
//        it=map.keySet().iterator();
//        while(it.hasNext())        
//        {
//            String type = (String) it.next();
//            String name = (String)map.get(type);
//            ret.append("<option value=\""+ name + "\"");
//            if(usertype.equals(name)) ret.append(" selected");
//            ret.append(">" + type + "</option>");
//        }        
//        ret.append("</select> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");        
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgRoles")+"</td> \n");
//        ret.append("<td class=\"valores\">\n");
//        ret.append("<select name=\"role\" size=\"5\" multiple>\n");
//        ret.append("<option value=''>"+paramRequest.getLocaleString("msgOption")+"</option>");
//        map=new TreeMap(Collator.getInstance(new Locale(paramRequest.getUser().getLanguage())));
//        Iterator<Role> en=SWBContext.getUserRepository(rep).listRoles();
//        while(en.hasNext())
//        {
//            Role recRole=en.next();
//            if(recRole!=null) map.put(recRole.getTitle(), recRole);
//        }        
//        it=map.values().iterator();
//        while(it.hasNext())
//        {
//            RecRole recRole=(RecRole)it.next();
//            ret.append("<option value=\""+recRole.getId()+"\">"+recRole.getName()+"</option> \n");
//        }
//        ret.append("</select> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgFiltersAdmin")+"</td> \n");
//        ret.append("<td class=\"valores\">\n");
//        ret.append("<select name=\"filteradmin\" size=\"5\" multiple>\n");
//        ret.append("<option value=''>"+paramRequest.getLocaleString("msgOption")+"</option>");
//        map=new TreeMap(Collator.getInstance(new Locale(paramRequest.getUser().getLanguage())));
//        en=DBAdmFilter.getInstance().getAdmFilters(TopicMgr.TM_ADMIN);
//        while(en.hasMoreElements())
//        {
//            RecAdmFilter filter = (RecAdmFilter)en.nextElement();
//            if(filter!=null) map.put(filter.getName(), filter);
//        } 
//        it=map.values().iterator();
//        while(it.hasNext())        
//        {
//            RecAdmFilter filter = (RecAdmFilter)it.next();
//            ret.append("<option value=\""+TopicMgr.TM_ADMIN+"|"+filter.getId()+"\">"+filter.getName()+"</option> \n");
//        }
//        ret.append("</select> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgFiltersSite")+"</td> \n");
//        ret.append("<td class=\"valores\">\n");
//        ret.append("<select name=\"filtersite\" size=\"5\" multiple>\n");
//        ret.append("<option value=''>"+paramRequest.getLocaleString("msgOption")+"</option>");
//        map=new TreeMap(Collator.getInstance(new Locale(paramRequest.getUser().getLanguage())));
//        en=DBAdmFilter.getInstance().getAdmFilterMaps();
//        while(en.hasMoreElements())
//        {
//            String tm=(String)en.nextElement();
//            if(!tm.equals(TopicMgr.TM_ADMIN))
//            {
//                Enumeration ft=DBAdmFilter.getInstance().getAdmFilters(tm);
//                while(ft.hasMoreElements())
//                {
//                    RecAdmFilter filter = (RecAdmFilter)ft.nextElement();
//                    if(filter!=null) map.put(filter.getName(), filter);
//                }
//            }
//        } 
//        it=map.values().iterator();
//        while(it.hasNext())        
//        {
//            RecAdmFilter filter = (RecAdmFilter)it.next();
//            ret.append("<option value=\""+filter.getTopicMapId()+"|"+filter.getId()+"\">"+filter.getName()+"</option> \n");
//        }
//        ret.append("</select> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");        
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgActive")+"</td> \n");
//        ret.append("<td class=\"valores\">");
//        ret.append("<input name=\"active\" type=\"radio\" value=1");
//        if("1".equals(active)) ret.append(" checked");
//        ret.append("> "+paramRequest.getLocaleString("msgYes")+"   &nbsp;&nbsp; \n");
//        ret.append("<input name=\"active\" type=\"radio\" value=0");
//        if("0".equals(active)) ret.append(" checked"); 
//        ret.append("> "+paramRequest.getLocaleString("msgNo")+"   &nbsp;&nbsp; \n");
//        ret.append("<input name=\"active\" type=\"radio\" value=2");
//        if("2".equals(active)) ret.append(" checked");
//        ret.append("> "+paramRequest.getLocaleString("msgBoth")+" \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");
//        ret.append("<tr> \n");
//        ret.append("<td width=\"150\" class=\"datos\">"+paramRequest.getLocaleString("msgResults")+"</td> \n");
//        ret.append("<td class=\"valores\"> \n");
//        ret.append("<select name=\"seg\"> \n");
//        ret.append("<option value=\"10\"");
//        if("10".equals(seg)) ret.append(" selected");
//        ret.append(">10</option> \n");
//        ret.append("<option value=\"20\"");
//        if("20".equals(seg)) ret.append(" selected");
//        ret.append(">20</option> \n");
//        ret.append("<option value=\"30\"");
//        if("30".equals(seg)) ret.append(" selected");
//        ret.append(">30</option> \n");
//        ret.append("</select> \n");
//        ret.append(" "+paramRequest.getLocaleString("msgRows")+"</td> \n");
//        ret.append("</tr> \n");         
//        ret.append("<tr> \n");
//        ret.append("<tr><td colspan=\"2\"><HR size=\"1\" noshade></td></tr> \n");
//        ret.append("<td align=\"right\" colspan=\"2\"> \n");
//        ret.append("<input type=button name=search value=\""+paramRequest.getLocaleString("btnSearch")+"\" onClick=\"javascript:send(this.form, 'search');\" class=\"boton\"> \n");
//        //ret.append("&nbsp;&nbsp;\n");
//        //ret.append("<input type=button name=add value=\""+paramRequest.getLocaleString("btnAdd")+"\" onClick=\"javascript:send(this.form, 'add');\" class=\"boton\"> \n");
//        //ret.append("&nbsp;&nbsp;\n");
//        ret.append("<input type=reset name=reset value=\""+paramRequest.getLocaleString("btnReset")+"\" class=\"boton\"> \n");
//        ret.append("</td> \n");
//        ret.append("</tr> \n");
//        ret.append("</table> \n");
//        ret.append("</form> \n");
//        ret.append("</div> \n");
//        ret.append("<script language=\"JavaScript\" type=\"text/JavaScript\"> \n");
//        ret.append("  if (document.frmSearch.rep.length==undefined || document.frmSearch.rep.length==1) { \n");
//        ret.append("     document.frmSearch.search.disabled=true;\n");
//        ret.append("     alert('"+paramRequest.getLocaleString("msgNotAccess")+"')\n");
//        ret.append("  } \n");
//        ret.append("function send(form, action) \n");
//        ret.append("{ \n");
//        ret.append("\n   if(form.rep.selectedIndex==0 || form.rep.options[form.rep.selectedIndex].value=='' || form.rep.options[form.rep.selectedIndex].value==' ')");
//        ret.append("\n   {");
//        ret.append("\n      alert('" + paramRequest.getLocaleString("msgRepositoryRequired") + "');");
//        ret.append("\n      form.rep.focus();");
//        ret.append("\n      return false;");
//        ret.append("\n   }");        
//        SWBResourceURL url = paramRequest.getRenderUrl().setMode("search");
//        /*
//        ret.append("    if(event.srcElement.name=='search') form.action='"+url.toString()+"'; \n");
//        ret.append("    if(event.srcElement.name=='add') form.action='");
//        ret.append(AFUtils.getInstance().getEnv("wb/webPath")+AFUtils.getInstance().getEnv("wb/distributor")+"/WBAdmin/WBAd_sys_UsersInfo");
//        ret.append("'; \n");
//         */
//        ret.append("    if(action=='search') form.action='"+url.toString()+"'; \n");
//        ret.append("    if(action=='add') form.action='");
//        ret.append(SWBPlatform.getEnv("wb/webPath")+SWBPlatform.getEnv("wb/distributor")+"/WBAdmin/WBAd_sys_UsersInfo");
//        ret.append("'; \n");
//        ret.append("    form.submit(); \n");
//        ret.append("} \n");
//        ret.append("</script> \n");        
//        response.getWriter().print(ret.toString());
//    }
//
//    public void doSearch (javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException 
//    {
//        StringBuffer ret = new StringBuffer("");
//        ArrayList users = getUsers(request);        
//        try
//        {
//            String rep = request.getParameter("rep");
//            ret.append("<script language=\"JavaScript\" type=\"text/JavaScript\">\n");
//            ret.append("function send(form, action) \n");
//            ret.append("{ \n");
//            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW);
//            ret.append("    if(action=='search') form.action='"+url.toString()+"'; \n");
//            ret.append("    if(action=='add') form.action='");
//            ret.append(SWBPlatform.getEnv("wb/webPath")+SWBPlatform.getEnv("wb/distributor")+"/WBAdmin/WBAd_sys_UsersInfo?rep"+rep);
//            ret.append("'; \n");
//            url = paramRequest.getActionUrl().setMode(paramRequest.Mode_EDIT); 
//            url.setAction("update");
//            url.setParameter("rep", rep);
//            ret.append("    if(action=='update') \n");
//            ret.append("    { \n");
//            ret.append("        if(!confirm('"+paramRequest.getLocaleString("msgConfirmActivate")+"')) return false; \n");
//            ret.append("        else \n");
//            ret.append("        { \n");
//            ret.append("            deactivate(form);\n");
//            ret.append("            form.action='"+url.toString()+"'; \n");
//            ret.append("        } \n");
//            ret.append("    } \n");
//            url.setAction("remove");
//            ret.append("    if(action=='remove') \n");
//            ret.append("    { \n");
//            ret.append("        if(!confirm('"+paramRequest.getLocaleString("msgConfirmRemove")+"')) return false; \n");
//            ret.append("        else form.action='"+url.toString()+"'; \n");
//            ret.append("    } \n");
//            ret.append("    form.submit(); \n");
//            ret.append("} \n");
//            ret.append("var checkact = \"false\"; \n");
//            ret.append("var checkrem = \"false\"; \n");
//            ret.append("function check(field, variable, msgcheck, msguncheck) \n");
//            ret.append("{ \n");
//            ret.append("    if (eval(variable+\"=='false'\")) \n");
//            ret.append("    { \n");
//            ret.append("        if (field.length!=undefined) \n");
//            ret.append("        { \n");
//            ret.append("            for (var i = 0; i < field.length; i++) field[i].checked = true; \n");
//            ret.append("        } \n");
//            ret.append("        else  field.checked = true; \n");
//            ret.append("        eval(variable+\"='true'\"); \n");
//            ret.append("        return msguncheck; \n");
//            ret.append("    } \n");
//            ret.append("    else \n");
//            ret.append("    { \n");
//            ret.append("        if (field.length!=undefined) \n");
//            ret.append("        { \n");
//            ret.append("            for (var i = 0; i < field.length; i++)  field[i].checked = false; \n");
//            ret.append("        } \n");
//            ret.append("        else  field.checked = false; \n");
//            ret.append("        eval(variable+\"='false'\"); \n");
//            ret.append("        return msgcheck; \n");
//            ret.append("    } \n");
//            ret.append("} \n");
//            ret.append("function deactivate(form) \n");
//            ret.append("{ \n");
//            ret.append("    if (form._active.length!=undefined) \n");
//            ret.append("    { \n");
//            ret.append("        form.deactivate.value=''; \n");
//            ret.append("        for (var i = 0; i < form._active.length; i++) \n");
//            ret.append("        { \n");
//            ret.append("            if (!form._active[i].checked) \n");
//            ret.append("            { \n");
//            ret.append("                if (form.deactivate.value!='') form.deactivate.value=form.deactivate.value+\",\";  \n");
//            ret.append("                form.deactivate.value=form.deactivate.value+form._active[i].value; \n");
//            ret.append("            } \n");
//            ret.append("        } \n");
//            ret.append("    } \n");
//            ret.append("    else if(!form._active.checked) \n");
//            ret.append("    { \n");
//            ret.append("        form.deactivate.value=form._active.value; \n");
//            ret.append("    } \n");
//            ret.append("    return; \n");
//            ret.append("} \n");            
//            ret.append("</script> \n");
//            ret.append("<div class=\"box\"> \n");
//            ret.append("<form name=\"frmSearch\" method=\"post\"> \n");
//            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \n");
//            if(users!=null && users.size() > 0) 
//            {
//                String start = request.getParameter("s");
//                int seg = 10;
//                try { seg = Integer.parseInt(request.getParameter("seg")); } 
//                catch (Exception e) { seg=10; }
//                int cont = 0;
//                int i = 0;      // Contador del segmento
//                int s = 1;      // Start
//                try { s = Integer.parseInt(start); } 
//                catch (Exception e) { s=1; }                
//                ret.append("<tr class=\"tabla\"> \n");
//                ret.append("<td>"+paramRequest.getLocaleString("msgID")+"</td> \n");
//                ret.append("<td>"+paramRequest.getLocaleString("msgName")+"</td> \n");
//                ret.append("<td>"+paramRequest.getLocaleString("msgEmail")+"</td> \n");
//                ret.append("<td><input type=\"button\" value=\""+paramRequest.getLocaleString("btnActivate")+"\" style=\"color:#0000FF; background-color:#FFFFFF; width=70; border:5; FONT-FAMILY: Verdana; FONT-SIZE: 12px; FONT-WEIGHT: normal\" onClick=\"this.value=check(this.form._active, 'checkact', '"+paramRequest.getLocaleString("btnActivate")+"', '"+paramRequest.getLocaleString("btnDeactivate")+"');\"></td>\n");
//                ret.append("<td><input type=\"button\" value=\""+paramRequest.getLocaleString("btnRemove")+"\" style=\"color:#0000FF; background-color:#FFFFFF; width=70; border:5; FONT-FAMILY: Verdana; FONT-SIZE: 12px; FONT-WEIGHT: normal\" onClick=\"this.value=check(this.form._remove, 'checkrem', '"+paramRequest.getLocaleString("btnRemove")+"', '"+paramRequest.getLocaleString("btnNotRemove")+"');\"></td>\n");
//                ret.append("</tr> \n");
//                for (int j=0; j < users.size(); j++) 
//                {
//                    User rusr=(User)users.get(j);
//                    if (rusr != null)
//                    {
//                        cont++;
//                        if (cont >= s && i < seg)
//                        {
//                            i++;
//                            String bgcolor="#EFEDEC";
//                            if(i%2==0) bgcolor="#FFFFFF";
//                            ret.append("<tr class=\"valores\" bgcolor=\""+bgcolor+"\"> \n");
//                            ret.append("<td><a href=\"");
//                            ret.append(SWBPlatform.getEnv("wb/webPath")+SWBPlatform.getEnv("wb/distributor")+"/WBAdmin/WBAd_sys_UsersInfo?rep="+rep+"&id="+rusr.getId());
//                            ret.append("\" class=\"link\">"+rusr.getId()+"</a></td> \n");
//                            ret.append("<td> \n");
//                            if(rusr.getUsrFirstName()!=null) ret.append(rusr.getUsrFirstName());
//                            if(rusr.getUsrLastName()!=null)  ret.append(" "+ rusr.getUsrLastName());
//                            if(rusr.getUsrSecondLastName()!=null)ret.append(" "+ rusr.getUsrSecondLastName());
//                            ret.append("</td> \n");
//                            ret.append("<td> \n");
//                            if(rusr.getUsrEmail()!=null) ret.append(rusr.getUsrEmail());
//                            ret.append("</td> \n");
//                            ret.append("<td align=\"center\"><input name=\"_active\" type=\"checkbox\" value="+rusr.getId());
//                            if(rusr.isActive()) ret.append(" checked");
//                            ret.append("></td> \n");
//                            ret.append("<td align=\"center\"><input name=\"_remove\" type=\"checkbox\" value="+rusr.getId()+"></td> \n");
//                            ret.append("<tr> \n");
//                        }
//                    }
//                }
//                ret.append("<tr class=\"valores\"> \n");
//                ret.append("<td colspan=5 align=center> \n");
//                ret.append("<br>"+paramRequest.getLocaleString("msgResults")+" "+ s +" - ");
//                if (i > 0) ret.append((s + i - 1));
//                else ret.append((s + i));
//                ret.append(" "+paramRequest.getLocaleString("msgOf")+" "+cont);
//                ret.append("</td></tr> \n");
//                ret.append("<tr class=\"valores\"> \n");
//                ret.append("<td colspan=5 align=center> \n");
//
//                String[] offset={"s"};
//                String _url=getParameters(request, offset);
//                if(cont > seg) ret.append("<br>"+paramRequest.getLocaleString("msgResults")+" ");
//                if (s > 1)
//                {
//                    int as = s - seg;
//                    if (as < 1) as = 1;
//                    ret.append("<a href=\""+ _url +"s="+as+"\" class=\"link\">"+paramRequest.getLocaleString("msgBack")+"</a> \n");
//                }
//                if (cont > seg)
//                {
//                    int p = (cont + seg - 1) / seg;
//                    for (int z = 0; z < p; z++)
//                    {
//                        if (s != ((z * seg) + 1)) ret.append("&nbsp;<a href=\""+ _url +"s="+((z * seg) + 1)+"\" class=\"link\">"+(z + 1)+"</a> \n");
//                        else ret.append("&nbsp;"+(z + 1));
//                    }
//                }
//                if (cont - 1 > s + seg) ret.append("&nbsp;<a href=\""+ _url +"s="+(s + seg)+"\" class=\"link\">"+paramRequest.getLocaleString("msgNext")+"</a> \n");
//                ret.append("</td></tr> \n");
//                ret.append("<tr> \n");
//                ret.append("<td align=\"right\" colspan=\"5\"> \n");
//                ret.append("<hr size=1 noshade> \n");
//                ret.append("<input type=hidden name=deactivate> \n");
//                ret.append("<input type=button name=search value=\""+paramRequest.getLocaleString("btnSearch")+"\" onClick=\"javascript:send(this.form, 'search');\" class=\"boton\"> \n");
//                //ret.append("&nbsp;&nbsp;\n");
//                ret.append("<input type=button name=update value=\""+paramRequest.getLocaleString("btnUpdate")+"\" onClick=\"javascript:send(this.form, 'update');\" class=\"boton\"> \n");
//                //ret.append("&nbsp;&nbsp;\n");
//                ret.append("<input type=button name=remove value=\""+paramRequest.getLocaleString("btnRemove")+"\" onClick=\"javascript:send(this.form, 'remove');\" class=\"boton\">\n");
//                //ret.append("&nbsp;&nbsp; \n");
//                ret.append("<input type=reset class=\"boton\" name=reset value=\""+paramRequest.getLocaleString("btnReset")+"\"> \n");
//                ret.append("</td> \n");
//                ret.append("</tr> \n");
//            }
//            else
//            {
//                ret.append("<tr> \n");
//                ret.append("<td align=\"center\" colspan=\"2\" class=\"datos\">"+paramRequest.getLocaleString("msgNotFound")+"</td> \n");
//                ret.append("</tr> \n");
//                ret.append("<tr> \n");
//                ret.append("<td align=\"right\" colspan=\"2\"> \n");
//                ret.append("<hr size=1 noshade> \n");
//                ret.append("<input type=button name=search value=\""+paramRequest.getLocaleString("btnSearch")+"\" onClick=\"javascript:send(this.form, 'search');\" class=\"boton\"> \n");
//                //ret.append("&nbsp;&nbsp;\n");
//                //ret.append("<input type=button name=add value=\""+paramRequest.getLocaleString("btnAdd")+"\" onClick=\"javascript:send(this.form, 'add');\" class=\"boton\"> \n");
//                ret.append("</td> \n");
//                ret.append("</tr> \n");               
//            }
//            ret.append("</table> \n");
//            ret.append("</form> \n");
//            ret.append("</div> \n");
//        } 
//        catch (Exception e) { log.error(e); }
//        response.getWriter().print(ret.toString());
//    }
//
//    private String getParameters(javax.servlet.http.HttpServletRequest request) 
//    {
//        StringBuffer sbRet=new StringBuffer();
//        sbRet.append("?");
//        Enumeration en = request.getParameterNames();
//        while(en.hasMoreElements())
//        {
//            String param = en.nextElement().toString();
//            String[] values = request.getParameterValues(param);
//            for (int i = 0; i < values.length; i++)
//                sbRet.append(param).append("=").append(values[i]).append("&");
//        } 
//        return sbRet.toString().replaceAll(" ", "%20");
//    }    
//
//    private String getParameters(javax.servlet.http.HttpServletRequest request, String[] offset) 
//    {
//        StringBuffer sbRet=new StringBuffer();
//        sbRet.append("?");
//        Enumeration en = request.getParameterNames();
//        while(en.hasMoreElements())
//        {
//            String param = en.nextElement().toString();
//            if(offset!=null && offset.length > 0)
//            {
//                for(int i=0; i < offset.length; i++)
//                {
//                    String off=(String)offset[i];
//                    if(!off.equals(param))
//                    {
//                        String[] values = request.getParameterValues(param);
//                        for (int j = 0; j < values.length; j++)
//                            sbRet.append(param).append("=").append(values[j]).append("&");
//                    }
//                }
//            }
//        } 
//        return sbRet.toString().replaceAll(" ", "%20");
//    }        
//    
//    public TreeMap getUserTypes(String rep, String language)
//    {
//        TreeMap usrtypes=new TreeMap(Collator.getInstance(new Locale(language)));
//        if(rep==null || (rep!=null && rep.trim().equals(""))) return usrtypes;
//        Enumeration en = DBUser.getInstance(rep).getUserAttrsTree().elements();
//        while(en.hasMoreElements())
//        {
//            WBUserAttribute att = (WBUserAttribute)en.nextElement();
//            if(att.getKind() == WBUserAttribute.USER_TYPE_ATTRIBUTE)
//                usrtypes.put(att.getValueLocalized(att.getName(), language),att.getName());
//        }
//        return usrtypes;
//    }
//    
//    public java.util.ArrayList getUsers(javax.servlet.http.HttpServletRequest request)
//    {
//        ArrayList ret = new ArrayList();
//        String rep     = request.getParameter("rep");
//        if(rep==null || (rep!=null && rep.trim().equals(""))) return ret;
//        
//        String firstname      = request.getParameter("firstname");
//        String lastname       = request.getParameter("lastname");
//        String middlename     = request.getParameter("middlename");
//        String email          = request.getParameter("email");
//        ArrayList roles       = toArrayList(request.getParameterValues("role"), true);
//        ArrayList filtersadmin= toArrayList(request.getParameterValues("filteradmin"), false);
//        ArrayList filterssite = toArrayList(request.getParameterValues("filtersite"), false);
//        ArrayList usertypes   = toArrayList(request.getParameterValues("usertype"), false);
//        int    active         = 2;
//        try { active=Integer.parseInt(request.getParameter("active")); }
//        catch (NumberFormatException e) { active=2; }
//
//        UserRepository dbUserRep=SWBContext.getUserRepository(rep);
//        Iterator<User> en=dbUserRep.listUsers();
//        while(en.hasNext())      // TODO buscar metodo para cambiar esto
//        {
//            User rusr=en.next();
//            if (null!=firstname)
//            if(!matches(firstname, rusr.getUsrFirstName())) continue;
//            if (null!=lastname)
//            if(!matches(lastname,  rusr.getUsrLastName()))  continue;
//            if (null!=middlename)
//            if(!matches(middlename,rusr.getUsrSecondLastName()))continue;
//            if (null!=email)
//            if(!matches(email,     rusr.getUsrEmail()))     continue;
//            if(active < 2) { if(!rusr.isActive())continue; }
//            if (null != roles)
//            if(!haveRoles(rusr, roles)) continue;
//            if (null != usertypes)
//            if(usertypes != null && !isUserType(rusr, usertypes)) continue;
//            if (null != filtersadmin)
//            if(filtersadmin != null && !haveFilters(rusr, filtersadmin)) continue;
//            if (null != filterssite)
//            if(filterssite != null && !haveFilters(rusr, filterssite)) continue;
//            ret.add(rusr);
//        }
//        return ret;
//    }
//
//    public ArrayList toArrayList(String[] in, boolean type)
//    {
//        if(in==null) return null;
//        ArrayList ret=new ArrayList(in.length);
//        for(int i=0; i < in.length; i++)
//        {
//            try
//            { 
//                if(type) ret.add(Integer.valueOf(in[i])); 
//                else ret.add(in[i]); 
//            }
//            catch(Exception e) {}
//        }
//        return ret;
//    }
//     
//    public boolean haveRoles(User rusr, ArrayList roles)
//    {
//        if(rusr==null || roles==null) return true;
//        if(!rusr.listRoles().hasNext()) return false;
//        boolean bOk=false;
//        try
//        {
//            Iterator<Role> it=rusr.listRoles();
//            while(it.hasNext())
//            {
//                if(roles.contains(it.next()))
//                {
//                    bOk = true;
//                    break;
//                }
//            }
//        }
//        catch (Exception e) { log.error(e); }
//        return bOk;
//    }
//    
//    public boolean haveFilters(User rusr, ArrayList tmfilters)
//    {
//        if(rusr==null || tmfilters==null) return true;
//        boolean bOk=false;        
//        try
//        {        
//            Iterator it=getTopicMaps(tmfilters);
//            while(it.hasNext())
//            {
//                if(bOk) break;
//                String tm=(String)it.next();
//                if(rusr.haveAdmFilters(tm))
//                {
//                    ArrayList filters=getFilters(tmfilters, tm);                
//                    Iterator ft=rusr.getAdmFilters(tm);
//                    while(ft.hasNext())
//                    {
//                        if(filters.contains((Integer)ft.next()))
//                        {
//                            bOk = true;
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e) { log.error(e); }
//        return bOk;
//    }
//    
//    public Iterator getTopicMaps(ArrayList tmfilters)    
//    {
//        ArrayList ret=new ArrayList();
//        if(tmfilters!=null)
//        {
//            try
//            {
//                Iterator it=tmfilters.iterator();
//                while(it.hasNext())
//                {
//                    String obj=(String)it.next();
//                    obj=obj.substring(0, obj.indexOf("|"));
//                    if(!ret.contains(obj)) ret.add(obj);
//                }
//            }
//            catch (Exception e) { log.error(e); }
//        }
//        return ret.iterator();
//    }
//    
//    public ArrayList getFilters(ArrayList tmfilters, String topicmap)    
//    {
//        ArrayList ret=new ArrayList();
//        if(tmfilters!=null && topicmap!=null)
//        {
//            try
//            {
//                Iterator it=tmfilters.iterator();
//                while(it.hasNext())
//                {
//                    String obj=(String)it.next();
//                    if(obj.startsWith(topicmap+"|")) 
//                    {
//                        try 
//                        { 
//                            obj=obj.substring(obj.indexOf("|")+1);
//                            ret.add(Integer.valueOf(obj)); 
//                        } 
//                        catch (Exception e) { log.error(e,"",true); }
//                    }
//                }
//            }
//            catch (Exception e) { log.error(e); }
//        }
//        return ret;
//    }    
//
//    public boolean isUserType(User rusr, ArrayList usertypes)
//    {
//        if(rusr==null || usertypes==null) return true;
//        boolean bOk=false;
//        try
//        {
//            User user= new User(rusr);
//            Enumeration en = user.getPropertyValue("USERTYPE");
//            if(en!=null)
//            while(en.hasMoreElements())
//            {
//                String s=(String)en.nextElement();
//                if(usertypes.contains(s))
//                {
//                    bOk = true;
//                    break;
//                }
//            }
//        }
//        catch (Exception e) { log.error(e); }
//        return bOk;
//    }
//    
//    public boolean matches(String pattern, String target)
//    {
//        boolean bOk = true;
//        if(pattern==null || target==null) return bOk;
//            
//        StringTokenizer st = new StringTokenizer(pattern, " ");
//        Vector words = new Vector();
//        while (st.hasMoreTokens()) words.addElement(st.nextToken());
//
//        Enumeration en = words.elements();
//        while (en.hasMoreElements())
//        {
//            String str = (String)en.nextElement();
//            StringSearch sch = new StringSearch();
//            if (!sch.compare(target, str)) 
//            {
//                bOk = false;
//                break;
//            }
//        }            
//        return bOk;
//    }
//
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
     
        if(paramRequest.getMode().equals("search")){
            doSearch(request,response,paramRequest);
        }
      //  else
     //   if(paramRequest.getMode().equals("details")){
        //
     //   }
        else super.processRequest(request, response, paramRequest);
    }
    
}
