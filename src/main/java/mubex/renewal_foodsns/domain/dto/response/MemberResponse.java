package mubex.renewal_foodsns.domain.dto.response;

import lombok.Builder;
import mubex.renewal_foodsns.domain.type.MemberRank;

@Builder
public record MemberResponse(
        int profileId,
        String nickName,
        long heart,
        MemberRank memberRank,
        int report,
        boolean inBlackList) {
}
