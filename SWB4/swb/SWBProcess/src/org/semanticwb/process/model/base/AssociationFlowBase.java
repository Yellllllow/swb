package org.semanticwb.process.model.base;


public abstract class AssociationFlowBase extends org.semanticwb.process.model.ConnectionObject 
{
    public static final org.semanticwb.platform.SemanticClass swp_AssociationFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow>(it, true);
        }

        public static org.semanticwb.process.model.AssociationFlow createAssociationFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AssociationFlow.ClassMgr.createAssociationFlow(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.AssociationFlow getAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AssociationFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.AssociationFlow createAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AssociationFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssociationFlow(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByToFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByToFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByFromFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByFromFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AssociationFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
