import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
public interface FacadeInterface {
    
    @GET
    @Path("/getstudent")
    @Produces({ "application/json" })
    public Student getStudent(
        @QueryParam("firstname") String firstName, 
        @QueryParam("lastname") String lastName
    );

    @GET
    @Path("/getrecord")
    @Produces({ "application/json" })
    public Record getRecord(@QueryParam("ine") String ine);
}
