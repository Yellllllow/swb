/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.xmlrpc;

import java.util.List;
import org.semanticwb.xmlrpc.XmlRpcDescription;
import org.semanticwb.xmlrpc.XmlRpcMethod;
import org.semanticwb.xmlrpc.XmlRpcParam;

/**
 *
 * @author victor.lorenzana
 */
public interface RPCProcess {
    @XmlRpcMethod(methodName="RPCProcess.closeProcessInstance")
    @XmlRpcDescription(description="Close a process instance")
    public void closeProcessInstance(@XmlRpcDescription(description="Parameter for the APIKey registered in apikey.config") @XmlRpcParam(name="APIKey")String APIKey,String UserID,String InstanceID,int closeStatus,String closeAction,String SiteID) throws Exception;
    @XmlRpcMethod(methodName="RPCProcess.closeTaskInstance")
    public void closeTaskInstance(String APIKey, String UserID, String UserGroupsIDs, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception;
    @XmlRpcMethod(methodName="RPCProcess.getProcessInstances")
    public List<String> getProcessInstances(String APIKey, String processSiteID, int instanceStatus, String SiteID) throws Exception;
    @XmlRpcMethod(methodName="RPCProcess.listUserTaskInstances")
    public List<String> listUserTaskInstances(String APIKey, String UserID, String processSiteID,String ProcessID, int instanceStatus, String SiteID) throws Exception;
    @XmlRpcMethod(methodName="RPCProcess.getProcessInstanceStatus")
    public int getProcessInstanceStatus(String APIKey, String processInstanceID, String SiteID) throws Exception;
}
