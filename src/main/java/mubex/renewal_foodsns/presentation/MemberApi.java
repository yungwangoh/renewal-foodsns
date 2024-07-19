package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.domain.dto.request.sign.SignInParam;
import mubex.renewal_foodsns.domain.dto.request.sign.SignUpParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdateNickNameParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdatePasswordParam;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Validated
@Slf4j
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("members")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpParam signUpParam) {

        memberService.signUp(
                signUpParam.email(),
                signUpParam.nickName(),
                signUpParam.password(),
                signUpParam.profileId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("members/sign-in")
    public ResponseEntity<MemberResponse> signIn(@RequestBody @Valid SignInParam signInParam) {

        MemberResponse memberResponse = memberService.signIn(
                signInParam.email(),
                signInParam.password()
        );

        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("members/sign-out")
    public ResponseEntity<Void> signOut() {

        memberService.signOut();

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("members/nick-name")
    public ResponseEntity<MemberResponse> updateNickName(@RequestBody @Valid UpdateNickNameParam updateNickNameParam) {

        MemberResponse memberResponse = memberService.updateNickName(
                updateNickNameParam.nickName(),
                updateNickNameParam.updatedNickName()
        );

        return ResponseEntity.ok(memberResponse);
    }

    @PatchMapping("members/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UpdatePasswordParam updatePasswordParam) {

        memberService.updatePassword(
                updatePasswordParam.nickName(),
                updatePasswordParam.password()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("members")
    public ResponseEntity<Void> deleteMember(@RequestParam("nickName") String nickName) {

        memberService.markAsDeleted(nickName);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("members/rank")
    public ResponseEntity<List<MemberResponse>> findMemberRank(@RequestParam("memberRank") MemberRank memberRank) {

        List<MemberResponse> memberResponses = memberService.findByMemberRank(memberRank);

        return ResponseEntity.ok(memberResponses);
    }

    @GetMapping("members/page")
    public ResponseEntity<Page<MemberResponse>> pagingByDescSort(PageRequest pageRequest) {

        Page<MemberResponse> memberResponses = memberService.viewPagingMemberRank(pageRequest);

        return ResponseEntity.ok(memberResponses);
    }
}
