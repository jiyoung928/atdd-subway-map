package subway.domain.section;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findALlByLineId(Long lineId);
    Section findByLineIdAndUpStationId(Long lineId, Long stationId);
    Section findByLineIdAndDownStationId(Long lineId, Long stationId);
    boolean existsByLineId(Long lineId);
    boolean existsByLineIdAndUpStationId(Long lineId, Long stationId);
}
