package mubex.renewal_foodsns.infrastructure.migration.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mubex.renewal_foodsns.domain.document.PostDocument;
import org.springframework.jdbc.core.RowMapper;

public class DataMigrationRowMapper implements RowMapper<PostDocument> {

    @Override
    public PostDocument mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return PostDocument.builder()
                .id(rs.getLong("id"))
                .text(rs.getString("text"))
                .title(rs.getString("title"))
                .heart(rs.getLong("heart"))
                .views(rs.getLong("views"))
                .inDeleted(rs.getBoolean("in_deleted"))
                .build();
    }
}
