package de.deepamehta.plugins.eduzen;

import java.util.logging.Logger;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;

import de.deepamehta.core.Topic;
import de.deepamehta.core.ResultSet;
import de.deepamehta.core.DeepaMehtaTransaction;
import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.model.TopicModel;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.model.CompositeValue;
import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.service.ClientState;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.core.service.event.PluginServiceArrivedListener;
import de.deepamehta.core.service.event.PluginServiceGoneListener;
import de.deepamehta.plugins.accesscontrol.AccessControlPlugin;
import de.deepamehta.plugins.eduzen.service.EduzenService;
import de.deepamehta.plugins.eduzen.model.Resource;
import de.deepamehta.plugins.eduzen.model.ResourceTopic;
import de.deepamehta.plugins.accesscontrol.service.AccessControlService;
import de.deepamehta.plugins.workspaces.service.WorkspacesService;



@Path("/eduzen")
@Consumes("application/json")
@Produces("application/json")
public class EduzenPlugin extends PluginActivator implements EduzenService, PluginServiceArrivedListener, PluginServiceGoneListener {

    private Logger log = Logger.getLogger(getClass().getName());
    private AccessControlService acl;
    private WorkspacesService ws;

    // Approach/ Excercise States (double check: URIs should be as in "tub.eduzen.comment")
    private static final String UNDECIDED = "tub.eduzen.undecided";
    private static final String CORRECT = "tub.eduzen.correct";
    private static final String WRONG = "tub.eduzen.wrong";
    // Excercise Text States (virtual for now)
    // private static final String AWAITING_COMMENT = "tub.eduzen.awaiting_comment";
    private static final String IN_PROGRESS = "tub.eduzen.in_progress";
    private static final String SOLVED = "tub.eduzen.solved";
    private static final String UNSOLVED = "tub.eduzen.unsolved";

    public EduzenPlugin() {
        log.info(".stdOut(\"Hello Zen-Master!\")");
    }

    /**
    * Creates a new <code>Content</code> instance based on the domain specific
    * REST call with a alternate JSON topic representation.
    */
    @POST
    @Path("/resource/create")
    @Override
    public Resource createContent(ResourceTopic topic, @HeaderParam("Cookie") ClientState clientState) {
        log.info("creating \"Resource\" " + topic);
        try {
            return new Resource(topic, dms, clientState);
        } catch (Exception e) {
            throw new WebApplicationException(new RuntimeException("something went wrong", e));
        }
    }

    /**
    * Experimental Server Method that shall do the trick for now.
    */

    @GET
    @Path("/comments/all")
    @Override
    public ResultSet<Topic> getAllUncommentedApproaches(@HeaderParam("Cookie") ClientState clientState) {
        // DeepaMehtaTransaction tx = dms.beginTx();
        Topic user = acl.getUsername();
        log.info("Retrieving all approaches by users in \"tub.eduzen.workspace_users\" for " + acl.getUsername());
        Topic editorsWs = dms.getTopic("uri", new SimpleValue("tub.eduzen.workspace_editors"), true, null);
        Topic membersWs = dms.getTopic("uri", new SimpleValue("tub.eduzen.workspace_users"), true, null);
        if (user == null) {
            throw new WebApplicationException(401);
        }
        if (!ws.isAssignedToWorkspace(user, editorsWs.getId())) {
            throw new WebApplicationException(401);
        }
        ResultSet<Topic> topics = dms.getTopics("tub.eduzen.excercise", false, 0, null);
        Set<Topic> resultset = new LinkedHashSet<Topic>();
        Iterator<Topic> results = topics.iterator();
        while ( results.hasNext() ) {
            Topic t = results.next();
            Topic author = t.getRelatedTopic("tub.eduzen.author", "dm4.core.default", 
              "dm4.core.default", "tub.eduzen.identity", false, false, null);
            Topic approached = t.getRelatedTopic("dm4.core.composition", "dm4.core.whole", 
              "dm4.core.part", "tub.eduzen.approach", false, true, null);
            if (approached != null) {
                if (author != null) {
                    log.info("adding excercise (" + t.getId() + ") approached by user " + author.getSimpleValue());
                    Topic commented = t.getRelatedTopic("dm4.core.composition", "dm4.core.whole", 
                        "dm4.core.part", "tub.eduzen.comment", false, true, null);
                    if (commented == null) {
                        // in this methods we are just interested in un-commented approaches
                        resultset.add(dms.getTopic(t.getId(), true, null));
                    }
                } else {
                    log.warning("Inconsistency discovered: no author set for ("+ approached.getId() + ")!");
                }
            } else {
                log.warning("Inconsistency discovered: Excercise (" + t.getId() + ") exists but no approach for it!");
            }
        }
        return new ResultSet<Topic>(resultset.size(), resultset);
    }

    /**
    * Experimental Server Method that shall do the trick for now.
    * Input: Excercise, Output: State of Excercise (Accomplished, In Progress)
    */

