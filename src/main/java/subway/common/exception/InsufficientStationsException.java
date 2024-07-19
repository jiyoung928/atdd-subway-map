package subway.common.response;

public class InsufficientStationsException extends BusinessException {
    public InsufficientStationsException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InsufficientStationsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
