package subway.controller.section;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.dto.line.LineRequest;
import subway.dto.line.LineResponse;
import subway.dto.section.SectionRequest;
import subway.service.section.SectionService;

import java.net.URI;

import static subway.common.response.Response.failure;
import static subway.common.response.Response.success;

@RestController
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/lines/{id}/sections")
    public ResponseEntity<LineResponse> createSection(@PathVariable Long id, @RequestBody SectionRequest sectionRequest) {

        LineResponse line = sectionService.saveSection(id, sectionRequest);
        if(line == null){
            return new ResponseEntity(failure("새로운 구간의 상행역이 해당 노선에 등록된 하행 종점역이 아니거나 새로운 구간의 하행 종점역이 해당 노선이 포함되어있습니다."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(success(line),HttpStatus.CREATED);
    }

    @DeleteMapping("/lines/{id}/sections")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id, @RequestParam("stationId") Long stationId ) {
        boolean result = sectionService.deleteSection(id, stationId);
        if(result){
            return new ResponseEntity(success(),HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity(failure("요청한 구간이 해당 노선의 하행 종점역이 아니거나 해당 노선의 구간이 1개입니다."), HttpStatus.BAD_REQUEST);
        }
    }


}
