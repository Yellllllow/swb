package org.semanticwb.process.model.base;


public abstract class ScriptTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.process.model.Callable,org.semanticwb.model.RoleRefable,org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.Traceable,org.semanticwb.process.model.ResourceAssignmentable
{
    public static final org.semanticwb.platform.SemanticProperty swp_scriptCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#scriptCode");
    public static final org.semanticwb.platform.SemanticClass swp_ScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScriptTask");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScriptTask");

    public static class ClassMgr
    {
       /**
       * Returns a list of ScriptTask for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTasks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ScriptTask for all models
       * @return Iterator of org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTasks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask>(it, true);
        }

        public static org.semanticwb.process.model.ScriptTask createScriptTask(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ScriptTask.ClassMgr.createScriptTask(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ScriptTask
       * @param id Identifier for org.semanticwb.process.model.ScriptTask
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return A org.semanticwb.process.model.ScriptTask
       */
        public static org.semanticwb.process.model.ScriptTask getScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ScriptTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ScriptTask
       * @param id Identifier for org.semanticwb.process.model.ScriptTask
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return A org.semanticwb.process.model.ScriptTask
       */
        public static org.semanticwb.process.model.ScriptTask createScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ScriptTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ScriptTask
       * @param id Identifier for org.semanticwb.process.model.ScriptTask
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       */
        public static void removeScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ScriptTask
       * @param id Identifier for org.semanticwb.process.model.ScriptTask
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return true if the org.semanticwb.process.model.ScriptTask exists, false otherwise
       */

        public static boolean hasScriptTask(String id, org.semanticwb.model.SWBModel model)
        {
            return (getScriptTask(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.ScriptTask
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ScriptTask with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.ScriptTask
       */

        public static java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTaskByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ScriptTask> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ScriptTaskBase.ClassMgr getScriptTaskClassMgr()
    {
        return new ScriptTaskBase.ClassMgr();
    }

   /**
   * Constructs a ScriptTaskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ScriptTask
   */
    public ScriptTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Callable property
* @return boolean with the Callable
*/
    public boolean isCallable()
    {
        return getSemanticObject().getBooleanProperty(swp_callable);
    }

/**
* Sets the Callable property
* @param value long with the Callable
*/
    public void setCallable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_callable, value);
    }

/**
* Gets the ScriptCode property
* @return String with the ScriptCode
*/
    public String getScriptCode()
    {
        return getSemanticObject().getProperty(swp_scriptCode);
    }

/**
* Sets the ScriptCode property
* @param value long with the ScriptCode
*/
    public void setScriptCode(String value)
    {
        getSemanticObject().setProperty(swp_scriptCode, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
