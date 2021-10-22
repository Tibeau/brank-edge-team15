package fact.it.brankedge.model;

public class Game {
    private  int id;
    private String name;
    private int release_year;
    private String developer;
    private int sales;

    public Game() {

    }

    public Game(String name, int release_year, String developer, int sales) {
        this.name = name;
        this.release_year = release_year;
        this.developer = developer;
        this.sales = sales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }


}
