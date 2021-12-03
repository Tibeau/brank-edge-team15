package fact.it.brankedge.controller;

import fact.it.brankedge.model.Developer;
import fact.it.brankedge.model.FilledGameDev;
import fact.it.brankedge.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("releases/developer/{Id}")
    public List<FilledGameDev> getDevelopersById(@PathVariable Integer Id) {


        List<FilledGameDev> returnList = new ArrayList();

        ResponseEntity<List<Game>> responseEntityGames =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/games/developer/{Id}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                        }, Id);

        List<Game> games = responseEntityGames.getBody();

        for (Game game :
                games) {
            Developer developer =
                    restTemplate.getForObject("http://" + developerServiceBaseUrl + "/games/{name}",
                            Developer.class, game.getName());

            returnList.add(new FilledGameDev(developer, games));
        }

        return returnList;
    }

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
                            }, developer.getName());

            returnList.add(new FilledGameDev(developer, responseEntityGames.getBody()));
        }

        return returnList;
    }

    @GetMapping("releases/rankings/developer/{name}")
    public FilledGameDev getReleasebyDevName(@PathVariable String ISBN) {

        Developer developer =
                restTemplate.getForObject("http://" + developerServiceBaseUrl + "/developers/{id}",
                        Developer.class, ISBN);

        ResponseEntity<List<Game>> responseEntityReviews =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/reviews/{ISBN}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                        }, developer.getName());

        return new FilledGameDev(developer, responseEntityReviews.getBody());
    }


}