    @GET
    @Path("/state/excercise/{excerciseId}")
    @Override
    public String getExcerciseState(@PathParam("excerciseId") long excerciseId, 
                                    @HeaderParam("Cookie") ClientState clientState) {
        Topic user = acl.getUsername();
        String status = "{\"excercise_state\": \"" + UNDECIDED + "\", \"quest_state\": \"" + UNSOLVED + "\"}";
        log.info("Retrieving state of excercise ("+ excerciseId +") for user " + acl.getUsername());
        if (user == null) throw new WebApplicationException(401);
        Topic excercise = dms.getTopic(excerciseId, true, null);
        List<TopicModel> approaches = excercise.getCompositeValue().getTopics("tub.eduzen.approach");
        int bunchOfTrues = 0;
        int bunchOfFalses = 0;
        Iterator<TopicModel> results = approaches.iterator();
        log.info("checking excercise-state for " + approaches.size() + " approaches");
        while (results.hasNext()) {
            TopicModel approachModel = results.next();
            Topic comment = dms.getTopic(approachModel.getId(), true, null); // complicated?
            try {
                Topic approach = dms.getTopic(approachModel.getId(), true, null);
                String stateOfApproach = checkStateOfApproach(approach);
                // try to determine excercise-state as of this current approach
                if (stateOfApproach == CORRECT && approaches.size() == 1) {
                    // approach to excercise seems to be correct with the first approach, level up
                    status = "{\"excercise_state\":\"" + CORRECT + "\",\"quest_state\":\"" + SOLVED + "\"}";
                    return status;
                } else if (stateOfApproach == CORRECT && approaches.size() > 1) {
                    // approach to excercise seems to be correct but with not with the first approach
                    // user can now try a to take on a new excercise
                    status = "{\"excercise_state\":\"" + CORRECT + "\",\"quest_state\":\"" + IN_PROGRESS + "\"}";
                    return status;
                } else if (stateOfApproach == UNDECIDED) {
                    // FIXME: excecise-state for many approaches needs to be discussed, e.g.
                    // if n-undecided or n-wrong
                    status = "{\"excercise_state\":\"" + UNDECIDED
                        + "\",\"quest_state\":\"" + IN_PROGRESS + "\"}";
                } else if (stateOfApproach == WRONG) {
                    status = "{\"excercise_state\":\"" + WRONG + "\",\"quest_state\":\"" + IN_PROGRESS + "\"}";
                }
                // a successful comment count for determining excercise-state
            } catch (ClassCastException ex) {
                log.warning("some comment "+ comment.getId() +" has not set their correctnes flag as boolean.. "
                    + ex.toString());
            }
        }
        return status;
    }

    /** returns either "tub.eduzen.undecided", "tub.eduzen.correct" or "tub.eduzen.wrong" */
    private String checkStateOfApproach(Topic approach) {
        // ResultSet<RelatedTopic> getRelatedTopics(String assocTypeUri, String myRoleTypeUri, String othersRoleTypeUri,
                                  // String othersTopicTypeUri, boolean fetchComposite, boolean fetchRelatingComposite,
                                  // int maxResultSize, ClientState clientState);
        String status = UNDECIDED;
        ResultSet<RelatedTopic> comments = approach.getRelatedTopics("dm4.core.composition", "dm4.core.whole", 
            "dm4.core.part", "tub.eduzen.comment", false, true, 0, null); // seems to be fetchRelatingComposite is broken.
        int bunchOfTrues = 0;
        int bunchOfFalses = 0;
        Iterator<RelatedTopic> results = comments.iterator();
        while (results.hasNext()) {
            RelatedTopic relatedComment = results.next();
            Topic comment = dms.getTopic(relatedComment.getId(), true, null);
            try {
                boolean commentValue = comment.getCompositeValue().getTopic("tub.eduzen.comment_correct")
                    .getSimpleValue().booleanValue(); // in JSON debug output syntax there is written value="false"
                if (commentValue) {
                    bunchOfTrues++;
                } else {
                    bunchOfFalses++;
                }
                // a successful comment count for determining excercise-state
            } catch (ClassCastException ex) {
                log.warning("some comment "+ comment.getId() +" has not set their correctnes flag as boolean.. "
                    + ex.toString());
            }
        }
        // determine state of approach
        if (bunchOfFalses == bunchOfTrues) {
            status = UNDECIDED;
        } else if (bunchOfFalses > bunchOfTrues) {
            status = WRONG;
        } else if (bunchOfFalses < bunchOfTrues) {
            status = CORRECT;
        }
        log.info("approach "+ approach.getId() +" was commented as being \""+ status +"\" ("
          + bunchOfTrues +" times TRUE, " + bunchOfFalses +" times FALSE");
        return status;
    }

    /** --- Input: Excercise Text, Output: State of Excercise Text (Accomplished, In Progress) --- */

    /** --- Implementing PluginService Interfaces to consume AccessControlService --- */

    @Override
    public void pluginServiceArrived(PluginService service) {
        if (service instanceof AccessControlService) {
            acl = (AccessControlService) service;
        } else if (service instanceof WorkspacesService) {
            ws = (WorkspacesService) service;
        }
    }

    @Override
    public void pluginServiceGone(PluginService service) {
        if (service == acl) {
            acl = null;
        } else if (service == ws) {
            ws = null;
        }
    }

}
