package mubex.renewal_foodsns.common.mapper.map;

import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PostMapper extends Mappable<PostResponse, Post>{

    @Override
    @Mapping(source = "member", target = "memberResponse")
    PostResponse toResponse(Post post);
}
