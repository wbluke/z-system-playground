package playground.config.interceptor;

public class InvalidLoginTokenException extends IllegalArgumentException {

    public static final String DEFAULT_MESSAGE = "유효한 로그인 정보가 아닙니다.";

    public InvalidLoginTokenException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidLoginTokenException(String message) {
        super(message);
    }

}
