/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.portal.resources.sem.forum;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.servlet.internal.UploadFormElement;

/**
* Resource that manage a forum
* @author : Jorge Alberto Jiménez
* @version 1.0
*/

public class SWBForum extends org.semanticwb.portal.resources.sem.forum.base.SWBForumBase
{

    public SWBForum()
    {
       
    }

    public SWBForum(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    private static Logger log = SWBUtils.getLogger(SWBForum.class);
    private String lang = "es";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("addThread")) {
            doABCThread(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editThread")) {
            doEditThread(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editPost")) {
            doEditPost(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("replyPost")) {
            doABCThread(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath()+"/swbadmin/jsp/forum/swbForum.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doABCThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
         try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath()+"/swbadmin/jsp/forum/abcthread.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doEditPost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        mgr.setLang(lang);
        mgr.setSubmitByAjax(false);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", request.getParameter("threadUri"));
        url.setParameter("postUri", semObject.getURI());
        url.setAction("editPost");
        mgr.setAction(url.toString());

        mgr.hideProperty(Post.swb_active);

        Resource base = paramRequest.getResourceBase();
        WebSite website = paramRequest.getWebPage().getWebSite();
        Post post = Post.getPost(semObject.getId(), website);
        String basepath = "";
        int count = 0;
        GenericIterator<Attachment> lAttchments = post.listAttachmentss();

        url.setAction("removeAttach");
        while (lAttchments.hasNext()) {
            count++;
            Attachment attch = lAttchments.next();
            basepath=SWBPortal.getWebWorkPath() + semObject.getWorkPath() + "/"+attch.getFileName();
            request.setAttribute("attach_hasAttachments_"+ count, basepath);
            request.setAttribute("attachTarget_hasAttachments_" +count, "blank");
            url.setParameter("removeAttach", attch.getURI());
            request.setAttribute("attachRemovePath_hasAttachments_" + count, url.toString());
        }
        if (count > 0) {
            request.setAttribute("attachCount_hasAttachments", "" + count);
        }
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        request.setAttribute("formName", mgr.getFormName());
        out.println(mgr.renderForm(request));
    }

    public void doEditThread(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }

        Iterator<SemanticProperty> itSemProps=semObject.getSemanticClass().listProperties();
        while(itSemProps.hasNext()){
            SemanticProperty semProp=itSemProps.next();
            if(semProp!=null)
            {
              if(semProp != Thread.swb_title && semProp != Thread.frm_thBody && semProp != Thread.frm_hasThAttachments
                && semProp != Thread.swb_created && semProp != Thread.swb_creator && semProp != Thread.swb_updated
                && semProp != Thread.swb_modifiedBy)
                {
                       mgr.hideProperty(semProp);
                }
            }
        }

        mgr.setLang(lang);
        mgr.setSubmitByAjax(false);
        mgr.setType(mgr.TYPE_XHTML);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("threadUri", semObject.getURI());
        url.setAction("editThread");
        mgr.setAction(url.toString());


        Resource base = paramRequest.getResourceBase();
        WebSite website = paramRequest.getWebPage().getWebSite();
        Thread thread= Thread.getThread(semObject.getId(), website);
        String basepath = "";
        int count = 0;
        GenericIterator<Attachment> lAttchments = thread.listAttachments();
        url.setAction("removeAttach");
        while (lAttchments.hasNext()) {
            count++;
            Attachment attch = lAttchments.next();
            //basepath = SWBPortal.getWebWorkPath() + "/models/" + website.getId() + "/Resource/" + base.getId() + "/threads/" + thread.getId() + "/" + attch.getFileName();
            basepath=SWBPortal.getWebWorkPath() + semObject.getWorkPath() + "/" + attch.getFileName();
            request.setAttribute("attach_hasThAttachments_" + count, basepath);
            request.setAttribute("attachTarget_hasThAttachments_" + count, "blank");
            url.setParameter("removeAttach", attch.getURI());
            request.setAttribute("attachRemovePath_hasThAttachments_" + count, url.toString());
        }
        if (count > 0) {
            request.setAttribute("attachCount_hasThAttachments", "" + count);
        }
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        request.setAttribute("formName", mgr.getFormName());
        out.println(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage page = response.getWebPage();
        WebSite website = page.getWebSite();
        User user = response.getUser();
        Resource base = response.getResourceBase();
        Date date = new Date();
        String action = response.getAction();
        if (action.equals("addThread")) {
            if(request.getParameter("title")!=null){
                try
                {
                    Thread newThread=Thread.createThread(website);
                    newThread.setTitle(request.getParameter("title"));
                    String body=request.getParameter("thBody");
                    if(body!=null && body.trim().length()>0)newThread.setBody(body);
                    newThread.setParent(page);
                    if (user != null && user.isSigned()) {
                        newThread.setCreator(user);
                    }
                    newThread.setForum(this);

                    processFiles(request, response, newThread.getSemanticObject());
                }catch(Exception e)
                {
                    //TODO:Validar
                    log.error(e);
                }
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
        } else if (action.equals("replyPost")) {
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(soThread.getId(), website);
            if(request.getParameter("pstBody")!=null && request.getParameter("pstBody").trim().length()>0)
            {
                SemanticObject soPost = null;
                Post post = null;
                if (request.getParameter("postUri") != null) {
                    soPost = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    post = Post.getPost(soPost.getId(), website);
                }

                try
                {
                    Post newPost=Post.createPost(website);
                    newPost.setBody(request.getParameter("pstBody"));
                    newPost.setThread(thread);
                    if (post != null) {
                        newPost.setParentPost(post);
                    }

                    processFiles(request, response, newPost.getSemanticObject());

                    thread.setReplyCount(thread.getReplyCount() + 1);
                    thread.setLastPostDate(date);
                    if (user != null && user.isSigned()) {
                        thread.setLastPostMember(user);
                    }
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
            response.setRenderParameter("threadUri", thread.getURI());
        } else if (action.equals("editPost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
            try
            {
                SemanticObject semObj = mgr.processForm(request);

                Post newPost = Post.getPost(semObj.getId(), website);

                processFiles(request, response, newPost.getSemanticObject());

                response.setMode(response.Mode_VIEW);
                response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            }catch(FormValidateException e)
            {
                //TODO:Validar
                log.error(e);
            }
            response.setAction("viewPost");
        } else if (action.equals("removeThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            semObject.remove();
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("removePost")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));

            Post Post2remove = Post.getPost(semObject.getId(), website);
            int childPost = 0;
            GenericIterator<Post> gitPost = Post2remove.listchildPosts();
            while (gitPost.hasNext()) {
                childPost++;
                childPost = getChilds2Remove(gitPost.next(), childPost);
            }
            semObject.remove();

            //Se supone que al borrar el objeto semantico, borra su directorio de trabajo
            //SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath() + "/replies/" + Post2remove.getId() + "/");
            
            //Resta el post al contador del thread
            SemanticObject soThread = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread thread = Thread.getThread(soThread.getId(), website);
            thread.setReplyCount(thread.getReplyCount() - (childPost + 1));
            //Forum forum = thread.getForum();
            //forum.setPostcount(forum.getPostcount() - (childPost + 1));
            //Redirecciona
            response.setRenderParameter("threadUri", thread.getURI());
            response.setMode(response.Mode_VIEW);
            response.setAction("viewPost");
        } else if (action.equals("addFavoriteThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            Thread favThread = Thread.getThread(semObject.getId(), page.getWebSite());
            UserFavThread frmUserThread = UserFavThread.createUserFavThread(website);
            frmUserThread.setThread(favThread);
            if (user != null && user.isSigned()) {
                frmUserThread.setUser(user);
            }
            response.setMode(response.Mode_VIEW);
            response.setAction("viewThreads");
        } else if (action.equals("editThread")) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);

            try
            {
                SemanticObject semObj = mgr.processForm(request);
                Thread thread = Thread.getThread(semObj.getId(), website);

                processFiles(request, response, thread.getSemanticObject());
            }catch(FormValidateException e)
            {
                //TODO:Validar
                log.error(e);
            }
            response.setMode(response.Mode_VIEW);
            response.setRenderParameter("threadUri", request.getParameter("threadUri"));
            response.setAction("viewPost");
        } else if (action.equals("removeAttach")) {
            if (request.getParameter("removeAttach") != null) {
                String attachType="threads";
                if(request.getParameter("postUri")!=null){
                    attachType="replies";
                }

                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("threadUri"));
                Thread thread = Thread.getThread(semObject.getId(), website);

                Post post=null;
                if(attachType.equals("replies")){
                    semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    post = Post.getPost(semObject.getId(), website);
                }

                GenericIterator<Attachment> lAttchments = thread.listAttachments();
                if(post!=null) {
                    lAttchments=post.listAttachmentss();
                }

                String attachUri=request.getParameter("removeAttach");
                while (lAttchments.hasNext()) {
                    Attachment attch = lAttchments.next();
                    if(attch.getURI().equals(attachUri)){
                        try{
                            File file = new File(SWBPortal.getWorkPath() + semObject.getWorkPath() + "/" + attch.getFileName());
                            file.delete();
                            attch.remove();
                        }catch(Exception e){
                            log.debug(e);
                        }
                    }
                }

                response.setRenderParameter("threadUri", request.getParameter("threadUri"));
                response.setRenderParameter("postUri", request.getParameter("postUri"));
            }
        }

    }

    private int getChilds2Remove(Post post, int childPost) {
        GenericIterator<Post> gitPost = post.listchildPosts();
        while (gitPost.hasNext()) {
            childPost++;
            childPost = getChilds2Remove(gitPost.next(), childPost);
        }
        return childPost;
    }

    private void processFiles(HttpServletRequest request, SWBActionResponse response, SemanticObject sobj) {
        Date date = new Date();
        User user = response.getUser();
        WebSite website = response.getWebPage().getWebSite();
        String basepath=SWBPortal.getWorkPath();
        if(sobj.instanceOf(Thread.sclass)){
            Thread threadTmp=(Thread)sobj.createGenericInstance();
            basepath+=threadTmp.getWorkPath() + "/";
        }else if(sobj.instanceOf(Post.sclass)){
            Post postTmp=(Post)sobj.createGenericInstance();
            basepath+=postTmp.getWorkPath() + "/";
        }
        if (request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED) != null) {
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();
            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    int fileSize = ((Long) item.getSize()).intValue();
                    String value = item.getName();
                    value = value.replace("\\", "/");
                    int pos = value.lastIndexOf("/");
                    if (pos > -1) {
                        value = value.substring(pos + 1);
                    }
                    File fichero = new File(basepath);
                    if (!fichero.exists()) {
                        fichero.mkdirs();
                    }
                    fichero = new File(basepath + value);
                    Attachment frmAttachment = Attachment.createAttachment(website);
                    if (user != null && user.isSigned()) {
                        frmAttachment.setCreator(user);
                    }
                    frmAttachment.setCreated(date);
                    frmAttachment.setFileName(value);
                    frmAttachment.setFileSize(fileSize);
                    frmAttachment.setMimeType(item.getContentType());

                    if(sobj.instanceOf(Thread.sclass)){
                        Thread thread=(Thread)sobj.createGenericInstance();
                        frmAttachment.setThread(thread);
                    }else if(sobj.instanceOf(Post.sclass)){
                        Post post=(Post)sobj.createGenericInstance();
                        frmAttachment.setPost(post);
                    }
                    try {
                        item.write(fichero);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug(e);
                    }
                }
            }
            request.getSession().setAttribute(UploadFormElement.FILES_UPLOADED, null);
        }
    }

}
