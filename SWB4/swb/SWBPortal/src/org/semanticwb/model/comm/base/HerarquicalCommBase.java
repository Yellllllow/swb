package org.semanticwb.model.comm.base;


public class HerarquicalCommBase extends org.semanticwb.model.comm.OrganizationComm implements org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Calendarable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Hiddenable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_HerarquicalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#HerarquicalComm");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#HerarquicalComm");

    public HerarquicalCommBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.HerarquicalComm> listHerarquicalComms(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.HerarquicalComm>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.HerarquicalComm> listHerarquicalComms()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.HerarquicalComm>(it, true);
    }

    public static org.semanticwb.model.comm.HerarquicalComm getHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.HerarquicalComm)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.comm.HerarquicalComm createHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.HerarquicalComm)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (getHerarquicalComm(id, model)!=null);
    }
}
