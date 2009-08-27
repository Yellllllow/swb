<%@page contentType="text/html"%>
<%@page import="java.text.*,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage wpage=paramRequest.getWebPage();
    MicroSiteWebPageUtil wputil=MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
    Member member=Member.getMember(user,wpage);
%>
<table border="0" width="100%">
<%
    Iterator<VideoElement> it=VideoElement.listVideoElementByWebPage(wpage,wpage.getWebSite());
    int i=0;
    while(it.hasNext())
    {
        VideoElement video=it.next();
        SWBResourceURL viewurl=paramRequest.getRenderUrl().setParameter("act","detail").setParameter("uri",video.getURI());
        if(video.canView(member))
        {
            
            if(i%2==0)out.println("<tr>");
%>
    <td width="50%" valign="top">
      <table border="0" width="100%" cellspacing="10">
        <tr><td valign="top" width="130">
        <a href="<%=viewurl%>">
            <img src="<%=video.getPreview()%>" width="125">
        </a>
        </td><td valign="top" align="left"><small>
        <b><%=video.getTitle()%></b> <BR>
        <%=video.getDescription()%> <BR>
        <%=video.getCreator().getFullName()%> <BR>
        <%=SWBUtils.TEXT.getTimeAgo(video.getCreated(),user.getLanguage())%> <BR>
        <%
            DecimalFormat df=new DecimalFormat("#0.0#");
            String rank=df.format(video.getRank());
        %>
        Calificación: <%=rank%> de 5<BR>
       
        <%=video.getViews()%> vistas<BR>
        </small></td></tr>
      </table>
    </td>
<%
            if(i%2==1)out.println("</tr>");
            i++;
        }
    }
    if(i%2==1)out.println("<td></td></tr>");
%>
</table>
<%
    if(member.canAdd())
    {
%>
<center>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act","add").toString()%>">Agregar Video</a>
<%
    if(wputil!=null && member.canView())
    {
        if(!wputil.isSubscribed(member))
        {
%>
    <a href="<%=paramRequest.getActionUrl().setParameter("act","subscribe").toString()%>">Suscribirse a este elemento</a>
<%
        }else
        {
%>
    <a href="<%=paramRequest.getActionUrl().setParameter("act","unsubscribe").toString()%>">Cancelar suscripción</a>
<%
        }

    }
%>
</center>
<%  }%>