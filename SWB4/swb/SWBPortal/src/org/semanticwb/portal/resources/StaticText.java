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
 


package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


// TODO: Auto-generated Javadoc
/**
 * StaticText se encarga de desplegar y administrar un texto est�tico, este texto
 * se agrega en la administraci�n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * StaticText is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 *
 * @author Javier Solis
 */

public class StaticText extends GenericAdmResource {
    
    /**
     * Obtiene la vista del recurso.
     * 
     * @param request El servlet container crea un objeto HttpServletRequest y
     * se pasa como argumento al m�todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     * se pasa como argumento al m�todo del servlet.
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        
//        SWBResourceURL url=paramRequest.getActionUrl();
//        url.setCallMethod(url.Call_DIRECT);
//        url.setMode(url.Mode_EDIT);
        
        PrintWriter out = response.getWriter();
        out.println(staticText);
        out.flush();
    }

    /**
     * Replace tags.
     * 
     * @param str the str
     * @param request the request
     * @param paramRequest the param request
     * @return the string
     */
    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest)
    {
        if(str==null || str.trim().length()==0)
            return "";
        
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
       
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramRequest)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{template.getArgument(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{template.getArgument(\""+s+"\")}", (String)paramRequest.getArgument(replaceTags(s,request,paramRequest)));
        }
        
        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramRequest)));
        }
        
        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
        str=SWBUtils.TEXT.replaceAll(str, "{topicurl}", paramRequest.getWebPage().getUrl());
        str=SWBUtils.TEXT.replaceAll(str, "{topic.title}", paramRequest.getWebPage().getTitle(paramRequest.getUser().getLanguage()));
        if(str.indexOf("{templatepath}")>-1)
        {
            //TODO:pasar template por paramrequest
            TemplateImp template=(TemplateImp)SWBPortal.getTemplateMgr().getTemplate(paramRequest.getUser(), paramRequest.getWebPage());
            str=SWBUtils.TEXT.replaceAll(str, "{templatepath}", template.getActualPath());
        }
        return str;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        PrintWriter out = response.getWriter();
        out.print(staticText);
        out.flush();
    }

    /*@Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = response.getResourceBase();
        base.setAttribute("text", request.getParameter("val"));
        try{
            base.updateAttributesToDB();
        }catch(Exception e){
            
        }
    }*/
    
}
