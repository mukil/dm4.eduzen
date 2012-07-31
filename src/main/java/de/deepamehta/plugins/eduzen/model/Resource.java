package de.deepamehta.plugins.eduzen.model;

import static de.deepamehta.plugins.eduzen.model.ResourceTopic.*;

import org.codehaus.jettison.json.JSONObject;

import de.deepamehta.core.DeepaMehtaTransaction;
import de.deepamehta.core.JSONEnabled;
import de.deepamehta.core.Topic;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.model.TopicModel;
import de.deepamehta.core.service.ClientState;
import de.deepamehta.core.service.DeepaMehtaService;

/**
 * A domain model class that wraps the underlying <code>Topic</code>.
 */
public class Resource implements JSONEnabled {

    private final Topic topic;
    private final DeepaMehtaService dms;

    /**
     * Loads an existing <code>Content</code> topic.
     */
    public Resource(long id, DeepaMehtaService dms, ClientState clientState) {
        topic = dms.getTopic(id, true, clientState);
        this.dms = dms;
    }

    /**
     * Creates a new <code>Content</code> topic from <code>ContentTopic</code> model.
     */
    public Resource(ResourceTopic model, DeepaMehtaService dms, ClientState clientState) {
        topic = dms.createTopic(model, clientState);
        this.dms = dms;
    }

    @Override
    public JSONObject toJSON() {
        return topic.toJSON();
    }
    
    // ------------------------------ simplified aggregated 1to1 composite access

    public String getFile() {
        return "null"; // getFileTopic().getCompositeValue().getTopic(FILE_PATH);
    }

    public String getURL() {
        return "null"; // getContentURL().getCompositeValue().getTopic(WEB_RESOURCE);
    }

    // ------------------------------ simplified composite access

    public String getContent() {
        return getContentTopic().getSimpleValue().toString(); // isHtml.. simpleValue?
    }

    public String getName() {
        return getNameTopic().getSimpleValue().toString();
    }

    // ------------------------------ private helper
    
    
    private TopicModel getContentURL() {
        // ### content_items = dms.getRelatedTopics(CONTENT_EDGE, WEB_RESOURCE);
        return topic.getCompositeValue().getTopic(URL);
    }
    
    
    private TopicModel getFileTopic() {
        // ### content_items = dms.getRelatedTopics(CONTENT_EDGE, FILE);
        return topic.getCompositeValue().getTopic(FILE);
    }

    private TopicModel getContentTopic() {
        return topic.getCompositeValue().getTopic(CONTENT);
    }

    private TopicModel getNameTopic() {
        return topic.getCompositeValue().getTopic(NAME);
    }

}
