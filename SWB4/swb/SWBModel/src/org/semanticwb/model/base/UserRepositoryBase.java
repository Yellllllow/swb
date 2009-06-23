package org.semanticwb.model.base;


public class UserRepositoryBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepLoginContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepLoginContext");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepAuthMethod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepAuthMethod");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepSecurityQuestionList=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepSecurityQuestionList");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepCallBackHandlerClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepCallBackHandlerClassName");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public UserRepositoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositorys(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserRepository>(it, true);
    }

    public static org.semanticwb.model.UserRepository getUserRepository(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.model.UserRepository ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.model.UserRepository)obj.createGenericInstance();
           }
       }
       return ret;
    }

    public static org.semanticwb.model.UserRepository createUserRepository(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.model.UserRepository)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    public static void removeUserRepository(String id)
    {
       org.semanticwb.model.UserRepository obj=getUserRepository(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    public static boolean hasUserRepository(String id)
    {
        return (getUserRepository(id)!=null);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

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

    public String getLoginContext()
    {
        return getSemanticObject().getProperty(swb_userRepLoginContext);
    }

    public void setLoginContext(String userRepLoginContext)
    {
        getSemanticObject().setProperty(swb_userRepLoginContext, userRepLoginContext);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
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

    public String getAuthMethod()
    {
        return getSemanticObject().getProperty(swb_userRepAuthMethod);
    }

    public void setAuthMethod(String userRepAuthMethod)
    {
        getSemanticObject().setProperty(swb_userRepAuthMethod, userRepAuthMethod);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public String getUserRepSecurityQuestionList()
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList);
    }

    public void setUserRepSecurityQuestionList(String userRepSecurityQuestionList)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, userRepSecurityQuestionList);
    }

    public String getUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getProperty(swb_userRepSecurityQuestionList, null, lang);
    }

    public String getDisplayUserRepSecurityQuestionList(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_userRepSecurityQuestionList, lang);
    }

    public void setUserRepSecurityQuestionList(String userRepSecurityQuestionList, String lang)
    {
        getSemanticObject().setProperty(swb_userRepSecurityQuestionList, userRepSecurityQuestionList, lang);
    }

    public String getCallBackHandlerClassName()
    {
        return getSemanticObject().getProperty(swb_userRepCallBackHandlerClassName);
    }

    public void setCallBackHandlerClassName(String userRepCallBackHandlerClassName)
    {
        getSemanticObject().setProperty(swb_userRepCallBackHandlerClassName, userRepCallBackHandlerClassName);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
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

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    public void setUndeleteable(boolean undeleteable)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, undeleteable);
    }

    public org.semanticwb.model.UserGroup getUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.getUserGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return org.semanticwb.model.UserGroup.listUserGroups(this);
    }

    public org.semanticwb.model.UserGroup createUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.createUserGroup(id,this);
    }

    public org.semanticwb.model.UserGroup createUserGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb_UserGroup);
        return org.semanticwb.model.UserGroup.createUserGroup(String.valueOf(id),this);
    } 

    public void removeUserGroup(String id)
    {
        org.semanticwb.model.UserGroup.removeUserGroup(id, this);
    }
    public boolean hasUserGroup(String id)
    {
        return org.semanticwb.model.UserGroup.hasUserGroup(id, this);
    }

    public org.semanticwb.model.User getUser(String id)
    {
        return org.semanticwb.model.User.getUser(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.User> listUsers()
    {
        return org.semanticwb.model.User.listUsers(this);
    }

    public org.semanticwb.model.User createUser(String id)
    {
        return org.semanticwb.model.User.createUser(id,this);
    }

    public org.semanticwb.model.User createUser()
    {
        long id=getSemanticObject().getModel().getCounter(swb_User);
        return org.semanticwb.model.User.createUser(String.valueOf(id),this);
    } 

    public void removeUser(String id)
    {
        org.semanticwb.model.User.removeUser(id, this);
    }
    public boolean hasUser(String id)
    {
        return org.semanticwb.model.User.hasUser(id, this);
    }

    public org.semanticwb.model.UserFavorite getUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.getUserFavorite(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.UserFavorite> listUserFavorites()
    {
        return org.semanticwb.model.UserFavorite.listUserFavorites(this);
    }

    public org.semanticwb.model.UserFavorite createUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.createUserFavorite(id,this);
    }

    public org.semanticwb.model.UserFavorite createUserFavorite()
    {
        long id=getSemanticObject().getModel().getCounter(swb_UserFavorite);
        return org.semanticwb.model.UserFavorite.createUserFavorite(String.valueOf(id),this);
    } 

    public void removeUserFavorite(String id)
    {
        org.semanticwb.model.UserFavorite.removeUserFavorite(id, this);
    }
    public boolean hasUserFavorite(String id)
    {
        return org.semanticwb.model.UserFavorite.hasUserFavorite(id, this);
    }

    public org.semanticwb.model.Role getRole(String id)
    {
        return org.semanticwb.model.Role.getRole(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Role> listRoles()
    {
        return org.semanticwb.model.Role.listRoles(this);
    }

    public org.semanticwb.model.Role createRole(String id)
    {
        return org.semanticwb.model.Role.createRole(id,this);
    }

    public org.semanticwb.model.Role createRole()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Role);
        return org.semanticwb.model.Role.createRole(String.valueOf(id),this);
    } 

    public void removeRole(String id)
    {
        org.semanticwb.model.Role.removeRole(id, this);
    }
    public boolean hasRole(String id)
    {
        return org.semanticwb.model.Role.hasRole(id, this);
    }
}
