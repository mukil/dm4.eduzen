package de.deepamehta.plugins.eduzen.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import de.deepamehta.core.model.CompositeValue;
import de.deepamehta.core.model.TopicModel;

public class ContentTopic extends TopicModel {

    public static final String TYPE = "dm4.eduzen.content";
    public static final String NAME = "dm4.eduzen.content.name";
    public static final String CONTENT = "dm4.eduzen.content.content";
    // 
    public static final String CONTENT_EDGE = "dm4.eduzen.content_item"; // identifies associated file and web contents
    //
    public static final String FILE = "dm4.files.file"; // identifies associated file and web contents
    public static final String FILE_PATH = "dm4.files.file_path"; // identifies associated file and web contents
    public static final String URL = "dm4.webbrowser.url"; // identifies associated file and web contents

    /**
     * @param model { name: "name", content: "content" }
     * @throws JSONException
     */
    public ContentTopic(JSONObject json) throws JSONException {
        super(TYPE);
        // 
        CompositeValue value = new CompositeValue();
        // 
        setCompositeValue(value.put(NAME, json.getString("name")));
        setCompositeValue(value.put(CONTENT, json.getString("content")));
    }

}
