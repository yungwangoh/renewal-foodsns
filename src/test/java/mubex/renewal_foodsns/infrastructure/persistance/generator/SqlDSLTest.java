package mubex.renewal_foodsns.infrastructure.persistance.generator;

import static org.assertj.core.api.Assertions.assertThat;

import mubex.renewal_foodsns.domain.entity.Post;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class SqlDSLTest {

    @Test
    void SQL_DSL은_INSERT_INTO_문을_작성해준다() {
        String expectedSql = "insert into post (title, text, thumbnail, heart, report, views, in_deleted, member_id, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String actualSql = SqlDSL.generator(Post.class)
                .insertInto()
                .values()
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }

    @Test
    void SQL_DSL은_SELECT_FROM_문을_작성해준다() {
        String expectedSql = "select * from post";

        String actualSql = SqlDSL.generator(Post.class)
                .selectFrom("*")
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }

    @Test
    void SQL_DSL은_SELECT_FROM_projection을_제공해준다() {
        String expectedSql = "select id, name, created_at, updated_at from post";

        String actualSql = SqlDSL.generator(Post.class)
                .selectFrom("id", "name", "created_at", "updated_at")
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }

    @Test
    void SQL_DSL은_SELECT_FROM_WHERE_문을_작성해준다() {
        String expectedSql = "select * from post where id = 1";

        String actualSql = SqlDSL.generator(Post.class)
                .selectFrom("*")
                .where("id", 1)
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }

    @Test
    void SQL_DSL은_SELECT_FROM_WHERE_AND_문을_작성해준다() {
        String expectedSql = "select * from post where id = 1 and name = 1";

        String actualSql = SqlDSL.generator(Post.class)
                .selectFrom("*")
                .where("id", 1)
                .and("name", 1)
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }

    @Test
    void SQL_DSL은_SELECT_FROM_WHERE_OR_문을_작성해준다() {
        String expectedSql = "select * from post where id = 1 or name = 1";

        String actualSql = SqlDSL.generator(Post.class)
                .selectFrom("*")
                .where("id", 1)
                .or("name", 1)
                .getSql();

        assertThat(actualSql).isEqualTo(expectedSql);
    }
}