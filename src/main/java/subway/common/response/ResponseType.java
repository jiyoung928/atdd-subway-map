package subway.common.response;

public enum ResponseType {
    SUCCESS(200),
    CREATED(201),
    NOCONTENT(202),
    FAILURE(400);


    private int code;

    ResponseType(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.name();
    }
}
