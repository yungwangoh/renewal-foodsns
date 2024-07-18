package mubex.renewal_foodsns.domain.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateNickNameParam(
        String nickName,

        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9가-힣]{2,20}$",
                message = "닉네임은 2~20자의 영문 대소문자, 숫자, 한글만 사용 가능합니다."
        )
        String updatedNickName
) {
}
