package mubex.renewal_foodsns.domain.dto.response;

import java.util.List;
import lombok.Builder;
import mubex.renewal_foodsns.domain.entity.Post;

@Builder
public record PostResponse(
        long id,
        String title,
        String text,
        long heart,
        int report,
        long views,
        boolean visible,
        MemberResponse memberResponse,
        List<PostImageResponse> postImageResponses
) {

    public static PostResponse of(final Post post,
                                  final MemberResponse memberResponse,
                                  final List<PostImageResponse> postImageResponses) {

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .heart(post.getHeart())
                .report(post.getReport())
                .views(post.getViews())
                .visible(post.isInDeleted())
                .memberResponse(memberResponse)
                .postImageResponses(postImageResponses)
                .build();
    }
}
