package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class WebPageBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebPageBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.RoleRef> listRoleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit);
    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        addObjectProperty(vocabulary.hasRoleRef, roleref);
    }

    public void removeAllRoleRef()
    {
        getRDFResource().removeAll(vocabulary.hasRoleRef.getRDFProperty());
    }

    public RoleRef getRoleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RoleRef> it=new SemanticIterator<org.semanticwb.model.RoleRef>(RoleRef.class, stit);
         return it.next();
    }

    public boolean isDeleted()
    {
        return getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(vocabulary.deleted, deleted);
    }

    public Date getCreated()
    {
        return getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        setDateProperty(vocabulary.created, created);
    }

    public String getTitle()
    {
        return getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        setProperty(vocabulary.title, title);
    }

    public void addUserCreated(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userCreated, user);
    }

    public void removeUserCreated()
    {
        getRDFResource().removeAll(vocabulary.userCreated.getRDFProperty());
    }

    public User getUserCreated()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userCreated.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.PortletRef> listPortletRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, stit);
    }

    public void addPortletRef(org.semanticwb.model.PortletRef portletref)
    {
        addObjectProperty(vocabulary.hasPortletRef, portletref);
    }

    public void removeAllPortletRef()
    {
        getRDFResource().removeAll(vocabulary.hasPortletRef.getRDFProperty());
    }

    public PortletRef getPortletRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.PortletRef> it=new SemanticIterator<org.semanticwb.model.PortletRef>(PortletRef.class, stit);
         return it.next();
    }

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }

    public Date getUpdated()
    {
        return getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        setDateProperty(vocabulary.updated, updated);
    }

    public void addUserModified(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userModified, user);
    }

    public void removeUserModified()
    {
        getRDFResource().removeAll(vocabulary.userModified.getRDFProperty());
    }

    public User getUserModified()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.TemplateRef> listTemplateRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, stit);
    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        addObjectProperty(vocabulary.hasTemplateRef, templateref);
    }

    public void removeAllTemplateRef()
    {
        getRDFResource().removeAll(vocabulary.hasTemplateRef.getRDFProperty());
    }

    public TemplateRef getTemplateRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.TemplateRef> it=new SemanticIterator<org.semanticwb.model.TemplateRef>(TemplateRef.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.RuleRef> listRuleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit);
    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        addObjectProperty(vocabulary.hasRuleRef, ruleref);
    }

    public void removeAllRuleRef()
    {
        getRDFResource().removeAll(vocabulary.hasRuleRef.getRDFProperty());
    }

    public RuleRef getRuleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RuleRef> it=new SemanticIterator<org.semanticwb.model.RuleRef>(RuleRef.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.Calendar> listCalendar()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        addObjectProperty(vocabulary.hasCalendar, calendar);
    }

    public void removeAllCalendar()
    {
        getRDFResource().removeAll(vocabulary.hasCalendar.getRDFProperty());
    }

    public Calendar getCalendar()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Calendar> it=new SemanticIterator<org.semanticwb.model.Calendar>(Calendar.class, stit);
         return it.next();
    }

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public void addIsChildOf(org.semanticwb.model.WebPage webpage)
    {
        addObjectProperty(vocabulary.isChildOf, webpage);
    }

    public void removeIsChildOf()
    {
        getRDFResource().removeAll(vocabulary.isChildOf.getRDFProperty());
    }

    public WebPage getIsChildOf()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.isChildOf.getRDFProperty());
         SemanticIterator<org.semanticwb.model.WebPage> it=new SemanticIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }
}
