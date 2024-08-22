package mubex.renewal_foodsns.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Setting(settingPath = "/static/elastic/settings.json")
@Mapping(mappingPath = "/static/elastic/post-mapping.json")
@Document(indexName = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String title;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String text;

    @Field(type = FieldType.Long, index = false)
    private long heart;

    @Field(type = FieldType.Long, index = false)
    private long views;

    @Field(type = FieldType.Boolean, index = false)
    private boolean inDeleted;

    @Builder
    public PostDocument(final Long id, final String title, final String text, final long heart, final long views,
                        final boolean inDeleted) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.heart = heart;
        this.views = views;
        this.inDeleted = inDeleted;
    }
}