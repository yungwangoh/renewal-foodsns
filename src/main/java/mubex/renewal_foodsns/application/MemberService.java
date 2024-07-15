package mubex.renewal_foodsns.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.common.mapper.map.MemberMapper;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginHandler loginHandler;
    private final Mappable<MemberResponse, Member> mappable;

    @Transactional
    public void signUp(final String email, final String nickName, final String password, int profileId) {

        if(memberRepository.existsByNickName(nickName)) {
            throw new IllegalArgumentException("닉네임이 중복 됩니다.");
        }

        Member member = Member.create(
                nickName,
                password,
                email,
                profileId,
                0,
                0,
                false,
                MemberRank.NORMAL,
                false);

        memberRepository.save(member);
    }

    @Transactional
    public MemberResponse signIn(final String email, final String password) {
        return loginHandler.signIn(email, password);
    }

    @Transactional
    public void signOut(final String nickName) {
        loginHandler.signOut(nickName);
    }

    @Transactional
    public MemberResponse updateNickName(final String nickName, final String updatedNickName) {
        Member member = memberRepository.findByNickName(nickName);

        if(!memberRepository.existsByNickName(updatedNickName)) {
            member.updateNickName(updatedNickName);

            return mappable.toResponse(member);
        } else {
            throw new IllegalArgumentException("변경하고자 하는 닉네임이 이미 존재합니다.");
        }
    }

    @Transactional
    public void updatePassword(final String nickName, final String updatePassword) {
        Member member = memberRepository.findByNickName(nickName);

        member.updatePassword(updatePassword);
    }

    @Transactional
    public void addHeart(final String nickName, long heart) {
        Member member = memberRepository.findByNickName(nickName);

        member.addHeart(heart);
    }

    @Transactional
    public void addReport(final String nickName, int report) {
        Member member = memberRepository.findByNickName(nickName);

        member.addReport(report);
    }

    @Transactional
    public void markAsDeleted(final String nickName) {
        Member member = memberRepository.findByNickName(nickName);

        if(member.isInDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 유저입니다.");
        }

        member.markAsDeleted();
    }

    @Transactional
    public void addToBlacklist(final String nickName) {
        Member member = memberRepository.findByNickName(nickName);

        if(member.checkMemberBlackList()) {
            member.addToBlacklist();
        } else {
            throw new IllegalStateException("이미 블랙리스트 기준에 적합하지 않은 유저입니다.");
        }
    }

    public List<MemberResponse> findByMemberRank(MemberRank memberRank) {
        return memberRepository.findAllByMemberRank(memberRank)
                .stream()
                .map(mappable::toResponse)
                .toList();
    }

    public Page<MemberResponse> viewPagingMemberRank(PageRequest pageRequest) {

        List<MemberResponse> list = memberRepository.findAll()
                .stream()
                .map(mappable::toResponse)
                .toList();

        return new PageImpl<>(list, pageRequest, list.size());
    }
}
