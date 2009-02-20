package org.semanticwb.model.base;


public class SelectTreeBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectTree=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");

    public SelectTreeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.SelectTree getSelectTree(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(org.semanticwb.model.SelectTree.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(org.semanticwb.model.SelectTree.class, it, true);
    }

    public static org.semanticwb.model.SelectTree createSelectTree(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeSelectTree(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasSelectTree(String id, org.semanticwb.model.SWBModel model)
    {
        return (getSelectTree(id, model)!=null);
    }
}
