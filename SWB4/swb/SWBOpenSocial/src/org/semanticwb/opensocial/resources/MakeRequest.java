/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class MakeRequest
{

    private static final Logger log = SWBUtils.getLogger(MakeRequest.class);

    private void sendResponse(String objresponse, HttpServletResponse response) throws IOException
    {
        Charset utf8 = Charset.forName("utf-8");
        response.setContentType("application/json;charset=" + utf8.name());
        OutputStream out = response.getOutputStream();
        out.write("throw 1; < don't be evil' >".getBytes());
        out.write(objresponse.getBytes(utf8));
        out.close();
    }

    private String encode(String xml)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : xml.toCharArray())
        {
            if (c != '\n')
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void getDocument(URL url, String headers, HttpServletResponse response) throws IOException, JDOMException, JSONException
    {
        System.out.println("get document " + url);
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (headers != null && !headers.equals(""))
            {
                StringTokenizer st = new StringTokenizer(headers, ":");
                if (st.countTokens() % 2 == 0)
                {
                }
            }
            String header = con.getHeaderField("Content-Type");
            Charset charset = Charset.defaultCharset();
            //String contentType="text/xml;charset=" + charset.name();
            if (header != null)
            {
                System.out.println("header: " + header);
                int pos = header.indexOf("charset=");
                if (pos != -1)
                {
                    String scharset = header.substring(pos + 6).trim();
                    charset = Charset.forName(scharset);
                }
            }
            InputStream in = con.getInputStream();
            java.io.InputStreamReader reader = new InputStreamReader(in, charset);
            /*DOMOutputter dOMOutputter = new DOMOutputter();
            SAXBuilder builder = new SAXBuilder();
            charset = Charset.forName("utf-8");
            Document doc = dOMOutputter.output(builder.build(reader));
            String xml = encode(SWBUtils.XML.domToXml(doc, charset.name(), false));*/
            StringBuilder sb=new StringBuilder();
            char[] buffer=new char[1028];
            int read=reader.read(buffer);
            while(read!=-1)
            {
                sb.append(buffer,0,read);
                read=reader.read(buffer);
            }
            reader.close();
            String xml=encode(sb.toString());
            System.out.println("xml " + xml);
            response.setContentType("application/json");
            JSONObject responseJSONObject = new JSONObject();
            JSONObject body = new JSONObject();
            body.put("body", xml);
            responseJSONObject.put(url.toString(), body);
            responseJSONObject.put("rc", con.getResponseCode());

            System.out.println("response");
            System.out.println(responseJSONObject.toString(4));
            sendResponse(responseJSONObject.toString(4), response);
        }
        catch (IOException e)
        {
            log.debug(e);
            throw e;
        }
        /*catch (JDOMException e)
        {
            log.debug(e);
            throw e;
        }*/
        catch (JSONException e)
        {
            log.debug(e);
            throw e;
        }

    }

    private void sendResponse(Document objresponse, HttpServletResponse response) throws IOException
    {
        Charset defaultCharset = Charset.defaultCharset();
        response.setContentType("text/xml;charset=" + defaultCharset.name());
        OutputStream out = response.getOutputStream();
        String xml = SWBUtils.XML.domToXml(objresponse, defaultCharset.name(), false);
        System.out.println("response: " + xml);
        out.write(xml.getBytes());
        out.close();
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        System.out.println("MakeRequest request.getRequestURI(): " + request.getRequestURI());
        System.out.println("MakeRequest request.getQueryString(): " + request.getQueryString());
        System.out.println("MakeRequest request.getContentType(): " + request.getContentType());
        String refresh = request.getParameter("refresh");
        System.out.println("refresh: " + refresh);
        String url = request.getParameter("url");
        System.out.println("url: " + url);
        String httpMethod = request.getParameter("httpMethod");
        System.out.println("httpMethod: " + httpMethod);
        String headers = request.getParameter("headers");
        System.out.println("headers: " + headers);
        String postData = request.getParameter("postData");
        System.out.println("postData: " + postData);
        String st = request.getParameter("st");
        System.out.println("MakeRequest st: " + st);
        String authz = request.getParameter("authz");
        System.out.println("authz: " + authz);
        String contentType = request.getParameter("contentType");
        System.out.println("contentType: " + contentType);
        String numEntries = request.getParameter("numEntries");
        System.out.println("numEntries: " + numEntries);
        String getSummaries = request.getParameter("getSummaries");
        System.out.println("getSummaries: " + getSummaries);
        String signOwner = request.getParameter("signOwner");
        System.out.println("signOwner: " + signOwner);
        String container = request.getParameter("container");
        System.out.println("container: " + container);
        String bypassSpecCache = request.getParameter("bypassSpecCache");
        System.out.println("bypassSpecCache: " + bypassSpecCache);
        String getFullHeaders = request.getParameter("getFullHeaders");
        System.out.println("getFullHeaders: " + getFullHeaders);
        String gadget = request.getParameter("gadget");
        System.out.println(gadget);
        Gadget g = SocialContainer.getGadget(gadget, site);
        if (g != null)
        {
            if (httpMethod.trim().equalsIgnoreCase("GET"))
            {
                if ("DOM".equals(contentType.trim()))
                {
                    try
                    {
                        getDocument(new URL(url), headers, response);

                        return;
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        response.sendError(505, e.getLocalizedMessage());
                    }
                }
                else if ("FEED".equals(contentType.trim()))
                {
                }
                else if ("FEED".equals(contentType.trim()))
                {
                }

            }
            else
            {
                response.sendError(404);
            }
        }
        response.sendError(404);
    }
}
