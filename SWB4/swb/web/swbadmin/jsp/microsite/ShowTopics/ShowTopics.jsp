<%@page import="java.text.*,java.net.*,org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<h2 class="titulo1">Temas</h2>
<div class="cajas">
    <ul>
        <%
            //User user = (User) request.getAttribute("user");
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            WebPage topics = webpage.getWebSite().getWebPage("Intereses");
            Iterator<WebPage> pages = topics.listVisibleChilds("es");
            while (pages.hasNext())
            {
                int comunidades=0;
                WebPage topic = pages.next();
                Iterator<WebPage> childs=topic.listVisibleChilds("es");
                while(childs.hasNext())
                {
                    comunidades++;
                    childs.next();
                }
                String title = topic.getTitle();
        %>
        <li><a href="<%=topic.getUrl()%>"><%=title%></a> (<%=comunidades%>)</li>
        <%
            }
        %>
    </ul>
</div