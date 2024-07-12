package mubex.renewal_foodsns.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpParam(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?!.*[_-]{2})[a-zA-Z0-9가-힣_-]{2,20}$",
                message = "닉네임은 2~20자의 영문 대소문자, 숫자, 한글, 특수문자(_,-)를 사용할 수 있으며, 특수문자는 연속으로 사용할 수 없습니다."
        )
        String nickName,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                message = "비밀번호는 8~20자의 영문 대/소문자, 숫자, 특수문자를 포함해야 합니다."
        )
        String password
) {
}
