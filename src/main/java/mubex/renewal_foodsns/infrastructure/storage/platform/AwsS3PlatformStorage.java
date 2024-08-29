package mubex.renewal_foodsns.infrastructure.storage.platform;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.property.AwsS3Properties;
import mubex.renewal_foodsns.common.util.StorageUtil;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Primary
@Repository
@RequiredArgsConstructor
public class AwsS3PlatformStorage implements PlatformStorage {

    private final S3Template s3Template;
    private final AwsS3Properties awsS3Properties;

    @Override
    public String process(final MultipartFile multipartFile) {
        return upload(multipartFile);
    }

    @Override
    public String process(final String userIdentifier, final MultipartFile multipartFile) {
        return upload(userIdentifier, multipartFile);
    }

    @Override
    public String update(final MultipartFile multipartFile) {

        s3Template.deleteObject(
                awsS3Properties.bucket(),
                Objects.requireNonNull(multipartFile.getOriginalFilename())
        );

        return upload(multipartFile);
    }

    @Override
    public String process(final byte[] content, final String format) {
        return "";
    }

    private String upload(final MultipartFile multipartFile) {
        try {
            final ObjectMetadata objectMetadata = ObjectMetadata.builder()
                    .contentType(multipartFile.getContentType())
                    .build();

            final S3Resource upload = s3Template.upload(
                    awsS3Properties.bucket(),
                    StorageUtil.generateS3Key(multipartFile.getOriginalFilename()),
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            return StorageUtil.CLOUD_FRONT.generate(upload.getFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String upload(final String userIdentifier, final MultipartFile multipartFile) {
        try {
            final ObjectMetadata objectMetadata = ObjectMetadata.builder()
                    .contentType(multipartFile.getContentType())
                    .build();

            final S3Resource upload = s3Template.upload(
                    awsS3Properties.bucket(),
                    StorageUtil.generateS3Key(userIdentifier, multipartFile.getOriginalFilename()),
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            return StorageUtil.CLOUD_FRONT.generate(upload.getFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
