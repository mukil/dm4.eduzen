package de.deepamehta.plugins.eduzen;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

public abstract class AbstractJaxrsReader<T> implements MessageBodyReader<T> {

    private final Class<T> clz;

    protected AbstractJaxrsReader(Class<T> clz) {
        this.clz = clz;
    }

    protected abstract T createInstance(InputStream entityStream) throws Exception;

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType) {
        return type == clz && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
            InputStream entityStream) throws IOException, WebApplicationException {
        try {
            return createInstance(entityStream);
        } catch (Exception e) {
            throw new WebApplicationException(new RuntimeException(
                "Creating a " + clz.getName() + " topic from message body failed", e));
        }
    }
}
