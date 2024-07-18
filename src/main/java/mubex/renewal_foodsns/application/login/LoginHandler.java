package mubex.renewal_foodsns.application.login;

import mubex.renewal_foodsns.domain.dto.response.MemberResponse;

public interface LoginHandler {

    MemberResponse signIn(String email, String password);

    void signOut();

    MemberResponse getMember();

    Long getMemberId();
}
