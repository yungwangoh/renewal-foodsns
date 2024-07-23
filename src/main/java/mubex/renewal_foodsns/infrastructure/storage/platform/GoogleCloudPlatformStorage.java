package mubex.renewal_foodsns.infrastructure.storage.platform;

import static mubex.renewal_foodsns.common.util.UriUtil.GCS_URI;

import com.google.auth.oauth2.GoogleCredentials;
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
    public String process(final MultipartFile multipartFile) {

        try {
            final StorageInfo storageInfo = getStorageInfo(multipartFile.getContentType());

            final Storage storage = getStorage();

            storage.create(storageInfo.blobInfo(), multipartFile.getBytes());

            return GCS_URI.generate(cloudProperties.bucket(), storageInfo.uuid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String process(final byte[] content, final String format) {

        try {
            final StorageInfo storageInfo = getStorageInfo(format);

            final Storage storage = getStorage();

            storage.create(storageInfo.blobInfo, content);

            return GCS_URI.generate(cloudProperties.bucket(), storageInfo.uuid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String update(final MultipartFile multipartFile) {

        try {
            final StorageInfo storageInfo = getStorageInfo(multipartFile.getContentType());

            final Storage storage = getStorage();

            if (storage.get(storageInfo.blobInfo().getBlobId()) != null) {
                storage.delete(storageInfo.blobInfo().getBlobId());
            }

            storage.create(storageInfo.blobInfo(), multipartFile.getBytes());

            return GCS_URI.generate(cloudProperties.bucket(), storageInfo.uuid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Storage getStorage() throws IOException {
        final InputStream keyFile = ResourceUtils.getURL(cloudProperties.credentials().location()).openStream();
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();
    }

    private StorageInfo getStorageInfo(final String format) {
        final String uuid = UUID.randomUUID().toString();

        final BlobInfo blobInfo = BlobInfo.newBuilder(cloudProperties.bucket(), uuid)
                .setContentType(format)
                .build();

        return new StorageInfo(uuid, blobInfo);
    }

    private record StorageInfo(String uuid, BlobInfo blobInfo) {
    }
}
