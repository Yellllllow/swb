/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.css.parser.Attribute;
import org.semanticwb.css.parser.CSSParser;
import org.semanticwb.css.parser.Selector;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.View;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class IFrame
{

    private static final String frame = SocialContainer.loadFrame("demoframe.html");
    private static final Logger log = SWBUtils.getLogger(IFrame.class);

    public static String parseHTML(String datos, URI gadget, URI proxy)
    {
        StringBuilder ret = new StringBuilder();
        StringBuilder cssString = new StringBuilder();
        HtmlTag tag = new HtmlTag();
        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(new ByteArrayInputStream(datos.getBytes()));
        boolean css = false;
        try
        {
            while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF)
            {
                int ttype = tok.getTokenType();
                if (ttype == HtmlStreamTokenizer.TT_TAG || ttype == HtmlStreamTokenizer.TT_COMMENT)
                {
                    if (ttype == HtmlStreamTokenizer.TT_COMMENT && tok.getRawString().equals("<!-- -->"))
                    {
                        continue;
                    }
                    tok.parseTag(tok.getStringValue(), tag);

                    if (!tag.isEndTag() && (tag.getTagString().toLowerCase().equals("script") || tag.getTagString().toLowerCase().equals("img")))
                    {
                        ret.append("<");
                        ret.append(tag.getTagString());
                        ret.append(" ");
                        for (int iparam = 0; iparam < tag.getParamCount(); iparam++)
                        {
                            String paramName = tag.getParamName(iparam);
                            String value = tag.getParamValue(iparam);
                            if ("src".equals(paramName))
                            {
                                try
                                {
                                    URI uriSRC = new URI(value);
                                    if (!uriSRC.isAbsolute())
                                    {
                                        uriSRC = gadget.resolve(uriSRC);
                                    }
                                    String url = uriSRC.toString();
                                    int pos = url.indexOf("?"); // elimina parametros por seguridad
                                    if (pos != -1)
                                    {
                                        url = url.substring(0, pos);
                                    }
                                    value = proxy + "?url=" + URLEncoder.encode(url);
                                }
                                catch (URISyntaxException use)
                                {
                                    log.debug(use);
                                    System.out.println("value: " + value);
                                }
                                //value=uriSRC.toString();
                            }
                            ret.append(paramName);
                            ret.append("=\"");
                            ret.append(value);
                            ret.append("\" ");
                        }
                        ret.append(">");
                    }
                    else if (tag.getTagString().toLowerCase().equals("style"))
                    {
                        if (tag.isEndTag())
                        {
                            ret.append(parseCSS(cssString.toString(), gadget, proxy));
                            //ret.append(cssString.toString());
                            cssString = new StringBuilder();
                            css = false;
                            ret.append(tok.getRawString());
                        }
                        else
                        {
                            css = true;
                            ret.append(tok.getRawString());
                        }
                    }
                    else
                    {
                        ret.append(tok.getRawString());

                    }

                }
                else
                {
                    if ((ttype == HtmlStreamTokenizer.TT_TEXT || ttype == HtmlStreamTokenizer.TT_COMMENT) && css)
                    {
                        cssString.append(tok.getRawString());
                        cssString.append("\r\n");

                    }
                    else
                    {
                        ret.append(tok.getRawString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return ret.toString();
    }

    public static String parseCSS(String cssbody, URI gadget, URI proxy)
    {
        StringBuilder sb = new StringBuilder("\r\n");
        try
        {
            CSSParser p = new CSSParser(cssbody);
            for (Selector selector : p.getSelectors())
            {
                sb.append(selector.getName());
                sb.append("\r\n{");
                for (Attribute att : selector.getAttributes())
                {

                    sb.append("\r\n");
                    sb.append(att.getName());
                    sb.append(":");
                    if (att.getName().equals("background-image") || att.getName().equals("background") || att.getName().equals("list-style"))
                    {
                        for (String value : att.getValues())
                        {
                            if (value.startsWith("url("))
                            {
                                value = value.substring(4);
                                int pos = value.indexOf(")");
                                if (pos != -1)
                                {
                                    value = value.substring(0, pos).trim();
                                    if (value.startsWith("\"") && value.endsWith("\""))
                                    {
                                        value = value.substring(1, value.length() - 1);
                                    }
                                    if (value.startsWith("'") && value.endsWith("'"))
                                    {
                                        value = value.substring(1, value.length() - 1);
                                    }
                                    try
                                    {
                                        URI uriValue = new URI(value);
                                        if (!uriValue.isAbsolute())
                                        {
                                            uriValue = gadget.resolve(uriValue);
                                        }
                                        String url = uriValue.toString();
                                        int pos2 = url.indexOf("?"); // elimina parametros por seguridad
                                        if (pos2 != -1)
                                        {
                                            url = url.substring(0, pos2);
                                        }
                                        value=url;
                                    }
                                    catch (URISyntaxException uie)
                                    {
                                        log.debug(uie);
                                    }
                                    sb.append("url('");
                                    sb.append(proxy.toString());
                                    sb.append("?url=");
                                    sb.append(URLEncoder.encode(value));
                                    sb.append("')");

                                }
                                else
                                {
                                    sb.append(value);
                                }
                            }
                            else
                            {
                                sb.append(value);
                                sb.append(",");
                            }
                        }
                        sb.append(";");
                    }
                    else
                    {
                        for (String value : att.getValues())
                        {
                            sb.append(value);
                            sb.append(" ");
                        }
                        sb.append(";");
                    }
                }
                sb.append("\r\n}\r\n\r\n");
            }
        }
        catch (Throwable e)
        {
            log.error(e);
        }

        return sb.toString();
    }

    private String getHTML(URL url)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Charset charset = Charset.defaultCharset();
            String contentType = con.getHeaderField("Content-Type");
            if (contentType != null && contentType.toLowerCase().indexOf("html") != -1)
            {
                int pos = contentType.indexOf("charset");
                if (pos != -1)
                {
                    String scharset = contentType.substring(pos + 1);
                    pos = scharset.indexOf("=");
                    if (pos != -1)
                    {
                        scharset = scharset.substring(pos + 1);
                        try
                        {
                            charset = Charset.forName(scharset);
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }
                }
            }
            InputStream in = con.getInputStream();
            byte[] buffer = new byte[1028];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read, charset));
                read = in.read(buffer);
            }
            in.close();
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return sb.toString();
    }

    private String getHTMLfromView(Gadget gadget, Element content, String sview, Map<String, String> variables)
    {
        String html = "";
        String type = "html";
        if (content.getAttribute("type") != null)
        {
            type = content.getAttribute("type");
        }
        if ("html".equalsIgnoreCase(type))
        {

            String view = content.getAttribute("view");
            if (view == null || view.trim().equals(""))
            {
                view = "default";
            }
            if (isInView(sview, view))
            {
                String href = content.getAttribute("href");
                if (href == null || href.trim().equals(""))
                {

                    NodeList childs = content.getChildNodes();
                    for (int j = 0; j < childs.getLength(); j++)
                    {
                        if (childs.item(j) instanceof CDATASection)
                        {
                            CDATASection section = (CDATASection) childs.item(j);
                            String htmltemp = section.getNodeValue();
                            for (String key : variables.keySet())
                            {
                                String value = variables.get(key);
                                htmltemp = htmltemp.replace(key, value);
                            }
                            html += htmltemp;
                        }
                    }
                }
                else
                {
                    try
                    {
                        URI urihref = new URI(href);
                        URI urigadget = new URI(gadget.getUrl());
                        if (!urihref.isAbsolute())
                        {
                            urigadget.resolve(urihref);
                        }
                        html = getHTML(urigadget.toURL());
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                        e.printStackTrace();
                    }
                }
            }
        }
        else if ("url".equalsIgnoreCase(type))
        {
            String href = content.getAttribute("href");

            if (href != null && !href.trim().equals(""))
            {
                try
                {
                    URI urihref = new URI(href);
                    URI urigadget = new URI(gadget.getUrl());
                    if (!urihref.isAbsolute())
                    {
                        urigadget.resolve(urihref);
                    }
                    // sends the html result from the href
                    String _url = urihref.toString() + "?";
                    for (String key : variables.keySet())
                    {
                        String value = variables.get(key);
                        _url += "&" + URLEncoder.encode(key) + "=" + URLEncoder.encode(value);
                    }
                    html = "<iframe frameborder=\"0\" src=\"" + _url + "\"></iframe>";
                }
                catch (Exception e)
                {
                    log.debug(e);
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    private boolean isInView(String view, String attribute)
    {
        if (view.equalsIgnoreCase(attribute))
        {
            return true;
        }
        String[] values = attribute.split(",");
        for (String value : values)
        {
            if (value.equalsIgnoreCase(view))
            {
                return true;
            }
        }
        return false;
    }

    public void doProcess(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebSite site = paramRequest.getWebPage().getWebSite();
        System.out.println("request.getRequestURI(): " + request.getRequestURI());
        System.out.println("request.getQueryString(): " + request.getQueryString());
        String url = request.getParameter("url");
        String country = request.getParameter("country");
        String lang = request.getParameter("lang");
        String moduleid = request.getParameter("moduleid");
        String sview = request.getParameter("view");
        if (sview == null)
        {
            sview = "default";
        }
        String html = "";
        if (moduleid == null)
        {
            moduleid = "0";
        }

        try
        {
            Gadget gadget = SocialContainer.getGadget(url, paramRequest.getWebPage().getWebSite());
            if (gadget != null)
            {
                boolean exists = false;
                for (View oview : gadget.getViews())
                {
                    if (oview.getName().equalsIgnoreCase(sview))
                    {
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                {
                    sview = "default";
                }
                Map<String, String> variables = SocialContainer.getVariablesubstituion(paramRequest.getUser(), gadget, lang, country, moduleid, site);

                NodeList contents = gadget.getOriginalDocument().getElementsByTagName("Content");
                for (int i = 0; i < contents.getLength(); i++)
                {
                    Node node = contents.item(i);
                    if (node instanceof Element)
                    {
                        Element content = (Element) node;
                        html = getHTMLfromView(gadget, content, sview, variables);
                        if ("".equals(html))
                        {
                            html = getHTMLfromView(gadget, content, "default", variables);
                        }

                    }
                }
                SWBResourceURL makerequest = paramRequest.getRenderUrl();
                makerequest.setCallMethod(SWBResourceURL.Call_DIRECT);
                makerequest.setMode(SocialContainer.Mode_MAKE_REQUEST);

                SWBResourceURL rpc = paramRequest.getRenderUrl();
                rpc.setCallMethod(SWBResourceURL.Call_DIRECT);
                rpc.setMode(SocialContainer.Mode_RPC);




                SWBResourceURL proxy = paramRequest.getRenderUrl();
                proxy.setCallMethod(SWBResourceURL.Call_DIRECT);
                proxy.setMode(SocialContainer.Mode_PROXY);



                html = parseHTML(html, new URI(gadget.getUrl()), new URI(proxy.toString()));
                SWBResourceURL javascript = paramRequest.getRenderUrl();
                javascript.setMode(SocialContainer.Mode_JAVASCRIPT);
                javascript.setCallMethod(SWBResourceURL.Call_DIRECT);
                javascript.setParameter("script", "core_rpc.js");
                String HtmlResponse = frame.replace("<%=js%>", javascript.toString());
                HtmlResponse = HtmlResponse.replace("<%=rpc%>", rpc.toString());
                HtmlResponse = HtmlResponse.replace("<%=proxy%>", proxy.toString());
                HtmlResponse = HtmlResponse.replace("<%=makerequest%>", makerequest.toString());
                HtmlResponse = HtmlResponse.replace("<%=html%>", html);
                PrintWriter out = response.getWriter();
                //System.out.println(HtmlResponse);
                out.write(HtmlResponse);
                out.close();
            }
        }
        catch (Exception e)
        {
            log.debug(e);
            e.printStackTrace();
            response.setStatus(500, e.getMessage());
        }
    }
}
