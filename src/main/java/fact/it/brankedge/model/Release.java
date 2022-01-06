package fact.it.brankedge.model;

public class Release {
    private String developerId;
    private String gameName;

    public Release(String developperId, String gameName) {
        this.developerId = developperId;
        this.gameName = gameName;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developperId) {
        developerId = developperId;
    }

    
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
