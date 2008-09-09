package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebPageBase extends GenericObjectBase implements Descriptiveable,PortletRefable,TemplateRefable,Indexable,Calendarable,Viewable,Activeable,RuleRefable,RoleRefable,Hiddenable,Deleteable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebPageBase(SemanticObject base)
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
             ret=(User)vocabulary.User.newGenericInstance(obj);
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

    public GenericIterator<org.semanticwb.model.Calendar> listCalendars()
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
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasCalendar);
         if(obj!=null)
         {
             ret=(Calendar)vocabulary.Calendar.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.hidden);
    }

    public void setHidden(boolean hidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.hidden, hidden);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.indexable, indexable);
    }

    public String getWebPageSortName()
    {
        return getSemanticObject().getProperty(vocabulary.webPageSortName);
    }

    public void setWebPageSortName(String webPageSortName)
    {
        getSemanticObject().setProperty(vocabulary.webPageSortName, webPageSortName);
    }

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
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
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRoleRef);
         if(obj!=null)
         {
             ret=(RoleRef)vocabulary.RoleRef.newGenericInstance(obj);
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

    public GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasWebPageVirtualParent.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }

    public void addVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasWebPageVirtualParent, webpage.getSemanticObject());
    }

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(vocabulary.hasWebPageVirtualParent);
    }

    public void removeVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasWebPageVirtualParent,webpage.getSemanticObject());
    }

    public WebPage getVirtualParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasWebPageVirtualChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getWebPageVirtualChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.views, views);
    }

    public GenericIterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, stit);
    }

    public void addPortletRef(org.semanticwb.model.PortletRef portletref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasPortletRef, portletref.getSemanticObject());
    }

    public void removeAllPortletRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasPortletRef);
    }

    public void removePortletRef(org.semanticwb.model.PortletRef portletref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasPortletRef,portletref.getSemanticObject());
    }

    public PortletRef getPortletRef()
    {
         PortletRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPortletRef);
         if(obj!=null)
         {
             ret=(PortletRef)vocabulary.PortletRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, stit);
    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasTemplateRef, templateref.getSemanticObject());
    }

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasTemplateRef,templateref.getSemanticObject());
    }

    public TemplateRef getTemplateRef()
    {
         TemplateRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasTemplateRef);
         if(obj!=null)
         {
             ret=(TemplateRef)vocabulary.TemplateRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPFlowRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, stit);
    }

    public void addPFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasPFlowRef, pflowref.getSemanticObject());
    }

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasPFlowRef);
    }

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasPFlowRef,pflowref.getSemanticObject());
    }

    public PFlowRef getPFlowRef()
    {
         PFlowRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPFlowRef);
         if(obj!=null)
         {
             ret=(PFlowRef)vocabulary.PFlowRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
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
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRuleRef);
         if(obj!=null)
         {
             ret=(RuleRef)vocabulary.RuleRef.newGenericInstance(obj);
         }
         return ret;
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
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasWebPageChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public void setParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(vocabulary.webPageParent, webpage.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(vocabulary.webPageParent);
    }

    public WebPage getParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.webPageParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.WebPage.newGenericInstance(obj);
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

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
