<%--
    Document   : treeWidget
    Created on : 30/12/2008, 05:09:54 PM
    Author     : Jei
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%
    String id;
    String showRoot;
    String rootLabel;
    String url;

    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    if(paramRequest!=null)
    {
        id=paramRequest.getArgument("id", "tree");
        showRoot=paramRequest.getArgument("showRoot", "false");
        rootLabel=paramRequest.getArgument("rootLabel");
        SWBResourceURL surl=paramRequest.getRenderUrl().setMode("json").setCallMethod(paramRequest.Call_DIRECT);
        surl.setParameter("id", id);
        url=surl.toString();
    }else
    {
        id=request.getParameter("id");
        showRoot=request.getParameter("showRoot");
        rootLabel=request.getParameter("rootLabel");
        if(id==null)id="tree";
        if(showRoot==null)showRoot="false";
        url="/swb/swbadmin/jsp/Tree.jsp?id="+id;
    }
    //System.out.println("id:"+id);
    if(rootLabel!=null)rootLabel="rootLabel=\""+rootLabel+"\"";
    else rootLabel="";
    String store=id+"Store";
    String model=id+"Model";
    String menu=id+"Menu";
%>
<!-- menu -->
<ul dojoType="dijit.Menu" id="<%=menu%>" style="display: none;" onOpen="hideApplet(true);" onClose="hideApplet(false);"></ul>
<!-- data for tree and combobox -->
<div dojoType="dojo.data.ItemFileWriteStore" jsId="<%=store%>" url="<%=url%>"></div>
<div dojoType="dijit.tree.ForestStoreModel" jsId="<%=model%>"
  store="<%=store%>" rootId="root" <%=rootLabel%> childrenAttrs="children"></div>
<!-- tree widget -->
<div dojoType="dijit.Tree" id="<%=id%>" model="<%=model%>" dndController="dijit._tree.dndSource" betweenThreshold="8" persist="false" showRoot="<%=showRoot%>">
    <script type="dojo/method" event="onClick" args="item, node">
        if(item)
        {
            act_treeNode=node;
            //alert("onOpen");
            //getHtml('/swb/swbadmin/jsp/viewProps.jsp?id='+encodeURIComponent(id), "vprop");
            executeTreeNodeEvent(<%=store%>,item,"onClick");
        }
    </script>
    <script type="dojo/method" event="onOpen" args="item, node">
        if(item)
        {
            act_treeNode=node;
            //alert("onOpen");
            executeTreeNodeEvent(<%=store%>,item,"onOpen");
        }
    </script>
    <script type="dojo/method" event="onDblClick" args="event">
        //alert("onDblClick:"+event);
        var domElement = event.target;
        var nodeWidget = dijit.getEnclosingWidget(domElement);
        act_treeNode=nodeWidget;
        if(nodeWidget && nodeWidget.isTreeNode){
            executeTreeNodeEvent(<%=store%>,nodeWidget.item,"onDblClick");
        }
    </script>
<!--
    <script type="dojo/method" event="onEnterKey" args="event">
        //alert("onEnterKey"+event);
        var domElement = event.target;
        var nodeWidget = dijit.getEnclosingWidget(domElement);
        act_treeNode=nodeWidget;
        if(nodeWidget && nodeWidget.isTreeNode){
            executeTreeNodeEvent(<%=store%>,nodeWidget.item,"onDblClick");
        }
    </script>
