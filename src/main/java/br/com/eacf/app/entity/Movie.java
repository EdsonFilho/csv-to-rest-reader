package br.com.eacf.app.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long  id;
    private String year;
    private String title;
    private boolean winner;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "filme_ator",
            joinColumns = { @JoinColumn(name = "filme_id") },
            inverseJoinColumns = { @JoinColumn(name = "ator_id") },
            uniqueConstraints = @UniqueConstraint(columnNames = { "filme_id", "ator_id" }, name = "permissao_filme_ator_key")
    )
    private List<Studio> studios;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "filme_producer",
            joinColumns = { @JoinColumn(name = "filme_id") },
            inverseJoinColumns = { @JoinColumn(name = "producer_id") },
            uniqueConstraints = @UniqueConstraint(columnNames = { "filme_id", "producer_id" }, name = "permissao_filme_producer_key")
    )
    private List<Producer> producers;

    protected Movie() {}

    public Movie(Long id, String year, String title, List<Studio> studios, List<Producer> producers, boolean winner) {
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", winner=" + winner +
                '}';
    }
}
