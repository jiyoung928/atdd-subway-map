package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import subway.line.LineRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 노선 관련 기능")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LineAcceptanceTest {

    @BeforeEach
    void initStation(){
        createStation("지하철역");
        createStation("새로운지하철역");
        createStation("또다른지하철역");
    }
    /**
     * Given: 새로운 지하철 노선 정보를 입력하고,
     * When: 관리자가 노선을 생성하면,
     * Then: 해당 노선이 생성되고 노선 목록에 포함된다.
    */
    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {

        // given
        // when
        // then
        assertThat(createLine(new LineRequest("신분당선", "bg-red-600", 1, 2, 10))
                .statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * Given: 여러 개의 지하철 노선이 등록되어 있고,
     * When: 관리자가 지하철 노선 목록을 조회하면,
     * Then: 모든 지하철 노선 목록이 반환된다.
     */
    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void showLines() {
        // given
        assertThat(createLine(new LineRequest("신분당선", "bg-red-600", 1, 2, 10))
                .statusCode()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(createLine(new LineRequest("분당선", "bg-green-600", 1, 3, 20))
                .statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // when
        List<String> lineNames =
                given().log().all()
                        .when().get("/lines")
                        .then().log().all()
                        .extract().jsonPath().getList("name", String.class);
        //then
        assertThat(lineNames).contains("신분당선", "분당선");
    }


    /**
     * Given: 특정 지하철 노선이 등록되어 있고,
     * When: 관리자가 해당 노선을 조회하면,
     * Then: 해당 노선의 정보가 반환된다.
     */
    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void showLine() {
        // given
        assertThat(createLine(new LineRequest("신분당선", "bg-red-600", 1, 2, 10))
                .statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // when
        List<String> lineNames =
                given().log().all()
                        .when().get("/lines")
                        .then().log().all()
                        .extract().jsonPath().getList("name", String.class);
        //then
        assertThat(lineNames).contains("신분당선");
    }

    /**
     * Given: 특정 지하철 노선이 등록되어 있고,
     * When: 관리자가 해당 노선을 수정하면,
     * Then: 해당 노선의 정보가 수정된다.
     */
    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void editLine() {
        // given
        String lineId  =   createLine(new LineRequest("신분당선", "bg-red-600", 1, 2, 10))
                .getHeaders().get("Location").toString().split("/")[2];


        //when
        Map<String, String> modifyParam = new HashMap<>();
        modifyParam.put("name", "다른분당선");
        modifyParam.put("color", "bg-red-600");

        ExtractableResponse<Response> response =
                given().log().all()
                        .body(modifyParam)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when().put("/lines/{id}", lineId)
                        .then().log().all()
                        .extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    /**
     * Given: 특정 지하철 노선이 등록되어 있고,
     * When: 관리자가 해당 노선을 삭제하면,
     * Then: 해당 노선이 삭제되고 노선 목록에서 제외된다.
     */
    @DisplayName("지하철 노선을 삭제한다.")
    @Test
    void deleteLine() {
        // given
        String lineId  =   createLine(new LineRequest("신분당선", "bg-red-400", 1, 2, 10))
                .getHeaders().get("Location").toString().split("/")[2];

        //when
        ExtractableResponse<Response> response =
                given().log().all()
                        .when().delete("/lines/{id}", lineId)
                        .then().log().all()
                        .extract();
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }
    private Response createLine(LineRequest lineRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(lineRequest)
                .when()
                .post("/lines")
                .then()
                .extract().response();
    }

    private void createTwoLines() {
        // given
        Map<String, String> param1 = new HashMap<>();
        param1.put("name", "신분당선");
        param1.put("color", "bg-red-600");
        param1.put("upStationId", "1");
        param1.put("downStationId", "2");
        param1.put("distance", "10");

        ExtractableResponse<Response> response =
                given().log().all()
                        .body(param1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when().post("/lines")
                        .then().log().all()
                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        Map<String, String> param2 = new HashMap<>();
        param2.put("name", "분당선");
        param2.put("color", "bg-green-400");
        param2.put("upStationId", "1");
        param2.put("downStationId", "3");
        param2.put("distance", "20");

        response =
                given().log().all()
                        .body(param2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when().post("/lines")
                        .then().log().all()
                        .extract();


        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    private Response createStation(String stationName) {
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"name\":\""+ stationName +"\"}")
                .when()
                .post("/stations")
                .then()
                .extract().response();
    }

}
