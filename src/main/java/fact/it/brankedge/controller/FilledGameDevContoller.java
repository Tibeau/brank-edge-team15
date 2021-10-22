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
public class FilledGameDevContoller {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    @Value("${developerservice.baseurl}")
    private String developerServiceBaseUrl;

    @GetMapping("/developer/{Id}")
    public List<FilledGameDev> getDevelopersById(@PathVariable Integer Id) {


        List<FilledGameDev> returnList= new ArrayList();

        ResponseEntity<List<Game>> responseEntityGames =
                restTemplate.exchange("http://" + gameServiceBaseUrl + "/games/developer/{Id}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                        }, Id);

        List<Game> games = responseEntityGames.getBody();

        for (Game game:
                games) {
            Developer developer =
                    restTemplate.getForObject("http://" + developerServiceBaseUrl + "/games/{name}",
                            Developer.class, game.getName());

            returnList.add(new FilledGameDev(developer, game));
        }

        return returnList;
    }

    @GetMapping("/developer/name/{name}")
    public List<FilledGameDev> getDevelopersByName(@PathVariable String name){

        List<FilledGameDev> returnList= new ArrayList();

        ResponseEntity<List<Developer>> responseEntityDeveloper =
                restTemplate.exchange("http://" + DeveloperServiceBaseUrl + "/developer/name/{name}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Developer>>() {
                        }, name);

        List<Developer> developers = responseEntityDevelopers.getBody();

        for (Developer developer:
                developers) {
            ResponseEntity<List<Game>> responseEntityGames =
                    restTemplate.exchange("http://" + gameServiceBaseUrl + "/games/{name}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Game>>() {
                            }, developer.getName());

            returnList.add(new FilledBookReview(book,responseEntityReviews.getBody()));
        }

        return returnList;
    }

    @GetMapping("/rankings/book/{ISBN}")
    public FilledBookReview getRankingsByISBN(@PathVariable String ISBN){

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class, ISBN);

        ResponseEntity<List<Review>> responseEntityReviews =
                restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews/{ISBN}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                        }, ISBN);

        return new FilledBookReview(book,responseEntityReviews.getBody());
    }

    @GetMapping("/rankings/{userId}/book/{ISBN}")
    public FilledBookReview getRankingByUserIdAndISBN(@PathVariable Integer userId, @PathVariable String ISBN){

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class, ISBN);

        Review review =
                restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN,
                        Review.class);

        return new FilledBookReview(book, review);
    }

    @PostMapping("/rankings")
    public FilledBookReview addRanking(@RequestParam Integer userId, @RequestParam String ISBN, @RequestParam Integer score){

        Review review =
                restTemplate.postForObject("http://" + reviewServiceBaseUrl + "/reviews",
                        new Review(userId,ISBN,score),Review.class);

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class,ISBN);

        return new FilledBookReview(book, review);
    }

    @PutMapping("/rankings")
    public FilledBookReview updateRanking(@RequestParam Integer userId, @RequestParam String ISBN, @RequestParam Integer score){

        Review review =
                restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN,
                        Review.class);
        review.setScoreNumber(score);

        ResponseEntity<Review> responseEntityReview =
                restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews",
                        HttpMethod.PUT, new HttpEntity<>(review), Review.class);

        Review retrievedReview = responseEntityReview.getBody();

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class,ISBN);

        return new FilledBookReview(book, retrievedReview);
    }

    @DeleteMapping("/rankings/{userId}/book/{ISBN}")
    public ResponseEntity deleteRanking(@PathVariable Integer userId, @PathVariable String ISBN){

        restTemplate.delete("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN);

        return ResponseEntity.ok().build();
    }
}
