package org.semanticwb.portal.community.base;


public class ClasifiedBuySellBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedBuySell=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedBuySell");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedBuySell");

    public ClasifiedBuySellBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySells(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySells()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell>(it, true);
    }

    public static org.semanticwb.portal.community.ClasifiedBuySell getClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedBuySell)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.ClasifiedBuySell createClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedBuySell)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasifiedBuySell(id, model)!=null);
    }
}
