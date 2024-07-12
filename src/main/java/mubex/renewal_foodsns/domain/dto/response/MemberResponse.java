package mubex.renewal_foodsns.domain.dto.response;

import mubex.renewal_foodsns.domain.type.MemberRank;

public record MemberResponse(
        String nickName,
        long heart,
        MemberRank memberRank
) {
}
