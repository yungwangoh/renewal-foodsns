package mubex.renewal_foodsns.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class FileUtilsTest {

    @Test
    void content_type이_image인지_video인지_확인하기_위해_파싱한다() {

        String content = FileUtils.parseContentType("image/jpeg");

        assertThat(content).isEqualTo("image");
    }
}