package de.deepamehta.plugins.eduzen.migrations;

import de.deepamehta.core.Topic;
import de.deepamehta.core.TopicType;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.service.Migration;



public class Migration4 extends Migration {

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public void run() {

        /** This is obsolete when starting with 4.0.14 */
        // set new role URIs
        /* Topic creatorRole = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.role_creator"), false, null);
        creatorRole.setUri("dm4.accesscontrol.user_role.creator");
        Topic ownerRole = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.role_owner"), false, null);
        ownerRole.setUri("dm4.accesscontrol.user_role.owner");
        Topic memberRole = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.role_member"), false, null);
        memberRole.setUri("dm4.accesscontrol.user_role.member");
        Topic userRole = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.role_user"), false, null);
        userRole.setUri("dm4.accesscontrol.user_role.user");
        Topic everyoneRole = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.role_everyone"), false, null);
        everyoneRole.setUri("dm4.accesscontrol.user_role.everyone");

        // set new operation URIs
        Topic writeOperation = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.operation_write"), false, null);
        writeOperation.setUri("dm4.accesscontrol.operation.write");
        Topic createOperation = dms.getTopic("uri", new SimpleValue("dm4.accesscontrol.operation_create"), false, null);
        createOperation.setUri("dm4.accesscontrol.operation.create");

        // update topictype
        TopicType roleType = dms.getTopicType("dm4.accesscontrol.role", null);
        roleType.setUri("dm4.accesscontrol.user_role");
        roleType.setSimpleValue("User Role"); **/
    }

}
