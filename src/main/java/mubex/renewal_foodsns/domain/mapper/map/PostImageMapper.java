package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.entity.PostImage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PostImageMapper extends Mappable<PostImageResponse, PostImage> {

    @Override
    PostImageResponse toResponse(PostImage postImage);
}
