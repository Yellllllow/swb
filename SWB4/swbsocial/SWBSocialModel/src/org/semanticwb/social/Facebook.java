package org.semanticwb.social;


import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.io.SWBFile;
import org.semanticwb.io.SWBFileInputStream;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

public class Facebook extends org.semanticwb.social.base.FacebookBase {
    
    
    private static final String CRLF = "\r\n";
    
    private static final String PREF = "--";
    
    private static final String FACEBOOKGRAPH = "https://graph.facebook.com/";
    
    private Logger log = SWBUtils.getLogger(Facebook.class);
    
    
    public Facebook(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void listen(Stream stream) {
        WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        System.out.println("Red SocialID:"+this.getId()+", Red Title:"+this.getTitle()+", sitio:"+wsite.getId());
    }


    
    public void postMsg(Message message, HttpServletRequest request,
                        SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        params.put("message", message.getMsg_Text());
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/feed";
        JSONObject jsonResponse = null;
        String facebookResponse = "";
        
        try {
            facebookResponse = postRequest(params, url, request.getHeader(""), "POST");
            jsonResponse = new JSONObject(facebookResponse);
            if (jsonResponse != null && jsonResponse.get("id") != null) 
            {
                //message.setSocialNetPostId(jsonResponse.getString("id"));
                //addPost(message);
                addSentPost(message, jsonResponse.getString("id"), this);
                //addPost(message, "IDpuestoxFacebook", this);
                //this.msg = message;
            }
        }catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta, detail: "
                        + ioe.getMessage() + "\"}");
            } catch (JSONException jsone2) {}
            log.error("Problemas con el envio/recepcion de la peticion/respuesta con Facebook", ioe);
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar, detail: "
                        + jsone.getMessage() + "\"}");
            } catch (JSONException jsone2) {}
            log.error("La operacion no se pudo realizar, objeto JSON mal formado debido a la respuesta de Facebook: "
                    + facebookResponse, jsone);
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request,
                          SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
        if (photo.getDescription() != null) {
            params.put("message", photo.getDescription());
        }
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/photos";
        JSONObject jsonResponse = null;
        
        try {
            String photoPath = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_photo.getName() +
                     "_" + photo.getId() + "_" + photo.getPhoto();
            SWBFile photoFile = new SWBFile(photoPath);
            
            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
                String facebookResponse = postFileRequest(params, url,
                        photo.getPhoto(), fileStream, "POST", "photo");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //photo.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(photo);
                addSentPost(photo, jsonResponse.getString("id"), this);
                //this.photo = photo;
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {}
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {}
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {}
        }
    }

    public void postVideo(Video video, HttpServletRequest request, SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(3);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
        if (video.getTitle() != null) {
            params.put("title", video.getTitle());
        }
        if (video.getDescription() != null) {
            params.put("description", video.getDescription());
        }
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/videos";
        JSONObject jsonResponse = null;
        
        try {
            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile videoFile = new SWBFile(videoPath);
            
            if (videoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(videoFile);
                String facebookResponse = postFileRequest(params, url,
                        video.getVideo(), fileStream, "POST", "video");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //video.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(video);
                addSentPost(video, jsonResponse.getString("id"), this);
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {}
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {}
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {}
        }
    }
    
    /**
     * Realiza una petici&oacute;n al grafo de Facebook, de acuerdo a la url recibida
     * @param urlString especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la petici&oacute;n a realizar
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook
     * @throws java.net.MalformedURLException si la url especificada a trav&eacute;s
     *          de {@literal urlString} no est&aacute; correctamente formada
     * @throws java.io.IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    public static String graphRequest(String urlString, String userAgent)
            throws java.net.MalformedURLException, java.io.IOException {

        URL pagina = new URL(urlString);
        URLConnection conex = null;
        String answer = null;
        try {
            String host = pagina.getHost();
            //Se realiza la peticion a la página externa
            conex = pagina.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            if (host != null) {
                conex.setRequestProperty("host", host);
            }
            conex.setDoOutput(true);

            conex.setConnectTimeout(5000);
        } catch (Exception nexc) {
            conex = null;
        }

        //Analizar la respuesta a la peticion y obtener el access token
        if (conex != null) {
            answer = getResponse(conex.getInputStream());
        }
        return answer;
    }

    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por alg&uacute;n m&eacute;todo en particular
     * @param params contiene los par&aacute;metros a enviar a Facebook para realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la petici&oacute;n a realizar
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido por Facebook para realizar
     *          una operaci&oacute;n, como: {@literal POST} o {@literal DELETE}
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook
     * @throws IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    private String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        URL serverUrl = new URL(url);
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();
            out = conex.getOutputStream();
            out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } finally {
            close(in);
            close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por alg&uacute;n m&eacute;todo
     * en particular, en las que adem&aacute;, es necesaria la inclusi&oacute;n de un archivo
     * @param params contiene los par&aacute;metros a enviar a Facebook para realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param fileName indica el nombre del archivo a incluir en la petici&oacute; a Facebook
     * @param fileStream representa el contenido del archivo, para incluirlo en la petici&oacute; a Facebook
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido por Facebook para realizar
     *          una operaci&oacute;n, como: {@literal POST}
     * @param itemToPost indica si el elemento a publicar en Facebook es una foto o un video.
     *          Los valores aceptados por este parametro son: {@literal photo} o {@literal video}
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook, o la 
     *          representaci&oacute;n de un objeto JSON con el resultado de la ejecuci&oacute;n del metodo.
     * @throws IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    private String postFileRequest(Map<String, String> params, String url, String fileName,
            InputStream fileStream, String method, String itemToPost) {

        HttpURLConnection conex = null;
        OutputStream urlOut = null;
        InputStream in = null;
        URL serverUrl = null;
        String facebookResponse = "{\"response\" : \"Sin procesar\"}";

        if (method == null) {
            method = "POST";
        }

        try {
            serverUrl = new URL(url);
            String boundary = "---MyFacebookFormBoundary" + Long.toString(System.currentTimeMillis(), 16);
            conex = (HttpURLConnection) serverUrl.openConnection();
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(itemToPost.equals("photo") ? 60000 : 3600000);
            conex.setRequestMethod(method);
            conex.setDoInput(true);
            conex.setDoOutput(true);
            conex.setUseCaches(false);
            conex.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conex.setRequestProperty("MIME-version", "1.0");

            conex.connect();
            urlOut = conex.getOutputStream();
            DataOutputStream out = new DataOutputStream(urlOut);
            
            for (Map.Entry<String, String> entry : params.entrySet()) {
                out.writeBytes(PREF + boundary + CRLF);
                out.writeBytes("Content-Type: text/plain;charset=UTF-8" + CRLF);
                out.writeBytes("Content-disposition: form-data; name=\"" + entry.getKey() + "\"" + CRLF);
                out.writeBytes(CRLF);
                byte[] valueBytes = entry.getValue().toString().getBytes("UTF-8");
                out.write(valueBytes);
                out.writeBytes(CRLF);
            }

            out.writeBytes(PREF + boundary + CRLF);
            out.writeBytes("Content-Type: " + (itemToPost.equals("photo") ? "image" : "file") + CRLF);
            out.writeBytes("Content-disposition: form-data; filename=\"" + fileName + "\"" + CRLF);

            // Write the file
            out.writeBytes(CRLF);
            byte buf[] = new byte[1024];
            int len = 0;
            while (len >= 0) {
                out.write(buf, 0, len);
                len = fileStream.read(buf);
            }

            out.writeBytes(CRLF + PREF + boundary + PREF + CRLF);
            out.flush();
            in = conex.getInputStream();
            facebookResponse = getResponse(in);
        } catch (IOException ioe) {
            facebookResponse = "{\"errorMsg\":\"Ocurrio un problema en la peticion a Facebook "
                    + ioe.getMessage() + "\"}";
        } finally {
            close(urlOut);
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        return facebookResponse;
    }

    /**
     * En base al contenido de la colecci&oacute;n recibida, arma una secuencia de caracteres compuesta de los pares:
     * <p>{@code Entry.getKey()} {@code equals} {@code Entry.getKey()} </p>
     * Si en la colecci&oacute; hay m&aacute;s de una entrada, los pares (como el anterior), se separan por {@code delimiter}.
     * @param entries la colecci&oacute;n con la que se van a formar los pares
     * @param delimiter representa el valor con que se van a separar los pares a representar
     * @param equals representa el valor con el que se van a relacionar los elementos de cada par a representar
     * @param doEncode indica si el valor representado en cada par, debe ser codificado (UTF-8) o no
     * @return la secuencia de caracteres que representa el conjunto de pares
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en la codificaci&oacute;n a UTF-8 del valor
     *      de alg&uacute;n par, si as&iacute; se indica en {@code doEncode}
     */
    private CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer
                = new StringBuilder(64);
	boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries ) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

    /**
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en la codificaci&oacute;n a UTF-8
     */
    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    /**
     * Lee un flujo de datos y lo convierte en un {@code String} con su contenido codificado en UTF-8
     * @param data el flujo de datos a convertir
     * @return un {@code String} que representa el contenido del flujo de datos especificado, codificado en UTF-8
     * @throws IOException si ocurre un problema en la lectura del flujo de datos
     */
    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        return response.toString();
    }

    /**
     * Cierra el objeto recibido
     * @param c cualquier objeto que tenga la factultad de cerrarse
     */
    private void close( Closeable c ) {
        if ( c != null ) {
            try {
                c.close();
            }
            catch ( IOException ex ) {
                //TODO: logeo de problemas al cerrar objeto
            }
        }
    }
    
    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
