import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class TestClient {
    public static void main(String[] args) {
        
        final String path = "http://localhost:8080/";

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(path));
        FacadeInterface proxy = target.proxy(FacadeInterface.class);

        // TEST STUDENT
        Student etu;

        etu = proxy.getStudent("Alain", "Tchana");
        System.out.println(
            "INE de " 
            + etu.getFirstname() 
            + " " 
            + etu.getLastname() 
            + " : " 
            + etu.getIne()
        );
        
        etu = proxy.getStudent("Daniel", "Hagimont");
        System.out.println(
            "INE de " 
            + etu.getFirstname() 
            + " " 
            + etu.getLastname() 
            + " : " 
            + etu.getIne()
        );

        // TEST RECORD
        Record record;
        String ine;
        
        ine = "1111111111";
        record = proxy.getRecord(ine);
        System.out.println(
            "Note en Intergiciels de l'étudiant d'INE " 
            + ine 
            + " : "
            + record.getMiddleware()
        );
        
        ine = "5555555555";
        record = proxy.getRecord(ine);
        System.out.println(
            "Note en Intergiciels de l'étudiant d'INE " 
            + ine 
            + " : "
            + record.getMiddleware()
        );
    }
}
