/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;
        

/**
 *
 * @author victor.lorenzana
 */
public class WordFileFilter extends FileFilter
{
    public boolean accept(File f)
    {
        boolean result=true;
        if(!f.isDirectory())
        {
            result=f.getName().endsWith(".dot");
        }
        return result;        
    }
    public String getDescription()
    {
        return "Open Document Text|.odt";
    }
}
