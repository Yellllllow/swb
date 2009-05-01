package org.semanticwb.codegen;


import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import static org.junit.Assert.*;

/**
 *
 * @author serch
 */
public class CodeGeneratorCatalogs {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.setUseDB(false);
        SWBPlatform.createInstance(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void generateRepository()
    {
        try
        {
            String path = getClass().getResource("/").getPath().replaceAll("%20", " ");
            File dir = new File(path + "../../src");
            String sPakage = "org.semanticwb.model.catalogs";
            CodeGenerator codeGeneration = new CodeGenerator();
            codeGeneration.generateCode("swbc", false, dir);
            System.out.println("Generación de clases completa");
        }
        catch (CodeGeneratorException cge)
        {
            fail(cge.getMessage());
        }
    }

}

