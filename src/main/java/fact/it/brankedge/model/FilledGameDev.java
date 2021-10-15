package fact.it.brankedge.model;

import java.util.ArrayList;
import java.util.List;

public class FilledGameDev {
    private String developerName;
    private List<Release> releases;

    public FilledGameDev(Developer developer, List<Game> games) {
        setDeveloperName(developer.getName());
        releases = new ArrayList<>();
        games.forEach(game -> {
            releases.add(new Release(game.getDeveloper(), game.getName()));
        });
        setReleases(releases);

    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }
}
