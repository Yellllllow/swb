/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.*;

/**
 *
 * @author Jei
 */
public class Banner extends GenericResource
{

    private static Logger log = SWBUtils.getLogger(GenericXformsResource.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        PrintWriter out=response.getWriter();
        out.println("Recurso Banner...");
        
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        System.out.println("base.getId():"+base.getId());
        System.out.println("base.getWorkPath():"+base.getWorkPath());
        System.out.println("TODA:"+ SWBPlatform.getWebWorkPath() + base.getWorkPath());
        try
        {
            String local=base.getProperty("local", "0").trim();
            if (local.equals("0"))
            {
                String img = base.getProperty("img", "").trim();
                if (!img.equals(""))
                {
                    String wburl=paramRequest.getActionUrl().toString();                    
                    String url = base.getProperty("url", "").trim();
                    String width = base.getProperty("width", "").trim();
                    String height = base.getProperty("height", "").trim();
                    if(url.toLowerCase().startsWith("mailto:") || url.toLowerCase().startsWith("javascript:")) {
                        wburl=url.replaceAll("\"","&#34;");
                    }
                    synchronized (ret)
                    {
                        if (img.endsWith(".swf"))
                        {
                            String schema=new URL(request.getRequestURL().toString()).getProtocol();
                                                        
                            ret.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\""+schema+"://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\"");
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">\n");
                            ret.append("<param name=movie value=\"" +  SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + img +"\" />\n");
                            ret.append("<param name=\"quality\" value=\"high\" />\n");
                            ret.append("<param name=\"wmode\" value=\"transparent\" /> \n");
                            ret.append("<param name=\"FlashVars\" value=\"liga=" + wburl + "\" />\n");
                            ret.append("<embed id=\"" + img + "\" name=\"" + img + "\" src=\"");
                            ret.append(SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/" + img + "\"");
                            ret.append(" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" ");
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">");
                            ret.append("</embed>\n");
                            ret.append("</object>");
                        } 
                        else
                        {
                            String alt = base.getProperty("alt", "").trim();
                            if (!url.equals(""))
                            {
                                String target = base.getProperty("target", "0").trim();
                                synchronized (ret)
                                {
                                    ret.append("<a href=\"" + wburl + "\"");
                                    if (target.equals("1")) {
                                        ret.append(" target=\"_newbnr\"");
                                    }
                                    ret.append(">");
                                }
                            }
                            ret.append("<img src=\"");
                            ret.append(SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/"+img + "\"");
                            if (paramRequest.getArguments().containsKey("border")) {
                                ret.append(" border=\"" + (String)paramRequest.getArguments().get("border") + "\"");
                            }
                            else {
                                ret.append(" border=\"0\"");
                            }
                            if (!alt.equals("")) {
                                ret.append(" alt=\"" + alt+ "\"");
                            }
                            if (!width.equals("")) {
                                ret.append(" width=\"" + width + "\"");
                            }
                            if (!height.equals("")) {
                                ret.append(" height=\"" + height + "\"");
                            }
                            ret.append(">");
                            if (!url.equals("")) {
                                ret.append("</a>");
                            }
                        }
                    }
                }
            } 
            else if (local.equals("1"))
            { //publicidad externa
                ret.append(base.getProperty("code", ""));
            }
        } 
        catch (Exception e) { log.error("Error in resource Banner while bringing HTML", e); }
        response.getWriter().print(ret.toString());
        
    }
}
