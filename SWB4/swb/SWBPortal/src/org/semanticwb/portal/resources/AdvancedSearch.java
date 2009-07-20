package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.nlp.translation.SWBSparqlTranslator;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.nlp.SWBLexicon;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericAdmResource;

/**
 *
 * @author Hasdai Pacheco {haxdai(at)gmail.com}
 */
public class AdvancedSearch extends GenericAdmResource {

    private String lang = "x-x";
    private SWBLexicon lex = null;
    private SWBSparqlTranslator tr;

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("SUGGEST")) {
            doSuggest(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL rUrl = paramRequest.getRenderUrl();
        User user = paramRequest.getUser();
        StringBuffer sbf = new StringBuffer();

        //Get user language if any
        if (user != null) {
            if (!lang.equals(user.getLanguage())) {
                lang = user.getLanguage();
            }
        } else {
            if (!lang.equals("es")) {
                lang = "es";
            }
        }

        //Create lexicon for NLP
        String pf = getResourceBase().getAttribute("prefixFilter");
        if (pf == null) {
            pf = "";
        }
        lex = new SWBLexicon(lang, pf);

        

        if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) {
            //Set URL call method to call_DIRECT to make an AJAX call
        rUrl.setCallMethod(rUrl.Call_DIRECT);
        rUrl.setMode("SUGGEST");

        //Add necesary scripting
        sbf.append("    \n<script type=\"text/javascript\">\n" +
                "      dojo.addOnLoad(function () {\n" +
                "        dojo.connect(dojo.byId('naturalQuery'), 'onkeydown', 'queryOnKeyDown');\n" +
                "        dojo.connect(dojo.byId('naturalQuery'), 'onkeyup', 'queryOnKeyUp');\n" +
                "        dojo.connect(dojo.byId('naturalQuery'), 'onblur', function () {\n" +
                "          clearSuggestions();\n" +
                "        });\n" +
                "      });\n" +
                "      var source =\"" + rUrl + "\";\n" +
                "      var lang =\"" + lang + "\";\n" +
                "      var displayed;\n" +
                "      var pdisplayed;\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\" charset=\"utf-8\" src=\"" +
                            SWBPlatform.getContextPath() + "/swbadmin/js/swb_admin.js\" ></script>\n\n");
        /**
         * Clears the suggestions list and gives focus to the textarea.
         */
        sbf.append("    <script type=\"text/javascript\">\n" +
                "      function clearSuggestions() {\n" +
                "        if (dojo.byId('results')) {\n" +
                "          dojo.byId('results').innerHTML = \"\";\n" +
                "        }\n" +
                "        displayed = false;\n" +
                "        curSelected = 0;\n" +
                "        dojo.byId('naturalQuery').focus();\n" +
                "      }\n");

        sbf.append("      function queryOnKeyDown (evt) {\n" +
                "        var wd = getCurrentWord('naturalQuery');\n" +
                "        if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "          clearSuggestions();\n" +
                "          return;\n" +
                "        }\n" +
                //CTRL+SHIFT+SPACE
                "        if (evt.ctrlKey && evt.shiftKey && evt.keyCode == dojo.keys.SPACE) {\n" +
                "          getSuggestions(wd, source, true, false);\n" +
                "          dojo.stopEvent(evt);\n" +
                "        } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "          setSelection(curSelected, (wd.word == \"con\")?\"con \":\"\");\n" +
                "          clearSuggestions();\n" +
                "          pdisplayed = false;\n" +
                "          dojo.stopEvent(evt);\n" +
                "        }\n" +
                "      }\n");

        sbf.append("      function queryOnKeyUp (evt) {\n" +
                "        var wd = getCurrentWord('naturalQuery');\n" +
                "        if (evt.target.value == '' || wd.word.length < 3) {\n" +
                "          clearSuggestions();\n" +
                "          return;\n" +
                "        }\n" +
                "        if((displayed || pdisplayed) && evt.keyCode == dojo.keys.ENTER) {\n" +
                "        } else if (!displayed && !pdisplayed && wd.word == \"con\") {\n" +
                "          var pwd = getPreviousName(wd);\n" +
                "          if (pwd != \"undefined\") {\n" +
                "            getSuggestions(getPreviousName(wd), source, true, true);\n" +
                "            pdisplayed = true;\n" +
                "          }\n" +
                "        } else if (!displayed && pdisplayed && wd.word != \"con\") {\n" +
                "          clearSuggestions();\n" +
                "          pdisplayed = false;\n" +
                "        } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.ESCAPE) {\n" +
                "          clearSuggestions();\n" +
                "          pdisplayed = false;\n" +
                "        }" +
                //UP_ARROW
                "        else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.UP_ARROW) {\n" +
                "          dojo.query('.resultEntry').style('background', 'white');\n" +
                "          curSelected--;\n" +
                "          if (curSelected < 0) {\n" +
                "            curSelected = 0;\n" +
                "          }" +
                "          console.log(curSelected);\n" +
                "          highLightSelection(curSelected, true);\n" +
                "          dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id' + curSelected)).t;\n" +
                "          dojo.byId('naturalQuery').focus();\n" +
                "          dojo.stopEvent(evt);\n" +
                //DOWN_ARROW
                "        } else if ((displayed || pdisplayed) && evt.keyCode == dojo.keys.DOWN_ARROW) {\n" +
                "          dojo.query('.resultEntry').style('background', 'white');\n" +
                "          curSelected++;\n" +
                "          if (curSelected > dojo.byId('resultlist').childNodes.length - 2) {\n" +
                "            curSelected = dojo.byId('resultlist').childNodes.length - 2;\n" +
                "          }\n" +
                "          console.log(curSelected);\n" +
                "          highLightSelection(curSelected, true);\n" +
                "          dojo.byId('resultlist').scrollTop = dojo.coords(dojo.byId('id'+curSelected)).t;\n" +
                "          dojo.byId('naturalQuery').focus();\n" +
                "          dojo.stopEvent(evt);\n" +
                "        }\n" +
                "      }\n");

        /**
         * Gets the word at current cursor position in a textarea.
         * @param elm textarea to extract word from.
         */
        sbf.append("      function getCurrentWord(elm) {\n" +
                "        var cPos = getCaretPos(elm);\n" +
                "        var txt = dojo.byId(elm).value;\n" +
                "        var prevBlank = -1;\n" +
                "        var aftBlank = -1;\n" +
                "        var found = false;\n" +
                "        var wd = null;\n" +
                "        var wo = \"undefined\";\n" +
                "        var delimiters = \"\\n\\t \";\n" +
                "        if (txt != '') {\n" +
                "          for (var i = 0; i < txt.length; i++) {\n" +
                "            if (delimiters.indexOf(txt.charAt(i)) != -1 && cPos > i) {\n" +
                "              prevBlank = i;\n" +
                "            }\n" +
                "          }\n" +
                "          for (i = cPos; i < txt.length && !found; i++) {\n" +
                "            if (delimiters.indexOf(txt.charAt(i)) != -1) {\n" +
                "              aftBlank = i;\n" +
                "              found = true;\n" +
                "            }\n" +
                "          }\n" +
                "          if (prevBlank == -1) {\n" +
                "            if (aftBlank == -1) {\n" +
                "              wd = txt;\n" +
                "              wo = {\n" +
                "                word: wd,\n" +
                "                startP: 0,\n" +
                "                endP: txt.length\n" +
                "              };\n" +
                "            } else {\n" +
                "              wd = txt.substring(0, aftBlank);\n" +
                "              wo = {\n" +
                "                word: wd,\n" +
                "                startP: 0,\n" +
                "                endP: aftBlank\n" +
                "              };\n" +
                "            }\n" +
                "          } else if (aftBlank == -1) {\n" +
                "            wd = txt.substring(prevBlank + 1, txt.length);\n" +
                "            wo = {\n" +
                "              word: wd,\n" +
                "              startP: prevBlank + 1,\n" +
                "              endP: txt.length\n" +
                "            };\n" +
                "          } else {\n" +
                "            wd = txt.substring(prevBlank + 1, aftBlank);\n" +
                "            wo = {\n" +
                "              word: wd,\n" +
                "              startP: prevBlank + 1,\n" +
                "              endP: aftBlank\n" +
                "            };\n" +
                "          }\n" +
                "        }\n" +
                "        return wo;\n" +
                "      }\n");

        /**
         * Gets cursor current position in a textarea.
         * @param elm textarea to calculate caret position from.
         */
        sbf.append("      function getCaretPos(elm) {\n" +
                "        var pos;\n" +
                "        if (dojo.doc.selection) {\n" +
                "          var Sel = document.selection.createRange();\n" +
                "          var SelLength = document.selection.createRange().text.length;\n" +
                "          Sel.moveStart ('character', -dojo.byId(elm).value.length);\n" +
                "          pos = Sel.text.length - SelLength;\n" +
                "        } else if (typeof dojo.byId(elm).selectionStart != undefined) {\n" +
                "          pos = dojo.byId(elm).selectionStart;\n" +
                "        }\n" +
                "          return pos;\n" +
                "      }\n");

        /**
         * Creates a suggestion list based on word at current cursor position.
         * @param word word at cursor position
         * @param src url of dataStore
         * @param clear wheter or not to clear previous list
         * @param props wheter or not to get properties of the current word as SemanticClass
         */
        sbf.append("      function getSuggestions(word, src, clear, props) {\n" +
                "        if (clear) {\n" +
                "          clearSuggestions();\n" +
                "        }\n" +
                "        if(word.word == '') {\n" +
                "          return;\n" +
                "        }\n" +
                "        if (dojo.byId('results') && word.word != '') {\n" +
                "          dojo.byId('results').innerHTML = '<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\" width=\"20\" height=\"20\"/>...';\n" +
                "        }\n" +
                "        getHtml(src + \"?word=\" + escape(word.word) + \"&lang=\" + lang + \"&props=\" + props, 'results');\n" +
                "        displayed = true;\n" +
                "        highLightSelection(0, true);\n" +
                "      }\n");

        /**
         * Highlights an option in the suggestions list.
         * @param id identifier of the list item to highlight
         * @param high toggles highlight color.
         */
        sbf.append("      function highLightSelection(id, high) {\n" +
                "        var ele = dojo.byId('id' + id);\n" +
                "        if (high) {\n" +
                "          dojo.style(ele, {\n" +
                "            \"background\":\"LightBlue\"\n" +
                "          });\n" +
                "        } else {\n" +
                "          dojo.style(ele, {\n" +
                "            \"background\":\"white\"\n" +
                "          });\n" +
                "        }\n" +
                "      }\n");

        sbf.append("      function getPreviousName (word) {\n" +
                "        var pName = \"\";\n" +
                "        var prevBrk = -1;\n" +
                "        var firstBrk = -1;\n" +
                "        var txt = dojo.byId('naturalQuery').value;\n" +
                "        var cPos = word.startP;\n" +
                "        var wd = null;\n" +
                "        var wo = \"undefined\";\n" +
                "        var found = false;" +
                "        for (var i = cPos; i >= 0 && !found; i--) {\n" +
                "          if (txt.charAt(i) == ']') {\n" +
                "            prevBrk = i;\n" +
                "            found = true;\n" +
                "          }\n" +
                "        }\n" +
                "        found = false;\n" +
                "        for (i = prevBrk; i > 0 && !found; i--) {\n" +
                "          if (txt.charAt(i) == '[') {\n" +
                "            firstBrk = i;\n" +
                "            found = true;\n" +
                "          }\n" +
                "        }\n" +
                "        if (prevBrk == -1) {\n" +
                "          return wo;\n" +
                "        }\n" +
                "        firstBrk++;\n" +
                "        wd = txt.substring((firstBrk==0)?++firstBrk:firstBrk, prevBrk);\n" +
                "        wo = {\n" +
                "          word: wd,\n" +
                "          startP: firstBrk,\n" +
                "          endP: prevBrk\n" +
                "        };\n" +
                "        return wo;\n" +
                "      }\n");
        /**
         * Replaces the current word in the textarea with the selected word from the
         * suggestions list.
         * @param selected index of current selected item
         * @param prep
         */
        sbf.append("      function setSelection(selected, prep) {\n" +
                "        var word = getCurrentWord('naturalQuery');\n" +
                "        var newText = dojo.byId('id' + selected).innerHTML.replace(/<(.|\\n)+?>/g, \"\");\n" +
                "        newText = prep + \"[\" + newText + \"]\";\n" +
                "        var valText = dojo.byId('naturalQuery').value;\n" +
                "        dojo.byId('naturalQuery').value = valText.substring(0, word.startP) +" +
                "        newText + valText.substring(word.endP, valText.length);\n" +
                "      }\n\n" +
                "      function getHtml(url, tagid) {\n" +
                "        dojo.xhrGet({\n" +
                "          url: url,\n" +
                "          load: function(response)\n" +
                "          {\n" +
                "            var tag=dojo.byId(tagid);\n" +
                "            if(tag) {\n" +
                "              var pan=dijit.byId(tagid);\n" +
                "              if(pan && pan.attr) {\n" +
                "                pan.attr('content',response);\n" +
                "              } else {\n" +
                "                tag.innerHTML = response;\n" +
                "              }\n" +
                "            } else {\n" +
                "              alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "            }\n" +
                "            return response;\n" +
                "          },\n" +
                "          error: function(response) {\n" +
                "            if(dojo.byId(tagid)) {\n" +
                "              dojo.byId(tagid).innerHTML = \"<p>Ocurrió un error con respuesta:<br />\" + response + \"</p>\";\n" +
                "            } else {\n" +
                "              alert(\"No existe ningún elemento con id \" + tagid);\n" +
                "            }\n" +
                "              return response;\n" +
                "          },\n" +
                "          handleAs: \"text\"\n" +
                "        });\n" +
                "      }\n" +
        "</script>");
        
            String url = "";
            //String url = getResourceBase().getAttribute("destUrl");
            Resourceable rsa = getResourceBase().getResourceable();
            if (rsa != null && rsa instanceof WebPage)
            {
                url = ((WebPage)rsa).getUrl();
            }
            sbf.append("    <form id=\"" + getResourceBase().getId() + "/natural\" " +
                    "action=\"" + url + "\" method=\"post\" >\n" +
                    "      <input type=\"text\" id=\"naturalQuery\" name=\"q\" autocomplete=\"off\">\n" +
                    "    </form>" +
                    "    <div id=\"results\"></div>\n");
            response.getWriter().print(sbf.toString());
        } else {
            doShowResults(request, response, paramRequest);
        }
    }

   public void doSuggest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sbf = new StringBuffer();
        SortedSet objOptions = new TreeSet();
        SortedSet proOptions = new TreeSet();
        String word = request.getParameter("word");
        boolean props = Boolean.parseBoolean(request.getParameter("props"));
        boolean lPar = false;
        boolean rPar = false;
        int idCounter = 0;

        word = URLDecoder.decode(word, "iso-8859-1");

        response.setContentType("text/html; charset=iso-8859-1");
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        if (word.indexOf("(") != -1) {
            lPar = true;
            word = word.replace("(", "");
        }
        if (word.indexOf(")") != -1) {
            rPar = true;
            word = word.replace(")", "");
        }

        word = word.replace("[", "");
        word = word.replace("]", "");
        word = word.trim();

        if (!props) {
            Iterator<String> cit = lex.listClassNames();
            while (cit.hasNext()) {
                String tempc = cit.next();
                //System.out.println(tempc);
                if (tempc.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                    objOptions.add(tempc);
                }
            }

            Iterator<String> sit = lex.listPropertyNames();
            while (sit.hasNext()) {
                String tempp = sit.next();
                //System.out.println(tempp);
                if (tempp.toLowerCase().indexOf(word.toLowerCase()) != -1) {
                    proOptions.add(tempp);
                }
            }

            if (proOptions.size() != 0 || objOptions.size() != 0) {
                idCounter = 0;
                int index;
                Iterator<String> rit = objOptions.iterator();

                sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                        "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                        "200px;width:300px;border:1px solid #a0a0ff;\">");
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }

                rit = proOptions.iterator();
                while (rit.hasNext()) {
                    String tempi = (String) rit.next();
                    index = tempi.toLowerCase().indexOf(word.toLowerCase());

                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", '');dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;pdisplayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"blue\">" + tempi + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }
                sbf.append("</ul>");
            }
        } else {
            String tag = lex.getObjWordTag(word).getObjId();

            sbf.append("<ul id=\"resultlist\" class=\"resultlist\" style=\"background:white;list-style-type:none;" +
                    "position:absolute;margin:0;padding:0;overflow:auto;max-height:" +
                    "200px;width:300px;border:1px solid #a0a0ff;\">");

            if (!tag.equals("")) {
                SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
                idCounter = 0;
                Iterator<SemanticProperty> sit = sc.listProperties();
                while (sit.hasNext()) {
                    SemanticProperty t = sit.next();
                    sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                            "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                            "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                            "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                            "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
                            "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
                            "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
                            (lPar ? ")" : "") + "</li>");
                    idCounter++;
                }
            } else {
                tag = lex.getPropWordTag(word).getRangeClassId();
                if (!tag.equals("")) {
                    SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(tag);
                    idCounter = 0;
                    Iterator<SemanticProperty> sit = sc.listProperties();
                    while (sit.hasNext()) {
                        SemanticProperty t = sit.next();
                        sbf.append("<li id=\"id" + idCounter + "\" class=\"resultEntry\" " +
                                "onmouseover=\"dojo.query('.resultEntry').style('background', 'white'); " +
                                "highLightSelection(" + idCounter + ",true); curSelected = " + idCounter + ";\" " +
                                "onmouseout=\"highLightSelection(" + idCounter + ",false);\" " +
                                "onmousedown=\"setSelection(" + idCounter + ", 'con ');pdisplayed=false;dojo.byId('results').innerHTML='';" +
                                "dojo.byId('naturalQuery').focus();displayed=false;\">" + (lPar ? "(" : "") +
                                "<font color=\"red\">" + t.getDisplayName(lang) + "</font>" +
                                (lPar ? ")" : "") + "</li>");
                        idCounter++;
                    }
                }
            }
            sbf.append("</ul>");
        }
        out.println(sbf.toString());
    }

    public void doShowResults(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        SWBResourceURL aUrl = paramRequest.getActionUrl();
        String query = request.getParameter("q");
        StringBuffer sbf = new StringBuffer();

        //new Search().doView(request, response, paramRequest);
        //Assert query string
        query = (query == null ? "" : query.trim());

        tr = new SWBSparqlTranslator(lex);
        long time = System.currentTimeMillis();
        String sparqlQuery = lex.getPrefixString() + "\n" + tr.translateSentence(query);
        System.out.println("\n+++Tiempo de traducción: " + String.valueOf(System.currentTimeMillis() - time) + "milisegundos");
        System.out.println(sparqlQuery);
        String errCount = Integer.toString(tr.getErrCode());
        StringBuffer sbf2 = new StringBuffer();

        if (errCount != null) {
            /*sbf.append("<fieldset>");
            sbf.append("<textarea rows=5 cols=70>");
            sbf.append(request.getParameter("sparqlQuery"));
            sbf.append("</textarea>");
            sbf.append("</fieldset>");*/

            try {
                Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                SemanticModel mod = new SemanticModel("local", model);
                QueryExecution qexec = mod.sparQLQuery(sparqlQuery);
                time = System.currentTimeMillis();

                try {
                    sbf2.append("<h2>Resultados de la búsqueda</h2>");
                    sbf2.append("<table>");
                    ResultSet rs = qexec.execSelect();
                    sbf2.append("<thead>");
                    sbf2.append("<tr>");

                    if (rs.hasNext()) {
                        Iterator<String> itcols = rs.getResultVars().iterator();
                        while (itcols.hasNext()) {
                            sbf2.append("<th>");
                            sbf2.append(itcols.next());
                            sbf2.append("</th>");
                        }
                        sbf2.append("</tr>");
                        sbf2.append("</thead>");
                        sbf2.append("<tbody>");

                        boolean odd = true;
                        while (rs.hasNext()) {
                            odd = !odd;
                            QuerySolution rb = rs.nextSolution();

                            if (odd) {
                                sbf2.append("<tr bgcolor=\"#EFEDEC\">");
                            } else {
                                sbf2.append("<tr>");
                            }

                            Iterator<String> it = rs.getResultVars().iterator();
                            boolean first = true;
                            while (it.hasNext()) {
                                String name = it.next();
                                RDFNode x = rb.get(name);
                                sbf2.append("<td >");
                                SemanticObject so = SemanticObject.createSemanticObject(x.toString());

                                if (so != null) {
                                    if (first) {
                                        sbf2.append("<a href=\"#\" onclick=\"parent.addNewTab('" + so.getURI() + "', '" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp', '" + so.getDisplayName(lang) + "');\">" + so.getDisplayName(lang) + "</a>");
                                        first = false;
                                    } else {
                                        SemanticClass tt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(so.getURI());
                                        if (tt != null) {
                                            sbf2.append(tt.getDisplayName(lang));
                                        } else {
                                            SemanticProperty stt = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(so.getURI());
                                            sbf2.append(stt.getDisplayName(lang));
                                        }
                                    }
                                } else {
                                    if (x != null) {
                                        sbf.append(x);
                                    } else {
                                        sbf2.append(" - ");
                                    }
                                }
                                sbf2.append("</td>");
                            }
                            sbf2.append("</tr>");
                        }
                    } else {
                        sbf2.append("<font color='red'>Sin resultados</font>");
                        sbf2.append("</tr>");
                        sbf2.append("</thead>");
                    }
                    sbf2.append("</tbody>");
                    sbf2.append("</table>");
                    sbf2.append("Tiempo de ejecución " + (System.currentTimeMillis() - time) + "ms.");
                } finally {
                    sbf.append(sbf2.toString());
                    qexec.close();
                }
            } catch (Exception e) {
                if (tr.getErrCode() != 0) {
                    sbf.append("<script language=\"javascript\" type=\"text/javascript\">alert('error');</script>");
                }
            }
        } else {
            sbf.append("<script language=\"javascript\" type=\"text/javascript\">");
            sbf.append("alert(\"" + tr.getErrors().replace("\n", "\\n") + "\");");
            sbf.append("</script>");
        }
        response.getWriter().print(sbf.toString());
    }
}
