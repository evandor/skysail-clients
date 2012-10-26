package de.twenty11.skysail.client.dbviewer.wicket.connection;

import java.io.IOException;

import org.codehaus.jackson.type.TypeReference;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.representation.Representation;
import org.restlet.resource.Resource;

import de.twenty11.skysail.common.responses.Response;

public class MyLocalJacksonCustomConverter<T> extends JacksonConverter {

    private TypeReference<Response<T>> type;

    public MyLocalJacksonCustomConverter(TypeReference<Response<T>> typeReference) {
        this.type = typeReference;
    }

    @Override
    public <T> T toObject(Representation source, Class<T> target, Resource resource) throws IOException {
        return getObjectMapper().readValue(source.getStream(), type);

    }

}
