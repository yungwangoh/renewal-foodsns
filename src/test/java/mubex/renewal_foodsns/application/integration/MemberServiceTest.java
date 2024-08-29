package mubex.renewal_foodsns.application.integration;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mubex.renewal_foodsns.application.MemberService;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Sql("/data.sql")
@Import(TestContainer.class)
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private HttpSession httpSession;

    private static final String SESSION_ID = "session_id";

    @Test
    void 로그인_유저_세션이_등록_되었다() throws IOException {
        // given
        String email = "qwer1234@na.com";
        String password = "qwer1234@A";
        String nickName = "name";

        memberService.signUp(email, nickName, password, getMultipartFiles().getFirst());

        // when
        MemberResponse memberResponse = memberService.signIn(email, password);
        MemberResponse member = loginHandler.getMember();

        // then
        assertThat(memberResponse).isEqualTo(member);
    }

    @Test
    void 로그아웃이_되면_세션이_없어진다() throws IOException {
        // given
        String email = "qwer1234@na.com";
        String password = "qwer1234@A";
        String nickName = "name";

        memberService.signUp(email, nickName, password, getMultipartFiles().getFirst());
        memberService.signIn(email, password);

        // when
        memberService.signOut();

        // then
        assertThat(httpSession.getAttribute(SESSION_ID)).isNull();
    }

    private List<MultipartFile> getMultipartFiles() throws IOException {
        String name = "image";
        String originalFileName = "test.jpeg";
        String contentType = "image/png";
        String fileUrl = "/Users/yungwang-o/Documents/FoodSNS.png";

        List<MultipartFile> mockMultipartFiles = new ArrayList<>();
        mockMultipartFiles.add(
                new MockMultipartFile(name, originalFileName, contentType, new FileInputStream(fileUrl))
        );

        return mockMultipartFiles;
    }
}
