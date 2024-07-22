package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PostPageMapper extends Mappable<PostPageResponse, Post> {

    @Override
    @Mapping(source = "member.nickName", target = "nickName")
    @Mapping(source = "inDeleted", target = "visible")
    PostPageResponse toResponse(Post post);
}
