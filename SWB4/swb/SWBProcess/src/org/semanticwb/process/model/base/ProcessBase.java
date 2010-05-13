package org.semanticwb.process.model.base;


public abstract class ProcessBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_processWebPageInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processWebPageInv");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessInstanceInv");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }

        public static org.semanticwb.process.model.Process createProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Process.ClassMgr.createProcess(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Process getProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Process createProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcess(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listContaineds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasContainedInv));
    }

    public boolean hasContained(org.semanticwb.process.model.GraphicalElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasContainedInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.GraphicalElement getContained()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasContainedInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
    {
        getSemanticObject().setObjectProperty(swp_processWebPageInv, value.getSemanticObject());
    }

    public void removeProcessWebPage()
    {
        getSemanticObject().removeProperty(swp_processWebPageInv);
    }

    public org.semanticwb.process.model.ProcessWebPage getProcessWebPage()
    {
         org.semanticwb.process.model.ProcessWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processWebPageInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessWebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(getSemanticObject().listObjectProperties(swp_hasProcessInstanceInv));
    }

    public boolean hasProcessInstance(org.semanticwb.process.model.ProcessInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.ProcessInstance getProcessInstance()
    {
         org.semanticwb.process.model.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swp_hasProcessClass.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessClass, value);
    }

    public void removeAllProcessClass()
    {
        getSemanticObject().removeProperty(swp_hasProcessClass);
    }

    public void removeProcessClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessClass,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getProcessClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_hasProcessClass);
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
