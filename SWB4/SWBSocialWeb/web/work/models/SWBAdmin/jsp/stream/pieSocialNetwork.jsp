<%-- 
    Document   : pieSocialNetwork
    Created on : 19/12/2013, 11:48:18 AM
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%> 


<%!
    JSONArray getObject(SemanticObject semObj, String lang, String filter) throws Exception {
        int positives = 0, negatives = 0, neutrals = 0, total = 0;
        int totalPost = 0;
        Iterator<PostIn> itObjPostIns = null;
        JSONArray node = new JSONArray();
        Stream stream = null;
        SocialTopic socialTopic = null;

        ArrayList socialNetworks = new ArrayList();

        if (semObj.getGenericInstance() instanceof Stream) {
            stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
            socialNetworks = SWBSocialUtil.sparql.getStreamSocialNetworks(stream);
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
            socialNetworks = SWBSocialUtil.sparql.getSocialTopicSocialNetworks(socialTopic);
        }


        while (itObjPostIns.hasNext()) {
            itObjPostIns.next();
            totalPost++;
        }

        Iterator i = socialNetworks.iterator();
        ArrayList<PostIn> postInSocialNetwork = new ArrayList<PostIn>();
        //Iterar las redes sociales que tienen postin
        while (i.hasNext()) {
            SemanticObject sO = (SemanticObject) i.next();
            SocialNetwork s = (SocialNetwork) sO.getGenericInstance();
            //obtenemos los post que estan en un  stream y una red en especifico
            if (semObj.getGenericInstance() instanceof Stream) {
                postInSocialNetwork = SWBSocialUtil.sparql.getPostInbyStreamAndSocialNetwork(stream, s);
            } else if (semObj.getGenericInstance() instanceof SocialTopic) {
                postInSocialNetwork = SWBSocialUtil.sparql.getPostInbySocialTopicAndSocialNetwork(socialTopic, s);
            }


            Iterator ii = postInSocialNetwork.iterator();
            //obtenemos los json del array
            getJsonArray(node, ii, s.getTitle(), totalPost, filter, positives, negatives, neutrals, total);

        }

        return node;
    }

    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }

    public JSONArray getJsonArray(JSONArray node, Iterator i, String title, int totalPost, String filter, int positives, int negatives, int neutrals, int total) throws Exception {


        float intPorcentaje = 0F;
        while (i.hasNext()) {

            SemanticObject so = (SemanticObject) i.next();
            PostIn pi = (PostIn) so.getGenericInstance();
            if (filter.equals("all") || filter.equals(pi.getPostInSocialNetwork().getTitle())) {
                total++;
                if (pi.getPostSentimentalType() == 0) {
                    neutrals++;
                } else if (pi.getPostSentimentalType() == 1) {
                    positives++;
                } else if (pi.getPostSentimentalType() == 2) {
                    negatives++;
                }
            }
        }

        if (filter.equals("all")) {
            intPorcentaje = ((float) total * 100) / (float) totalPost;
            JSONObject node1 = new JSONObject();
            node1.put("label", "" + title);
            node1.put("value1", "" + total);
            node1.put("value2", "" + round(intPorcentaje));
            if (positives > negatives && positives > neutrals) {
                node1.put("color", "#008000");
            } else if (negatives > neutrals) {
                node1.put("color", "#FF0000");
            } else {
                node1.put("color", "#FFD700");
            }
            node1.put("label2", "");
            node1.put("chartclass", "possClass");
            node1.put("label3", "Total de Post: ");
            node.put(node1);
        } else {
            float intPorcentajeNeutrals = 0;
            float intPorcentajePositives = 0;
            float intPorcentajeNegatives = 0;

            if (total != 0) {
                intPorcentajeNeutrals = ((float) neutrals * 100) / (float) total;
                intPorcentajePositives = ((float) positives * 100) / (float) total;
                intPorcentajeNegatives = ((float) negatives * 100) / (float) total;
            }

            if (positives > 0) {
                JSONObject node1 = new JSONObject();
                node1.put("label", "Positivos");
                node1.put("value1", "" + positives);
                node1.put("value2", "" + round(intPorcentajePositives));
                node1.put("color", "#008000");
                node1.put("label2", "");
                node1.put("chartclass", "possClass");
                node1.put("label3", "Total de Post: " + totalPost);
                node.put(node1);
            }

            if (negatives > 0) {
                JSONObject node2 = new JSONObject();
                node2.put("label", "Negativos");
                node2.put("value1", "" + negatives);
                node2.put("value2", "" + round(intPorcentajeNegatives));
                node2.put("color", "#FF0000");
                node2.put("label2", "");
                node2.put("chartclass", "possClass");
                node2.put("label3", "Total de Post: " + totalPost);
                node.put(node2);
            }

            if (neutrals > 0) {
                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "" + neutrals);
                node3.put("value2", "" + round(intPorcentajeNeutrals));
                node3.put("color", "#FFD700");
                node3.put("label2", "");
                node3.put("chartclass", "possClass");
                node3.put("label3", "Total de Post: " + totalPost);
                node.put(node3);
            }

        }




        return node;
    }

    public float getPositivesNegativesNeutrals(int positives, int negatives, int neutrals, Iterator i, float porcentaje, int totalPost, int total, String filter) {

        while (i.hasNext()) {

            SemanticObject so = (SemanticObject) i.next();
            PostIn pi = (PostIn) so.getGenericInstance();
            if (filter.equals("all") || filter.equals(pi.getPostInSocialNetwork().getTitle())) {
                total++;
                if (pi.getPostSentimentalType() == 0) {
                    neutrals++;
                } else if (pi.getPostSentimentalType() == 1) {
                    positives++;
                } else if (pi.getPostSentimentalType() == 2) {
                    negatives++;
                }
            }
        }

        porcentaje = ((float) total * 100) / (float) totalPost;

        return porcentaje;

    }


%>
<%

    if (request.getParameter("objUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        String filter = request.getParameter("filter");
        out.println(getObject(semObj, lang, filter));
    }
%>