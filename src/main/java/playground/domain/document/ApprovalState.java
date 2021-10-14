package playground.domain.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprovalState {

    DRAFTING("결재중"),
    APPROVED("승인"),
    CANCELED("거절");

    private final String text;

    public boolean isDrafting() {
        return this == DRAFTING;
    }

    public boolean isApproved() {
        return this == APPROVED;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

    public boolean isCompleted() {
        return isApproved() || isCanceled();
    }

}
