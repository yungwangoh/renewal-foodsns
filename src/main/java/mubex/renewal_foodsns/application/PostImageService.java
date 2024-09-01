package mubex.renewal_foodsns.application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.call.AsyncCall;
import mubex.renewal_foodsns.application.processor.multipart.MultiPartFileProcessor;
import mubex.renewal_foodsns.application.repository.PostImageRepository;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostImage;
import mubex.renewal_foodsns.domain.mapper.map.PostImageMapper;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImageService {

    private final MultiPartFileProcessor fileProcessor;
    private final PostImageRepository postImageRepository;

    @Transactional
    public List<PostImageResponse> create(final Post post, final List<MultipartFile> multipartFiles) {

        final AsyncCall<MultipartFile, String> asyncCall = new AsyncCall<>(multipartFiles);

        final List<String> fileNames = asyncCall.execute(10, fileProcessor::write);

        return fileNames.stream()
                .map(fileName -> savePostImage(fileName, post))
                .map(PostImageMapper.INSTANCE::toResponse)
                .toList();
    }

    @Transactional
    public PostImageResponse create(final Post post, final MultipartFile multipartFile) {

        final PostImage postImage = savePostImage(fileProcessor.write(multipartFile), post);

        return PostImageMapper.INSTANCE.toResponse(postImage);
    }

    @Deprecated
    @Transactional
    public PostImageResponse thumbnail(final Post post, final MultipartFile multipartFile) {

        try {
            final BufferedImage bufferedImage = Thumbnails.of(multipartFile.getInputStream())
                    .size(100, 100)
                    .asBufferedImage();

            final String thumbnail = fileProcessor.thumbnail(
                    imageToByteArray(bufferedImage, multipartFile.getContentType()),
                    multipartFile.getContentType()
            );

            return PostImageMapper.INSTANCE.toResponse(savePostImage(thumbnail, post));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<PostImageResponse> update(final Post post, final List<MultipartFile> multipartFiles) {

        final List<PostImage> postImages = postImageRepository.findAllByPostId(post.getId());

        return IntStream.rangeClosed(0, multipartFiles.size() - 1)
                .mapToObj(idx -> update(multipartFiles, idx, postImages))
                .map(PostImageMapper.INSTANCE::toResponse)
                .toList();
    }

    private PostImage update(final List<MultipartFile> multipartFiles,
                             final int idx,
                             final List<PostImage> postImages) {

        final PostImage postImage = postImages.get(idx);

        final MultipartFile multipartFile = multipartFiles.get(idx);

        final String updateFileName = fileProcessor.update(multipartFile);

        postImage.updateOriginFileName(updateFileName);
        return postImage;
    }

    private PostImage savePostImage(final String fileName, final Post savePost) {
        final PostImage postImage = PostImage.builder()
                .originFileName(fileName)
                .post(savePost)
                .build();

        return postImageRepository.save(postImage);
    }

    private byte[] imageToByteArray(final BufferedImage bufferedImage, final String format) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
