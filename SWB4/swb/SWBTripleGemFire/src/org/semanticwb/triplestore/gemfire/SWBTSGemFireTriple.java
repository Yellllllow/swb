/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.gemfire;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author javier.solis.g
 */
public class SWBTSGemFireTriple implements Serializable
{
    private String id=null;
    private String subj=null;
    private String prop=null;
    private String obj=null;
    private String ext=null;

    public SWBTSGemFireTriple()
    {
        
    }
    
    public SWBTSGemFireTriple(String subj, String prop, String obj, String ext)
    {
        this(UUID.randomUUID().toString(), subj, prop, obj, ext);
    }
    
    public SWBTSGemFireTriple(String id, String subj, String prop, String obj, String ext)
    {
        this.id=id;
        this.subj=subj;
        this.prop=prop;
        this.obj=obj;
        if(ext!=null && ext.length()>0)this.ext=ext;
    }        

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }    
    
    public String getSubj()
    {
        return subj;
    }

    public void setSubj(String subj)
    {
        this.subj = subj;
    }

    public String getProp()
    {
        return prop;
    }

    public void setProp(String prop)
    {
        this.prop = prop;
    }

    public String getObj()
    {
        return obj;
    }

    public void setObj(String obj)
    {
        this.obj = obj;
    }

    public String getExt()
    {
        return ext;
    }

    public void setExt(String ext)
    {
        this.ext = ext;
    }
    
    
}
