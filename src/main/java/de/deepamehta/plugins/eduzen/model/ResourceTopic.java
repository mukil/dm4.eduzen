package de.deepamehta.plugins.eduzen.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import de.deepamehta.core.model.CompositeValue;
import de.deepamehta.core.model.TopicModel;

public class ResourceTopic extends TopicModel {

    public static final String TYPE = "tub.eduzen.resource";
    public static final String NAME = "tub.eduzen.resource_name";
    public static final String CONTENT = "tub.eduzen.resource_content";
    // 
    public static final String CONTENT_EDGE = "tub.eduzen.content_item"; // identifies associated file and web contents
    //
    public static final String FILE = "dm4.files.file"; // identifies associated file and web contents
    public static final String FILE_PATH = "dm4.files.file_path"; // identifies associated file and web contents
    public static final String URL = "dm4.webbrowser.url"; // identifies associated file and web contents

    /**
     * @param model { name: "name", content: "content" }
     * @throws JSONException
     */
    public ResourceTopic(JSONObject json) throws JSONException {
        super(TYPE);
        // 
        CompositeValue value = new CompositeValue();
        // 
        setCompositeValue(value.put(NAME, json.getString("name")));
        setCompositeValue(value.put(CONTENT, json.getString("content")));
    }

}
