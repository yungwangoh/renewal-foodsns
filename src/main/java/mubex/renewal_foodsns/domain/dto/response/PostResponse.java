package mubex.renewal_foodsns.domain.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PostResponse(
        long id,
        String title,
        String text,
        long heart,
        int report,
        long views,
        MemberResponse memberResponse,
        List<PostImageResponse> postImageResponses
) {
}
