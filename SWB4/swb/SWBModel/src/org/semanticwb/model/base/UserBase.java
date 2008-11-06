package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class UserBase extends GenericObjectBase implements Groupable,Activeable,Traceable,Roleable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public String getUsrSecondLastName()
    {
        return getSemanticObject().getProperty(vocabulary.usrSecondLastName);
    }

    public void setUsrSecondLastName(String usrSecondLastName)
    {
        getSemanticObject().setProperty(vocabulary.usrSecondLastName, usrSecondLastName);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrEmail()
    {
        return getSemanticObject().getProperty(vocabulary.usrEmail);
    }

    public void setUsrEmail(String usrEmail)
    {
        getSemanticObject().setProperty(vocabulary.usrEmail, usrEmail);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public String getUsrFirstName()
    {
        return getSemanticObject().getProperty(vocabulary.usrFirstName);
    }

    public void setUsrFirstName(String usrFirstName)
    {
        getSemanticObject().setProperty(vocabulary.usrFirstName, usrFirstName);
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(vocabulary.usrLanguage);
    }

    public void setLanguage(String usrLanguage)
    {
        getSemanticObject().setProperty(vocabulary.usrLanguage, usrLanguage);
    }

    public Date getUsrPasswordChanged()
    {
        return getSemanticObject().getDateProperty(vocabulary.usrPasswordChanged);
    }

    public void setUsrPasswordChanged(Date usrPasswordChanged)
    {
        getSemanticObject().setDateProperty(vocabulary.usrPasswordChanged, usrPasswordChanged);
    }

    public String getUsrLastName()
    {
        return getSemanticObject().getProperty(vocabulary.usrLastName);
    }

    public void setUsrLastName(String usrLastName)
    {
        getSemanticObject().setProperty(vocabulary.usrLastName, usrLastName);
    }

    public GenericIterator<org.semanticwb.model.ObjectGroup> listGroups()
    {
        return new GenericIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, getSemanticObject().listObjectProperties(vocabulary.hasGroup));    }

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasGroup, objectgroup.getSemanticObject());
    }

    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(vocabulary.hasGroup);
    }

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasGroup,objectgroup.getSemanticObject());
    }

    public ObjectGroup getGroup()
    {
         ObjectGroup ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasGroup);
         if(obj!=null)
         {
             ret=(ObjectGroup)vocabulary.swb_ObjectGroup.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public Date getUsrLastLogin()
    {
        return getSemanticObject().getDateProperty(vocabulary.usrLastLogin);
    }

    public void setUsrLastLogin(Date usrLastLogin)
    {
        getSemanticObject().setDateProperty(vocabulary.usrLastLogin, usrLastLogin);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getUsrPassword()
    {
        return getSemanticObject().getProperty(vocabulary.usrPassword);
    }

    public void setUsrPassword(String usrPassword)
    {
        getSemanticObject().setProperty(vocabulary.usrPassword, usrPassword);
    }

    public String getUsrLogin()
    {
        return getSemanticObject().getProperty(vocabulary.usrLogin);
    }

    public void setUsrLogin(String usrLogin)
    {
        getSemanticObject().setProperty(vocabulary.usrLogin, usrLogin);
    }

    public GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, getSemanticObject().listObjectProperties(vocabulary.hasRole));    }

    public void addRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRole, role.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(vocabulary.hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRole,role.getSemanticObject());
    }

    public Role getRole()
    {
         Role ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRole);
         if(obj!=null)
         {
             ret=(Role)vocabulary.swb_Role.newGenericInstance(obj);
         }
         return ret;
    }

    public int getUsrSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(vocabulary.usrSecurityQuestion);
    }

    public void setUsrSecurityQuestion(int usrSecurityQuestion)
    {
        getSemanticObject().setLongProperty(vocabulary.usrSecurityQuestion, usrSecurityQuestion);
    }

    public String getUsrSecurityAnswer()
    {
        return getSemanticObject().getProperty(vocabulary.usrSecurityAnswer);
    }

    public void setUsrSecurityAnswer(String usrSecurityAnswer)
    {
        getSemanticObject().setProperty(vocabulary.usrSecurityAnswer, usrSecurityAnswer);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public UserRepository getUserRepository()
    {
        return new UserRepository(getSemanticObject().getModel().getModelObject());
    }
}
