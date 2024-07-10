package subway.line;

public class LineRequest {
    private String name;
    private String color;
    private int upStationId;
    private int downStationId;
    private int distance;

    public LineRequest() {
    }

    public LineRequest(String name, String color, int upStationId, int downStationId, int distance) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return (long) upStationId;
    }

    public Long getDownStationId() {
        return (long) downStationId;
    }

    public int getDistance() {
        return distance;
    }


}
