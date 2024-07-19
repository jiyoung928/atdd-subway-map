package subway.service.section;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.common.exception.InsufficientStationsException;
import subway.common.exception.InvalidDownStationException;
import subway.common.exception.InvalidUpSationException;
import subway.common.exception.NotLastStationException;
import subway.common.response.*;
import subway.domain.line.Line;
import subway.domain.line.LineRepository;
import subway.domain.section.Section;
import subway.domain.section.SectionRepository;
import subway.domain.station.Station;
import subway.domain.station.StationRepository;
import subway.dto.line.LineResponse;
import subway.dto.section.SectionRequest;
import subway.service.line.LineService;

@Service
@Transactional(readOnly = true)
public class SectionService {
    private final SectionRepository sectionRepository;
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final LineService lineService;

    public SectionService(SectionRepository sectionRepository, LineRepository lineRepository, StationRepository stationRepository, LineService lineService) {
        this.sectionRepository = sectionRepository;
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.lineService = lineService;
    }

    @Transactional
    public LineResponse saveSection(Long lineId, SectionRequest sectionRequest) {

        Line line = lineRepository.findById(lineId).orElseThrow(IllegalArgumentException::new);
        Station upStation = stationRepository.findById(sectionRequest.getUpStationId())
                .orElseThrow(IllegalArgumentException::new);
        Station downStation = stationRepository.findById(sectionRequest.getDownStationId())
                .orElseThrow(IllegalArgumentException::new);

        line.addSection(new Section(upStation.getId(), downStation.getId(), sectionRequest.getDistance()));

        return LineResponse.createResponse(line, lineService.getLineStations(line));
    }

    @Transactional
    public void deleteSection(Long id, Long stationId) {

        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        line.removeLastStation(stationId);

    }
}
