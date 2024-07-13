package mubex.renewal_foodsns.common.mapper;

import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberResponse toResponse(Member member);
}
