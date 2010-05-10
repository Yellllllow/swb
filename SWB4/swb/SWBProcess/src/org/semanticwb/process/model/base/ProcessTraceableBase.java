package org.semanticwb.process.model.base;

public interface ProcessTraceableBase extends org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
    public static final org.semanticwb.platform.SemanticProperty swp_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessTraceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessTraceable");

    public java.util.Date getEnded();

    public void setEnded(java.util.Date value);

    public void setEndedby(org.semanticwb.model.User user);

    public void removeEndedby();

    public org.semanticwb.model.User getEndedby();
}
