package playground.response;

import lombok.Getter;

import static playground.response.ApiExceptionResponseCode.BAD_PARAMETER;

@Getter
public class ApiExceptionResponse {

    private final ApiExceptionResponseCode code;
    private final String message;

    public ApiExceptionResponse(ApiExceptionResponseCode code) {
        this(code, code.getMessage());
    }

    public ApiExceptionResponse(ApiExceptionResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiExceptionResponse badParameter(String message) {
        return new ApiExceptionResponse(BAD_PARAMETER, message);
    }

}
