/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import static org.junit.Assert.*;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.util.IndentedWriter;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author serch
 */
public class UserTest {

    static public final String NL = System.getProperty("line.separator") ;

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform.createInstance(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class User.
     */
    //@Test
    public void testGetName() {
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("urswb");
        System.out.println("Repository:"+repository);
        System.out.println("Model:"+repository.getSemanticObject().getModel().getRDFModel());
        SemanticClass cls = repository.getUserType("estudiante");
        repository.createStringExtendedAttribute("escuela", "estudiante");
        repository.createIntExtendedAttribute("edad");
        User instance = repository.getUserByLogin("admin");
        instance.addUserType("estudiante");
        //instance.getSemanticObject().addSemanticClass(cls);
        try
        {

            instance.setUserTypeAttribute("estudiante", "escuela", "ESIME-CULHUACAN");
        } catch (SWBException ex)
        {
            ex.printStackTrace();
        }

        Iterator<SemanticProperty> it  = repository.listAttributes();
        while(it.hasNext()){
            SemanticProperty sp = it.next();
            
            System.out.println("getName: "+sp.getName()+" getRange: "+sp.getRange());
        }
        System.out.println("Extended");
        it  = repository.listExtendedAttributes();
        while(it.hasNext()){
            SemanticProperty sp = it.next();
            
            System.out.println("getName: "+sp.getName()+" getRange: "+sp.getRange());
        }
        System.out.println("UserType estudiante");
        it  = repository.listAttributesofUserType("estudiante");
        while(it.hasNext()){
            SemanticProperty sp = it.next();
            
            System.out.println("getName: "+sp.getName()+" getRange: "+sp.getRange());
        }
        System.out.println("Básicos");
        it  = repository.listBasicAttributes();
        while(it.hasNext()){
            SemanticProperty sp = it.next();
            
            System.out.println("getName: "+sp.getName()+" getRange: "+sp.getRange());
        }
            // repository.createStringExtendedAttribute("escuela", "estudiante");

            //repository.createUser();
            /*
            SemanticClass cls=SWBContext.getVocabulary().swb_Dns;
            Dns dns=(Dns)SWBPlatform.getSemanticMgr().getOntology().getGenericObject("localhost", cls);
            System.out.println("dns:"+dns);
            System.out.println("dns_model:"+dns.getSemanticObject().getModel().getRDFModel());
             */
//        if (null==repository)
//        {
//            repository = SWBContext.createUserRepository("swb_users", "http://www.infotec.com.mx");
//        }
//        System.out.println("Repository2;"+repository);
//        System.out.println("Rep_SemObj:"+repository.getSemanticObject());
//        System.out.println("Rep_SemObj_Mod:"+repository.getSemanticObject().getModel());
//        System.out.println("Rep_SemObj_Mod_NS:"+repository.getSemanticObject().getModel().getNameSpace());
//
//
//        User instance = repository.getUser("serch");
//        //User instance = repository.createUser("serch");
//        System.out.println("User;"+instance+" "+instance.getSemanticObject());
//
//        instance.setCreated(new Date());
//        instance.setStatus(1);
//        instance.setUsrEmail("serch@infotec.com.mx");
//        instance.setUsrFirstName("Sergio");
//        instance.setUsrLastName("Martínez");
//        String expResult = "serch";
//        String result = instance.getName();
//        instance.setUsrPassword("serch08");
//
//        instance = repository.getUser("serch");
//        System.out.println("UsrEmail:"+instance.getUsrEmail());
//        System.out.println("UsrLastName:"+instance.getUsrLastName());
//        System.out.println("Language:"+instance.getLanguage());
//
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
      
        
        
        
       // repository.createStringExtendedAttribute("escuela", "estudiante");
        
        
        //repository.createUser();
        /*
        SemanticClass cls=SWBContext.getVocabulary().swb_Dns;
        Dns dns=(Dns)SWBPlatform.getSemanticMgr().getOntology().getGenericObject("localhost", cls);
        System.out.println("dns:"+dns);
        System.out.println("dns_model:"+dns.getSemanticObject().getModel().getRDFModel());
        */
//        if (null==repository) 
//        {
//            repository = SWBContext.createUserRepository("swb_users", "http://www.infotec.com.mx");
//        }
//        System.out.println("Repository2;"+repository);
//        System.out.println("Rep_SemObj:"+repository.getSemanticObject());
//        System.out.println("Rep_SemObj_Mod:"+repository.getSemanticObject().getModel());
//        System.out.println("Rep_SemObj_Mod_NS:"+repository.getSemanticObject().getModel().getNameSpace());
//        
//        
//        User instance = repository.getUser("serch");
//        //User instance = repository.createUser("serch");
//        System.out.println("User;"+instance+" "+instance.getSemanticObject());
//        
//        instance.setCreated(new Date());
//        instance.setStatus(1);
//        instance.setUsrEmail("serch@infotec.com.mx");
//        instance.setUsrFirstName("Sergio");
//        instance.setUsrLastName("Martínez");
//        String expResult = "serch";
//        String result = instance.getName();
//        instance.setUsrPassword("serch08");
//        
//        instance = repository.getUser("serch");
//        System.out.println("UsrEmail:"+instance.getUsrEmail());
//        System.out.println("UsrLastName:"+instance.getUsrLastName());
//        System.out.println("Language:"+instance.getLanguage());
//        
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUsrPassword method, of class User.
     */
  /*  @Test
    public void testSetUsrPassword() {
        System.out.println("setUsrPassword");
        String password = "";
        User instance = null;
        instance.setUsrPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

   // @Test
    public void listUserAttr(){
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("urswb");
        Iterator <SemanticProperty> itsp = repository.listBasicAttributes();
        while ( itsp.hasNext()){
            System.out.println(itsp.next().getName());
        }
    }

    @Test
    public void FillUsers(){
      UserRepository repository = null;
      String[] nombres = {"Sergio", "Javier", "Jorge", "Carlos", "Edgar", "Nohemi", "Victor", "Melissa", "Nancy", "Rogelio", "Jose", "Aura"};
      String[] apellidos = {"Martínez", "Solís", "Jimenez", "Ramos", "Estrada", "Vargas", "Lorenzana", "Aduna", "Lopez", "Esperon", "Gonzalez", "Castro"};

      String nombre = null;
      String apellido1 = null;
      String apellido2 = null;
      String login = null;
      String mail = null;
      repository = SWBContext.getUserRepository("externalUsers");
      for (int i=0; i<100; i++){
          nombre = nombres[(int)Math.floor(Math.random()*12)];
          apellido1 = apellidos[(int)Math.floor(Math.random()*12)];
          apellido2 = apellidos[(int)Math.floor(Math.random()*12)];
          login = ""+apellido1.substring(0, 1)+nombre+"_"+i;
          mail = login+"@webbuilder.info";
          User current = repository.createUser();
          current.setActive(true);
          current.setUsrFirstName(nombre);
          current.setUsrLastName(apellido1);
          current.setUsrSecondLastName(apellido2);
          current.setUsrLogin(login);
          current.setUsrEmail(mail);
          current.setUsrPassword(apellido2+"."+apellido1);
      }
    }

    //   @Test
    public void test()
    {
        long time=System.currentTimeMillis();

        Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

        // First part or the query string
        String prolog = "PREFIX swb: <"+SemanticVocabulary.URI+">" ;
        prolog+= "PREFIX rdf: <"+SemanticVocabulary.RDF_URI+">" ;
        prolog+= "PREFIX rdfs: <"+SemanticVocabulary.RDFS_URI+">" ;

        // Query string.
        //"SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}"

        String queryString = prolog + NL +
            "SELECT ?x WHERE {?x rdf:type swb:User}" ;

        Query query = QueryFactory.create(queryString) ;
        // Print with line numbers
        query.serialize(new IndentedWriter(System.out,true)) ;
        System.out.println() ;

        // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset

        QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
        // Or QueryExecutionFactory.create(queryString, model) ;

        try {
            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect() ;

            Iterator<String> it=rs.getResultVars().iterator();
            while(it.hasNext())
            {
                System.out.print(it.next()+"\t");
            }
            System.out.println();

            // The order of results is undefined.
            for ( ; rs.hasNext() ; )
            {
                QuerySolution rb = rs.nextSolution() ;


                it=rs.getResultVars().iterator();
                while(it.hasNext())
                {
                    String name=it.next();
                    RDFNode x = rb.get(name) ;
                    System.out.print(x+"\t");
                }
                System.out.println();

                // Get title - variable names do not include the '?' (or '$')
//                RDFNode x = rb.get("x") ;
//                RDFNode title = rb.get("title") ;
//
//                System.out.println("x:"+x+" title:"+title) ;
//                // Check the type of the result value
//                if ( x.isLiteral() )
//                {
//                    Literal titleStr = (Literal)x  ;
//                    System.out.println("    "+titleStr) ;
//                }
//                else
//                    System.out.println("Strange - not a literal: "+x) ;

            }
        }
        finally
        {
            // QueryExecution objects should be closed to free any system resources
            qexec.close() ;
        }


        System.out.println("Time:"+(System.currentTimeMillis()-time));
    }

   // @Test
    public void addRyG(){
        UserRepository rep = SWBContext.createUserRepository("externalUsers", "ussrepext");
        Role role = rep.createRole();
        role.setTitle("Director");
        role = rep.createRole();
        role.setTitle("Gerente");
        role = rep.createRole();
        role.setTitle("Analista");
        role = rep.createRole();
        role.setTitle("Operador");
        UserGroup group = rep.createUserGroup("OPER");
        group = rep.createUserGroup("CAT");
        group = rep.createUserGroup("SALES");
        group = rep.createUserGroup("INVEST");
    }

    //@Test
    public void urdemo(){
        UserRepository rep = SWBContext.getUserRepository("externalUsers");
        rep.setTitle("Usuarios Externos");
    }

}