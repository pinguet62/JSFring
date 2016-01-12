package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.RightWebservice.PATH;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.ws.dto.RightDto;

@Path(PATH)
public final class RightWebservice {

    public static final String PATH = "/right";

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private RightService rightService;

    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public RightDto get(@PathParam("code") String code) {
        Right right = rightService.get(code);
        if (right == null)
            return null;
        return conversionService.convert(right, RightDto.class);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RightDto> list() {
        List<Right> pojos = rightService.getAll();
        return (List<RightDto>) conversionService.convert(pojos, collection(List.class, valueOf(Right.class)),
                collection(List.class, valueOf(RightDto.class)));
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(RightDto rightDto) {
        Right right = conversionService.convert(rightDto, Right.class);
        rightService.update(right);
    }

}