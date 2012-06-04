package org.semanticwb.social.base;


   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales. 
   */
public abstract class SocialNetworkBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.social.Listenerable
{
   /**
   * En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostListenerContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido recibidos desde una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostListenerContainer");
   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * Propiedad inversa la cual regresa las instancias de SocialNetworkUser, es decir, los usuarios creadores de Post que entran por el Listener (PostIn) y que pertenecen a una determinada red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetworkUsersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetworkUsersInv");
   /**
   * Password o contraseña con la cual una organización se puede conectar a una determinada instancia de Red Social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_password=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#password");
   /**
   * Clase que contiene todos los post que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido enviados a una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer");
   /**
   * Login o usuario con la cual una organización se puede conectar a una determinada instancia de Red Social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_login=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#login");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Esta propiedad inversa me puede decir cuales son los mensajes que han llegado por el Listener por una determinada red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInSocialNetworkInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInSocialNetworkInv");
   /**
   * Tiempo en que se ira a buscar información a una determinada red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_PoolTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PoolTime");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetwork for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetwork for all models
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork getSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork createSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       */
        public static void removeSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
       */

        public static boolean hasSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetwork(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostListenerContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetworkUsersInv
       * @param value SocialNetworkUsersInv of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetworkUsersInv(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkUsersInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetworkUsersInv
       * @param value SocialNetworkUsersInv of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetworkUsersInv(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkUsersInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a SocialNetworkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetwork
   */
    public SocialNetworkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostListenerContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostListenerContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer>(getSemanticObject().listObjectProperties(social_hasPostListenerContainer));
    }

   /**
   * Gets true if has a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to verify
   * @return true if the org.semanticwb.social.PostListenerContainer exists, false otherwise
   */
    public boolean hasPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to add
   */

    public void addPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostListenerContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostListenerContainer
   */

    public void removeAllPostListenerContainer()
    {
        getSemanticObject().removeProperty(social_hasPostListenerContainer);
    }
   /**
   * Removes a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to remove
   */

    public void removePostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostListenerContainer
   * @return a org.semanticwb.social.PostListenerContainer
   */
    public org.semanticwb.social.PostListenerContainer getPostListenerContainer()
    {
         org.semanticwb.social.PostListenerContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostListenerContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostListenerContainer)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetworkUser
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetworkUser
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUsersInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(getSemanticObject().listObjectProperties(social_hasSocialNetworkUsersInv));
    }

   /**
   * Gets true if has a SocialNetworkUsersInv
   * @param value org.semanticwb.social.SocialNetworkUser to verify
   * @return true if the org.semanticwb.social.SocialNetworkUser exists, false otherwise
   */
    public boolean hasSocialNetworkUsersInv(org.semanticwb.social.SocialNetworkUser value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetworkUsersInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetworkUsersInv
   * @return a org.semanticwb.social.SocialNetworkUser
   */
    public org.semanticwb.social.SocialNetworkUser getSocialNetworkUsersInv()
    {
         org.semanticwb.social.SocialNetworkUser ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetworkUsersInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetworkUser)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Password property
* @return String with the Password
*/
    public String getPassword()
    {
        return getSemanticObject().getProperty(social_password);
    }

/**
* Sets the Password property
* @param value long with the Password
*/
    public void setPassword(String value)
    {
        getSemanticObject().setProperty(social_password, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Gets all the org.semanticwb.social.PostContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> listPostContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer));
    }

   /**
   * Gets true if has a PostContainer
   * @param value org.semanticwb.social.PostContainer to verify
   * @return true if the org.semanticwb.social.PostContainer exists, false otherwise
   */
    public boolean hasPostContainer(org.semanticwb.social.PostContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostContainer
   * @param value org.semanticwb.social.PostContainer to add
   */

    public void addPostContainer(org.semanticwb.social.PostContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostContainer
   */

    public void removeAllPostContainer()
    {
        getSemanticObject().removeProperty(social_hasPostContainer);
    }
   /**
   * Removes a PostContainer
   * @param value org.semanticwb.social.PostContainer to remove
   */

    public void removePostContainer(org.semanticwb.social.PostContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostContainer
   * @return a org.semanticwb.social.PostContainer
   */
    public org.semanticwb.social.PostContainer getPostContainer()
    {
         org.semanticwb.social.PostContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostContainer)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Login property
* @return String with the Login
*/
    public String getLogin()
    {
        return getSemanticObject().getProperty(social_login);
    }

/**
* Sets the Login property
* @param value long with the Login
*/
    public void setLogin(String value)
    {
        getSemanticObject().setProperty(social_login, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInSocialNetworkInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInSocialNetworkInv));
    }

   /**
   * Gets true if has a PostInSocialNetworkInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInSocialNetworkInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInSocialNetworkInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInSocialNetworkInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInSocialNetworkInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PoolTime property
* @return int with the PoolTime
*/
    public int getPoolTime()
    {
        return getSemanticObject().getIntProperty(social_PoolTime);
    }

/**
* Sets the PoolTime property
* @param value long with the PoolTime
*/
    public void setPoolTime(int value)
    {
        getSemanticObject().setIntProperty(social_PoolTime, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
}
