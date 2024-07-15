package subway.dto.section;

public class SectionRequest {
    private int upStationId;
    private int downStationId;
    private int distance;

    public SectionRequest(int upStationId, int downStationId, int distance) {
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Long getUpStationId() {
        return Long.valueOf(upStationId);
    }

    public Long getDownStationId() {
        return Long.valueOf(downStationId);
    }

    public int getDistance() {
        return distance;
    }
}
