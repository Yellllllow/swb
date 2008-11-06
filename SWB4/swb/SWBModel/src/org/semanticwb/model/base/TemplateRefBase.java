package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class TemplateRefBase extends ReferenceBase implements Templateable,Priorityable,Activeable,Deleteable
{

    public TemplateRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public void setTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().setObjectProperty(vocabulary.template, template.getSemanticObject());
    }

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(vocabulary.template);
    }

    public Template getTemplate()
    {
         Template ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.template);
         if(obj!=null)
         {
             ret=(Template)vocabulary.swb_Template.newGenericInstance(obj);
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }
}
