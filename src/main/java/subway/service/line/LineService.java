package subway.service.line;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.station.Station;
import subway.domain.line.Line;
import subway.domain.line.LineRepository;
import subway.domain.station.StationRepository;
import subway.dto.line.LineRequest;
import subway.dto.line.LineResponse;

import java.util.ArrayList;
import java.util.List;



@Service
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public LineResponse saveLine(LineRequest lineRequest) {
        Line line = lineRepository.save(lineRequest.toLine());

        return createLineResponse(line, findStationById(line.getUpStationId(), line.getDownStationId()));
    }

    public LineResponse createLineResponse(Line line, List<Station> stationList) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                stationList
        );
    }


    public List<LineResponse> findAllLines() {
        List<Line> lines = lineRepository.findAll();
        List<LineResponse> lineResponses = new ArrayList<>();

        for (Line line : lines) {
            LineResponse lineResponse = new LineResponse(line.getId(), line.getName(), line.getColor(),
                    findStationById(line.getUpStationId(), line.getDownStationId()));
            lineResponses.add(lineResponse);

        }

        return lineResponses;
    }

    public List<Station> findStationById(Long upStationId, Long downStationId) {
        List<Station> stationList = new ArrayList<>();

        stationList.add(stationRepository.findById(upStationId)
                .orElseThrow(IllegalArgumentException::new));
        stationList.add(stationRepository.findById(downStationId)
                .orElseThrow(IllegalArgumentException::new));

        return stationList;
    }
    @Transactional
    public void editLineById(Long id, LineRequest lineRequest) {
        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        line.updateLine(lineRequest.getName(), lineRequest.getColor());
        lineRepository.save(line);

    }
    @Transactional
    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }


    public LineResponse findLine(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        List<Station> stationList = findStationById(line.getUpStationId(), line.getDownStationId());

        return createLineResponse(line, stationList);

    }
}
