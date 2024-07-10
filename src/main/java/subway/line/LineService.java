package subway.line;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.station.*;

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
        Line line = lineRepository.save(new Line(lineRequest.getName(), lineRequest.getColor(), lineRequest.getUpStationId(), lineRequest.getDownStationId(), lineRequest.getDistance()));

        List<Station> stationList = new ArrayList<>();

        stationList.add(stationRepository.findById(lineRequest.getUpStationId())
                .orElseThrow(IllegalArgumentException::new));
        stationList.add(stationRepository.findById(lineRequest.getDownStationId())
                .orElseThrow(IllegalArgumentException::new));

        return createLineResponse(line, stationList);
    }

    private LineResponse createLineResponse(Line line, List<Station> stationList) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                stationList
        );
    }


    public List<LineResponse> findAllLines() {
        List<Line> lines = lineRepository.findAll();
        List<Station> stationList = new ArrayList<>();
        List<LineResponse> lineResponses = new ArrayList<>();

        for (Line line : lines) {
            stationList.add(stationRepository.findById(line.getUpStationId())
                    .orElseThrow(IllegalArgumentException::new));
            stationList.add(stationRepository.findById(line.getDownStationId())
                    .orElseThrow(IllegalArgumentException::new));

            LineResponse lineResponse = new LineResponse(line.getId(), line.getName(), line.getColor(), stationList);
            lineResponses.add(lineResponse);

        }

        return lineResponses;
    }

    @Transactional
    public void editLineById(Long id, LineRequest lineRequest) {
        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        line.updateLine(lineRequest.getName(), lineRequest.getColor());
        lineRepository.save(line);

    }
    @Transactional
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }


}
