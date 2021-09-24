package playground.service.auth.exception;

public class LoginFailException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "이메일 혹은 비밀번호를 확인해 주세요.";

    public LoginFailException() {
        super(DEFAULT_MESSAGE);
    }

    public LoginFailException(String message) {
        super(message);
    }

}
