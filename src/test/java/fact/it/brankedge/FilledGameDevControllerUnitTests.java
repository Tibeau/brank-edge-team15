package fact.it.brankedge;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.brankedge.model.Developer;
import fact.it.brankedge.model.Game;
import fact.it.brankedge.model.Release;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.net.URI;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class FilledGameDevControllerUnitTests {

    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    @Value("${developerservice.baseurl}")
    private String developerServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Developer developer1 = new Developer("Blizzard", 651, 1950);
    private Developer developer2 = new Developer("Activision", 8411, 2000);

    private Game game1 = new Game("Plants vs zombies", 2001, "Activision", 3511);
    private Game game2 = new Game("The Sims 4", 2011, "Activision", 8615);
    private Game game3 = new Game("World of warcraft", 1990, "Blizzard", 351151);

    private List<Game> games1 = List.of(game1, game2);
    private List<Game> games2 = Arrays.asList(game1, game2, game3);
    private List<Game> games3 = List.of(game1);
    private List<Developer> allDevs = Arrays.asList(developer1, developer2);

    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);


    }

    @Test
    public void whenGetReleases_thenReturnFilledGameDevJson() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + gameServiceBaseUrl + "/games")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(games2))
                );

        mockMvc.perform(get("/releases/games"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].releases[0].developerId", is("Activision")))
                .andExpect(jsonPath("$[0].releases[0].gameName", is("Plants vs zombies")));

    }

    @Test
    public void whenAddRelease_thenReturnFilledGameDevJson() throws Exception {

        Game game1 = new Game("The sims 4", 1925, "Activision", 6812);


        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + gameServiceBaseUrl + "/games")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(game1))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + developerServiceBaseUrl + "/developers/Activision")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(developer2))
                );

        mockMvc.perform(post("/releases")
                .param("gameName", game1.getName())
                .param("developerName", game1.getDeveloperName())
                .param("release_year",game1.getRelease_year().toString())
                .param("sales",game1.getSales().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.developerName",is("Activision")))
                .andExpect(jsonPath("$.releases[0].developerId", is("Activision")))
                .andExpect(jsonPath("$.releases[0].gameName", is("The sims 4")));
    }

    @Test
    public void whenDeleteRelease_thenReturnStatusOk() throws Exception {


        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + gameServiceBaseUrl + "/games/Plants%20Vs%20Zombies")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/releases/games/{name}", "Plants Vs Zombies"))
                .andExpect(status().isOk());
    }



}
