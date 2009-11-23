<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.lib.*,java.text.*,org.semanticwb.platform.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            Member member = Member.getMember(user, wpage);

            String lang = user.getLanguage();

            String uri = request.getParameter("uri");
            PhotoElement photo = (PhotoElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");

            DecimalFormat df = new DecimalFormat("#0.0#");
            String rank = df.format(photo.getRank());
            if (photo != null && photo.canView(member))
            {
                photo.incViews();  //Incrementar apariciones
                String title = "";
                if (photo.getTitle() != null)
                {
                    title = photo.getTitle();
                }
                String description = "";
                if (photo.getDescription() != null)
                {
                    description = photo.getDescription();
                }
                String pathPhoto = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/PhotoResource/sinfoto.png";
                        String path = wpage.getWorkPath();
                        if (photo.getPhotoThumbnail() != null)
                        {
                            int pos = photo.getPhotoThumbnail().lastIndexOf("/");
                            if (pos != -1)
                            {
                                String sphoto = photo.getPhotoThumbnail().substring(pos + 1);
                                photo.setPhotoThumbnail(sphoto);
                            }
                            pathPhoto = SWBPortal.getWebWorkPath() + path + "/" + photo.getPhotoThumbnail();
                        }
%>
<div class="columnaIzquierda">
    <h2><%=title%></h2><br>
    <p><%= description%></p>
    <p align="center"><a title="<%= title%>" href="<%= SWBPortal.getWebWorkPath() + photo.getImageURL()%>">
            <img id="img_<%=photo.getId()%>" src="<%= pathPhoto%>" alt="<%=title%>" width="50%" height="50%" />
        </a></p>            

    <%
                }
               
                SWBResponse res = new SWBResponse(response);
                photo.renderGenericElements(request, res, paramRequest);
                out.write(res.toString());
                
    %>
</div>
<div class="columnaCentro">
    <p>&nbsp;</p>
    <p>Autor: <%= photo.getCreator().getFullName()%></p>
    <p>Creado el: <%=dateFormat.format(photo.getCreated())%></p>
    <p><%= photo.getViews()%> vistas</p>
    <p>Calificación: <%=rank%></p>
    <p><a href="<%=paramRequest.getRenderUrl()%>">[Ver todas las fotos]</a></p>
    <%if (photo.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getRenderUrl().setParameter("act", "edit").setParameter("uri", photo.getURI())%>">[Editar información]</a></p>
    <%}%>
    <%if (photo.canModify(member))
            {%>
    <p><a href="<%=paramRequest.getActionUrl().setParameter("act", "remove").setParameter("uri", photo.getURI())%>">[Eliminar]</a></p>
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
                    String pageUri = "/swbadmin/jsp/microsite/rss/rss.jsp?photo=" + java.net.URLEncoder.encode(wpage.getURI());
        %>
        <li><a class="rss" href="<%=pageUri%>">Suscribirse via RSS al canal de fotos de la comunidad</a></li>
    </ul>
</div>