package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MemberMapper extends Mappable<MemberResponse, Member> {

    @Override
    MemberResponse toResponse(Member member);
}
