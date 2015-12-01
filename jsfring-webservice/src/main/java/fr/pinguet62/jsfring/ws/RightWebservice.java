package fr.pinguet62.jsfring.ws;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.ws.dto.RightDto;

@Path("/right")
public final class RightWebservice {

    @Inject
    private RightService rightService;

    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public RightDto get(@PathParam("code") String code) {
        Right right = rightService.get(code);
        if (right == null)
            return null;
        return new RightDto(right);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RightDto> list() {
        return rightService.getAll().stream().map(RightDto::new).collect(toList());
    }

}