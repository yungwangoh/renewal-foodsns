package mubex.renewal_foodsns.domain.dto.request.sign;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpParam(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9가-힣]{2,20}$",
                message = "닉네임은 2~20자의 영문 대소문자, 숫자, 한글만 사용 가능합니다."
        )
        String nickName,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                message = "비밀번호는 8~20자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        String password,

        int profileId
) {
}
