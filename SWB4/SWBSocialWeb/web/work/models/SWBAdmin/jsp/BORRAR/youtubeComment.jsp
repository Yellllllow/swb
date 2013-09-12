<%-- 
    Document   : youtubeComment
    Created on : 12/09/2013, 03:57:14 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.SWBUtils"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<%@page import="javax.xml.parsers.ParserConfigurationException"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.xml.sax.InputSource"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="org.apache.http.HttpResponse"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.apache.http.entity.StringEntity"%>
<%@page import="org.apache.http.client.HttpResponseException"%>
<%@page import="org.apache.http.HttpVersion"%>
<%@page import="org.apache.http.params.CoreProtocolPNames"%>
<%@page import="org.apache.http.impl.client.BasicResponseHandler"%>
<%@page import="org.apache.http.client.ResponseHandler"%>
<%@page import="org.apache.http.client.methods.HttpPost"%>
<%@page import="org.apache.http.impl.client.DefaultHttpClient"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.http.client.HttpClient"%>
<%@page import="java.util.HashMap"%>

<%
    HttpClient client = new DefaultHttpClient();
    client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    HttpPost post = new HttpPost("http://gdata.youtube.com/feeds/api/videos/F_ZYFz9rNR0/comments");

    try {
        post.setHeader("Content-Type", "application/atom+xml");
        post.setHeader("Authorization", "Bearer " + "ya29.AHES6ZTGkABrFTiV3msGlgR6lERJdNlF0pmwMWRYy-nVUsfiifxMrAjKfA");
        post.setHeader("GData-Version", "2");
        post.setHeader("X-GData-Key", "key=AI39si4LK4YxaCRYV0HAjypDOx3qfrqkC90wLfieDPFLlSmvDsb4HivpXKXb8CNtYcOkAnmGQoK4-aSrayHocpTGI8DHGD2r9g");

        System.out.println(post.getMethod());
        StringEntity entity = new StringEntity(
                "<?xml version=\"1.0\"?>"
                + "<entry xmlns=\"http://www.w3.org/2005/Atom\""
                + " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">"
                + "<content>This is a comment from Youtube API</content>"
                + "</entry>");
        post.setEntity(entity);


        //ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpResponse responseBody = client.execute(post);

        //Recupera idComment
        BufferedReader rd = new BufferedReader(new InputStreamReader(responseBody.getEntity().getContent()));
        String xmlResponse = rd.readLine();
        System.out.println("docxml:" + xmlResponse);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        builder = factory.newDocumentBuilder();
        Document xmlDoc = builder.parse(new InputSource(new StringReader(xmlResponse)));
        xmlDoc.getDocumentElement().normalize();
        NodeList rootNode = xmlDoc.getDocumentElement().getChildNodes();
        for (int i = 0; i < rootNode.getLength(); i++) {
            Node childNode = rootNode.item(i);
            if(childNode.getNodeName().equals("error")){//Problem found
                NodeList childError = childNode.getChildNodes();
                for (int childE = 0; childE < childError.getLength(); childE++) {
                    if(childError.item(childE).getNodeName().equals("internalReason")){
                        System.out.println("El error es: " + childError.item(childE).getFirstChild().getNodeValue());
                        return;
                    }
                }
            }
            if(childNode.getNodeName().equals("id")){
                String idString = childNode.getFirstChild().getNodeValue();
                System.out.println("value:" + idString);//Assuming the comment is at the end of the string
                System.out.println("The comment id: " +  idString.substring(idString.lastIndexOf(":")+1));
                break;
            }
        }
    }catch(HttpResponseException e){
        System.out.println("Msg " + e.getMessage());
        System.out.println("Error code " + e.getStatusCode());
        e.printStackTrace();
    }catch(Exception e){
        System.out.println("Other error:" + e.getMessage());
        e.printStackTrace();
    }
%>

