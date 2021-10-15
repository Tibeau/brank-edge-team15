package fact.it.brankedge.model;

public class Release {
    private String DeveloperId;
    private String gameName;

    public Release(String developperId, String gameName) {
        this.DeveloperId = developperId;
        this.gameName = gameName;
    }

    public String getDeveloperId() {
        return DeveloperId;
    }

    public void setDeveloperId(String developperId) {
        DeveloperId = developperId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
