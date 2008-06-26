/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.calc;

import org.semanticwb.openoffice.*;
import com.sun.star.beans.PropertyValue;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XEnumeration;
import com.sun.star.container.XEnumerationAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.semanticwb.openoffice.util.FileUtil.getPathURL;

/**
 * 
 * @author victor.lorenzana
 */
public class WB4CalcApplication extends OfficeApplication
{
    private static final String DESKTOP_NOT_FOUND = "The desktop was not found";
    private static final String DESKTOP_PATH = "com.sun.star.frame.Desktop";
    private static final String DOCUMENT_CAN_NOT_BE_OPEN = "The document can not be open";
    private static final String SPREADSHEETDOCUMENT_PATH = "com.sun.star.sheet.SpreadsheetDocument";
    private static final String TARGET_BLANK = "_blank";
    
    private final XComponentContext m_xContext;
    /**
     * Create a representation of a Calc Application as Office Application
     * @param m_xContext XComponentContext that represent a Calc Application
     * @see XComponentContext
     */
    public WB4CalcApplication(XComponentContext m_xContext)
    {
        this.m_xContext = m_xContext;
    }

    /**
     * Gets the Calc documents opened
     * @return List of OfficeDocument that represent the documents
     * @throws org.semanticwb.openoffice.WBException In case that the Desktop of Office can not be used
     */
    public List<OfficeDocument> getDocuments() throws WBException
    {
        ArrayList<OfficeDocument> documents = new ArrayList<OfficeDocument>();
        XMultiComponentFactory serviceManager = m_xContext.getServiceManager();
        try
        {
            Object desktop = serviceManager.createInstanceWithContext(
                     DESKTOP_PATH,m_xContext);
            XDesktop xdesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, desktop);
            XEnumerationAccess access = xdesktop.getComponents();
            XEnumeration enumeration = access.createEnumeration();
            while (enumeration.hasMoreElements())
            {
                try
                {
                    Object nextElement = enumeration.nextElement();
                    XComponent document = (XComponent) UnoRuntime.queryInterface(XComponent.class, nextElement);
                    XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(
                            XServiceInfo.class, document);
                    if (xServiceInfo.supportsService(SPREADSHEETDOCUMENT_PATH))
                    {
                        WB4Calc calDocument = new WB4Calc(document);
                        documents.add(calDocument);
                    }
                }
                catch (NoSuchElementException nse)
                {
                    ErrorLog.log(nse);
                }
            }
        }
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException( DESKTOP_NOT_FOUND,e);
        }
        return documents;
    }

    /**
     * Open a Document in a certain path
     * @param file A File path of a Open Office document to Open
     * @return OfficeDocument opened
     * @throws org.wb.WBException If the document can not be opened
     */
    public final OfficeDocument open(File file) throws WBException
    {
        // Obtener la factoria de servicios de OpenOffice   
        XMultiComponentFactory xMCF = m_xContext.getServiceManager();

        try
        {
            // Obtener la ventana principal (Desktop) de OpenOffice   
            Object oRawDesktop = xMCF.createInstanceWithContext( DESKTOP_PATH,m_xContext);
            XDesktop oDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oRawDesktop);

            // Obtener interfaz XComponentLoader del XDesktop   
            XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, oDesktop);
            PropertyValue[] loadProps = new PropertyValue[0];
            String url =  getPathURL(file);
            xCompLoader.loadComponentFromURL(url,TARGET_BLANK, 0, loadProps);
            return new WB4Calc(m_xContext);
        }        
        catch (com.sun.star.uno.Exception e)
        {
            throw new WBOfficeException( DOCUMENT_CAN_NOT_BE_OPEN,e);
        }

    }
}
