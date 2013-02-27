package de.deepamehta.plugins.eduzen.migrations;

import de.deepamehta.core.TopicType;
import de.deepamehta.core.model.AssociationDefinitionModel;
import de.deepamehta.core.service.Migration;



public class Migration3 extends Migration {

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public void run() {
        // hide "Person"-topic from create menu, now needed for start test-drive the dm4-mail plugin
        // dms.getTopicType("dm4.contacts.person", null).getViewConfig()
            // .addSetting("dm4.webclient.view_config", "dm4.webclient.add_to_create_menu", false);
        dms.getTopicType("dm4.contacts.person", null).getViewConfig()
            .addSetting("dm4.webclient.view_config", "dm4.webclient.is_searchable_unit", false);
        // hide "Institution"-topic from create menu
        dms.getTopicType("dm4.contacts.institution", null).getViewConfig()
            .addSetting("dm4.webclient.view_config", "dm4.webclient.add_to_create_menu", false);
    }

}
