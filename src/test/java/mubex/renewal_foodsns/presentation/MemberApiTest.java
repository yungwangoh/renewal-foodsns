package mubex.renewal_foodsns.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.common.TestContainer;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.dto.request.PostParam;
import mubex.renewal_foodsns.domain.dto.request.sign.SignInParam;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.type.MemberRank;
import mubex.renewal_foodsns.domain.type.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("/data.sql")
@Import(TestContainer.class)
class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        Member member = Member.create("qwer1234", "qwer1234@A", "qwer1234@naver.com", "", 0, 0, true, MemberRank.NORMAL,
                false);

        memberRepository.save(member);
    }

    @Test
    void 회원의_유효하지_않은_세션_일_떄_기능을_이용하면_예외_발생_401() {
        // given && when
        ExtractableResponse<Response> extract = RestAssured.given()
                .log().all()
                .when()
                .post("/api/v1/members/sign-out")
                .then()
                .log().all()
                .extract();

        int aStatusCode = extract.statusCode();
        int eStatusCode = ExceptionResolver.UNAUTHORIZED_MEMBER.getStatusCode().value();
        ProblemDetail aPd = extract.body().as(ProblemDetail.class);
        ProblemDetail ePd = ExceptionResolver.UNAUTHORIZED_MEMBER.getBody();
        ePd.setInstance(URI.create("/api/v1/members/sign-out"));

        // then
        assertSoftly(softly -> {
            softly.assertThat(aStatusCode).isEqualTo(eStatusCode);
            softly.assertThat(aPd)
                    .usingRecursiveComparison()
                    .isEqualTo(ePd);
        });
    }

    @Test
    void 블랙리스트인_회원은_기능을_사용하지_못한다_400() throws Exception {
        // given
        CookieFilter cookieFilter = login();

        PostParam postParam = new PostParam("Title", "Text");
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.SOUP);

        String postParamJson = new ObjectMapper().writeValueAsString(postParam);

        // when
        ExtractableResponse<Response> extract = RestAssured.given()
                .log().all()
                .filter(cookieFilter)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("post", "post", postParamJson, MediaType.APPLICATION_JSON_VALUE)
                .multiPart("image", "image.png", new byte[]{}, MediaType.IMAGE_PNG_VALUE)
                .param("tag", tags)
                .when()
                .post("/api/v1/posts")
                .then()
                .log().all()
                .extract();

        int aStatusCode = extract.statusCode();
        int eStatusCode = HttpStatus.BAD_REQUEST.value();

        ProblemDetail aPd = extract.body().as(ProblemDetail.class);
        ProblemDetail ePd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "블랙리스트 유저는 사용할 수 없습니다.");
        ePd.setInstance(URI.create("/api/v1/posts"));

        // then
        assertSoftly(softly -> {
            softly.assertThat(aStatusCode).isEqualTo(eStatusCode);
            softly.assertThat(aPd)
                    .usingRecursiveComparison()
                    .isEqualTo(ePd);
        });
    }

    @Test
    void 블랙리스트인_회원은_읽기_기능은_사용_가능하다_200() {
        // given
        CookieFilter cookieFilter = login();

        // when
        ExtractableResponse<Response> extract = RestAssured.given()
                .log().all()
                .filter(cookieFilter)
                .when()
                .get("/api/v1/posts")
                .then()
                .log().all()
                .extract();

        // then
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private CookieFilter login() {
        CookieFilter cookieFilter = new CookieFilter();

        SignInParam sign = new SignInParam("qwer1234@naver.com", "qwer1234@A");

        RestAssured.given()
                .filter(cookieFilter)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(sign)
                .when()
                .post("/api/v1/members/sign-in")
                .then()
                .statusCode(200);

        return cookieFilter;
    }
}