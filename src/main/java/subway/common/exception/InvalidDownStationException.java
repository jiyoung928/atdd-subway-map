package subway.common.response;

public class InvalidDownStationException extends BusinessException{
    public InvalidDownStationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InvalidDownStationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
