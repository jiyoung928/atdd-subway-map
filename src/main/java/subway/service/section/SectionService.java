package subway.service.section;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.domain.line.Line;
import subway.domain.line.LineRepository;
import subway.domain.section.Section;
import subway.domain.section.SectionRepository;
import subway.domain.station.Station;
import subway.domain.station.StationRepository;
import subway.dto.line.LineResponse;
import subway.dto.section.SectionRequest;
import subway.service.line.LineService;

import java.util.ArrayList;
import java.util.List;

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
        Station station = stationRepository.findById(sectionRequest.getUpStationId())
                .orElseThrow(IllegalArgumentException::new);

        if(sectionRequest.getUpStationId().equals(line.getDownStationId()) && !sectionRepository.existsByLineIdAndUpStationId(lineId, sectionRequest.getUpStationId())){
            // add section
            sectionRepository.save(new Section(station, line, line.getUpStationId(), sectionRequest.getDownStationId()));

            // update line downstationId
            line.updateDownStationId(sectionRequest.getDownStationId());
            lineRepository.save(line);

            return lineService.createLineResponse(line, findALLStationById(lineId, line.getUpStationId(), line.getDownStationId()));
        }
        else{
            return null;
        }
    }

    public List<Station> findALLStationById(Long lineId, Long upStationId, Long downStationId) {
        List<Station> stationList = new ArrayList<>();
        int sectionSize = sectionRepository.findALlByLineId(lineId).size();
        // upStationId setting
        stationList.add(stationRepository.findById(upStationId)
                .orElseThrow(IllegalArgumentException::new));

        // section setting
        for(int i = 0; i < sectionSize; i++) {
            Section section = sectionRepository.findByLineIdAndUpStationId(lineId, upStationId);
            stationList.add(stationRepository.findById(section.getStation().getId())
                    .orElseThrow(IllegalArgumentException::new));
        }

        // downStationId setting
        stationList.add(stationRepository.findById(downStationId)
                .orElseThrow(IllegalArgumentException::new));


        return stationList;
    }

    @Transactional
    public boolean deleteSection(Long id, Long stationId) {

        Line line = lineRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Section section = sectionRepository.findByLineIdAndDownStationId(id, stationId);

        if(line.getDownStationId().equals(stationId) && sectionRepository.existsByLineId(id)){
            // update line downstationId
            line.updateDownStationId(section.getUpStationId());
            lineRepository.save(line);

            // delete section
            sectionRepository.delete(section);
            return true;
        }
        else{
            return false;
        }
    }
}
