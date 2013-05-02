/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.xmlrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.xmlrpc.XmlRpcObject;

/**
 *
 * @author victor.lorenzana
 */
public class RPCProcessImp extends XmlRpcObject implements RPCProcess
{

    @Override
    public void closeProcessInstance(String APIKey, String UserID, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(InstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The user " + UserID + " was not found");
        }
        pi.close(u, closeStatus, closeAction);
    }

    @Override
    public void closeTaskInstance(String APIKey, String UserID, String UserGroupsIDs, String InstanceID, int closeStatus, String closeAction, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        FlowNodeInstance pi = FlowNodeInstance.ClassMgr.getFlowNodeInstance(InstanceID, site);
        if (pi == null)
        {
            throw new Exception("The FlowNodeInstance with id " + InstanceID + " was not found");
        }
        User u = site.getUserRepository().getUser(UserID);
        if (u == null)
        {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        pi.close(u, closeStatus, closeAction);
    }

    @Override
    public InstanceInfo[] getProcessInstances(String APIKey, int instanceStatus, String SiteID) throws Exception
    {
        List<InstanceInfo> getProcessInstances = new ArrayList<InstanceInfo>();
        ProcessSite p = ProcessSite.ClassMgr.getProcessSite(SiteID);
        
        if (p == null) {
            throw new Exception("The ProcessSite with id " + SiteID + " was not found");
        }
        Iterator<ProcessInstance> gi = SWBProcessMgr.getProcessInstanceWithStatus(p, instanceStatus);
        if (gi.hasNext()) {
            while (gi.hasNext()) {
                ProcessInstance pi = gi.next();
                getProcessInstances.add(getProcessInstanceInfo(pi));
            }
        }
        return getProcessInstances.toArray(new InstanceInfo[getProcessInstances.size()]);
    }

    /*@Override
    public List<String> getProcessInstances(String APIKey, int instanceStatus, String SiteID) throws Exception
    {
    List<String> getProcessInstances = new ArrayList<String>();
    WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
    if (site == null)
    {
    throw new Exception("The site " + SiteID + " was not found");
    }
    Iterator<ProcessSite> sites = ProcessSite.ClassMgr.listProcessSites(site);
    while (sites.hasNext())
    {
    ProcessSite p = sites.next();
    List<String> tmp=getProcessInstances(APIKey, p.getId(), instanceStatus, SiteID);
    getProcessInstances.addAll(tmp);
    }
    return getProcessInstances;
    }*/
    @Override
    public FlowNodeInstanceInfo[] listUserTaskInstances(String APIKey, String UserID, String ProcessID, int instanceStatus, String SiteID) throws Exception
    {
        List<FlowNodeInstanceInfo> listUserTaskInstances = new ArrayList<FlowNodeInstanceInfo>();
        ProcessSite site = ProcessSite.ClassMgr.getProcessSite(SiteID);
        User u = site.getUserRepository().getUser(UserID);
        int status = instanceStatus<0?FlowNodeInstance.STATUS_PROCESSING:instanceStatus;
        
        if (site == null) {
            throw new Exception("The processSite with id " + SiteID + " was not found");
        }
        
        if (u == null) {
            throw new Exception("The User with id " + UserID + " was not found");
        }
        
        org.semanticwb.process.model.Process p = org.semanticwb.process.model.Process.ClassMgr.getProcess(ProcessID, site);
        
        Iterator<FlowNodeInstance> flows = SWBProcessMgr.getUserTaskInstancesWithStatus(site, status, p).iterator();
        while (flows.hasNext()) {
            FlowNodeInstance fni = flows.next();
            if (validUserTaskInstance(fni, u, status)) {
                FlowNodeInstanceInfo info = getFlowNodeInstanceInfo(fni);
                System.out.println("->id:"+info.id);
                listUserTaskInstances.add(info);
            }
        }
        System.out.println("--->id:"+listUserTaskInstances.get(0).id);
        FlowNodeInstanceInfo [] ret = listUserTaskInstances.toArray(new FlowNodeInstanceInfo[listUserTaskInstances.size()]);
        System.out.println("----->id:"+ret[0].id);
        return ret;
    }

    @Override
    public int getProcessInstanceStatus(String APIKey, String processInstanceID, String SiteID) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(SiteID);
        if (site == null)
        {
            throw new Exception("The site " + SiteID + " was not found");
        }
        ProcessInstance pi = ProcessInstance.ClassMgr.getProcessInstance(processInstanceID, site);
        if (pi == null)
        {
            throw new Exception("The ProcessInstance with id " + processInstanceID + " was not found");
        }
        return pi.getStatus();
    }
    
    public InstanceInfo getProcessInstanceInfo(ProcessInstance pi) {
        InstanceInfo ret = new InstanceInfo();
        ret.id = pi.getId();
        ret.title = pi.getProcessType().getTitle();
        ret.description = pi.getProcessType().getDescription();
        ret.status = pi.getStatus();
        ret.creator = pi.getCreator()==null?null:pi.getCreator().getId();
        ret.created = pi.getCreated();
        ret.closed = pi.getEnded();
        ret.closedBy = pi.getEndedby()==null?null:pi.getEndedby().getId();
        return ret;
    }
    
    public FlowNodeInstanceInfo getFlowNodeInstanceInfo(FlowNodeInstance flow) {
        FlowNodeInstanceInfo ret = new FlowNodeInstanceInfo();
        ret.id = flow.getId();
        ret.title = flow.getFlowNodeType().getTitle();
        ret.description = flow.getFlowNodeType().getDescription();
        ret.status = flow.getStatus();
        ret.creator = flow.getCreator()==null?null:flow.getCreator().getId();
        ret.assignedTo = flow.getAssignedto()==null?null:flow.getAssignedto().getId();
        ret.assigned = flow.getAssigned();
        ret.created = flow.getCreated();
        ret.closed = flow.getEnded();
        ret.closedBy = flow.getEndedby()==null?null:flow.getEndedby().getId();
        ret.subject = flow.getSubject();
        ret.processInstance = getProcessInstanceInfo(flow.getProcessInstance());
        return ret;
    }
    
    private boolean validUserTaskInstance(FlowNodeInstance fni, User user, int statusFilter) {
        boolean hasStatus = false;
        boolean canAccess = false;
        
        org.semanticwb.process.model.Process pType = fni.getProcessInstance().getProcessType();
        
        if (!pType.isValid()) {
            return false;
        }
        canAccess = fni.haveAccess(user);
        
        if (canAccess) {
            //Verificar filtrado por estatus
            if (statusFilter > 0 && fni.getStatus() == statusFilter) {
                hasStatus = true;
            }
        }
        return canAccess && hasStatus;
    }
}
