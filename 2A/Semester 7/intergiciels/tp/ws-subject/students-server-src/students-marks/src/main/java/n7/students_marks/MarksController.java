package n7.students_marks;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MarksController {
    
    /** URL où est hébergé le service students-server de Hagimont. */
    final String path = "http://sd-160040.dedibox.fr:8080/students-server/";

    @GetMapping("/getmark")
    public String getMark(
        @RequestParam("firstname") String firstName,
        @RequestParam("lastname") String lastName,
        @RequestParam("lecture") String lecture
    ) {
        try {
            RestClient client = RestClient.create();
            Student etu = client.get().uri(path + "getstudent?firstname=" + firstName + "&lastname=" + lastName).retrieve().body(new ParameterizedTypeReference<>() {});
            String ine = etu.getIne();
            Record record = client.get().uri(path + "getrecord?ine=" + ine).retrieve().body(new ParameterizedTypeReference<>() {});

            switch (lecture) {
                case "mathematics":
                    return record.getMathematics();
                
                case "middleware":
                    return record.getMiddleware();
                
                case "networks":
                    return record.getNetworks();

                case "systems":
                    return record.getSystems();

                case "architecture":
                    return record.getArchitecture();

                case "programming":
                    return record.getProgramming();

                default:
                    return "Lecture doesn't exists.";
            }
        } catch (Exception e) {
            throw new RuntimeException("Oulaaa, petit problème dans le MarksController...");
        }
    }
}
