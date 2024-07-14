package mubex.renewal_foodsns.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.domain.dto.request.sign.SignUpParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/data.sql")
class MemberApiTest extends TestContainer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원_가입_API_201() throws Exception {
        // given
        String email = "qwer1234@gmail.com";
        String password = "qwer1234@A";
        String nickName = "yesIdo";

        SignUpParam signUpParam = new SignUpParam(email, nickName, password, 1);

        String s = objectMapper.writeValueAsString(signUpParam);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/members")
                .content(s)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
    }

}