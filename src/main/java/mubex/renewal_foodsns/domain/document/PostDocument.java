package mubex.renewal_foodsns.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post")
public record PostDocument(
        @Id
        @Field(type = FieldType.Long)
        Long id,

        @Field(type = FieldType.Text)
        String title,

        @Field(type = FieldType.Text)
        String text,

        @Field(type = FieldType.Long)
        long heart,

        @Field(type = FieldType.Long)
        long views,

        @Field(type = FieldType.Boolean)
        boolean inDeleted
) {
}