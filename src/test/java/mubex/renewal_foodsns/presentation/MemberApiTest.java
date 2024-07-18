package mubex.renewal_foodsns.presentation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.domain.dto.request.sign.SignUpParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdateNickNameParam;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberApi.class)
class MemberApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private LoginHandler loginHandler;

    @Test
    void 회원_가입_API_201() throws Exception {
        // given
        String email = "qwer1234@gmail.com";
        String password = "qwer1234@A";
        String nickName = "yesIdo";

        SignUpParam signUpParam = new SignUpParam(email, nickName, password, 1);

        doNothing().when(memberService)
                .signUp(
                        signUpParam.email(),
                        signUpParam.nickName(),
                        signUpParam.password(),
                        signUpParam.profileId()
                );

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpParam)));

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void 회원_가입_할_때_입력_유효성을_지키지_않은_경우_400() throws Exception {
        // given
        String email = "qwer1234@gmail.com";
        String password = "qwer1234";
        String nickName = "yesIdo";

        SignUpParam signUpParam = new SignUpParam(email, nickName, password, 1);

        doNothing().when(memberService)
                .signUp(
                        signUpParam.email(),
                        signUpParam.nickName(),
                        signUpParam.password(),
                        signUpParam.profileId()
                );

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpParam)));

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 로그인_하지_않고_접근_할떄_예외_401() throws Exception {
        // given
        UpdateNickNameParam updateNickNameParam = new UpdateNickNameParam("nickName", "updateName");
        MemberResponse memberResponse = MemberResponse.builder()
                .memberRank(MemberRank.NORMAL)
                .heart(0)
                .report(0)
                .profileId(1)
                .inBlackList(false)
                .nickName("nickName")
                .build();

        given(memberService.updateNickName(anyString(), anyString())).willReturn(memberResponse);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/members/nick-name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateNickNameParam)));

        // then
        resultActions.andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void 유저_탈퇴_204() throws Exception {
        doNothing().when(memberService).markAsDeleted(anyString());

        mockMvc.perform(delete("/api/v1/members")
                        .param("nickName", "testNick"))
                .andExpect(status().isNoContent());
    }
}