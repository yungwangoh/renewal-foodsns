package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.domain.dto.request.SignInParam;
import mubex.renewal_foodsns.domain.dto.request.SignOutParam;
import mubex.renewal_foodsns.domain.dto.request.SignUpParam;
import mubex.renewal_foodsns.domain.dto.request.UpdateNickNameParam;
import mubex.renewal_foodsns.domain.dto.request.UpdatePasswordParam;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.presentation.annotation.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Validated
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpParam signUpParam) {

        memberService.signUp(
                signUpParam.email(),
                signUpParam.nickName(),
                signUpParam.password()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<MemberResponse> signIn(@RequestBody @Valid SignInParam signInParam) {

        MemberResponse memberResponse = memberService.signIn(
                signInParam.email(),
                signInParam.password()
        );

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<Void> signOut(@RequestParam("nickName") String nickName) {

        memberService.signOut(nickName);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/nick-name")
    public ResponseEntity<String> updateNickName(@RequestBody @Valid UpdateNickNameParam updateNickNameParam) {

        String updateNickName = memberService.updateNickName(
                updateNickNameParam.nickName(),
                updateNickNameParam.updatedNickName()
        );

        return ResponseEntity.ok(updateNickName);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordParam updatePasswordParam) {

        memberService.updatePassword(
                updatePasswordParam.nickName(),
                updatePasswordParam.password()
        );

        return ResponseEntity.ok().build();
    }
}
