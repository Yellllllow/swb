package org.semanticwb.model.base;


public abstract class PasswordUpdateBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_passUpdVerify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#passUpdVerify");
    public static final org.semanticwb.platform.SemanticClass swbxf_PasswordUpdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.PasswordUpdate> listPasswordUpdates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PasswordUpdate>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.PasswordUpdate> listPasswordUpdates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PasswordUpdate>(it, true);
        }

        public static org.semanticwb.model.PasswordUpdate getPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PasswordUpdate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.PasswordUpdate createPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PasswordUpdate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removePasswordUpdate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPasswordUpdate(id, model)!=null);
        }
    }

    public PasswordUpdateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getVerifyText()
    {
        return getSemanticObject().getProperty(swb_passUpdVerify);
    }

    public void setVerifyText(String value)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, value);
    }

    public String getVerifyText(String lang)
    {
        return getSemanticObject().getProperty(swb_passUpdVerify, null, lang);
    }

    public String getDisplayVerifyText(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_passUpdVerify, lang);
    }

    public void setVerifyText(String passUpdVerify, String lang)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, passUpdVerify, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
