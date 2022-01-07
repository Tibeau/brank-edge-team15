package fact.it.brankedge.model;

public class Developer {
    private Integer id;

    private String name;
    private Integer count_workers;
    private Integer founding_year;

    public Developer() {
    }

    public Developer(String name, Integer count_workers, Integer founding_year) {
        this.name = name;
        this.count_workers = count_workers;
        this.founding_year = founding_year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount_workers() {
        return count_workers;
    }

    public void setCount_workers(Integer count_workers) {
        this.count_workers = count_workers;
    }

    public Integer getFounding_year() {
        return founding_year;
    }

    public void setFounding_year(Integer founding_year) {
        this.founding_year = founding_year;
    }
}
