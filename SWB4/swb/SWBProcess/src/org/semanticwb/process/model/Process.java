/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;
import org.semanticwb.platform.SemanticProperty;

public class Process extends org.semanticwb.process.model.base.ProcessBase 
{
    private static Logger log=SWBUtils.getLogger(SWBPClassMgr.class);
    
    static 
    {
        swp_parentWebPage.registerObserver(new SemanticObserver() 
        {
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                //System.out.println("obj:"+obj+" prop:"+prop+" action:"+action);
                if(obj.instanceOf(Process.sclass))
                {
                    Process process=(Process)obj.createGenericInstance();                    
                    process.getProcessWebPage().removeAllVirtualParent();
                    if(action!=null && action.equals("SET"))
                    {
                        if(process.getParentWebPage()!=null)
                        {
                            process.getProcessWebPage().addVirtualParent(process.getParentWebPage());
                        }
                    }
                }
                        
            }
        });
    }
    
    
    public Process(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        getProcessWebPage();  //Crea pagina web de proceso
        
    }

    /**
     * Crea una instancia del Proceso
     * @param fobj
     * @param pinst
     * @return
     */
    public ProcessInstance createInstance()
    {
        //System.out.println("createInstance process:"+this);
        ProcessInstance inst=null;
        inst=this.getProcessSite().createProcessInstance();
        inst.setProcessType(this);
        inst.setStatus(Instance.STATUS_INIT);

        Iterator<ItemAware> it=listRelatedItemAware().iterator();
        while (it.hasNext())
        {
            ItemAware item = it.next();
            SemanticClass scls=item.getItemSemanticClass();
            SemanticProperty sprop=null;
            SWBModel model=this.getProcessSite();
            String id=null;
            String code=null;
            if(item instanceof Collectionable)
            {
                model=this.getProcessSite().getProcessDataInstanceModel();
            }else
            {
                id=((DataStore)item).getDataObjectId();
                code=((DataStore)item).getInitializationCode();
            }

            if(scls!=null)
            {
                SemanticObject ins=null;
                if(code!=null)
                {
                    Object ret=null;
                    try
                    {
                        //long ini=System.currentTimeMillis();
                        Interpreter i = SWBPClassMgr.getInterpreter();
                        ret=i.eval(code);
                        //System.out.println("ret:"+ret);
                        //System.out.println("time:"+ (System.currentTimeMillis()-ini ));
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                    //String action=source.getAction();
                    if(ret!=null && ret instanceof SemanticObject && ((SemanticObject)ret).instanceOf(scls))
                    {
                        ins=((SemanticObject)ret);
                    }

                    if(ret!=null && ret instanceof SWBClass && ((SWBClass)ret).getSemanticObject().instanceOf(scls))
                    {
                        ins=((SWBClass)ret).getSemanticObject();
                    }
                }
                if(ins==null)
                {                
                    if(id==null)
                    {
                        id=String.valueOf(model.getSemanticModel().getCounter(scls));
                    }else
                    {
                        ins=SemanticObject.createSemanticObject(model.getSemanticModel().getObjectUri(id, scls));
                    }
                    if(ins==null)
                    {
                        Iterator auxit=ItemAwareMapping.ClassMgr.listItemAwareMappingByLocalItemAware(item, item.getProcessSite());
                        if(!auxit.hasNext())
                        {
                            ins=model.getSemanticModel().createSemanticObjectById(id, scls);
                        }
                    }
                }
                

                ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                ref.setItemAware(item); 
                if(ins!=null)
                {
                    ref.setProcessObject((SWBClass)ins.createGenericInstance());
                }
                inst.addItemAwareReference(ref);
                //System.out.println("addItemAwareReference:"+ref);
            }
        }

        return inst;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public List<ItemAware> listRelatedItemAware()
    {
        //System.out.println("getRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof ItemAware)
            {
                ItemAware item=(ItemAware)graphicalElement;

                if(!item.listInputConnectionObjects().hasNext() && !item.listOutputConnectionObjects().hasNext())
                {
                    arr.add(item);
                }
            }
        }
        return arr;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    public List<ItemAware> listHerarquicalRelatedItemAware()
    {
        //System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        return listRelatedItemAware();
    }
    
    /**
     * Obtiene una lista de todos los nodos contenidos en un proceso.
     * @return 
     */
    public Iterator<GraphicalElement> listAllContaineds()
    {
        ArrayList<GraphicalElement> arr=new ArrayList();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement gElement = it.next();
            arr.add(gElement);
            if(gElement instanceof SubProcess)
            {
                Iterator<GraphicalElement> it2=((SubProcess)(gElement)).listAllContaineds();
                while (it2.hasNext())
                {
                    GraphicalElement gElement2 = it2.next();
                    arr.add(gElement2);
                }
            }
        }
        return arr.iterator();
    }

    @Override
    public WrapperProcessWebPage getProcessWebPage() 
    {
        WrapperProcessWebPage wp=super.getProcessWebPage();
        if(wp==null)
        {
            wp=WrapperProcessWebPage.ClassMgr.createWrapperProcessWebPage(getId()+"_swp", getProcessSite());
            setProcessWebPage(wp);
            wp.setActive(true);
            //eliminar cache
            //getSemanticObject().removeCache(wp.getURI());
            //wp=super.getProcessWebPage();
        }
        return wp;
    }    
    
    

}
