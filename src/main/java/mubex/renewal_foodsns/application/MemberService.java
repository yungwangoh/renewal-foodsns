package mubex.renewal_foodsns.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.event.RegisteredSubscribeEvent;
import mubex.renewal_foodsns.application.login.LoginHandler;
import mubex.renewal_foodsns.domain.dto.response.MemberResponse;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.mapper.map.MemberMapper;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
import mubex.renewal_foodsns.domain.type.MemberRank;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void signUp(final String email, final String nickName, final String password, int profileId) {

        if (memberRepository.existsByNickName(nickName)) {
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
        MemberResponse memberResponse = loginHandler.signIn(email, password);

        Member member = memberRepository.findByNickName(memberResponse.nickName());

        publisher.publishEvent(new RegisteredSubscribeEvent(member.getId()));

        return memberResponse;
    }

    @Transactional
    public void signOut() {
        loginHandler.signOut();
    }

    @Transactional
    public MemberResponse updateNickName(final String nickName, final String updatedNickName) {
        Member member = memberRepository.findByNickName(nickName);

        if (!memberRepository.existsByNickName(updatedNickName)) {
            member.updateNickName(updatedNickName);

            return MemberMapper.INSTANCE.toResponse(member);
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
    public void markAsDeleted(final String nickName) {
        Member member = memberRepository.findByNickName(nickName);

        if (member.isInDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 유저입니다.");
        }

        member.markAsDeleted();
    }

    @Transactional
    public void addToBlacklist(final String nickName) {
        Member member = memberRepository.findByNickName(nickName);

        member.addToBlacklist();
    }

    @Transactional
    public void addToBlackList(final Long id) {
        Member member = memberRepository.findById(id);

        member.addToBlacklist();
    }

    @Transactional
    public Member addHeart(final Long id, final long heart) {
        Member member = memberRepository.findById(id);

        member.addHeart(heart);

        return member;
    }

    @Transactional
    public Member addReport(final Long id, final int report) {
        Member member = memberRepository.findById(id);

        member.addReport(report);

        return member;
    }

    public Member findAfterCheckBlackList(final Long id) {
        Member member = memberRepository.findById(id);

        member.checkMemberBlackList();

        return member;
    }

    public Member findMember(final Long id) {
        return memberRepository.findById(id);
    }

    public Member findMember(final String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    public List<MemberResponse> findByMemberRank(final MemberRank memberRank) {
        return memberRepository.findAllByMemberRank(memberRank)
                .stream()
                .map(MemberMapper.INSTANCE::toResponse)
                .toList();
    }

    public Page<MemberResponse> viewPagingMemberRank(final PageRequest pageRequest) {

        List<MemberResponse> list = memberRepository.findAll()
                .stream()
                .map(MemberMapper.INSTANCE::toResponse)
                .toList();

        return new PageImpl<>(list, pageRequest, list.size());
    }
}
