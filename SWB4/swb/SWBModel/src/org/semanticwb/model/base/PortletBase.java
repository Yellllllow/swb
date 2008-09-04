package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletBase extends GenericObjectBase implements Versionable,Indexable,Descriptiveable,Viewable,Calendarable,Activeable,RuleRefable,Traceable,RoleRefable,Priorityable,Deleteable,Localeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletBase(SemanticObject base)
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

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.modifiedBy.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().addObjectProperty(vocabulary.actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         VersionInfo ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.actualVersion.getRDFProperty());
         GenericIterator<org.semanticwb.model.VersionInfo> it=new GenericIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public int getHits()
    {
        return getSemanticObject().getIntProperty(vocabulary.hits);
    }

    public void setHits(int hits)
    {
        getSemanticObject().setLongProperty(vocabulary.hits, hits);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().addObjectProperty(vocabulary.lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         VersionInfo ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.lastVersion.getRDFProperty());
         GenericIterator<org.semanticwb.model.VersionInfo> it=new GenericIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.Calendar> listCalendar()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasCalendar, calendar.getSemanticObject());
    }

    public void removeAllCalendar()
    {
        getSemanticObject().removeProperty(vocabulary.hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasCalendar,calendar.getSemanticObject());
    }

    public Calendar getCalendar()
    {
         Calendar ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
         GenericIterator<org.semanticwb.model.Calendar> it=new GenericIterator<org.semanticwb.model.Calendar>(Calendar.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public boolean isPortletWindow()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.portletWindow);
    }

    public void setPortletWindow(boolean portletWindow)
    {
        getSemanticObject().setBooleanProperty(vocabulary.portletWindow, portletWindow);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.indexable, indexable);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit);
    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRoleRef, roleref.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRoleRef,roleref.getSemanticObject());
    }

    public RoleRef getRoleRef()
    {
         RoleRef ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.RoleRef> it=new GenericIterator<org.semanticwb.model.RoleRef>(RoleRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
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

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.views, views);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().addObjectProperty(vocabulary.language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         Language ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.language.getRDFProperty());
         GenericIterator<org.semanticwb.model.Language> it=new GenericIterator<org.semanticwb.model.Language>(Language.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.creator.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit);
    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRuleRef, ruleref.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRuleRef,ruleref.getSemanticObject());
    }

    public RuleRef getRuleRef()
    {
         RuleRef ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.RuleRef> it=new GenericIterator<org.semanticwb.model.RuleRef>(RuleRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public void setPortletSubType(org.semanticwb.model.PortletSubType portletsubtype)
    {
        getSemanticObject().addObjectProperty(vocabulary.portletSubType, portletsubtype.getSemanticObject());
    }

    public void removePortletSubType()
    {
        getSemanticObject().removeProperty(vocabulary.portletSubType);
    }

    public PortletSubType getPortletSubType()
    {
         PortletSubType ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.portletSubType.getRDFProperty());
         GenericIterator<org.semanticwb.model.PortletSubType> it=new GenericIterator<org.semanticwb.model.PortletSubType>(PortletSubType.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public void setPortletType(org.semanticwb.model.PortletType portlettype)
    {
        getSemanticObject().addObjectProperty(vocabulary.portletType, portlettype.getSemanticObject());
    }

    public void removePortletType()
    {
        getSemanticObject().removeProperty(vocabulary.portletType);
    }

    public PortletType getPortletType()
    {
         PortletType ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.portletType.getRDFProperty());
         GenericIterator<org.semanticwb.model.PortletType> it=new GenericIterator<org.semanticwb.model.PortletType>(PortletType.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
