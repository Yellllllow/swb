package org.semanticwb.process.model.base;

public interface BiMessageableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Message");
    public static final org.semanticwb.platform.SemanticProperty swp_outMessageRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outMessageRef");
    public static final org.semanticwb.platform.SemanticProperty swp_inMessageRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#inMessageRef");
    public static final org.semanticwb.platform.SemanticClass swp_BiMessageable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BiMessageable");

    public void setOutMessageRef(org.semanticwb.process.model.Message message);

    public void removeOutMessageRef();

    public org.semanticwb.process.model.Message getOutMessageRef();

    public void setInMessageRef(org.semanticwb.process.model.Message message);

    public void removeInMessageRef();

    public org.semanticwb.process.model.Message getInMessageRef();
}
