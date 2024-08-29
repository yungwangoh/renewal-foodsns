package mubex.renewal_foodsns.domain.dto.response;

import mubex.renewal_foodsns.domain.entity.PostTag;
import mubex.renewal_foodsns.domain.mapper.map.PostMapper;

public record PostTagResponse(String tag, PostResponse postResponse) {

    public static PostTagResponse of(PostTag postTag) {
        return new PostTagResponse(postTag.getTag(), PostMapper.INSTANCE.toResponse(postTag.getPost()));
    }
}
