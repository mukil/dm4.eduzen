package de.deepamehta.plugins.eduzen;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.model.TopicModel;
import de.deepamehta.core.service.ClientState;
import de.deepamehta.plugins.eduzen.service.EduzenService;
import de.deepamehta.plugins.eduzen.model.Content;
import de.deepamehta.plugins.eduzen.model.ContentTopic;

@Path("/eduzen")
@Produces("application/json")
public class EduzenPlugin extends PluginActivator implements EduzenService {

    private Logger log = Logger.getLogger(getClass().getName());

    public EduzenPlugin() {
        log.info(".stdOut(\"Hello Zen-Master!\")");
    }

    /**
     * Creates a new <code>Content</code> instance based on the domain specific
     * REST call with a alternate JSON topic representation.
     */
    @POST
    @Path("/content/create")
    @Override
    public Content createContent(ContentTopic topic, @HeaderParam("Cookie") ClientState clientState) {
        log.info("create Content " + topic);
        try {
            return new Content(topic, dms, clientState);
        } catch (Exception e) {
            throw new WebApplicationException(new RuntimeException("something went wrong", e));
        }
    }

}
