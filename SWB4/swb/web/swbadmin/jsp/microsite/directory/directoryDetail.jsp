<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.GenericIterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.Descriptiveable"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.portal.SWBFormMgr"%>
<%@page import="org.semanticwb.model.FormElement"%>
<%@page import="org.semanticwb.portal.SWBFormButton"%>
<%@page import="org.semanticwb.portal.community.*"%>


<%
            HashMap map = new HashMap();
            map.put("separator", "-");
            WebPage wpage = paramRequest.getWebPage();
            Resource base = paramRequest.getResourceBase();
            String perfilPath = wpage.getWebSite().getWebPage("perfil").getUrl();
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            DirectoryObject dirObj = (DirectoryObject) semObject.createGenericInstance();
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_VIEW);
            String path = SWBPortal.getWebWorkPath() + base.getWorkPath() + "/" + semObject.getId() + "/";
            //Obtener valores de propiedades genericas
            String dirPhoto = semObject.getProperty(DirectoryObject.ClassMgr.swbcomm_dirPhoto);

            String[] sImgs = null;
            int cont = 0;
            Iterator<String> itExtraPhotos = dirObj.listExtraPhotos();
            while (itExtraPhotos.hasNext()) {
                cont++;
                itExtraPhotos.next();
            }
            boolean bprincipalPhoto = false;
            if (dirPhoto != null) {
                cont++;
                bprincipalPhoto = true;
            }
            sImgs = new String[cont];

            cont = -1;
            if (bprincipalPhoto) {
                sImgs[0] = path + dirPhoto;
                cont = 0;
            }
            itExtraPhotos = dirObj.listExtraPhotos();
            while (itExtraPhotos.hasNext()) {
                cont++;
                String photo = itExtraPhotos.next();
                sImgs[cont] = path + photo;
            }

            String imggalery = null;
            if (sImgs.length > 0) {
                imggalery = SWBPortal.UTIL.getGalleryScript(sImgs);
                //imggalery="<img src=\""+SWBPortal.getContextPath()+"/swbadmin/images/noDisponible.gif\"/>";
            } else {
                imggalery = "<img src=\"" + SWBPortal.getContextPath() + "/swbadmin/images/noDisponible.gif\"/>";
            }
            String title = semObject.getProperty(dirObj.swb_title);
            String description = semObject.getProperty(dirObj.swb_description);
            String tags = semObject.getProperty(DirectoryObject.ClassMgr.swb_tags);
            String creator = "";
            String lat = "";
            String lon = "";
            SemanticObject semUser = semObject.getObjectProperty(DirectoryObject.swb_creator);
            if (semUser != null) {
                User userObj = (User) semUser.createGenericInstance();
                creator = "<a href=\"" + perfilPath + "?user=" + userObj.getEncodedURI() + "\">" + userObj.getFullName() + "</a>";
            }
            String created = semObject.getProperty(dirObj.swb_created);
            String mapa = "";
            Iterator<SemanticProperty> itProps = semObject.listProperties();
            /*if (semObject.instanceOf(Geolocalizable.swb_Geolocalizable)) {
                mapa = true;
                lat = semObject.getDoubleProperty(Geolocalizable.swb_latitude);
                lon = semObject.getDoubleProperty(Geolocalizable.swb_longitude);
            }*/
            while (itProps.hasNext()) {
                SemanticProperty semProp = itProps.next();
                if (semProp == Geolocalizable.swb_latitude) {
                    //mapa = true;
                    mapa = mgr.renderElement(request, semProp.getName());
                    break;
                }
            }
            String streetName = semObject.getProperty(Commerce.swbcomm_streetName);
            String intNumber = semObject.getProperty(Commerce.swbcomm_intNumber);
            String extNumber = semObject.getProperty(Commerce.swbcomm_extNumber);
            String city = semObject.getProperty(Commerce.swbcomm_city);
            /*----------  Personal Data ---------*/
            String contactName = semObject.getProperty(Commerce.swbcomm_contactName);
            String contactPhoneNumber = semObject.getProperty(Commerce.swbcomm_contactPhoneNumber);
            String contactEmail = semObject.getProperty(Commerce.swbcomm_contactEmail);
            String website = semObject.getProperty(Commerce.ClassMgr.swbcomm_webSite);
            /*---------- Facilities ------------*/
            String paymentType = semObject.getProperty(Commerce.ClassMgr.swbcomm_paymentType);
            String impairedPeopleAccessible = semObject.getProperty(Commerce.swbcomm_impairedPeopleAccessible);
            String parkingLot = semObject.getProperty(Commerce.swbcomm_parkingLot);
            String elevator = semObject.getProperty(Commerce.swbcomm_elevator);
            String foodCourt = semObject.getProperty(Commerce.swbcomm_foodCourt);
            String serviceHours = semObject.getProperty(Commerce.ClassMgr.swbcomm_serviceHours);

            SWBResourceURL url = paramRequest.getActionUrl();
            url.setParameter("uri", semObject.getURI());
            url.setAction(url.Action_REMOVE);
%>

