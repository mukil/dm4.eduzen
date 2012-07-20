package de.deepamehta.plugins.eduzen.provider;

import java.io.InputStream;

import javax.ws.rs.ext.Provider;

import org.codehaus.jettison.json.JSONObject;

import de.deepamehta.core.util.JavaUtils;
import de.deepamehta.plugins.eduzen.AbstractJaxrsReader;
import de.deepamehta.plugins.eduzen.model.ContentTopic;

/**
 * Creates a <code>ContentTopic</code> from JSON.
 * 
 * @see ContentTopic#ContenTopic(JSONObject)
 */
@Provider
public class ContentTopicProvider extends AbstractJaxrsReader<ContentTopic> {

    public ContentTopicProvider() {
        super(ContentTopic.class);
    }

    @Override
    protected ContentTopic createInstance(InputStream entityStream) throws Exception {
        return new ContentTopic(new JSONObject(JavaUtils.readText(entityStream)));
    }

}
