package mubex.renewal_foodsns.domain.dto.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PostParam(
        String title,
        String text,
        List<MultipartFile> multipartFiles
) {
}
