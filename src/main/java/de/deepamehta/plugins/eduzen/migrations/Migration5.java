package de.deepamehta.plugins.eduzen.migrations;

import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.ResultSet;
import de.deepamehta.core.Topic;
import de.deepamehta.core.model.AssociationModel;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.model.TopicRoleModel;
import de.deepamehta.core.service.Directives;
import de.deepamehta.core.service.Migration;
import java.util.logging.Logger;



public class Migration5 extends Migration {

    private Logger logger = Logger.getLogger(getClass().getName());

    private String DEFAULT_URI = "de.workspaces.deepamehta";

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public void run() {

        // rename default Workspace
        Topic defaultWorkspace = dms.getTopic("uri", new SimpleValue(DEFAULT_URI), false, null);
        // updating name of the deepamehta default workspace
        defaultWorkspace.getCompositeValue().set("dm4.workspaces.name", "EduZEN Editors", null, new Directives());
        // update all associatons to this workspace according to new workspace-assignment in 4.0.12
        /** ResultSet<RelatedTopic> topics = defaultWorkspace.getRelatedTopics("dm4.workspaces.workspace_context",
            "dm4.core.default", null, null, false, false, 0, null);
        for (RelatedTopic topic : topics) {
            // probably irrelevant now: topic.getAssociation("dm4.workspaces.workspace_context", "dm4.core.default");
            topic.getRelatingAssociation().update(new AssociationModel("dm4.core.aggregation",
                new TopicRoleModel(defaultWorkspace.getId(), "dm4.core.part"),
                new TopicRoleModel(topic.getId(), "dm4.core.whole")
            ), null, new Directives());
        } **/

        // delete deprecated association type and two deprecated topic instances
        // deprecated association type is well, deprecated as of 4.0.14
        /** dms.getAssociationType("dm4.workspaces.workspace_context", null).delete(new Directives());
        dms.getTopic("uri", new SimpleValue("dm4.workspaces.workspace_topic"), false, null).delete(new Directives());
        dms.getTopic("uri", new SimpleValue("dm4.workspaces.workspace_type"), false, null).delete(new Directives()); **/
    }

}
