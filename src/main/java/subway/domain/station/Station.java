package subway.domain.station;

import subway.domain.section.Section;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @OneToMany(mappedBy = "station")
    private List<Section> sections = new ArrayList<>();

    public Station() {
    }

    public Station(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
