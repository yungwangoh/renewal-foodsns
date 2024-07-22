package mubex.renewal_foodsns.domain.mapper.map;

import java.util.List;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PostMapper extends Mappable<PostResponse, Post> {

    @Override
    @Mapping(source = "member", target = "memberResponse")
    @Mapping(source = "inDeleted", target = "visible")
    @Mapping(target = "postImageResponses", ignore = true)
    PostResponse toResponse(Post post);

    default PostResponse toResponseWithImages(Post post, List<PostImageResponse> postImageResponses) {
        PostResponse response = toResponse(post);
        return new PostResponse(
                response.id(),
                response.title(),
                response.text(),
                response.heart(),
                response.report(),
                response.views(),
                response.visible(),
                response.memberResponse(),
                postImageResponses
        );
    }
}
