package de.twenty11.skysail.client.dbviewer.wicket;

import java.util.List;

import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;

public class RestletUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(RestletUtils.class);

    public static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i).getClass().equals(converterClass)) {
                logger.info("replacing converter {} with {}", converterClass, newConverter);
                converters.set(i, newConverter);
                break;
            }
            if (converters.get(i).getClass().equals(MyLocalJacksonCustomConverter.class)) {
                logger.info("replacing converter {} with {}", converters.get(i), newConverter);
                converters.set(i, newConverter);
                break;
            }
        }

    }

}