-->
    <script type="dojo/method" event="onDndDrop" args="source,nodes,copy">
        //alert(source+" "+nodes+" "+copy+" "+this.containerState);
		// summary:
		//		Topic event processor for /dnd/drop, called to finish the DnD operation..
		//		Updates data store items according to where node was dragged from and dropped
		//		to.   The tree will then respond to those data store updates and redraw itself.
		// source: Object: the source which provides items
		// nodes: Array: the list of transferred items
		// copy: Boolean: copy items, if true, move items otherwise

		if(this.containerState == "Over"){
			var tree = this.tree,
				model = tree.model,
				target = this.current,
				requeryRoot = false;	// set to true iff top level items change

			this.isDragging = false;

			// Compute the new parent item
			var targetWidget = dijit.getEnclosingWidget(target),
				newParentItem = (targetWidget && targetWidget.item) || tree.item;

			// If we are dragging from another source (or at least, another source
			// that points to a different data store), then we need to make new data
			// store items for each element in nodes[].  This call get the parameters
			// to pass to store.newItem()
			var newItemsParams;
			if(source != this){
				newItemsParams = this.itemCreator(nodes, target);
			}
            var expand=false;
			dojo.forEach(nodes, function(node, idx)
            {
                //alert(node+" "+idx);
				if(source == this){
					// This is a node from my own tree, and we are moving it, not copying.
					// Remove item from old parent's children attribute.
					// TODO: dijit._tree.dndSelector should implement deleteSelectedNodes()
					// and this code should go there.
					var childTreeNode = dijit.getEnclosingWidget(node),
						childItem = childTreeNode.item,
						oldParentItem = childTreeNode.getParent().item;
                        //alert(childItem.id+" "+newParentItem.id+" "+oldParentItem.id);
                        if(childItem && oldParentItem && newParentItem && oldParentItem!=newParentItem && childItem!=newParentItem)
                        {
                            var ac=confirm("Aceptar el movimiento?");
                            if(ac)
                            {
                                var f=showStatusURL('<%=SWBPlatform.getContextPath()%>/swbadmin/jsp/drop.jsp?suri='+encodeURIComponent(childItem.id)+'&newp='+encodeURIComponent(newParentItem.id)+'&oldp='+encodeURIComponent(oldParentItem.id),true);
                                if(f)
                                {
                                    //model.pasteItem(childItem, oldParentItem, newParentItem, copy);
                                    pasteItemByURIs(childItem.id,oldParentItem.id,newParentItem.id);
                                    reloadTab(childItem.id);
                                    expand=true;
                                }
                            }
                        }
				}else{
					model.newItem(newItemsParams[idx], newParentItem);
				}
			}, this);

			// Expand the target node (if it's currently collapsed) so the user can see
			// where their node was dropped.   In particular since that node is still selected.
			if(expand)this.tree._expandNode(targetWidget);
		}
		this.onDndCancel();
        this.containerState="";
    </script>

    <script type="dojo/method" event="checkItemAcceptance" args="node,source">
        //if(source.tree && source.tree.id == "collectionsTree"){
        //    return true;
        //}
        var ret=false;
        var dropNode = dijit.getEnclosingWidget(node);
        var dragNode = act_treeNode;
        //self.status="checkItemAcceptance-->dropNode"+dropNode+" dragSupport:"+dragNode.item.dragSupport;
        if(dropNode && dragNode)
        {
            var dragItem=dragNode.item;
            var dropItem=dropNode.item;
            if(dragItem!=dropItem && !isParent(dragNode,dropNode) && matchDropLevel(dragNode,dropNode))
            {
                for (var m in dropItem.dropacc)
                {
                    //alert(dropItem.dropacc[m]);
                    if(dragItem.type.toString()==dropItem.dropacc[m].toString())
                    {
                        ret=true;
                    }
                }
            }
            //alert(dragItem.type+" "+dropItem.dropacc.length+" "+(dragItem.type.toString() in dropItem.dropacc));
            //alert("("+dragItem.type.toString()+")("+dropItem.type.toString()+")");
        }
        //alert(this.current);
        //self.status="checkItemAcceptance-->ret:"+ret;
        return ret;
    </script>

    <script type="dojo/method" event="checkAcceptance" args="source,nodes">
        //if (this.tree.id=="myTree"){
        //    return false;
        //}
        dojo.forEach(nodes, function(node, idx)
        {
            act_treeNode=dijit.getEnclosingWidget(node);
        }, this);
        //var m=dojo.dnd.manager();
        //m.canDrop(false);
        //alert(act_treeNode.item.id);
        //self.status="checkAcceptance-->act_treeNode:"+act_treeNode+" drag:"+act_treeNode.item.dragSupport;
        if(act_treeNode.item.dragSupport=="true")return true;
        return false;
    </script>

    <script type="dojo/method" event="onDndStart" args="_1a,_1b,_1c">
        //alert("Hola");
        /*
        if(this.isSource){
            this._changeState("Source",this==_1a?(_1c?"Copied":"Moved"):"");
        }
        var _1d=this.checkAcceptance(_1a,_1b);
        this._changeState("Target",_1d?"":"Disabled");
        if(_1d){
            dojo.dnd.manager().overSource(this);
        }
        this.isDragging=true;
        */
    </script>


    <script type="dojo/method" event="getIconClass" args="item, opened">
        if(item)
        {
            try
            {
                return <%=store%>.getValue(item, "icon");
            }catch(err)
            {
                //return (!item||this.model.mayHaveChildren(item))?(opened?"dijitFolderOpened":"dijitFolderClosed"):"dijitLeaf";
                return "swbIconTemplateGroup";
            }
        }
    </script>
<!--
    <script type="dojo/method" event="getLabel" args="item">
        if(item)
        {
            return <%=store%>.getLabel(item);
        }
    </script>
-->
    <script type="dojo/connect">
        var menuEmpty = dijit.byId("<%=menu%>");

        menuEmpty.bindDomNode(this.domNode);

        registerTree(this);

        dojo.connect(menuEmpty, "_openMyself", this, function(e)
        {
            var treeNode = dijit.getEnclosingWidget(e.target);
            act_treeNode=treeNode;

            var ch = menuEmpty.getChildren();
            //console.log("menu children is "+ch);
            if (ch && ch != "undefined")
            {
                dojo.forEach(ch, function(child)
                {
                    //console.log("Remving old child "+child);
                    menuEmpty.removeChild(child);
                });
            }

            if(treeNode.item.menus)
            {
                //console.log("Adding new submenus");
                for (var m in treeNode.item.menus)
                {
                    var menu = treeNode.item.menus[m];
                    //console.log("Adding submenu " + mstr);
                    var mi = document.createElement('div')
                    var sm;
                    if(menu.title=="_")
                    {
                        sm = new dijit.MenuSeparator();
                    }else
                    {
                        sm = new dijit.MenuItem(
                        {
                            label: menu.title,
                            iconClass:menu.icon,
                            action: menu.action
                        }, mi);
                        sm.onClick = function(ele)
                        {
                            var m=dijit.getEnclosingWidget(ele.target);
                            //console.log("e.target:"+e.target);
                            //console.log("this"+this);
                            //console.log("this.store:"+this.store);
                            executeAction(<%=store%>, treeNode.item, m.action)
                        };
                    }
                    menuEmpty.addChild(sm);
                }
            }
        });
    </script>
</div>
