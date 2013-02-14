package org.semanticwb.social;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.listener.twitter.SWBSocialStatusListener;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Tweet;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class Twitter extends org.semanticwb.social.base.TwitterBase {

    Logger log = SWBUtils.getLogger(Twitter.class);
    TwitterStream trial=null;
    
    private RequestToken requestToken;
    
    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }
    
    public RequestToken getRequestToken() {
        return requestToken;
    }

    public Twitter(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        if (message != null && message.getMsg_Text() != null && message.getMsg_Text().trim().length() > 1) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            try {
                twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
                AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
                twitter.setOAuthAccessToken(accessToken);
//                StatusUpdate sup = new StatusUpdate(new String(message.getMsg_Text().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(message.getMsg_Text()).getBytes(), "ISO-8859-1"));

                Status stat = twitter.updateStatus(sup);
                Long longStat = stat.getId();
                // System.out.println("longStat: " + longStat + " texto: " + stat.getText());
                //getPostContainer().getPost().setSocialNetPostId("");
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception" + ex);
            } catch (TwitterException ex) {
                log.error("Exception" + ex);
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        User user = response.getUser();

        if (photo != null && photo.getPhoto() != null && photo.getPhoto().trim().length() > 1) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
            AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
            String description = photo.getDescription(user.getLanguage()) == null ? (photo.getDescription() == null ? "" : photo.getDescription()) : photo.getDescription(user.getLanguage());
            twitter.setOAuthAccessToken(accessToken);
            String photoSend = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_photo.getName()
                    + "_" + photo.getId() + "_" + photo.getPhoto();
            try {
                twitter.setOAuthAccessToken(accessToken);
                //StatusUpdate sup = new StatusUpdate(new String(photo.getComment().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
                sup.setMedia(new File(photoSend));
                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception" + ex);
            } catch (TwitterException ex) {
                log.error("Exception" + ex);
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
            //System.out.println("Mensaje de Photo de Twitter:" + description);
            //System.out.println("Photo de Twitter:" + photoSend);
        }
    }

    private String shortUrl(String urlLong) {
        String mensajeNuevo = "";
        if (urlLong != null && !urlLong.equals("")) {
            String delimiter = " ";
            String[] temp = urlLong.split(delimiter);
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i].startsWith("http://") || temp[i].startsWith("https://")) && ((temp[i].length() > 9))) {
                    tinyURL tU = new tinyURL();
                    temp[i] = tU.getTinyURL(temp[i]);
                }
                mensajeNuevo += temp[i] + " ";
            }
        }
        return mensajeNuevo;
    }

    @Override
    public void listen(Stream stream) {
        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("Red SocialID:"+this.getId()+", Red Title:"+this.getTitle()+", sitio:"+wsite.getId());
        ArrayList <ExternalPost> aListExternalPost=new ArrayList();
        try {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            Query query = new Query(stream.getPhrase());
            QueryResult result=twitter.search(query);
            List<Tweet> tweets = result.getTweets();
            for (Tweet tweet : tweets) {
                ExternalPost external = new ExternalPost();
                external.setPostId(String.valueOf(tweet.getId())); 
                external.setCreatorId(String.valueOf(tweet.getFromUserId()));
                external.setCreatorName("@"+tweet.getFromUser());
                external.setCreationTime(""+tweet.getCreatedAt());
                //external.setUpdateTime();
                if (tweet.getText()!=null) {
                    external.setMessage(tweet.getText());
                }
                //if (tweet.getAnnotations().) { TODO: Ver si en las anotaciones, se encuentra una descripción del tweet
                //    external.setDescription(postsData.getJSONObject(k).getString("description"));
                //}
                aListExternalPost.add(external);
            }
            new Classifier(aListExternalPost, stream, this);
        } catch (Exception te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }

    }


    @Override
    public void listenAlive(SWBModel model) {
        try {
            StatusListener listener = new SWBSocialStatusListener(model, this, null);
            /*create filterQuery*/
            FilterQuery query = new FilterQuery();
            //NOTE: format of values: {minLongitude, minLatitude}, {...}
            //double[][] loc = {{-118, 37}, {-86, 33}}; //Bounding Box de San Francisco
            //double[][] loc = {{37.78452999999, -122.39532395324}, {37.78452999998, -122.39532395323}}; //Bounding Box de San Francisco
            //double[][] loc = {{32.718620, -86.703392}, {14.532850, -118.867172}}; //Bounding Box de México (País) Encontrado en http://isithackday.com/geoplanet-explorer/index.php?woeid=23424900
            //query.locations(loc);

            //Palabras a monitorear
            String words2Monitor=SWBSocialUtil.words2Monitor.getWords2Monitor(",", model);
            //System.out.println("words2MonitorGeorge:"+words2Monitor+", en cta:"+this);
            if(words2Monitor!=null && words2Monitor.trim().length()>0)
            {
                String[] tr = {words2Monitor};
                query.track(tr);
            }


            //System.out.println(query.toString());

            //Autenticación con Oath, comentado, para despues utilizarlo
        /*
            TwitterStream twitterStream = new
            TwitterStreamFactory(listener).getInstance();
            twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            twitterStream.setOAuthAccessToken(new AccessToken(TOKEN,
            TOKEN_SECRET));
            FilterQuery filterQuery = new FilterQuery();
            filterQuery.track(new String[] {"is a"});
            twitterStream.filter(filterQuery);
             *
             */

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true);
            //simple http form based authentication, you can use oAuth if you have one, check Twitter4j documentation
            //cb.setUser(this.getLogin());
            //cb.setPassword(this.getPassword());
            cb.setUser("George24Mx");
            cb.setPassword("george24");
            // creating the twitter listener


            Configuration cfg = cb.build();
            trial = new TwitterStreamFactory(cfg).getInstance();

            trial.addListener(listener);


            trial.filter(query);

            //System.out.println(" here is stuff : " + trial.getFilterStream(query));


            //trial.sample();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e);
        }
    }

    @Override
    public void stopListenAlive() {
        if(trial!=null)
        {
            trial.cleanUp();
            trial.shutdown();
            System.out.println("DETUVO LA CONEXION EN:"+this.getId());
        }
    }

    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("Twitter.autenticate.............");
        String verifier = request.getParameter("oauth_verifier");
        if(verifier==null)
        {
System.out.println("Twitter----paso 1");
            twitter4j.Twitter twitterFI = new TwitterFactory().getInstance();
            twitterFI.setOAuthConsumer(getAppKey(), getSecretKey());
            try {
                requestToken = twitterFI.getOAuthRequestToken(getRedirectUrl(request, paramRequest));
                //response.sendRedirect(requestToken.getAuthorizationURL());
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println(" function ioauth() {");
                out.println("  mywin = window.open('"+requestToken.getAuthorizationURL()+"','_blank','width=840,height=680',true);");
                out.println("  mywin.focus();");
                out.println(" }");
                out.println(" if(confirm('autenticar la cuenta en twitter?')) {");
                out.println("  ioauth();");
                out.println(" }");
                out.println("</script>");
            }catch(TwitterException te) {
                throw new IOException(te.getMessage());
            }
        }
        else
        {
System.out.println("Twitter----2");
            twitter4j.Twitter twitterFI = new TwitterFactory().getInstance();
            twitterFI.setOAuthConsumer(getAppKey(), getSecretKey());
            try {
                AccessToken accessToken = twitterFI.getOAuthAccessToken(requestToken, verifier);
                setAccessToken(accessToken.getToken());
                setAccessTokenSecret(accessToken.getTokenSecret());
                setSn_authenticated(true);
                //SWBResourceURL posted = paramRequest.getRenderUrl().setMode("view").setParameter("objUri", getEncodedURI());
                //response.sendRedirect(request.getContextPath() + posted);
            }catch(TwitterException te) {
te.printStackTrace(System.out);
                throw new IOException(te.getMessage());
            }catch(Exception e) {
e.printStackTrace(System.out);
                throw new IOException(e.getMessage());
            }finally {
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("  window.close();");
                out.println("</script>");
            }
        }
    }
    
    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest)
    {
System.out.println("getRedirectUrl....");
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/"+paramRequest.getWebPage().getId()+"/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
System.out.println("URL callback="+address);
        return address.toString();
    }

    @Override
    public String doRequestPermissions() {
        return null;        
    }

    @Override
    public String doRequestAccess() {
        return null;
    }

}
