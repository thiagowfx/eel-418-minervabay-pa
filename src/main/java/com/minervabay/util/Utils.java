package com.minervabay.util;

import java.io.File;
import javax.servlet.ServletContext;

/**
 *
 * @author thiago
 */
public class Utils {
    
    private static void assertDirectoryExists(String d) {
        File file = new File(d);
        file.mkdir();
    }

    public static String getBookPath(ServletContext servletContext, String arquivo) {
        String filesdir = 
                servletContext.getRealPath("/WEB-INF")
                + File.separator
                + servletContext.getInitParameter("FILES_PATH");
        assertDirectoryExists(filesdir);
        return filesdir + File.separator + arquivo;
    }
}
