<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*"%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    
    out.println("suri="+id);
%>
<!--<a href="?suri=<%=org.semanticwb.base.util.URLEncoder.encode("http://www.sep.gob.mx/WebPage#home")%>">liga</a>-->
<form action="/swb/swbadmin/jsp/SemObjectEditor.jsp" method="post">
    <input type="hidden" name="suri" value="<%=id%>">
<%
    
    if(id==null)return;
    
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    SemanticObject obj=ont.getSemanticObject(id);
    SemanticClass cls=obj.getSemanticClass();
    
    String update=request.getParameter("update");
    if(update!=null)
    {
        Iterator<SemanticProperty> it=cls.listProperties();
        while(it.hasNext())
        {
            SemanticProperty prop=it.next();
            if(prop.isDataTypeProperty())
            {
                String value=request.getParameter(prop.getName());
                if(value!=null)
                {
                    if(value.length()>0)
                    {
                        if(prop.isBoolean())obj.setBooleanProperty(prop, Boolean.parseBoolean(value));
                        if(prop.isInt())obj.setLongProperty(prop, Integer.parseInt(value));
                        if(prop.isString())obj.setProperty(prop, value);
                    }else
                    {
                        obj.removeProperty(prop);
                    }
                }
            }
        }
    }
    
    Iterator<SemanticProperty> it=cls.listProperties();
    while(it.hasNext())
    {
        SemanticProperty prop=it.next();
        if(prop.isDataTypeProperty())
        {

//<!--            Range:<%=prop.getRange()% >-->

            String value=obj.getProperty(prop);
            if(prop.isInt())value=""+obj.getIntProperty(prop);
            if(value==null)value="";
            
            String label=prop.getName();
            if(prop.getLabel()!=null)label=prop.getLabel();
%>
            <%=label%>: <input name="<%=prop.getName()%>" type="text" value="<%=value%>"><br>
<%      }else
        {
/*            
<!--
            Name:<%=prop.getName()% >
            Range:< %=prop.getRange()% >
            Valor:< %=obj.getObjectProperty(prop)% >
-->            
*/            
        }
%>
<%
    }
%>
    <input type="submit" name="update">
</form>
