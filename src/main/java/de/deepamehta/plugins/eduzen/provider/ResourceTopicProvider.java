package de.deepamehta.plugins.eduzen.provider;

import java.io.InputStream;

import javax.ws.rs.ext.Provider;

import org.codehaus.jettison.json.JSONObject;

import de.deepamehta.core.util.JavaUtils;
import de.deepamehta.plugins.eduzen.AbstractJaxrsReader;
import de.deepamehta.plugins.eduzen.model.ResourceTopic;

/**
 * Creates a <code>ResourceTopic</code> from JSON.
 * 
 * @see ResourceTopic#ResourceTopic(JSONObject)
 */
@Provider
public class ResourceTopicProvider extends AbstractJaxrsReader<ResourceTopic> {

    public ResourceTopicProvider() {
        super(ResourceTopic.class);
    }

    @Override
    protected ResourceTopic createInstance(InputStream entityStream) throws Exception {
        return new ResourceTopic(new JSONObject(JavaUtils.readText(entityStream)));
    }

}
