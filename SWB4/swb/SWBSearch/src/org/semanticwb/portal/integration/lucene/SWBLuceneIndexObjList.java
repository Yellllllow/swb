package org.semanticwb.portal.integration.lucene;

/*
 * WBIndexObjList.java
 *
 * Created on 11 de julio de 2006, 02:55 PM
 */
import org.apache.lucene.search.Hits;
import org.apache.lucene.document.Document;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.indexer.SWBIndexObjList;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBLuceneIndexObjList extends SWBIndexObjList
{
    private static Logger log=SWBUtils.getLogger(SWBLuceneIndexObjList.class);


    Hits hits;
    
    /** Creates a new instance of WBIndexObjList */
    public SWBLuceneIndexObjList(User user, Hits hits, int page, int pindex)
    {
        super(user,page,pindex);
        this.hits=hits;
        //setHits(hits.length());
        build();
    }
    
    private void build()
    {
        //System.out.println("build:"+size());
        //System.out.println("hits:"+hits.length());
        //System.out.println("page:"+page);
        //System.out.println("pindex:"+pindex);
        //insert left
        int maxs=10*getPageLength();
        try
        {
            int apage=page-1;
            int apindex=pindex-1;
            int asize=size();
            for(int x=pindex-1;size()<maxs && x>=0;x--)
            {
                Document doc=hits.doc(x);
                SWBLuceneIndexObj aux=new SWBLuceneIndexObj(doc);
                aux.setScore(hits.score(x));
                aux.setIndex(x);
                add(0,aux);
                aux.setPage(apage);
                if(asize!=size() && (size() % getPageLength())==0)
                {
                    asize=size();
                    apage--;
                    //apindex=x;
                }
                //aux.setPageIndex(apindex);
            }
            
            if(size()>0)
            {
                for(int x=0;x<size();x++)
                {
                    SWBLuceneIndexObj aux=(SWBLuceneIndexObj)get(x);
                    if(apage!=aux.getPage() || x==0)
                    {
                        apage=aux.getPage();
                        apindex=aux.getIndex();
                    }
                    aux.setPage(apage);
                    aux.setPageIndex(apindex);
                }
            }
        }catch(Exception e){log.error(e);}

        //insert right
        int x=0;
        try
        {
            int sizeOff=size();
            int apage=page;
            int apindex=pindex;
            int asize=size();
            for(x=pindex;size()<(maxs+sizeOff) && x<hits.length();x++)
            {
                Document doc=hits.doc(x);
                SWBLuceneIndexObj aux=new SWBLuceneIndexObj(doc);
                aux.setScore(hits.score(x));
                aux.setIndex(x);
                add(aux);
                aux.setPage(apage);
                aux.setPageIndex(apindex);
                if(asize!=size() && ((size()-sizeOff) % getPageLength())==0 && x!=pindex)
                {
                    asize=size();
                    apage++;
                    apindex=x+1;
                    //System.out.println("size2:"+size());
                    //System.out.println("x:"+x);
                    //System.out.println("apage:"+apage);
                    //System.out.println("apindex:"+apindex);
                }
            }
        }catch(Exception e){log.error(e);}
        
        if(x>0 && x==hits.length() && size()>0)
        {
            SWBLuceneIndexObj aux=(SWBLuceneIndexObj)get(0);
            int p=aux.getPage()*getPageLength();
            setHits(p+size());
        }else if(size()>0)
        {
            int t=hits.length();
            SWBLuceneIndexObj aux=(SWBLuceneIndexObj)get(size()-1);
            int p=aux.getPage()*getPageLength();
            int pi=aux.getPageIndex();
            int tot=(int)(((float)(t*p))/pi);
            setHits(tot);
        }
    }
    
    
    
}
