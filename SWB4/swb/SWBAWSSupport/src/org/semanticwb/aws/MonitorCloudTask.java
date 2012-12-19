/*
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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.aws;

import java.util.Calendar;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 *
 * @author serch
 */
public final class MonitorCloudTask extends TimerTask {

    private static Logger log = SWBUtils.getLogger(MonitorCloudTask.class);
    private long lastLaunchTime = 0;
    private final static long _LAUNCH_PERIOD = 10 * 60 * 1000l;

    public MonitorCloudTask() {
        lastLaunchTime = System.currentTimeMillis();
    }


    @Override
    public void run() {
        try {
        log.trace("Monitor AWS Timer Wake Up...");
        AWSServices awsServ = SWBPortal.getAWSCloud();
        SWBCloudControlCenter cc = awsServ.getControlCenter();
        cc.reloadData();
        log.trace("found a load of: "+cc.getAverageLoad());
        if (cc.isLaunched()) {
            log.trace("System is in Launched state, last launch period: "+(System.currentTimeMillis() - lastLaunchTime + _LAUNCH_PERIOD));
            if ((lastLaunchTime + _LAUNCH_PERIOD) < System.currentTimeMillis()
                    && cc.getMaxInstances() > cc.currentInstances()
                    && cc.getAverageLoad() > cc.getAvgCPU()) {
                log.trace("Time to launch a new instance");
                launchNew(awsServ, cc);
                lastLaunchTime = System.currentTimeMillis();
                log.trace("Instance Launched "+lastLaunchTime);
            }
            if ((lastLaunchTime + _LAUNCH_PERIOD) < System.currentTimeMillis()
                    && 1 < cc.currentInstances()
                    && cc.getAverageLoad() < 20.0d){
                log.trace("Time to Terminate an instance");
                InstanceData id = cc.getFisrtInstance();
                awsServ.removeInstance(cc.getLbName(), id.getId());
                cc.removeInstanceData(id);
                log.trace("Instance "+id.getId()+" terminated");
            }
        }
        log.trace("Monitor AWS Timer goes to sleep...");
        } catch (Exception e){
            log.error("Monitor AWS Timer",e);
        }
    }
    
    private String launchNew(final AWSServices awsServ, final SWBCloudControlCenter controlCenter){
        return awsServ.createInstance(controlCenter.getPlacement(), controlCenter.getAmiID(), 
                controlCenter.getInstanceType(), controlCenter.getSeg(), controlCenter.getKeyPair(), 
                controlCenter.getMemory(), controlCenter.getAppServ(), controlCenter.getElasticDNS(), 
                controlCenter.getLbName());
    }
}
