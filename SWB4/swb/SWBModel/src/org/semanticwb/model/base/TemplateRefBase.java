package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class TemplateRefBase extends Reference implements Inheritable,Priorityable,Activeable,Deleteable
{

    public TemplateRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_deleted, deleted);
    }

    public void setTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_template, template.getSemanticObject());
    }

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(vocabulary.swb_template);
    }

    public Template getTemplate()
    {
         Template ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_template);
         if(obj!=null)
         {
             ret=(Template)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public int getInherita()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_inherita);
    }

    public void setInherita(int inherita)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_inherita, inherita);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_priority, priority);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
