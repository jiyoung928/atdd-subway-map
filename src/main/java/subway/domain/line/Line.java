package subway.domain.line;

import subway.domain.section.Section;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String color;
    private Long upStationId;
    private Long downStationId;
    private Integer distance;

    @OneToMany(mappedBy = "line")
    private List<Section> sections = new ArrayList<>();


    public Line() {
    }

    public Line(String name, String color, Long upStationId, Long downStationId, Integer distance) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
    public Long getUpStationId() {
        return upStationId;
    }
    public Long getDownStationId() {
        return downStationId;
    }


    public void updateLine(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public void updateDownStationId(Long downStationId) {
        this.downStationId = downStationId;
    }
}
