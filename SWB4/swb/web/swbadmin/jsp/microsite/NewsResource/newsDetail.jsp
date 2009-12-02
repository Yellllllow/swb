<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.SimpleDateFormat, org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
            java.text.SimpleDateFormat dateFormat;
            String lang = "es";
            Locale locale = new Locale(lang);
            dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy", locale);
            String[] months =
            {
                "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
            };
            java.text.DateFormatSymbols fs = dateFormat.getDateFormatSymbols();
            fs.setShortMonths(months);
            dateFormat.setDateFormatSymbols(fs);

            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",locale);
            java.text.DecimalFormat df = new java.text.DecimalFormat("#0.0#");
            String uri = request.getParameter("uri");
            NewsElement anew = (NewsElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            String rank = df.format(anew.getRank());

            String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/MembershipResource/userIMG.jpg";
            String path = anew.getWorkPath();
            if (anew.getNewsThumbnail() != null)
            {
                int pos = anew.getNewsThumbnail().lastIndexOf("/");
                if (pos != -1)
                {
                    String sphoto = anew.getNewsThumbnail().substring(pos + 1);
                    anew.setNewsThumbnail(sphoto);
                }
                pathPhoto = SWBPortal.getWebWorkPath() + path + "/" + anew.getNewsThumbnail();
            }
%>

<div class="columnaIzquierda">



    <%
            if (anew != null && anew.canView(member))
            {
                anew.incViews();
    %>




    <p><img id="img_<%=anew.getId()%>" src="<%= pathPhoto%>" alt="<%= anew.getTitle()%>" border="0" width="380" height="100%" /></p>



    <p class="descripcion" style="text-align:justify;"><%=anew.getFullText()%></p>



    <%  }
            SWBResponse res = new SWBResponse(response);
            anew.renderGenericElements(request, res, paramRequest);
            out.write(res.toString());
    %>
</div>
<div class="columnaCentro">
    <h2 class="blogTitle"><%=anew.getTitle()%></h2><br>
    <p><%=anew.getDescription()%></p>
    <p>Por: <%= anew.getAuthor()%></p>
    <p>Fuente: <%= anew.getCitation()%></p>
    <p>Creado el: <%=dateFormat.format(anew.getCreated())%></p>
    <p><%=anew.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
    <p><a href="<%=paramRequest.getRenderUrl()%>">[Ver todas las noticias]</a></p>
    <%if (anew.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", anew.getURI())%>">[Editar Información]</a></p>
    <%}%>
    <%if (anew.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", anew.getURI())%>">[Eliminar]</a></p>
    <%}%>
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
            String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?news=" + java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de noticias de la comunidad</a></li>
    </ul>

</div>