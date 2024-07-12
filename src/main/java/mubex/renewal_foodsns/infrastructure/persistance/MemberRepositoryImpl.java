package mubex.renewal_foodsns.infrastructure.persistance;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.domain.entity.Member;
import mubex.renewal_foodsns.domain.exception.NotFoundException;
import mubex.renewal_foodsns.domain.repository.MemberRepository;
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
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public Member findByNickName(String nickName) {
        return memberJpaRepository.findByNickName(nickName)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public List<Member> findAllByMemberRank(MemberRank memberRank) {
        return memberJpaRepository.findAllByMemberRank(memberRank);
    }

    @Override
    public boolean existsByEmailAndPassword(String email, String password) {
        return memberJpaRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public Member findByEmailAndPassword(String email, String password) {
        return memberJpaRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_MEMBER));
    }

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public boolean existsByNickName(String nickName) {
        return memberJpaRepository.existsByNickName(nickName);
    }
}
