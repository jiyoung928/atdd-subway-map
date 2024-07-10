package subway.line;

public class LineRequest {
    private String name;
    private String color;
    private int upStationId;
    private int downStationId;
    private int distance;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUpStationId(int upStationId) {
        this.upStationId = upStationId;
    }

    public void setDownStationId(int downStationId) {
        this.downStationId = downStationId;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
