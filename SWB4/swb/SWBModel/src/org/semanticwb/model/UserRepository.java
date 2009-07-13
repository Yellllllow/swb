package org.semanticwb.model;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.security.auth.ExtUserRepInt;

public class UserRepository extends UserRepositoryBase
{

    static Logger log = SWBUtils.getLogger(UserRepository.class);
    //public static final String SWBUR_AuthMethod = "SWBUR_AuthMethod";
    //public static final String SWBUR_LoginContext = "SWBUR_LoginContext";
    //public static final String SWBUR_CallBackHandlerClassName = "SWBUR_CallBackHandlerClassName";
    public static final String SWBUR_ClassHold = "extendedAttributes";
    public static final String SWBUR_ClassPost = "#clsExtendedAttibutes";
    public static final String SWBUR_ClassUserTypeHold = "userType";
    public static final String SWBUR_ClassUserTypePost = "/clsUserType";
    private static ArrayList<String> userTypes = new ArrayList<String>();
    private static final String NL = System.getProperty("line.separator");
    private final boolean EXTERNAL;
    private final ExtUserRepInt bridge;

    public UserRepository(SemanticObject base)
    {
        super(base);
        boolean ret = false;
        ExtUserRepInt classRet = null;
        if (null == super.getUserRepExternalConfigFile())
        {
            ret = false;
        } else
        {
            try
            {
                Properties props = SWBUtils.TEXT.getPropertyFile(super.getUserRepExternalConfigFile());
                String className = props.getProperty("class");
                Class clase = Class.forName(className);
                classRet = (ExtUserRepInt) clase.getConstructor(UserRepository.class, Properties.class).newInstance(this, props);
                log.trace("External User Bridge Creado!"+classRet);
                ret = true;
            } catch (Exception ex)
            {
                ret = false;
                log.debug("Can't load class for External Repository Bridge", ex);
            }

        }
        EXTERNAL = ret;
        bridge = classRet;
        //System.out.println("***********UserRepository***************");
        StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
        while (ptopIt.hasNext())
        {
            Statement sp = (Statement) ptopIt.next();
            //System.out.println("getPredicate:"+sp.getPredicate());
            if (sp.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
            {
                String uri = sp.getObject().toString();
                userTypes.add(uri.split("#")[1]);
            //System.out.println("userTypes:"+uri.split("#")[1]);
            //getSemanticObject().getModel().registerClass(uri);
            }
        }
    //System.out.println("***********end***************");

    /*
    String uri = getProperty(SWBUR_ClassHold);
    if (uri != null)
    {
    uri = getId() + SWBUR_ClassPost;
    getSemanticObject().getModel().registerClass(uri);
    }
    StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
    while (ptopIt.hasNext())
    {
    Statement sp = (Statement) ptopIt.next();
    if (sp.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
    {
    uri = sp.getObject().toString();
    userTypes.add(uri.split("#")[1]);
    getSemanticObject().getModel().registerClass(uri);
    }
    }
     */
    }

    public Iterator<String> searchUsersBy(String usrFirstName, String usrLastName, String usrSecondLastName, String usrEmail, String Role, String Group, String Active)
    {
        //System.out.println("Grp: "+Group);

        Iterator<String> ret = null;
        //Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
        Model model = getSemanticObject().getModel().getRDFModel();
        // First part or the query string
        String prolog = "PREFIX swb: <" + SemanticVocabulary.URI + ">\n";
        prolog += "PREFIX rdf: <" + SemanticVocabulary.RDF_URI + ">\n";
        prolog += "PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + ">\n";


        String _usrFirstName = usrFirstName != null ? usrFirstName : "";
        String _usrLastName = usrLastName != null ? usrLastName : "";
        String _usrSecondLastName = usrSecondLastName != null ? usrSecondLastName : "";
        String _usrEmail = usrEmail != null ? usrEmail : "";
        String _Role = Role != null ? Role : null;
        String _Group = Group != null ? Group : null;

        if (null != _Role)
        {
            if (hasRole(_Role))
            {
                _Role = getRole(_Role).getURI();
            } else
            {
                _Role = null;
            }
        }

        if (null != _Group)
        {
            if (hasUserGroup(_Group))
            {
                _Group = getUserGroup(_Group).getURI();
            } else
            {
                _Group = null;
            }
        }


        // Query string.
        //"SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}"

        String queryString = prolog + NL +
                "SELECT \n?x \nWHERE { \n?x rdf:type swb:User. \n";
//                        "SELECT \n?x ?fname ?lname ?slname ?mail ?login \nWHERE { \n?x rdf:type swb:User. \n";
//        queryString += "?x swb:usrLogin ?login. \n";
        if (null != _Role)
        {
            queryString += "?x swb:hasRole <" + _Role + ">.\n";
        }
        if (null != _Group)
        {
            queryString += "?x swb:userGroup <" + _Group + ">.\n";
        }
        if (null != Active)
        {
            queryString += "?x swb:active " + Active + ".\n";
        }
        if (!"".equals(_usrFirstName))
        {
            queryString += "?x swb:usrFirstName ?gfn .   FILTER regex(?gfn, \"" + _usrFirstName + "\", \"i\"). \n";
        }
        if (!"".equals(_usrLastName))
        {
            queryString += "?x swb:usrLastName ?gln.   FILTER regex(?gln, \"" + _usrLastName + "\", \"i\"). \n";
        }
        if (!"".equals(_usrSecondLastName))
        {
            queryString += "?x swb:usrSecondLastName ?gsln.   FILTER regex(?gsln, \"" + _usrSecondLastName + "\", \"i\"). \n";
        }
        if (!"".equals(_usrEmail))
        {
            queryString += "?x swb:usrEmail ?gml.   FILTER regex(?gml, \"" + _usrEmail + "\", \"i\"). \n";
        }
        queryString +=
                //                     "OPTIONAL{\n ?x swb:usrFirstName ?fname. \n}\n" +
                //                     "OPTIONAL{\n  ?x swb:usrLastName ?lname. \n}\n" +

                //                "OPTIONAL{\n?x swb:usrSecondLastName ?slname. \n}\n" +
                //                "OPTIONAL{\n?x swb:usrEmail ?mail. \n}\n" +

                "}";

        //  System.out.println(queryString);
        Query query = QueryFactory.create(queryString);

        // System.out.println(getId());
        // Print with line numbers
        // query.serialize(new IndentedWriter(System.out,true)) ;
        // System.out.println() ;

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try
        {
            ResultSet rs = qexec.execSelect();
            ArrayList<String> lista = new ArrayList<String>();
            for (; rs.hasNext();)
            {
                QuerySolution rb = rs.nextSolution();
                String current = rb.get("x").toString();
                /*+ "||" +
                (null==rb.get("fname")?"":rb.get("fname")) + "||" +
                (null==rb.get("lname")?"":rb.get("lname")) + "||" +
                (null==rb.get("slname")?"":rb.get("slname")) + "||" +
                (null==rb.get("mail")?"":rb.get("mail")) + "||" +
                (null==rb.get("login")?"":rb.get("login"));*/
                lista.add(current);

            }
            ret = lista.iterator();
        } finally
        {
            // QueryExecution objects should be closed to free any system resources
            qexec.close();
        }

        return ret;
    }

    public User getUserByLogin(String login)
    {
        User ret = null;
        log.debug("Login a buscar: "+login+" External:"+EXTERNAL);
        if (null != login)
        {
            Iterator aux = getSemanticObject().getRDFResource().getModel().listStatements(null, User.swb_usrLogin.getRDFProperty(), getSemanticObject().getModel().getRDFModel().createLiteral(login));
            Iterator it = new GenericIterator(aux, true);
            if (it.hasNext())
            {
                ret = (User) it.next();
            }
            if (EXTERNAL)
            {
                if (bridge.syncUser(login, ret))
                {
                    if (null == ret)
                    {
                        it = new GenericIterator(aux, true);
                        if (it.hasNext())
                        {
                            ret = (User) it.next();
                        }
                    }
                }
            }
        }
        return ret;
    }

    public void syncUsers()
    {
        if (EXTERNAL)
        {   System.out.println("entrando a syncUsers");
            bridge.syncUsers(); System.out.println("UsersSynced");
        }
    }

    public SemanticProperty getExtendedAttribute(String name)
    {
        return getExtendedAttributesClass().getProperty(name);
    }

    public SemanticProperty createIntExtendedAttribute(String name)
    {
        return createIntExtendedAttribute(name, null);
    }

    public SemanticProperty createIntExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INT);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_i_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createLongExtendedAttribute(String name)
    {
        return createLongExtendedAttribute(name, null);
    }

