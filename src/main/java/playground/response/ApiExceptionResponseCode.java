package playground.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionResponseCode {

    BAD_PARAMETER("요청 파라미터가 잘못되었습니다."),
    NOT_FOUND("요청한 정보를 찾을 수 없습니다."),
    UNAUTHORIZED("인증이 실패하였습니다.");

    private final String message;

}
