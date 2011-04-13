package org.semanticwb.resources.sem.forumcat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class SWBForumCatResource extends org.semanticwb.resources.sem.forumcat.base.SWBForumCatResourceBase
{

    private static Logger log = SWBUtils.getLogger(SWBForumCatResource.class);
    public static final int STATUS_REGISTERED = 1;
    public static final int STATUS_ACEPTED = 2;
    public static final int STATUS_REMOVED = 3;

    public SWBForumCatResource()
    {
    }

    public SWBForumCatResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    private static String clean(String html)
    {
        html = html.replace("&lt;", "<");
        html = html.replace("&gt;", ">");
        String text = html;
        int pos = text.indexOf("<");
        while (pos != -1)
        {
            int pos2 = text.indexOf(">", pos);
            if (pos2 == -1)
            {
                text = text.substring(0, pos).trim();
            }
            else
            {
                String tmp = text.substring(0, pos).trim();
                String tmp2 = text.substring(pos2 + 1).trim();
                text = tmp + " " + tmp2;
            }
            pos = text.indexOf("<");
        }
        text = text.trim();
        return text;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String jsp = getViewJSP();
        if (jsp == null || jsp.trim().equals(""))
        {
            jsp = "/work/models/"+paramRequest.getWebPage().getWebSite().getId()+"/jsp/"+ this.getClass().getSimpleName() +"/swbForumCat.jsp";
        }
        try
        {
            request.setAttribute("listQuestions", listQuestionInvs());
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            rd.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        User user = response.getUser();
        WebSite website = response.getWebPage().getWebSite();

        if (isAcceptGuessComments() || user.isSigned())
        {
            String action = response.getAction();
            response.setAction(response.Action_EDIT);
            if (request.getParameter("page") != null)
            {
                response.setRenderParameter("page", request.getParameter("page"));
            }
            if (request.getParameter("deleted") != null)
            {
                response.setRenderParameter("deleted", request.getParameter("deleted"));
            }
            if (request.getParameter("cat") != null)
            {
                response.setRenderParameter("cat", request.getParameter("cat"));
            }
            if (action.equals("addQuestion"))
            {
                SWBFormMgr mgr = new SWBFormMgr(Question.forumCat_Question, getResourceBase().getSemanticObject(), SWBFormMgr.MODE_EDIT);
                try
                {
                    SemanticObject semObject = mgr.processForm(request);
                    Question question = (Question) semObject.createGenericInstance();
                    if (user != null && user.isSigned())
                    {
                        question.setCreator(user);
                    }
                    question.setCreated(Calendar.getInstance().getTime());
                    question.setForumResource(this);
                    question.setQuestion(placeAnchors(question.getQuestion()));
                    question.setQuestionReferences(request.getParameter("questionReferences"));
                    if (request.getParameter("tags") != null)
                    {
                        question.setTags(request.getParameter("tags"));
                    }
                    if (request.getParameter("categoryuri") != null && !request.getParameter("categoryuri").trim().equals(""))
                    {
                        SemanticObject semObjectChild = SemanticObject.createSemanticObject((request.getParameter("categoryuri")));
                        WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                        question.setWebpage(webPage);
                    }
                    if (isIsModerate())
                    {
                        question.setQueStatus(STATUS_REGISTERED);
                    }
                    else
                    {
                        question.setQueStatus(STATUS_ACEPTED);
                    }
                }
                catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (isUseScoreSystem() && user != null)
                {
                    UserPoints points = getUserPointsObject(user, website);
                    if (points == null)
                    {
                        points = UserPoints.ClassMgr.createUserPoints(website);
                        points.setPointsForum(this);
                        points.setPointsUser(user);
                    }
                    points.setPoints(points.getPoints() + getPointsPublishQuestion());
                }
            }
            else if (action.equals("editQuestion"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                    if (request.getParameter("categoryuri") != null && !request.getParameter("categoryuri").equals(""))
                    {
                        Question question = (Question) semObject.createGenericInstance();
                        boolean canedit = false;
                        if (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId()))
                        {
                            canedit = true;
                        }
                        if (canedit)
                        {
                            SemanticObject semObjectChild = SemanticObject.createSemanticObject((request.getParameter("categoryuri")));
                            WebPage webPage = (WebPage) semObjectChild.createGenericInstance();
                            question.setWebpage(webPage);
                            question.setQuestion(placeAnchors(question.getQuestion()));
                            if (request.getParameter("tags") != null)
                            {
                                question.setTags(request.getParameter("tags"));
                            }
                        }
                    }
                }
                catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                }
                else
                {
                    response.setAction("edit");
                }
                response.setRenderParameter("uri", request.getParameter("uri"));
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
            else if (action.equals("removeQuestion"))
            {
                try
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Question question = (Question) semObject.createGenericInstance();
                    boolean isAdmin = false;
                    Role role = user.getUserRepository().getRole("adminForum");
                    UserGroup group = user.getUserRepository().getUserGroup("admin");
                    if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                    {
                        isAdmin = true;
                    }
                    if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
                    {

                        question.remove();
                        if (request.getParameter("org") != null)
                        {
                            response.setAction(request.getParameter("org"));
                        }
                        else
                        {
                            response.setAction("edit");
                        }
                        if (request.getParameter("page") != null)
                        {
                            response.setRenderParameter("page", request.getParameter("page"));
                        }
                        if (request.getParameter("cat") != null)
                        {
                            response.setRenderParameter("cat", request.getParameter("cat"));
                        }
                        if (request.getParameter("deleted") != null)
                        {
                            response.setRenderParameter("deleted", request.getParameter("deleted"));
                        }
                    }

                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            else if (action.equals("answerQuestion"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();
                SWBFormMgr mgr = new SWBFormMgr(Answer.forumCat_Answer, semObject, SWBFormMgr.MODE_EDIT);
                try
                {
                    SemanticObject semObjectChild = mgr.processForm(request);
                    Answer answer = (Answer) semObjectChild.createGenericInstance();
                    answer.setAnsQuestion(question);
                    answer.setAnswer(placeAnchors(answer.getAnswer()));
                    answer.setReferences(request.getParameter("references"));
                    answer.setCreated(Calendar.getInstance().getTime());
                    if (isIsModerate())
                    {
                        answer.setAnsStatus(STATUS_REGISTERED);
                    }
                    else
                    {
                        answer.setAnsStatus(STATUS_ACEPTED);
                    }
                }
                catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                }
                else
                {
                    response.setAction("showDetail");
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (isUseScoreSystem() && user != null)
                {
                    UserPoints points = getUserPointsObject(user, website);
                    if (points == null)
                    {
                        points = UserPoints.ClassMgr.createUserPoints(website);
                        points.setPointsForum(this);
                        points.setPointsUser(user);
                    }
                    points.setPoints(points.getPoints() + getPointsAnswer());
                }
                response.setRenderParameter("uri", request.getParameter("uri"));
            }
            else if (action.equals("editAnswer"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                try
                {
                    mgr.processForm(request);
                }
                catch (FormValidateException e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                }
                else
                {
                    response.setAction("showDetail");
                }
                Answer answer = (Answer) semObject.createGenericInstance();
                boolean canedit = false;
                if (answer.getCreator() != null && answer.getCreator().getId() != null && user.getId() != null && user.getId() != null && answer.getCreator().getId().equals(user.getId()))
                {
                    canedit = true;
                }
                if (canedit)
                {
                    answer.setAnswer(placeAnchors(answer.getAnswer()));
                    response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                }
            }
            else if (action.equals("markQuestionAsInnapropiate"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();
                try
                {
                    if (!question.isQueIsApropiate())
                    {
                        int innapropiateCount = question.getQueInappropriate() + 1;
                        question.setQueInappropriate(innapropiateCount);
                        if (innapropiateCount % getMaxInnapropiateCount() == 0 && innapropiateCount > 0)
                        {//Enviar correo a administradores del foro
                            HashSet<User> users = new HashSet<User>();
                            Role role = website.getUserRepository().getRole("adminForum");
                            if (role != null)
                            {
                                Iterator<User> itusers = User.ClassMgr.listUserByRole(role);
                                while (itusers.hasNext())
                                {
                                    User ruser = itusers.next();
                                    users.add(ruser);
                                }
                            }
                            if (users.size() > 0)
                            {
                                SWBMail swbMail = new SWBMail();

                                ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                                for (User ouser : users)
                                {
                                    if (ouser.getEmail() != null)
                                    {
                                        InternetAddress address1 = new InternetAddress();
                                        address1.setAddress(ouser.getEmail());
                                        aAddress.add(address1);
                                    }
                                }
                                String port = "";
                                if (request.getServerPort() != 80)
                                {
                                    port = ":" + request.getServerPort();
                                }
                                SWBResourceURLImp imp = new SWBResourceURLImp(request, response.getResourceBase(), response.getWebPage(), SWBResourceURL.UrlType_RENDER);
                                imp.setAction("showDetail");
                                imp.setParameter("uri", question.getURI());
                                URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port + imp.toString());
                                swbMail.setAddress(aAddress);
                                swbMail.setContentType("text/html");
                                swbMail.setData("Un mensaje ya sobrepaso el umbral de lo considerado como inapropiado en la direccion web " + urilocal + " con el texto: " + clean(question.getQuestion()));
                                swbMail.setSubject("Mensaje inapropiado " + response.getWebPage().getTitle());
                                swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                                swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                                SWBUtils.EMAIL.sendBGEmail(swbMail);

                            }

                        }
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                    else
                    {
                        response.setAction("edit");
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            else if (action.equals("markAnswerAsInnapropiate"))
            {

                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer = (Answer) semObject.createGenericInstance();
                try
                {
                    if (!answer.isAnsIsAppropiate())
                    {

                        int innapropiateCount = answer.getAnsInappropriate() + 1;
                        answer.setAnsInappropriate(innapropiateCount);
                        if (innapropiateCount % getMaxInnapropiateCount() == 0 && innapropiateCount > 0)
                        {
                            //Enviar correo a administradores del foro
                            HashSet<User> users = new HashSet<User>();
                            Role role = website.getUserRepository().getRole("adminForum");
                            if (role != null)
                            {
                                Iterator<User> itusers = User.ClassMgr.listUserByRole(role);
                                while (itusers.hasNext())
                                {
                                    User ruser = itusers.next();
                                    users.add(ruser);
                                }
                            }
                            if (users.size() > 0)
                            {
                                SWBMail swbMail = new SWBMail();

                                ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                                for (User ouser : users)
                                {
                                    if (ouser.getEmail() != null)
                                    {
                                        InternetAddress address1 = new InternetAddress();
                                        address1.setAddress(ouser.getEmail());
                                        aAddress.add(address1);
                                    }
                                }
                                String port = "";
                                if (request.getServerPort() != 80)
                                {
                                    port = ":" + request.getServerPort();
                                }
                                SWBResourceURLImp imp = new SWBResourceURLImp(request, response.getResourceBase(), response.getWebPage(), SWBResourceURL.UrlType_RENDER);
                                imp.setAction("showDetail");
                                imp.setParameter("uri", answer.getAnsQuestion().getURI());
                                URL urilocal = new URL(request.getScheme() + "://" + request.getServerName() + port + imp.toString());
                                swbMail.setAddress(aAddress);
                                swbMail.setContentType("text/html");
                                swbMail.setData("Una respuesta ya sobrepaso el umbral de lo considerado como inapropiado en la dirección web " + urilocal + " con el texto: " + clean(answer.getAnswer()));
                                swbMail.setSubject("Respuesta inapropiada " + response.getWebPage().getTitle());
                                swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                                swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                                SWBUtils.EMAIL.sendBGEmail(swbMail);

                            }

                        }
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                    else
                    {
                        response.setAction("edit");
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            else if (action.equals("bestAnswer"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer = (Answer) semObject.createGenericInstance();
                try
                {
                    if (!answer.isBestAnswer())
                    {
                        answer.setBestAnswer(true);
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
                if (request.getParameter("org") != null)
                {
                    response.setAction(request.getParameter("org"));
                }
                else
                {
                    response.setAction("showDetail");
                }

                if (isUseScoreSystem() && user != null)
                {
                    UserPoints points = getUserPointsObject(user, website);
                    if (points == null)
                    {
                        points = UserPoints.ClassMgr.createUserPoints(website);
                        points.setPointsForum(this);
                        points.setPointsUser(user);
                    }
                    points.setPoints(points.getPoints() + getPointsMarkBestAnswer());

                    points = getUserPointsObject(answer.getCreator(), website);
                    if (points == null)
                    {
                        points = UserPoints.ClassMgr.createUserPoints(website);
                        points.setPointsForum(this);
                        points.setPointsUser(answer.getCreator());
                    }
                    points.setPoints(points.getPoints() + getPointsBestAnswer());
                }
                response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
            }
            else if (action.equals("closeQuestion"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
                {

                    question.isClosed();
                    try
                    {
                        if (!question.isClosed())
                        {
                            question.setClosed(true);
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                    }
                    else
                    {
                        response.setAction("showDetail");
                    }
                    response.setRenderParameter("uri", question.getURI());
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                }
            }
            else if (action.equals("openQuestion"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin || (question.getCreator() != null && question.getCreator().getId() != null && user.getId() != null && question.getCreator().getId().equals(user.getId())))
                {

                    try
                    {
                        if (question.isClosed())
                        {
                            question.setClosed(false);
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                    }
                    else
                    {
                        response.setAction("showDetail");
                    }
                    response.setRenderParameter("uri", question.getURI());
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                }
            }
            else if (action.equals("voteQuestion"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();

                boolean hasVoted = false;
                if (user != null && question.userHasVoted(user))
                {
                    hasVoted = true;
                }

                QuestionVote questionVote = null;
                if (!hasVoted)
                {
                    questionVote = QuestionVote.ClassMgr.createQuestionVote(website);
                    questionVote.setQuestionVote(question);
                    if (user != null)
                    {
                        questionVote.setUserVote(user);
                    }
                }

                if (questionVote != null)
                {
                    if (request.getParameter("commentVote") != null && request.getParameter("commentVote").trim().length() > 0)
                    {
                        questionVote.setCommentVote(request.getParameter("commentVote"));
                    }
                    if (request.getParameter("likeVote") != null)
                    {
                        boolean likeVote = Boolean.parseBoolean(request.getParameter("likeVote"));
                        questionVote.setLikeVote(likeVote);
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                    else
                    {
                        response.setAction("edit");
                    }
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
            else if (action.equals("voteAnswer"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer = (Answer) semObject.createGenericInstance();

                boolean hasVoted = answer.userHasVoted(user);
                AnswerVote answerVote = null;
                if (!hasVoted)
                {
                    answerVote = AnswerVote.ClassMgr.createAnswerVote(website);
                    answerVote.setAnswerVote(answer);
                    if (user != null)
                    {
                        answerVote.setAnsUserVote(user);
                    }
                }

                if (answerVote != null)
                {
                    if (request.getParameter("commentVote") != null && request.getParameter("commentVote").trim().length() > 0)
                    {
                        answerVote.setAnsCommentVote(request.getParameter("commentVote"));
                    }
                    if (request.getParameter("likeVote") != null)
                    {
                        boolean likeVote = Boolean.parseBoolean(request.getParameter("likeVote"));
                        answerVote.setLikeAnswer(likeVote);
                        if (isUseScoreSystem() && user != null)
                        {
                            UserPoints points = getUserPointsObject(user, website);
                            if (points == null)
                            {
                                points = UserPoints.ClassMgr.createUserPoints(website);
                                points.setPointsForum(this);
                                points.setPointsUser(user);
                            }
                            points.setPoints(points.getPoints() + getPointsVoteAnswer());

                            points = getUserPointsObject(answer.getCreator(), website);
                            if (points == null)
                            {
                                points = UserPoints.ClassMgr.createUserPoints(website);
                                points.setPointsForum(this);
                                points.setPointsUser(answer.getCreator());
                            }

                            if (likeVote)
                            {
                                points.setPoints(points.getPoints() + getPointsLikeAnswer());
                            }
                            else
                            {
                                int p = points.getPoints() - getPointsDontLikeAnswer();
                                if (p < 0)
                                {
                                    p = 0;
                                }
                                points.setPoints(p);
                            }
                        }
                    }
                    if (request.getParameter("irrelevant") != null)
                    {
                        boolean likeVote = Boolean.parseBoolean(request.getParameter("irrelevant"));
                        answerVote.setIrrelevantVote(likeVote);

                        int irrelevantCount = answer.getAnsIrrelevant() + 1;
                        answer.setAnsIrrelevant(irrelevantCount);
                        if (isUseScoreSystem() && user != null)
                        {
                            UserPoints points = getUserPointsObject(answer.getCreator(), website);
                            if (points == null)
                            {
                                points = UserPoints.ClassMgr.createUserPoints(website);
                                points.setPointsForum(this);
                                points.setPointsUser(answer.getCreator());
                            }
                            int p = points.getPoints() - getPointsIrrelevantAnswer();
                            if (p < 0)
                            {
                                p = 0;
                            }
                            points.setPoints(p);
                        }
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                    }
                    else
                    {
                        response.setAction("showDetail");
                    }
                    response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
            else if (action.equals("removeAnswer"))
            {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Answer answer = (Answer) semObject.createGenericInstance();
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin || (answer.getCreator() != null && answer.getCreator().getId() != null && user.getId() != null && answer.getCreator().getId().equals(user.getId())))
                {
                    try
                    {

                        if (request.getParameter("org") != null)
                        {
                            response.setAction(request.getParameter("org"));
                        }
                        else
                        {
                            response.setAction("showDetail");
                        }

                        response.setRenderParameter("uri", answer.getAnsQuestion().getURI());
                        answer.remove();
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
                if (request.getParameter("deleted") != null)
                {
                    response.setRenderParameter("deleted", request.getParameter("deleted"));
                }
                if (request.getParameter("page") != null)
                {
                    response.setRenderParameter("page", request.getParameter("page"));
                }
                if (request.getParameter("cat") != null)
                {
                    response.setRenderParameter("cat", request.getParameter("cat"));
                }
            }
            else if (action.equals("subcribe2question"))
            {
                boolean isSuscribed = false;
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                Question question = (Question) semObject.createGenericInstance();
                Iterator<QuestionSubscription> it_subs = QuestionSubscription.ClassMgr.listQuestionSubscriptionByQuestionObj(question);
                while (it_subs.hasNext() && !isSuscribed)
                {
                    QuestionSubscription qs = it_subs.next();
                    if (user != null && qs.getUserObj() != null && qs.getUserObj().getURI().equals(user.getURI()))
                    {
                        isSuscribed = true;
                    }
                }

                if (!isSuscribed)
                {
                    try
                    {
                        QuestionSubscription questionSubs = QuestionSubscription.ClassMgr.createQuestionSubscription(website);
                        questionSubs.setQuestionObj(question);
                        if (user != null)
                        {
                            questionSubs.setUserObj(user);
                        }
                        //TODO: Enviar correo
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                    if (request.getParameter("org") != null)
                    {
                        response.setAction(request.getParameter("org"));
                        response.setRenderParameter("uri", request.getParameter("uri"));
                    }
                    else
                    {
                        response.setAction("edit");
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    if (request.getParameter("page") != null)
                    {
                        response.setRenderParameter("page", request.getParameter("page"));
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                }
            }
            else if (action.equals("subcribe2category"))
            {
                try
                {
//TODO: que se hace aqui
/* esta no supe cuando se debe de llamar  -RGJS*/
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    WebPage category = (WebPage) semObject.createGenericInstance();
                    CategorySubscription catSubs = CategorySubscription.ClassMgr.createCategorySubscription(website);
                    catSubs.setCategoryWebpage(category);
                    if (user != null)
                    {
                        catSubs.setCategoryUser(user);
                    }
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            else if (action.equals("AcceptQuestion"))
            {
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin)
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Question question = (Question) semObject.createGenericInstance();
                    question.setQueStatus(STATUS_ACEPTED);
                    response.setAction("moderate");
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                }
            }
            else if (action.equals("AcceptAnswer"))
            {
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin)
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Answer answer = (Answer) semObject.createGenericInstance();
                    answer.setAnsStatus(STATUS_ACEPTED);
                    response.setAction("moderate");
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                }
            }
            else if (action.equals("RejectQuestion"))
            {
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin)
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Question question = (Question) semObject.createGenericInstance();
                    question.setQueStatus(STATUS_REMOVED);
                    if (question.getCreator() != null && question.getCreator().getEmail() != null)
                    {
                        String text = clean(question.getQuestion());
                        SWBMail swbMail = new SWBMail();
                        String toemail = question.getCreator().getEmail();
                        ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                        try
                        {
                            aAddress.add(new InternetAddress(toemail));
                            swbMail.setAddress(aAddress);
                            swbMail.setSubject("Mensaje rechazado en el foro " + request.getServerName());
                            swbMail.setData("Su mensaje fue rechazado por no cumplir con las politicas de uso del portal.<br>" + text);
                            swbMail.setContentType("text/html");
                            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                            SWBUtils.EMAIL.sendBGEmail(swbMail);
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    response.setAction("moderate");
                }
            }
            else if (action.equals("RejectAnswer"))
            {
                boolean isAdmin = false;
                Role role = user.getUserRepository().getRole("adminForum");
                UserGroup group = user.getUserRepository().getUserGroup("admin");
                if (role != null && (user.hasRole(role) || user.hasUserGroup(group)))
                {
                    isAdmin = true;
                }
                if (isAdmin)
                {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                    Answer answer = (Answer) semObject.createGenericInstance();
                    answer.setAnsStatus(STATUS_REMOVED);
                    if (answer.getCreator() != null && answer.getCreator().getEmail() != null)
                    {
                        String text = clean(answer.getAnswer());
                        String toemail = answer.getCreator().getEmail();
                        try
                        {
                            SWBMail swbMail = new SWBMail();
                            ArrayList<InternetAddress> aAddress = new ArrayList<InternetAddress>();
                            aAddress.add(new InternetAddress(toemail));
                            swbMail.setAddress(aAddress);
                            swbMail.setSubject("Respuesta rechazada en el foro " + request.getServerName());
                            swbMail.setData("Su respuesta fue rechazada por no cumplir con las politicas de uso del portal.<br>" + text);
                            swbMail.setContentType("text/html");
                            swbMail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                            swbMail.setHostName(SWBPlatform.getEnv("swb/smtpServer"));
                            SWBUtils.EMAIL.sendBGEmail(swbMail);
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                    }
                    if (request.getParameter("cat") != null)
                    {
                        response.setRenderParameter("cat", request.getParameter("cat"));
                    }
                    if (request.getParameter("deleted") != null)
                    {
                        response.setRenderParameter("deleted", request.getParameter("deleted"));
                    }
                    response.setAction("moderate");
                }
            }
        }
        response.setMode(response.Mode_VIEW);
    }

    public String placeAnchors(String text)
    {
        if (text == null || text.trim().equals(""))
            return "";
        String ret = text;
        String regex = "https?://[\\+\\w+\\d+%#@:=/\\.\\?&_~-]+";
        Matcher m = Pattern.compile(regex, 0).matcher(text);

        while (m.find())
        {
            String urlt = m.group();
            if (urlt.endsWith(".") || urlt.endsWith(",") || urlt.endsWith(";") || urlt.endsWith(":"))
            {
                urlt = urlt.substring(0, urlt.length() - 1);
            }
            ret = ret.replaceAll(Pattern.quote(urlt), "<a href=\"" + urlt + "\">" + urlt + "</a>");
        }
        return ret;
    }

    private UserPoints getUserPointsObject(User user, WebSite model)
    {
        UserPoints ret = null;
        boolean found = false;

        if (user != null)
        {
            Iterator<UserPoints> itpoints = UserPoints.ClassMgr.listUserPointsByPointsUser(user, model);
            while (itpoints.hasNext() && !found)
            {
                UserPoints points = itpoints.next();
                if (points.getPointsForum().getURI().equals(getURI()))
                {
                    ret = points;
                    found = true;
                }
            }
        }
        return ret;
    }
}
