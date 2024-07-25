package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.MemberRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.util.PasswordUtil;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.exception.LoginException;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.domain.type.MemberRank;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.MemberJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    @Transactional
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Member findById(final Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public Member findByNickName(final String nickName) {
        return memberJpaRepository.findByNickName(nickName)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public List<Member> findAllByMemberRank(final MemberRank memberRank) {
        return memberJpaRepository.findAllByMemberRank(memberRank);
    }

    @Override
    public boolean existsByEmailAndPassword(final String email, final String password) {
        return memberJpaRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public Member findByEmailAndPassword(final String email, final String password) {
        final Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));

        if (PasswordUtil.match(member.getPassword(), password)) {
            return member;
        } else {
            throw new LoginException(ExceptionResolver.LOGIN_FAILED);
        }
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public boolean existsByNickName(final String nickName) {
        return memberJpaRepository.existsByNickName(nickName);
    }
}
