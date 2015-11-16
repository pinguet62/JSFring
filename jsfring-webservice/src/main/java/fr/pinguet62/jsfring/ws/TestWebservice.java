package fr.pinguet62.jsfring.ws;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fr.pinguet62.jsfring.dao.UserDao;

@Path("/hello")
public class TestWebservice {

    @Inject
    private UserDao userDao;

    @GET
    @Path("/{param}")
    public String getMsg(@PathParam("param") String msg) {
        return "Jersey say : " + userDao.getAll().get(0).getEmail();
    }

}