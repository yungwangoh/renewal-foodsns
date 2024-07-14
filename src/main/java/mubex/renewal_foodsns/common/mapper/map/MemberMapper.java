package mubex.renewal_foodsns.common.mapper.map;

import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = ComponentModel.SPRING)
public interface MemberMapper extends Mappable<MemberResponse, Member> {

    @Override
    MemberResponse toResponse(Member member);
}
