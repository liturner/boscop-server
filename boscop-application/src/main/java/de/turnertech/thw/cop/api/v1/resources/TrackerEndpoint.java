package de.turnertech.thw.cop.api.v1.resources;

import de.turnertech.ows.gml.IFeature;
import de.turnertech.ows.gml.Point;
import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.model.UnitModel;
import de.turnertech.thw.cop.trackers.Tracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@Path("/tracker")
public class TrackerEndpoint {

    @Context
    private UriInfo uriInfo;

    @Context
    private HttpHeaders headers;

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tracker> getAllTrackers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tracker> criteriaQuery = criteriaBuilder.createQuery(Tracker.class);
        criteriaQuery.select(criteriaQuery.from(Tracker.class));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateEntity(@FormParam("opta") String opta, @FormParam("lat") Double lat, @FormParam("lon") Double lon) {

        if(opta == null) {
            // Server Error, because the filter should already have prevented us getting here.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("opta parameter must be present.").build();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tracker> criteriaQuery = criteriaBuilder.createQuery(Tracker.class);
        Root<Tracker> from = criteriaQuery.from(Tracker.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("opta"), opta));
        Tracker existingTracker = entityManager.createQuery(criteriaQuery).getSingleResult();

        if(existingTracker == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("'opta' parameter was not present").build();
        }

        existingTracker.setOpta(opta);
        existingTracker.setLat(lat);
        existingTracker.setLon(lon);
        entityManager.persist(existingTracker);


        // This is the Geo Stuff. Should be decoupled later probably?

        IFeature geoObject = null;
        for(IFeature tracker : UnitModel.INSTANCE.getAll()) {
            if(opta.equals(tracker.getPropertyValue(UnitModel.OPTA_FIELD))) {
                geoObject = tracker;
                break;
            }
        }

        if(geoObject == null) {
            // This can occur after a restart. There is a stored Key, but no active tracker.
            geoObject = UnitModel.INSTANCE.getFeatureType().createInstance();
            geoObject.setPropertyValue(UnitModel.ID_FIELD, opta);
            geoObject.setPropertyValue(UnitModel.OPTA_FIELD, opta);
            geoObject.setPropertyValue(UnitModel.GEOMETRY_FIELD, new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
            UnitModel.INSTANCE.add(geoObject);
        }

        Point trackerLocation = (Point)geoObject.getPropertyValue(UnitModel.GEOMETRY_FIELD);

        if(lat != null) {
            trackerLocation.setY(lat);
            geoObject.setPropertyValue(UnitModel.GEOMETRY_FIELD, trackerLocation);
        }
        if(lon != null) {
            trackerLocation.setX(Double.valueOf(lon));
            geoObject.setPropertyValue(UnitModel.GEOMETRY_FIELD, trackerLocation);
        }

        Logging.LOG.fine(() -> "TrackerSubServlet: " + opta);

        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response createEntity(@FormParam("opta") String opta, @FormParam("lat") Double lat, @FormParam("lon") Double lon) {
        if(lat == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("'lat' parameter was not present").build();
        } else if (lon == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("'lon' parameter was not present").build();
        } else if(opta == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("'opta' parameter was not present").build();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tracker> criteriaQuery = criteriaBuilder.createQuery(Tracker.class);
        Root<Tracker> from = criteriaQuery.from(Tracker.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("opta"), opta));
        Tracker existingTracker = entityManager.createQuery(criteriaQuery).getSingleResult();

        if(existingTracker == null) {
            existingTracker = new Tracker();
        }
        existingTracker.setLat(lat);
        existingTracker.setLon(lon);
        entityManager.persist(existingTracker);

        // This is the Geo Stuff. Should be decoupled later probably?

        IFeature geoObject = null;
        for(IFeature feature : UnitModel.INSTANCE.getAll()) {
            if(opta.equals(feature.getPropertyValue(UnitModel.OPTA_FIELD))) {
                geoObject = feature;
                break;
            }
        }

        if(geoObject == null) {
            geoObject = UnitModel.INSTANCE.getFeatureType().createInstance();
            UnitModel.INSTANCE.add(geoObject);
        }

        geoObject.setPropertyValue(UnitModel.ID_FIELD, opta);
        geoObject.setPropertyValue(UnitModel.OPTA_FIELD, opta);
        geoObject.setPropertyValue(UnitModel.GEOMETRY_FIELD, new Point(lon, lat));

        if(headers.getHeaderString("Referer") == null) {
            return Response.ok().build();
        }
        return Response.seeOther(UriBuilder.fromUri(headers.getHeaderString("Referer")).build()).build();
    }

}
