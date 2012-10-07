package de.deepamehta.plugins.eduzen.service;

import de.deepamehta.core.Topic;
import de.deepamehta.core.ResultSet;
import de.deepamehta.core.model.TopicModel;
import de.deepamehta.core.service.ClientState;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.plugins.eduzen.model.Resource;
import de.deepamehta.plugins.eduzen.model.ResourceTopic;

public interface EduzenService extends PluginService {

  Resource createContent(ResourceTopic topic, ClientState clientState);
  
  // Queries for correctors ...

  ResultSet<Topic> getAllUncommentedApproaches(ClientState clientState);

  String getExcerciseState(long excerciseId, ClientState clientState);

}
