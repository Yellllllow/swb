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
 


/*
 * FUpload.java
 *
 * Created on 15 de noviembre de 2004, 07:00 PM
 */

package applets.htmleditor;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author  Victor Lorenzana
 */
public class FUpload extends javax.swing.JDialog implements FileUploadListener{
    
    /** Creates new form FUpload */
    URL url;
    String jsess;
    Vector listeners=new Vector();
    String path;
    java.io.File f;
    //jTableFileModel model;
    //Directory dir;
    
    private String topicMap;
    private String id;
    private String version;
    private String type;
    
    public FUpload(java.awt.Frame parent, boolean modal,String jsess,URL url) {
        super(parent, modal);
        initComponents();
        this.jsess=jsess;
        this.url=url;     
        this.setLocation(400,300);
        this.setSize(200, 50);    
        //System.out.println("FUpload");
    }
    
    public void addSendListener(FileUploadListener listener)
    {
        listeners.add(listener);
    }
    
    public void fireSend(int size,int value)
    {
        Iterator it=listeners.iterator();
        while(it.hasNext())
        {
            FileUploadListener fl=(FileUploadListener)it.next();
            fl.onSend(size,value);
        }
    }
   
    public void sendFile(String path,java.io.File f)//,jTableFileModel model,Directory dir) 
    {   
        //System.out.println("sendFile");
        
        this.setTitle(f.getName());
        this.setVisible(true);
        this.path=path;
        this.f=f;
        //this.model=model;
        //this.dir=dir;        
        Worker worker = new Worker();        
        this.addSendListener(this);        
        worker.start();               
    }
   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.add(jProgressBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    
    
    
    public void onSend(int size, int value) {               
        this.jProgressBar1.setValue(value);            
        this.jProgressBar1.repaint();
        this.jProgressBar1.updateUI();
        SwingUtilities.updateComponentTreeUI(this.jProgressBar1);
    }    
    
    /**
     * Getter for property topicMap.
     * @return Value of property topicMap.
     */
    public java.lang.String getTopicMap()
    {
        return topicMap;
    }
    
    /**
     * Setter for property topicMap.
     * @param topicMap New value of property topicMap.
     */
    public void setTopicMap(java.lang.String topicMap)
    {
        this.topicMap = topicMap;
    }
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId()
    {
        return id;
    }
    
    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }
    
    /**
     * Getter for property version.
     * @return Value of property version.
     */
    public java.lang.String getVersion()
    {
        return version;
    }
    
    /**
     * Setter for property version.
     * @param version New value of property version.
     */
    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getType()
    {
        return type;
    }
    
    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(java.lang.String type)
    {
        this.type = type;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
       
    private class Worker extends Thread
    {
       
        
        Worker()
        {
        
        }

        public void run()
        {           
           try
           {            
                int max=(int)(f.length()/8192);
                max++;    
                jProgressBar1.setMaximum(max);
                jProgressBar1.setIndeterminate(false);
                jProgressBar1.setStringPainted(true);
                jProgressBar1.setValue(0);
                jProgressBar1.updateUI();
                if(f.exists())
                {
                    try
                    {
                        URLConnection con=url.openConnection();
                        con.setUseCaches(false);
                        con.setDefaultUseCaches(false);                        
                        if(jsess!=null)con.setRequestProperty("Cookie","JSESSIONID="+jsess);
                        con.addRequestProperty("PATHFILEWB",path);
                        con.addRequestProperty("TM",topicMap);
                        con.addRequestProperty("ID",id);
                        con.addRequestProperty("VER",version);
                        con.addRequestProperty("TYPE", type);
                        con.setDoOutput(true);                
                        OutputStream out=con.getOutputStream();
                        FileInputStream fin=new FileInputStream(f);
                        byte[] bcont=new byte[8192];
                        int ret=fin.read(bcont);
                        int ivalue=0;
                        while(ret!=-1)
                        {                           
                           
                            out.write(bcont,0,ret);                    
                            ivalue++;                            
                            fireSend(max, ivalue);                                                                                     
                            ret=fin.read(bcont);
                        }                                                
                        out.close();
                        fin.close();                                                              
                        //painter.die(true);
                        //System.out.println(painter.isAlive());
                        String resp=con.getHeaderField(0);
                        StringTokenizer st=new StringTokenizer(resp," ");
                        if(st.countTokens()>=2)
                        {
                            String intcode=st.nextToken();
                            intcode=st.nextToken();
                            if(intcode.equals("200"))
                            {
                                //java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                                //java.sql.Date date=new java.sql.Date(System.currentTimeMillis());                                    
                                //JOptionPane.showMessageDialog(null,"El archivo fue enviado con �xito...","WebBuilder",JOptionPane.INFORMATION_MESSAGE);            
/*                                
                                applets.ftp.File newfile=new applets.ftp.File(dir,f.getName(),path,String.valueOf(f.length()),df.format(date));                                                                                                                               
                                synchronized(model)
                                {
                                    model.addFile(newfile);                                                            
                                }
*/
                            }
                            else
                            {
                                if(st.countTokens()>=3)
                                {
                                    JOptionPane.showMessageDialog(null,"Error:"+intcode,"WebBuilder",JOptionPane.ERROR_MESSAGE);            
                                }
                            }
                        }

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,e,"WebBuilder",JOptionPane.ERROR_MESSAGE);            
                    }
                }
            }
            catch(Exception err)
            {
                System.out.println(err.getMessage());
            }      
           setVisible(false);
           dispose();            
        }          
    }
}
