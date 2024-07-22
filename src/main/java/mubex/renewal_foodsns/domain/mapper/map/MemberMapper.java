package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper extends Mappable<MemberResponse, Member> {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Override
    MemberResponse toResponse(Member member);
}
