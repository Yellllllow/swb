package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta. 
   */
public abstract class PostInBase extends org.semanticwb.social.Post implements org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Descriptiveable
{
   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * Usuario creador del Post que llega por el Listener (PostIn)
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSocialNetworkUser");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Red Social de la cual es procedente el mensaje que llega por el Listener.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSocialNetwork");
   /**
   * Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainerBase");
   /**
   * En esta propiedad se guarda el post que llegó por el listener y que sirvió de base para que se creara un post nuevo desde SSMCC.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postListenerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postListenerBase");
   /**
   * Propiedad con valor entero que representa el tipo de Sentimientos que expresa el Post, estos se estan definiendo de esta manera: 0) Neutro 1) Positivo 2)Negativo, estos valores pueden ser mas y permanecer en un objeto tipo colección en lo futuro.
   */
    public static final org.semanticwb.platform.SemanticProperty social_PostSentimentalType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PostSentimentalType");
   /**
   * Valor que es resultado del algoritmo de intensidad, mediante este valor se puede determinar si la intencidad es alta, media o baja
   */
    public static final org.semanticwb.platform.SemanticProperty social_PostIntensityValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PostIntensityValue");
   /**
   * Valor que resulta del algoritmo de analisis sentimental, aqui se puede ver el porque se pone cierto valir a la propiedad PostSentimentalType
   */
    public static final org.semanticwb.platform.SemanticProperty social_PostSentimentalValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PostSentimentalValue");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostIn for all models
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn getPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn createPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       */
        public static void removePostIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return true if the org.semanticwb.social.PostIn exists, false otherwise
       */

        public static boolean hasPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PostInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostIn
   */
    public PostInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property PostInSocialNetworkUser
   * @param value PostInSocialNetworkUser to set
   */

    public void setPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInSocialNetworkUser, value.getSemanticObject());
        }else
        {
            removePostInSocialNetworkUser();
        }
    }
   /**
   * Remove the value for PostInSocialNetworkUser property
   */

    public void removePostInSocialNetworkUser()
    {
        getSemanticObject().removeProperty(social_postInSocialNetworkUser);
    }

   /**
   * Gets the PostInSocialNetworkUser
   * @return a org.semanticwb.social.SocialNetworkUser
   */
    public org.semanticwb.social.SocialNetworkUser getPostInSocialNetworkUser()
    {
         org.semanticwb.social.SocialNetworkUser ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInSocialNetworkUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetworkUser)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property PostInSocialNetwork
   * @param value PostInSocialNetwork to set
   */

    public void setPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInSocialNetwork, value.getSemanticObject());
        }else
        {
            removePostInSocialNetwork();
        }
    }
   /**
   * Remove the value for PostInSocialNetwork property
   */

    public void removePostInSocialNetwork()
    {
        getSemanticObject().removeProperty(social_postInSocialNetwork);
    }

   /**
   * Gets the PostInSocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPostInSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInSocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property PostListenerBase
   * @param value PostListenerBase to set
   */

    public void setPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postListenerBase, value.getSemanticObject());
        }else
        {
            removePostListenerBase();
        }
    }
   /**
   * Remove the value for PostListenerBase property
   */

    public void removePostListenerBase()
    {
        getSemanticObject().removeProperty(social_postListenerBase);
    }

   /**
   * Gets the PostListenerBase
   * @return a org.semanticwb.social.PostListenerContainerBase
   */
    public org.semanticwb.social.PostListenerContainerBase getPostListenerBase()
    {
         org.semanticwb.social.PostListenerContainerBase ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postListenerBase);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostListenerContainerBase)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PostSentimentalType property
* @return int with the PostSentimentalType
*/
    public int getPostSentimentalType()
    {
        return getSemanticObject().getIntProperty(social_PostSentimentalType);
    }

/**
* Sets the PostSentimentalType property
* @param value long with the PostSentimentalType
*/
    public void setPostSentimentalType(int value)
    {
        getSemanticObject().setIntProperty(social_PostSentimentalType, value);
    }

/**
* Gets the PostIntensityValue property
* @return float with the PostIntensityValue
*/
    public float getPostIntensityValue()
    {
        return getSemanticObject().getFloatProperty(social_PostIntensityValue);
    }

/**
* Sets the PostIntensityValue property
* @param value long with the PostIntensityValue
*/
    public void setPostIntensityValue(float value)
    {
        getSemanticObject().setFloatProperty(social_PostIntensityValue, value);
    }

/**
* Gets the PostSentimentalValue property
* @return float with the PostSentimentalValue
*/
    public float getPostSentimentalValue()
    {
        return getSemanticObject().getFloatProperty(social_PostSentimentalValue);
    }

/**
* Sets the PostSentimentalValue property
* @param value long with the PostSentimentalValue
*/
    public void setPostSentimentalValue(float value)
    {
        getSemanticObject().setFloatProperty(social_PostSentimentalValue, value);
    }
}
