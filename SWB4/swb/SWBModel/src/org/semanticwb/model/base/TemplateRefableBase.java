/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;

// TODO: Auto-generated Javadoc
/**
 * The Interface TemplateRefableBase.
 */
public interface TemplateRefableBase extends org.semanticwb.model.Referensable
{
    
    /** The Constant swb_TemplateRef. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    
    /** The Constant swb_hasTemplateRef. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
    
    /** The Constant swb_TemplateRefable. */
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRefable");

    /**
     * List template refs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs();
    
    /**
     * Checks for template ref.
     * 
     * @param templateref the templateref
     * @return true, if successful
     */
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef templateref);
    
    /**
     * List inherit template refs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs();

    /**
     * Adds the template ref.
     * 
     * @param templateref the templateref
     */
    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref);

    /**
     * Removes the all template ref.
     */
    public void removeAllTemplateRef();

    /**
     * Removes the template ref.
     * 
     * @param templateref the templateref
     */
    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref);

    /**
     * Gets the template ref.
     * 
     * @return the template ref
     */
    public org.semanticwb.model.TemplateRef getTemplateRef();
}
