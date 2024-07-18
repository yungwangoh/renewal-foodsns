package mubex.renewal_foodsns.domain.dto.request.update;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record UpdatePostParam(
        Long postId,
        String title,
        String text,
        List<MultipartFile> multipartFiles
) {
}
