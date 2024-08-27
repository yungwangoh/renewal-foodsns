package mubex.renewal_foodsns.domain.dto.response;

import lombok.Builder;
import mubex.renewal_foodsns.domain.type.MemberRank;

@Builder
public record MemberResponse(
        String nickName,
        long heart,
        MemberRank memberRank,
        int report,
        boolean inBlackList,
        String profileImage) {
}
