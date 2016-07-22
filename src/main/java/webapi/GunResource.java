package webapi;

import dao.GunDao;
import listeners.DbInitListener;
import model.Gun;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("gun")
@Produces(APPLICATION_JSON)
public class GunResource implements JsonRestfulWebResource {

    private GunDao gunDao;

    @Context
    public void init(ServletContext context) {
        gunDao = (GunDao) context.getAttribute(DbInitListener.GUN_DAO);
    }

    @GET
    public Response getAll() {
        final Collection<Gun> guns = gunDao.findAll();
        return guns.size() > 0 ? ok(guns) : noContent();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id) {
        return gunDao.getById(id)
                .map(this::ok)
                .orElse(noContent());
    }
}