System.out.println("********************   Facebook.authenticate");
System.out.println("objUri="+request.getAttribute("objUri"));
        String code = request.getParameter("code");
System.out.println("code="+code);
        String state = request.getParameter("state");
System.out.println("state="+state);
        String error = request.getParameter("error");
System.out.println("error="+error);

        HttpSession session = request.getSession(true);
        
        if(code==null)
        {
System.out.println("paso 1");
            String url = doRequestPermissions(request);
System.out.println("url="+url);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open("+url+",'_blank','width=840,height=680',true);");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('autenticar en facebook?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        }
        else if( state!=null && state.equals(session.getAttribute("state")) && error==null  )
        {
System.out.println("paso 2");
            String accessToken = null;
            long secsToExpiration = 0L;
            //Si el usuario otorgo los permisos necesarios, se obtiene el token de acceso
            String token_url = "https://graph.facebook.com/oauth/access_token?" + "client_id=116470745174271&redirect_uri=" + URLEncoder.encode("http://localhost:8088/es/swbsocial5/SocialNetworks/_rid/24/_mod/oauth/_lang/es", "utf-8") + "&client_secret=f392625af4ae987e409a257d77f43c94&code=" + code;
            if (session.getAttribute("accessToken") != null) {
                accessToken = (String) session.getAttribute("accessToken");
                secsToExpiration = ((Long) session.getAttribute("secsToExpiration")).longValue();
            }
System.out.println("accessToken="+accessToken);
            if (accessToken == null) {
                URL pagina = new URL(token_url);
                URLConnection conex = null;
                try {
                    String host = pagina.getHost();
                    String header = request.getHeader("user-agent");
                    //Se realiza la peticion a la página externa
                    conex = pagina.openConnection();
                    if (header != null) {
                        conex.setRequestProperty("user-agent", header);
                    }
                    if (host != null) {
                        conex.setRequestProperty("host", host);
                    }
                    conex.setConnectTimeout(5000);
                } catch (Exception nexc) {
                    conex = null;
System.out.println("error........"+nexc);
                }

                //Analizar la respuesta a la peticion y obtener el access token
System.out.println("conex="+conex);
                if (conex != null) {
                    String answer = SWBUtils.IO.readInputStream(conex.getInputStream());
System.out.println("answer="+answer);
                    String aux = null;
                    //System.out.println("Respuesta de Facebook para el accessToken: \n" + answer);

                    if (answer.indexOf("&") > 0) {
                        aux = answer.split("&")[0];
                        if (aux.indexOf("=") > 0) {
                            accessToken = aux.split("=")[1];
                        }
                        session.setAttribute("accessToken", accessToken);                        
                        aux = answer.split("&")[1];
                        if (aux.indexOf("=") > 0) {
                            secsToExpiration = Long.parseLong(aux.split("=")[1]);
                        }
                        session.setAttribute("secsToExpiration", Long.valueOf(secsToExpiration));
                    }
                }
            }
            if (accessToken != null) {
                //Hacer consulta a los datos del usuario a través del grafo
                String graph_url = "https://graph.facebook.com/me?access_token="
                    + accessToken;
                String me = Facebook.graphRequest(graph_url, request.getHeader("user-agent"));
                setActive(true);
                try {
                    JSONObject userData = new JSONObject(me);
                    String userId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("  window.close();");
                    out.println("</script>");
                }catch(JSONException e) {
                    response.getWriter().println("<p>ya valio queso.......</p><br/>"+e);
                }
            }
        }
        else
        {
            System.out.println("Se ha encontrado un problema con la respuesta obtenida, se considera no aut&eacute;ntica.");
        }
        
        
        /*else if(state!=null && error!=null)
        {
System.out.println("\n------NO HUBO CHANCE--------------\n");
        }
        else
        {
System.out.println("segundo paso de autenticacion");
            StringBuilder url = new StringBuilder();
            url.append(FACEBOOKGRAPH);
            url.append("oauth/access_token");
            url.append("?client_id=");
            url.append(getAppKey());
            url.append("&redirect_uri=");
            url.append("http://localhost:8088/es/swbsocial5/SocialNetworks/_rid/24/_mod/oauth/_lang/es");
            url.append("&client_secret");
            url.append(getSecretKey());
            url.append("&code=");
            url.append(code);
            //url.append("\\");
            
            String accessToken = null;
            long secsToExpiration = 0L;

            HttpSession session = request.getSession(true);
            if (session.getAttribute("accessToken") != null) {
                accessToken = (String) session.getAttribute("accessToken");
                secsToExpiration = ((Long) session.getAttribute("secsToExpiration")).longValue();
            }

            if (accessToken == null) {
System.out.println("3");
                URL pagina = new URL(url.toString());
                URLConnection conex = null;
                try {
                    String host = pagina.getHost();
                    String header = request.getHeader("user-agent");
                    //Se realiza la peticion a la página externa
                    conex = pagina.openConnection();
                    if (header != null) {
                        conex.setRequestProperty("user-agent", header);
                    }
                    if (host != null) {
                        conex.setRequestProperty("host", host);
                    }
                    conex.setConnectTimeout(5000);
                } catch (Exception nexc) {
                    conex = null;
                }

                //Analizar la respuesta a la peticion y obtener el access token
                if (conex != null) {
                    String answer = SWBUtils.IO.readInputStream(conex.getInputStream());
                    String aux = null;
                    //System.out.println("Respuesta de Facebook para el accessToken: \n" + answer);

                    if (answer.indexOf("&") > 0) {
                        aux = answer.split("&")[0];
                        if (aux.indexOf("=") > 0) {
                            accessToken = aux.split("=")[1];
                        }
                        session.setAttribute("accessToken", accessToken);
                        aux = answer.split("&")[1];
                        if (aux.indexOf("=") > 0) {
                            secsToExpiration = Long.parseLong(aux.split("=")[1]);
                        }
                        session.setAttribute("secsToExpiration", Long.valueOf(secsToExpiration));
                    }
                }
            }
            if (accessToken != null) {
System.out.println("4");
                //Hacer consulta a los datos del usuario a través del grafo
                String graph_url = "https://graph.facebook.com/me?access_token="
                    + accessToken;
                String me = Facebook.graphRequest(graph_url, request.getHeader("user-agent"));
                try {
                    JSONObject userData = new JSONObject(me);
                    String userId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                    System.out.println("\n\nPara publicar info en Facebook haz clic <a href=\""
                            + paramRequest.getActionUrl().setAction("saveToken").setParameter("token", accessToken).setParameter("userId", userId)
                            + "\">aqu&iacute;</a>");
                }catch(org.json.JSONException e) {
                    e.printStackTrace(System.out);
                }
            }
        }*/
    }
    
    public String doRequestPermissions(HttpServletRequest request)
    {
        StringBuilder url = new StringBuilder();
        final String state = getState((String)request.getAttribute("objUri"));
        
        url.append("'https://www.facebook.com/dialog/oauth?'+");
        url.append("'client_id='+");
        url.append("'"+getAppKey()+"'+");
        url.append("'&redirect_uri='+");
        url.append("encodeURI('http://localhost:8088/es/swbsocial5/SocialNetworks/_rid/24/_mod/oauth/_lang/es')+");
        //url.append("encodeURI('http://localhost:8088/mifacebook')+");
        url.append("'&scope='+");
        url.append("encodeURIComponent('publish_stream,read_stream')+");
        url.append("'&state='+");        
        url.append("'"+state+"'");
        request.getSession(true).setAttribute("state", state);
        return url.toString();

        //heroku pass admin12345678
    }
    
    @Override
    public String doRequestPermissions() {
        return null;
    }
    
    @Override
    public String doRequestAccess() {
        return null;
    }
    
    public String getState(String value) {
        return "abc123";
    }
}
