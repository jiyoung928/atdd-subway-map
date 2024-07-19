package subway.common.response;

public class NotLastStationException extends BusinessException {
    public NotLastStationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotLastStationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
