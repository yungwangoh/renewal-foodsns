package mubex.renewal_foodsns.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.login.LoginHandler;
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

    @Transactional
    public void signUp(final String email, final String nickName, final String password) {

        Member member = Member.create(
                nickName,
                password,
                email,
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
    public String updateNickName(final String nickName, final String updatedNickName) {
        Member member = memberRepository.findByNickName(nickName);

        if(!memberRepository.existsByNickName(updatedNickName)) {
            member.updateNickName(updatedNickName);

            return memberRepository.findById(member.getId()).getNickName();
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

        member.markAsDeleted();
    }

    @Transactional
    public void addToBlacklist(final String nickName) {
        Member member = memberRepository.findByNickName(nickName);

        if(member.getReport() >= 10) {
            member.addToBlacklist();
        } else {
            throw new IllegalStateException();
        }
    }

    public List<Member> findByMemberRank(MemberRank memberRank) {
        return memberRepository.findAllByMemberRank(memberRank);
    }

    public Page<Member> viewPagingMemberRank(PageRequest pageRequest) {

        List<Member> members = memberRepository.findAll();

        return new PageImpl<>(members, pageRequest, members.size());
    }
}
