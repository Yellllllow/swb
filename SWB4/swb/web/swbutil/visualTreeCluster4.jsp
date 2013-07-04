<%-- 
    Document   : visualTreeCluster
    Created on : 03-jul-2013, 15:06:59
    Author     : javier.solis.g
--%><%@page import="org.semanticwb.model.*"%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String args="";
    String model=request.getParameter("model");
    String lang=request.getParameter("lang");
    String w=request.getParameter("w");if(w==null)w="1280";
    String h=request.getParameter("h");if(h==null)h="800";
    if(model==null)model="demo";
    args="?model="+model;
    if(lang!=null)args+="&lang="+lang;
    WebSite site=SWBContext.getWebSite(model);    
%> 
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="http://mbostock.github.io/d3/talk/20111116/d3/d3.js"></script>
    <script type="text/javascript" src="http://mbostock.github.io/d3/talk/20111116/d3/d3.layout.js"></script>
    <link type="text/css" rel="stylesheet" href="http://mbostock.github.io/d3/talk/20111018/style.css"/>
    <style type="text/css">

text {
  font-size: 11px;
  pointer-events: none;
}

text.parent {
  fill: #1f77b4;
}

circle {
  fill: #ccc;
  stroke: #999;
  pointer-events: all;
}

circle.parent {
  fill: #1f77b4;
  fill-opacity: .1;
  stroke: steelblue;
}

circle.parent:hover {
  stroke: #ff7f0e;
  stroke-width: .5px;
}

circle.child {
  pointer-events: none;
}

    </style>
  </head>
  <body>
    <div id="body">
      <div id="footer">
        Sitio <%=site.getDisplayTitle(lang)%>
        <div class="hint">
          WebPage views circle packing
        </div>
      </div>
    </div>
    <script type="text/javascript">

var w = <%=w%>,
    h = <%=h%>,
    r = 720,
    x = d3.scale.linear().range([0, r]),
    y = d3.scale.linear().range([0, r]),
    node,
    root;

var pack = d3.layout.pack()
    .size([r, r])
    .value(function(d) { return d.size; })

var vis = d3.select("body").insert("svg:svg", "h2")
    .attr("width", w)
    .attr("height", h)
  .append("svg:g")
    .attr("transform", "translate(" + (w - r) / 2 + "," + (h - r) / 2 + ")");

d3.json("jsonTree.jsp<%=args%>", function(data) {
  node = root = data;

  var nodes = pack.nodes(root);

  vis.selectAll("circle")
      .data(nodes)
    .enter().append("svg:circle")
      .attr("class", function(d) { return d.children ? "parent" : "child"; })
      .attr("cx", function(d) { return d.x; })
      .attr("cy", function(d) { return d.y; })
      .attr("r", function(d) { return d.r; })
      .on("click", function(d) { return zoom(node == d ? root : d); });

  vis.selectAll("text")
      .data(nodes)
    .enter().append("svg:text")
      .attr("class", function(d) { return d.children ? "parent" : "child"; })
      .attr("x", function(d) { return d.x; })
      .attr("y", function(d) { return d.y; })
      .attr("dy", ".35em")
      .attr("text-anchor", "middle")
      .style("opacity", function(d) { return d.r > 20 ? 1 : 0; })
      .text(function(d) { return d.name; });

  d3.select(window).on("click", function() { zoom(root); });
});

function zoom(d, i) {
  var k = r / d.r / 2;
  x.domain([d.x - d.r, d.x + d.r]);
  y.domain([d.y - d.r, d.y + d.r]);

  var t = vis.transition()
      .duration(d3.event.altKey ? 7500 : 750);

  t.selectAll("circle")
      .attr("cx", function(d) { return x(d.x); })
      .attr("cy", function(d) { return y(d.y); })
      .attr("r", function(d) { return k * d.r; });

  t.selectAll("text")
      .attr("x", function(d) { return x(d.x); })
      .attr("y", function(d) { return y(d.y); })
      .style("opacity", function(d) { return k * d.r > 20 ? 1 : 0; });

  node = d;
  d3.event.stopPropagation();
}
    </script>
  </body>
</html>
