package de.deepamehta.plugins.eduzen;

import java.util.logging.Logger;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
    try {
      Topic user = acl.getUsername();
      log.info("Retrieving all approaches by users in \"tub.eduzen.workspace_users\" for " + acl.getUsername());
      Topic editorsWs = dms.getTopic("uri", new SimpleValue("tub.eduzen.workspace_editors"), true, null);
      Topic membersWs = dms.getTopic("uri", new SimpleValue("tub.eduzen.workspace_users"), true, null);
      if (user == null) {
        throw new RuntimeException("Querying user(?) is not authenticated. Returning 401.");
      }
      if (!ws.isAssignedToWorkspace(user, editorsWs.getId())) {
        throw new RuntimeException("Querying user is not member of the editors workspace. Returning 401.");
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
              resultset.add(dms.getTopic(t.getId(), true, null));
            }
          } else {
            // log.info("Excercise: (" + t.getId() + ") was approached, but not submitted by any TUB user.. ");
          }
        } else {
          log.warning("Inconsistency discovered: Excercise (" + t.getId() + ") exists but no approach for it ... ");
        }
      }
      // tx.success();
      return new ResultSet<Topic>(resultset.size(), resultset);
    } catch (Exception e) {
      log.warning("ROLLBACK!");
      throw new WebApplicationException(new RuntimeException("something went wrong", e));
    } finally {
      // tx.finish();
    }
  }

  /** --- Implementing PluginService Interfaces to consume AccessControlService --- **/

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
