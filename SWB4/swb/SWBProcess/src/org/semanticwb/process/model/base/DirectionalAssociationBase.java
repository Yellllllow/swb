package org.semanticwb.process.model.base;


public abstract class DirectionalAssociationBase extends org.semanticwb.process.model.AssociationFlow 
{
    public static final org.semanticwb.platform.SemanticClass swp_DirectionalAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DirectionalAssociation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DirectionalAssociation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation>(it, true);
        }

        public static org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.createDirectionalAssociation(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DirectionalAssociation getDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DirectionalAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DirectionalAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDirectionalAssociation(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByToFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByToFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByFromFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByFromFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DirectionalAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
