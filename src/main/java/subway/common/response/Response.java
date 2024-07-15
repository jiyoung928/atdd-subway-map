package subway.common.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static subway.common.response.ResponseType.FAILURE;
import static subway.common.response.ResponseType.SUCCESS;

@Getter
@NoArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;

    @Builder
    public Response(ResponseType responseType, T data){
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public static Response success(){
        return Response.builder()
                .responseType(SUCCESS)
                .build();
    }
    public static  <T> Response<T> success(T data){
        return new Response<>(SUCCESS,data);
    }

    public static Response failure() {
        return Response.builder()
                .responseType(FAILURE)
                .build();
    }
    public static  <T> Response<T> failure(T data){
        return new Response<>(FAILURE,data);
    }
}
