package br.com.eacf.app.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long  id;
    private Integer year;
    private String title;
    private boolean winner;

    @ElementCollection
    private List<String> studios;

    @ElementCollection
    private List<String> producers;

    protected Movie() {}

    public Movie(Long id, Integer year, String title, List<String> studios, List<String> producers, boolean winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", winner=" + winner +
                ", studios=" + studios +
                ", producers=" + producers +
                '}';
    }
}
