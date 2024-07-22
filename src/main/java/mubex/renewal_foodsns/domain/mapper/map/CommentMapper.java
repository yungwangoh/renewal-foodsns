package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.dto.response.CommentResponse;
import mubex.renewal_foodsns.domain.entity.Comment;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper extends Mappable<CommentResponse, Comment> {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    
    @Override
    @Mapping(source = "member.nickName", target = "nickName")
    @Mapping(source = "post.id", target = "postId")
    CommentResponse toResponse(Comment comment);
}
