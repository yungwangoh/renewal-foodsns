package mubex.renewal_foodsns.infrastructure.persistance;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.repository.PostRepository;
import mubex.renewal_foodsns.common.exception.ExceptionResolver;
import mubex.renewal_foodsns.common.exception.NotFoundException;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.infrastructure.persistance.generator.SqlDSL;
import mubex.renewal_foodsns.infrastructure.persistance.jpa.PostJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Post save(final Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    @Transactional
    public void saveAll(final List<Post> posts) {

        String sql = SqlDSL.generator(Post.class)
                .insertInto()
                .values()
                .getSql();

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Post post = posts.get(i);
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getText());
                ps.setString(3, post.getThumbnail());
                ps.setLong(4, post.getHeart());
                ps.setInt(5, post.getReport());
                ps.setLong(6, post.getViews());
                ps.setBoolean(7, post.isInDeleted());
                ps.setLong(8, post.getMember().getId());
                ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            }

            @Override
            public int getBatchSize() {
                return posts.size();
            }
        });
    }

    @Override
    public Post findById(final Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionResolver.NOT_FOUND_POST));
    }

    @Override
    public Page<Post> findByTitle(final String title, final Pageable pageable) {
        return postJpaRepository.findAllByTitle(title, pageable);
    }

    @Override
    public Slice<Post> findSliceAll(final Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAll(final Pageable pageable) {
        return postJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findByMemberId(final Long memberId, final Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByNickName(final String nickName, final Pageable pageable) {
        return postJpaRepository.findAllByMemberNickName(nickName, pageable);
    }

    @Override
    public boolean existsByTitle(final String title) {
        return postJpaRepository.existsByTitle(title);
    }

    @Override
    public Slice<Post> findAllByTitleOrText(final String title, final String text, final Pageable pageable) {
        return postJpaRepository.findAllByTitleOrText(title, text, pageable);
    }
}
