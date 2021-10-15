package fact.it.brankedge.model;

public class Developer {
    private int id;

    private String name;
    private int count_workers;
    private int founding_year;

    public Developer() {
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

    public int getCount_workers() {
        return count_workers;
    }

    public void setCount_workers(int count_workers) {
        this.count_workers = count_workers;
    }

    public int getFounding_year() {
        return founding_year;
    }

    public void setFounding_year(int founding_year) {
        this.founding_year = founding_year;
    }
}
