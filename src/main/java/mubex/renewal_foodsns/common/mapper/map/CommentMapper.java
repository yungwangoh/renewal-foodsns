package mubex.renewal_foodsns.common.mapper.map;

import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import mubex.renewal_foodsns.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CommentMapper extends Mappable<CommentResponse, Comment> {

    @Override
    @Mapping(source = "member.nickName", target = "nickName")
    @Mapping(source = "post.id", target = "postId")
    CommentResponse toResponse(Comment comment);
}
