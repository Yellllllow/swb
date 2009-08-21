<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.*"%>
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>


<%
   String imgPath=SWBPlatform.getContextPath()+"/swbadmin/images/";
   SWBResourceURL url = paramRequest.getActionUrl();
   User owner=paramRequest.getUser();
   User user=owner;
   if(request.getParameter("user")!=null){
        SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
        user=(User)semObj.createGenericInstance();
   }
   String data=null;
   Resource base=paramRequest.getResourceBase();
   String twitterConf=base.getAttribute("twitterConf","1");
   if(twitterConf.equals("1"))data=base.getData();
   if(twitterConf.equals("2"))data=base.getData(user);
   if(twitterConf.equals("3"))data=base.getData(paramRequest.getWebPage());

   if(data!=null){
       int pos=data.indexOf("|");
       if(pos>-1){
       String userLogin=data.substring(0,pos);
       String userPass=data.substring(pos+1);
       Twitter twitter = new Twitter(userLogin, userPass);
       Iterator<Status> itStatuses = (twitter.getFriendsTimeline()).iterator();
       url.setAction("send2Twitter");       
%>
        <a href="http://twitter.com"><img src="<%=imgPath%>twitter_logo.png" valign="top"/></a>
        <form action="<%=url.toString()%>">
        <table>
            <tr>
                <td>�Que estas haciendo ahora?</td>
            </tr>
            <tr>
                <td><textarea name="status" cols="40" rows="3"></textarea></td>
            </tr>
            <tr>
                <td><input type="submit" value="enviar"></td>
            </tr>
        </table>
        </form>
        <%
            int cont=0;
            while(itStatuses.hasNext()){
                Status twitt=itStatuses.next();
                String timeAgo=SWBUtils.TEXT.getTimeAgo(twitt.getCreatedAt(), "es");
                %>
                 <p class="addOn">
                    <img src="<%=imgPath%>twitter.png" valign="top" width="20" height="20"/><%=twitt.getText()%><br><%=timeAgo%><br/>
                 </p>
                <%
                cont++;
                if(cont>=10) break;
            }
         }
     }else if(owner.getURI().equals(user.getURI())){ //Forma para que el usuario proporcione login y password de twitter
       url.setAction("saveUserData");
       %>
       <form action="<%=url.toString()%>" method="post">
        <table>
            <tr>
                <td colspan="2" align="center">Proporciona tus datos de tu cuenta de twitter</td>
            </tr>
            <tr>
                <td>Login:</td><td><input type="text" name="twitterLogin" size="25"/></td>
            </tr>
            <tr>
                <td>Password:</td><td><input type="password" name="twitterPass" size="25"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="enviar"></td>
            </tr>
        </table>
        </form>
      <%
     }else{%>
        <%=user.getFirstName()%><%=user.getLastName()%><br>
                No tiene una cuenta de twitter configuarada en este portal
       <%
     }
     %>