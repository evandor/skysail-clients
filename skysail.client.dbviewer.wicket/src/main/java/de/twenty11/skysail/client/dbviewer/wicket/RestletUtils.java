package de.twenty11.skysail.client.dbviewer.wicket;

import java.util.List;

import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;

import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;

public class RestletUtils {

    public static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i).getClass().equals(converterClass)) {
                converters.set(i, newConverter);
                break;
            }
            if (converters.get(i).getClass().equals(MyLocalJacksonCustomConverter.class)) {
                converters.set(i, newConverter);
                break;
            }
        }

    }

}