    public SemanticProperty createLongExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_LONG);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_l_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createBooleanExtendedAttribute(String name)
    {
        return createBooleanExtendedAttribute(name, null);
    }

    public SemanticProperty createBooleanExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_BOOLEAN);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_b_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name)
    {
        return createDateTimeExtendedAttribute(name, null);
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DATETIME);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_t_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createDoubleExtendedAttribute(String name)
    {
        return createDoubleExtendedAttribute(name, null);
    }

    public SemanticProperty createDoubleExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DOUBLE);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_d_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createFloatExtendedAttribute(String name)
    {
        return createFloatExtendedAttribute(name, null);
    }

    public SemanticProperty createFloatExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_FLOAT);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_f_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createStringExtendedAttribute(String name)
    {
        return createStringExtendedAttribute(name, null);
    }

    public SemanticProperty createStringExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_s_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);
            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
        }
        return sp;
    }

    public SemanticProperty createListExtendedAttribute(String name, String[] values) //values= ["value1:value2:value3:...:valuen","lang|label1:label2:label3:...:labeln",...,"lang|label1:label2:label3:...:labeln"]
    {
        return createListExtendedAttribute(name, null, values);
    }

    public SemanticProperty createListExtendedAttribute(String name, String clsName, String[] values)//values= [value:label|value:label{@lang}]
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
            SemanticObject dp = sp.getDisplayProperty();
            if (dp == null)
            {
                dp = getSemanticObject().getModel().createSemanticObject("swbxff_l_" + name, DisplayProperty.swbxf_DisplayProperty);
            }
            DisplayProperty dobj = new DisplayProperty(dp);

            Statement stmt = getSemanticObject().getModel().getRDFModel().createStatement(getSemanticObject().getModel().getRDFModel().getResource(sp.getURI()),
                    SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel().getProperty(SemanticVocabulary.SWB_PROP_DISPLAYPROPERTY),
                    dobj.getSemanticObject().getRDFResource().as(Resource.class));
            getSemanticObject().getModel().getRDFModel().add(stmt);
            String[] vals = values[0].split(":");
            for (int i = 1; i < values.length; i++)
            {
                String lang = values[1].split("\\|")[0];
                String[] labels = values[1].split("\\|")[1].split(":");
                StringBuilder cad = new StringBuilder(250);
                for (int j = 0; j < vals.length; j++)
                {
                    cad.append(vals[j]).append(":").append(labels[j]).append((j + 1 == vals.length ? "" : "|"));
                }
                dobj.setSelectValues(cad.toString(), lang); //value:label|value:label{@lang}
            }
        }
        return sp;
    }

    public Iterator<SemanticProperty> listAttributesofUserType(String name)
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        Iterator<SemanticProperty> itsp = getUserType(name).listProperties();
        while (itsp.hasNext())
        {
            SemanticProperty sp = itsp.next();
            log.debug("Encontrada... " + sp + " - " + sp.getURI());
            if (null == sp.getRange() || null == sp.getDisplayProperty() || !sp.getURI().startsWith(getId()))
            {
                continue;
            }
            log.trace("Agregada... " + sp);
            alsp.add(sp);
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listExtendedAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        Iterator<SemanticProperty> itsp = getExtendedAttributesClass().listProperties();
        while (itsp.hasNext())
        {
            SemanticProperty sp = itsp.next();
            log.debug("Encontrada... " + sp + " - " + sp.getURI());
            if (null == sp.getRange() || null == sp.getDisplayProperty() || !sp.getURI().startsWith(getId()))
            {
                continue;
            }
            log.trace("Agregada... " + sp);
            alsp.add(sp);
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listBasicAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(User.swb_User.getURI());
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                log.trace("Encontrada... " + sp);
                log.trace("DisplayProperty: " + sp.getDisplayProperty());
                if (null == sp.getRange() || null == sp.getDisplayProperty())
                {
                    continue;
                }
                log.debug("Agregada... " + sp);
                alsp.add(sp);
            }
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        String uri = null;
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(User.swb_User.getURI());
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                log.trace("Encontrada..B. " + sp);
                if (null == sp.getRange() || null == sp.getDisplayProperty())
                {
                    continue;
                }
                log.trace("Agregada..B. " + sp);
                alsp.add(sp);
            }
        }
        sc = getExtendedAttributesClass();
        Iterator<SemanticProperty> itsp = sc.listProperties();
        while (itsp.hasNext())
        {
            SemanticProperty sp = itsp.next();
            log.trace("Encontrada..E. " + sp);
            if (null == sp.getRange() || null == sp.getDisplayProperty())
            {
                continue;
            }
            log.trace("Agregada..E. " + sp);
            alsp.add(sp);
        }
        Iterator<String> itut = getUserTypes();
        while (itut.hasNext())
        {
            sc = getUserType(itut.next());
            log.trace("Clase de usuario: " + sc);
            itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                log.trace("Encontrada..U. " + sp);
                if (null == sp.getRange() || null == sp.getDisplayProperty())
                {
                    continue;
                }
                log.trace("Agregada..U. " + sp);
                alsp.add(sp);
            }
        }
        return alsp.iterator();
    }

    public SemanticClass getExtendedAttributesClass()
    {
        SemanticClass cls = null;
        String uri = getProperty(SWBUR_ClassHold);
        if (uri == null)
        {
            uri = getId() + SWBUR_ClassPost;
            cls = getSemanticObject().getModel().createSemanticClass(uri);
            setProperty(SWBUR_ClassHold, uri);
        } else
        {
            cls = getSemanticObject().getModel().createSemanticClass(uri);
        }
        return cls;
    }

    public SemanticClass getUserType(String name)
    {

        SemanticClass cls = null;
        if (null != name && !"".equals(name.trim()))
        {
            String uri = getProperty(SWBUR_ClassUserTypeHold + name);
            if (uri == null)
            {
                uri = getId() + SWBUR_ClassUserTypePost + "#" + name;
                cls = getSemanticObject().getModel().createSemanticClass(uri);
                setProperty(SWBUR_ClassUserTypeHold + name, uri);
                userTypes.add(uri.split("#")[1]);
            } else
            {
                cls = getSemanticObject().getModel().createSemanticClass(uri);
            }
        }
        return cls;
    }

    public Iterator<String> getUserTypes()
    {
        return userTypes.iterator();
    }

    public boolean hasUserType(String name)
    {
        return userTypes.contains(name);
    }

    public boolean isExternal()
    {
        return EXTERNAL;
    }

    ExtUserRepInt getBridge()
    {
        return bridge;
    }
}
