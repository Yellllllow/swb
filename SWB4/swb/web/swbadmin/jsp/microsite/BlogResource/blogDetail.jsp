<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<script type="text/javascript">
    function validateremove(url, title,uri)
    {
        if(confirm('¿Esta seguro de borrar la entrada '+title+'?'))
        {
            var url=url+'&uri='+escape(uri);
            window.location.href=url;
        }
    }
</script>

<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            if (paramRequest == null)
            {
                return;
            }
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            PostElement post = (PostElement) request.getAttribute("post");
            Member member = Member.getMember(user, wpage);
            if (!post.canView(member) || post == null)
            {
%>
<p>No tiene permisos para ver esta entrada, o la entrada ya no existe</p>
<%
        return;
    }
    

    String updated = SWBUtils.TEXT.getTimeAgo(post.getUpdated(), user.getLanguage());
    String postAuthor = post.getCreator().getFirstName();
    postAuthor = post.getCreator().getFullName();        
    post.incViews();  //Incrementar apariciones    
    DecimalFormat df = new DecimalFormat("#0.0#");
    String rank = df.format(post.getRank());
    SWBResourceURL removeUrl = paramRequest.getActionUrl();
    removeUrl.setParameter("act", "remove");
    boolean canadd=post.canModify(member);
    String editURL=paramRequest.getRenderUrl().setParameter("act","edit").setParameter("uri",post.getURI()).setParameter("mode","editpost").toString();
    String deleteUrl="javascript:validateremove('"+removeUrl+"','"+post.getTitle()+"','"+post.getURI()+"')";
%>

<div class="columnaIzquierda">    
    <h2 class="hidden"><%=post.getTitle()%></h2>
    
    <p><%=post.getContent()%></p>
    <br>
    <br>

    <%
            SWBResponse res = new SWBResponse(response);
            post.renderGenericElements(request, res, paramRequest);
            out.write(res.toString());
%>
</div>
<div class="columnaCentro">
    <h2 class="blogTitle"><%=post.getTitle()%></h2>
    <p> <%=post.getDescription()%> </p>
    <p> Autor: <%=postAuthor%> </p>
    <p> Actualizado: <%=updated%> </p>
    <p> Calificación: <%=rank%> </p>
    <p> <%=post.getViews()%> visitas  </p>
    <p><a href="<%=paramRequest.getRenderUrl()%>">[Ver todas las entradas del blog]</a></p>
    <%
            if (canadd)
            {
        %>
        <p><a href="<%=editURL%>">[Editar Entrada]</a></p>
        <p><a href="<%=deleteUrl%>">[Eliminar Entrada]</a></p>
        <%
            }
        %>
    <ul class="miContenido">
        <%
            SWBResourceURL urla = paramRequest.getActionUrl();
            if (user.isRegistered())
            {
                if (member == null)
                {
                    urla.setParameter("act", "subscribe");
        %>
        <li><a href="<%=urla%>">Suscribirse a esta comunidad</a></li>
        <%
                }
                else
                {
                    urla.setParameter("act", "unsubscribe");
        %>
        <li><a href="<%=urla%>">Cancelar suscripción a comunidad</a></li>
        <%
                }
            }
            String pageUri="/swbadmin/jsp/microsite/rss/rss.jsp?blog="+java.net.URLEncoder.encode(post.getBlog().getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al blog de la comunidad</a></li>
    </ul>
</div>

