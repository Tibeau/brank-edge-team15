package fact.it.brankedge.controller;

import fact.it.brankedge.model.Developer;
import fact.it.brankedge.model.FilledGameDev;
import fact.it.brankedge.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilledGameDevController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    @Value("${developerservice.baseurl}")
    private String developerServiceBaseUrl;


    @GetMapping("releases/developer/name/{name}")
    public List<FilledGameDev> getDevelopersByName(@PathVariable String name) {

        List<FilledGameDev> returnList = new ArrayList();

        ResponseEntity<List<Developer>> responseEntityDevelopers =
                restTemplate.exchange("http://" + developerServiceBaseUrl + "/developer/name/{name}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {
                        }, name);

        List<Developer> developers = responseEntityDevelopers.getBody();

        for (Developer developer :
                developers) {
            ResponseEntity<List<Game>> responseEntityGames =
                    restTemplate.exchange("http://" + gameServiceBaseUrl + "/games/{name}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                            }, name);

            returnList.add(new FilledGameDev(developer, responseEntityGames.getBody()));
        }

        return returnList;
    }


    @GetMapping("releases/developer/{developerName}")
    public FilledGameDev getReleasebyDevName(@PathVariable String developerName) {
        Developer developer1 =
                restTemplate.getForObject("http://" + developerServiceBaseUrl + "/developers/{name}",
                        Developer.class, developerName);

        ResponseEntity<List<Game>> responseEntityGame =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/games/{name}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                        }, developerName);

        return new FilledGameDev(developer1, responseEntityGame.getBody());
    }

    @DeleteMapping("/releases/games/{id}")
    public void deleteGame(@PathVariable String id){
        restTemplate.delete("http://" + gameServiceBaseUrl + "/games/{id}" );
    }
}
