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
 * The Class RichTextBase.
 */
public abstract class RichTextBase extends org.semanticwb.model.TextArea 
{
    
    /** The Constant swbxf_RichText. */
    public static final org.semanticwb.platform.SemanticClass swbxf_RichText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#RichText");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List rich texts.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }

        /**
         * List rich texts.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RichText> listRichTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RichText>(it, true);
        }

        /**
         * Gets the rich text.
         * 
         * @param id the id
         * @param model the model
         * @return the rich text
         */
        public static org.semanticwb.model.RichText getRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the rich text.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. rich text
         */
        public static org.semanticwb.model.RichText createRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RichText)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the rich text.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeRichText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for rich text.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasRichText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRichText(id, model)!=null);
        }
    }

    /**
     * Instantiates a new rich text base.
     * 
     * @param base the base
     */
    public RichTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
