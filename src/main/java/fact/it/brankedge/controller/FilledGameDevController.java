package fact.it.brankedge.controller;

import fact.it.brankedge.model.Developer;
import fact.it.brankedge.model.FilledGameDev;
import fact.it.brankedge.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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


    @DeleteMapping("/releases/games/{id}")
    public void deleteGame(@PathVariable String id){
        restTemplate.delete("http://" + gameServiceBaseUrl + "/games/" + id );
    }

    @PostMapping("/releases")
    public FilledGameDev addRelease(@RequestParam String gameName, @RequestParam Integer release_year, @RequestParam String developerName, @RequestParam Integer sales){

        Game game =
                restTemplate.postForObject("http://" + gameServiceBaseUrl + "/games",
                        new Game(gameName, release_year, developerName, sales),Game.class);

        Developer developer =
                restTemplate.getForObject("http://" + developerServiceBaseUrl + "/developers/" + developerName,
                        Developer.class, developerName);

        return new FilledGameDev(developer, game);
    }

    @GetMapping("releases/games")
    public List<FilledGameDev> getGames() {
        List<FilledGameDev> returnList = new ArrayList();

        ResponseEntity<List<Game>> responseEntityGame =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/games",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                        });

        returnList.add(new FilledGameDev(responseEntityGame.getBody()));
        return returnList;
    }

    @PutMapping("/releases")
    public FilledGameDev updateRelease(@RequestParam String developerId, @RequestParam String gameName){

        Game game = restTemplate.getForObject("http://" + gameServiceBaseUrl + "/games/name/" + gameName, Game.class);

        ResponseEntity<Game> responseEntityGame =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/games",
                        HttpMethod.PUT, new HttpEntity<>(game), Game.class);

        Game retrievedGame = responseEntityGame.getBody();
        Developer developer =
                restTemplate.getForObject("http://" + developerServiceBaseUrl + "/developers/{developerId}",
                        Developer.class, developerId);

        return new FilledGameDev(developer, retrievedGame);
    }

}
