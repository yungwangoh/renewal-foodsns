package mubex.renewal_foodsns.infrastructure.storage.platform;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.common.property.CloudProperties;
import mubex.renewal_foodsns.infrastructure.storage.PlatformStorage;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Repository
@RequiredArgsConstructor
public class GoogleCloudPlatformStorage implements PlatformStorage {

    private final CloudProperties cloudProperties;

    @Override
    public String process(MultipartFile multipartFile) {

        try {
            String uuid = UUID.randomUUID().toString();

            String ext = multipartFile.getContentType();

            BlobInfo blobInfo = BlobInfo.newBuilder(cloudProperties.bucket(), uuid)
                    .setContentType(ext)
                    .build();

            InputStream keyFile = ResourceUtils.getURL(cloudProperties.credentials().location()).openStream();
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build()
                    .getService();

            storage.create(blobInfo, multipartFile.getBytes());

            return multipartFile.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
