package de.twenty11.skysail.client.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * this class initializes the restletUrl which is needed to communicate with the skysail server
 * 
 * @author carsten
 *
 */
public class SkysailClientApplication {

	//http://localhost:8099/rest/osgi/services/
	private static String restletUrlScheme = "http";
	private static String restletUrlUserInfo = null;
	private static String restletUrlHost = "localhost";
	private static int    restletUrlPort = 8099;
	private static String restletUrlPath = "/rest/";
    
    //private static Logger logger = LoggerFactory.getLogger(SkysailClientApplication.class);

    static {
        String confDir = System.getProperty("skysail.confDir", "./conf");
        //logger.info("skysail.confDir system property resolved to " + confDir + " (default './conf')");
        String path = "";
        
        try {
            Properties props = new Properties();
            InputStreamReader reader;
            path = confDir + File.separator
                + "server" + File.separator + "restlet.properties";
            
            //logger.info ("trying to open file '"+path+"'");
            reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(path)), "UTF-8");
            props.load(reader);
            reader.close();
            restletUrlScheme = props.getProperty("skysail.server.restlet.protocol");
            restletUrlHost = props.getProperty("skysail.server.restlet.host");
            restletUrlPort = Integer.parseInt(props.getProperty("skysail.server.restlet.port"));
            restletUrlPath = props.getProperty("skysail.server.restlet.path");
            
        } catch (Exception e) {
//            logger.error ("Could not open file '"+path+"', expection is " + e.getMessage());
//            logger.error("current path is " + new File(".").getAbsolutePath());
        }
    }

    public static String getRestletUrl(String addedPath, String query, String fragment) {
		URI uri;
		try {
			uri = new URI(
					restletUrlScheme,
					restletUrlUserInfo,
					restletUrlHost, 
					restletUrlPort, 
					restletUrlPath + addedPath,
					query,
					fragment);
	        return uri.toString();
		} catch (URISyntaxException e) {
			throw new RuntimeException("URI invalid", e);
		}
    }

    public static String getRestletUrl(String addedPath, String query) {
    	return getRestletUrl(addedPath, query, null);
    }

    public static String getRestletUrl(String addedPath) {
    	return getRestletUrl(addedPath, null, null);
    }

}
