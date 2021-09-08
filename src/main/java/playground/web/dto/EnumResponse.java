package playground.web.dto;

import lombok.Getter;
import playground.domain.EnumResponseType;

@Getter
public class EnumResponse<T extends EnumResponseType> {

    private final T value;
    private final String text;

    public EnumResponse(T value) {
        this.value = value;
        this.text = value.getText();
    }

}
