package subway.domain.section;

import subway.domain.line.Line;
import subway.domain.station.Station;

import javax.persistence.*;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    private Long upStationId;
    private Long downStationId;

    public Section() {}

    public Section(Station station, Line line, Long upStationId, Long downStationId) {
        this.station = station;
        this.line = line;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
    }

    public Station getStation() {
        return station;
    }

    public Long getUpStationId() {
        return upStationId;
    }
}
