package subway.common.response;

public class InvalidUpSationException extends BusinessException{
    public InvalidUpSationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
    public InvalidUpSationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
