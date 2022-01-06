package fact.it.brankedge.model;

public class Game {
    private  String id;
    private String name;
    private Integer release_year;
    private String developerName;
    private Integer sales;

    public Game() {

    }

    public Game(String name, Integer release_year, String developerName, Integer sales) {
        this.name = name;
        this.release_year = release_year;
        this.developerName = developerName;
        this.sales = sales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }


}