<div class="columnaIzquierda">
    <div class="adminTools">
        <a class="adminTool" onclick="javascript:history.go(-1);" href="#">Regresar al indice</a>
        <%
        if (paramRequest.getAction().equals(paramRequest.Action_REMOVE)) {
            %>
            <a class="adminTool" href="<%=url%>">Borrar</a>
            <%
        }
        %>
    </div>
    <p class="tituloRojo"><%=title%></p>
    <div class="resumenText">
        <%if (tags != null) {%><p><span class="itemTitle">Palabras clave: </span><%=tags%></p><%}%>
        <%if (creator != null) {%><p><span class="itemTitle">Creado por: </span><%=creator%></p><%}%>
        <%if (created != null) {%><p><span class="itemTitle">Fecha de publicación: </span><%=created%></p><%}%>
        <%if (paymentType != null) {%><p><span class="itemTitle">Forma de pago: </span><%=paymentType%></p><%}%>
        <%
    if (impairedPeopleAccessible != null) {
        String sPeopleAccessible = "";
        if (impairedPeopleAccessible.equals("true")) {
            sPeopleAccessible = "Si";
        } else {
            sPeopleAccessible = "No";
        }
        %>
        <p><span class="itemTitle">Habilitado para discapacitados: </span><%=sPeopleAccessible%></p>
        <%
}
        %>
        <%
    if (parkingLot != null) {
        String sparkingLot = "";
        if (parkingLot.equals("true")) {
            sparkingLot = "Si";
        } else {
            sparkingLot = "No";
        }
        %>
        <p><span class="itemTitle">Estacionamiento: </span><%=sparkingLot%></p>
        <%
    }
        %>
        <%
    if (elevator != null) {
        String selevator = "";
        if (elevator.equals("true")) {
            selevator = "Si";
        } else {
            selevator = "No";
        }
        %>
        <p><span class="itemTitle">Elevador: </span><%=selevator%></p>
        <%
    }
        %>
        <%
    if (foodCourt != null) {
        String sfoodCourt = "";
        if (foodCourt.equals("true")) {
            sfoodCourt = "Si";
        } else {
            sfoodCourt = "No";
        }
        %>
        <p><span class="itemTitle">Area de comida: </span><%=sfoodCourt%></p>
        <%
    }
        %>
        <%if (serviceHours != null) {%><p><span class="itemTitle">Horario: </span><%=serviceHours%></p><%}%>
    </div>
    <p class="abstract"></p>
    <%if (description != null) {%><h2>Descripci&oacute;n</h2><p><%=description%></p><%}%>
    <h2>Ubicaci&oacute;n</h2>
    <%if (streetName != null) {%><p><span class="itemTitle">Calle: </span><%=streetName%></p><%}%>
    <%if (intNumber != null) {%><p><span class="itemTitle">N&uacute;mero Interior: </span><%=intNumber%></p><%}%>
    <%if (extNumber != null) {%><p><span class="itemTitle">N&uacute;mero Exterior: </span><%=extNumber%></p><%}%>
    <%if (city != null) {%><p><span class="itemTitle">Ciudad: </span><%=city%></p><%}%>
    <div class="googleMapsResource">
        <%if(mapa != null){%><%=mapa%><%}%>
    </div>
    <div class="commentBox">
        <a class="userTool" href="">Escribir comentario</a>
        <a class="userTool" href="">Ocultar comentarios</a>
        <form action="" method="post">
            <div>
                <label for="commentTextArea">Comentario</label>
                <textarea id="commentTextArea" rows="5" cols="45" name="commentTextArea"></textarea>
            </div>
        </form>
        <a class="userTool">Publicar</a>
    </div>
    <h2>Comentarios</h2>
    <div id="commentsBoard">
        <div class="comment">
            <img alt="usuario" src="images/commentImagePlaceHolder.jpg"/>
            <div class="commentText">
                <p>
                    Escrito por <a href="">Jei Solis</a> 19/10/09 3 d&iacute;a(s) atr&aacute;s
                </p>
                <p>
                    Frosted Flakes", by itself, is purely a description of the product; as a result that term cannot be trademarked and can be used by any company making a similar product. By contrast, "Kellogg's Frosted Flakes"
                </p>
                <p>
                    <a href="">[marcar como spam]</a>
                </p>
            </div>
        </div>
        <div class="comment">
            <img alt="usuario" src="images/commentImagePlaceHolder.jpg"/>
            <div class="commentText">
                <p>
                    Escrito por <a href="">Jei Solis</a> 19/10/09 3 d&iacute;a(s) atr&aacute;s
                </p>
                <p>
                    Frosted Flakes", by itself, is purely a description of the product; as a result that term cannot be trademarked and can be used by any company making a similar product. By contrast, "Kellogg's Frosted Flakes"
                </p>
                <p>
                    <a href="">[marcar como spam]</a>
                </p>
            </div>
        </div>
        <div class="comment">
            <img alt="usuario" src="images/commentImagePlaceHolder.jpg"/>
            <div class="commentText">
                <p>
                    Escrito por <a href="">Jei Solis</a> 19/10/09 3 d&iacute;a(s) atr&aacute;s
                </p>
                <p>
                    Frosted Flakes", by itself, is purely a description of the product; as a result that term cannot be trademarked and can be used by any company making a similar product. By contrast, "Kellogg's Frosted Flakes"
                </p>
                <p>
                    <a href="">[marcar como spam]</a>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="columnaCentro">
    <div style="margin:20px; padding:0px;">
        <%if (imggalery != null) {%><%=imggalery%><%}%>
    </div>
    <h2>Datos de contacto</h2>
    <ul class="listaElementos">
            <%if (creator != null) {%><li><p class="itemTitle">Contacto:</p><span class="autor"><%=creator%></span></li><%}%>
            <%if (contactPhoneNumber != null) {%><li><p class="itemTitle">Tel&eacute;fono:</p><%=contactPhoneNumber%></li><%}%>
            <%if (contactEmail != null) {%><li><p class="itemTitle">Correo electr&oacute;nico:</p><a href="mailto:<%=contactEmail%>"><%=contactEmail%></a></li><%}%>
            <%if (tags != null) {%><li><p class="itemTitle">Palabras clave:</p><%=tags%></li><%}%>
    </ul>
</div>