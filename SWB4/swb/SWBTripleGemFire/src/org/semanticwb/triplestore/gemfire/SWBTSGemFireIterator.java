/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.triplestore.gemfire;

import com.gemstone.gemfire.cache.Region;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Filter;
import com.hp.hpl.jena.util.iterator.Map1;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jei
 */
public class SWBTSGemFireIterator implements ExtendedIterator<Triple>
{

    private static Logger log=SWBUtils.getLogger(SWBTSGemFireIterator.class);

    private SWBTSGemFireGraph graph=null;
    private TripleMatch tm=null;

    private Iterator<SWBTSGemFireTriple> cur;

    private Triple actual=null;
    private Triple next=null;

    private boolean closed=false;

    private static int counter=0;

    public SWBTSGemFireIterator(SWBTSGemFireGraph graph, TripleMatch tm)
    {
        counter++;
        //System.out.println("SWBTSGemFireIterator:"+counter+" tm:"+tm+" "+graph.getName());
        
        this.graph=graph;
        this.tm=tm;

        String subj=SWBTSGemFireUtil.node2HashString(tm.getMatchSubject(),"lgs");
        String prop=SWBTSGemFireUtil.node2HashString(tm.getMatchPredicate(),"lgp");
        String obj=SWBTSGemFireUtil.node2HashString(tm.getMatchObject(),"lgo");
        
        //System.out.println("subj:"+subj+" prop:"+prop+" obj:"+obj);

        try
        {
            Region db = SWBTSGemFire.getCache().getRegion(SWBPlatform.getEnv("swb/gemfire_region_name","swb"));
            Region<String,SWBTSGemFireTriple> g= db.getSubregion("swb_graph_ts"+graph.getId());
            if(g==null)
            {
                g=db.createSubregion("swb_graph_ts"+graph.getId(), db.getAttributes());
            }

            cur=graph.find(g, subj, prop, obj).iterator();
            
            if(cur.hasNext())
            {
                SWBTSGemFireTriple dbobj=cur.next();
                
                String ext=(String)dbobj.getExt();
                next = new Triple(SWBTSGemFireUtil.string2Node((String)dbobj.getSubj(),ext), SWBTSGemFireUtil.string2Node((String)dbobj.getProp(),ext), SWBTSGemFireUtil.string2Node((String)dbobj.getObj(),ext));
            }else
            {
                close();
            }
        }catch(Exception e)
        {
            log.error(e);
        }

        //Thread.currentThread().dumpStack();
    }

    @Override
    public Triple removeNext()
    {
        Triple tp=next();
        remove();
        return tp;
    }

    @Override
    public <X extends Triple> ExtendedIterator<Triple> andThen(Iterator<X> other)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterKeep(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtendedIterator<Triple> filterDrop(Filter<Triple> f)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> ExtendedIterator<U> mapWith(Map1<Triple, U> map1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Triple> toList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Triple> toSet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close()
    {
        if(!closed)
        {
            closed=true;
            try
            {
                counter--;
            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    public boolean hasNext()
    {
        return next!=null;
    }

    public Triple next()
    {
        actual=next;
        next=null;
        try
        {
            if(cur.hasNext())
            {
                SWBTSGemFireTriple dbobj=cur.next();
                String ext=(String)dbobj.getExt();
                next = new Triple(SWBTSGemFireUtil.string2Node((String)dbobj.getSubj(),ext), SWBTSGemFireUtil.string2Node((String)dbobj.getProp(),ext), SWBTSGemFireUtil.string2Node((String)dbobj.getObj(),ext));
            }else
            {
                close();
            }

        }catch(Exception e)
        {
            log.error(e);
        }
        return actual;
    }

    public void remove()
    {
        graph.performDelete(actual);
    }

    @Override
    protected void finalize() throws Throwable
    {
        if(!closed)
        {
            log.warn("Iterator is not closed, "+tm);
            close();
        }
    }
      

}