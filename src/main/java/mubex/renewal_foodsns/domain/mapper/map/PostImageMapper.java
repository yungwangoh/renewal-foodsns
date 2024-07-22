package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.entity.PostImage;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostImageMapper extends Mappable<PostImageResponse, PostImage> {

    PostImageMapper INSTANCE = Mappers.getMapper(PostImageMapper.class);

    @Override
    PostImageResponse toResponse(PostImage postImage);
}
