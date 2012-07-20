package de.deepamehta.plugins.eduzen.service;

import de.deepamehta.core.service.ClientState;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.plugins.eduzen.model.Content;
import de.deepamehta.plugins.eduzen.model.ContentTopic;

public interface EduzenService extends PluginService {

    Content createContent(ContentTopic topic, ClientState clientState);

}
