package mubex.renewal_foodsns.application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.processor.MultiPartFileProcessor;
import mubex.renewal_foodsns.common.call.AsyncCall;
import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.PostImageResponse;
import mubex.renewal_foodsns.domain.entity.Post;
import mubex.renewal_foodsns.domain.entity.PostImage;
import mubex.renewal_foodsns.domain.repository.PostImageRepository;
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
    private final Mappable<PostImageResponse, PostImage> mappable;

    @Transactional
    public List<PostImageResponse> create(Post post, List<MultipartFile> multipartFiles) {

        AsyncCall<MultipartFile, String> asyncCall = new AsyncCall<>(multipartFiles);

        List<String> fileNames = asyncCall.execute(10, fileProcessor::write);

        return fileNames.stream()
                .map(fileName -> savePostImage(fileName, post))
                .map(mappable::toResponse)
                .toList();
    }

    @Transactional
    public PostImageResponse create(Post post, MultipartFile multipartFile) {

        PostImage postImage = savePostImage(fileProcessor.write(multipartFile), post);

        return mappable.toResponse(postImage);
    }

    @Transactional
    public PostImageResponse thumbnail(Post post, MultipartFile multipartFile) {

        try {
            BufferedImage bufferedImage = Thumbnails.of(multipartFile.getInputStream())
                    .size(100, 100)
                    .asBufferedImage();

            String thumbnail = fileProcessor.thumbnail(
                    imageToByteArray(bufferedImage, multipartFile.getContentType()),
                    multipartFile.getContentType()
            );

            return mappable.toResponse(savePostImage(thumbnail, post));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<PostImageResponse> update(Post post, List<MultipartFile> multipartFiles) {

        List<PostImage> postImages = postImageRepository.findAllByPostId(post.getId());

        return IntStream.rangeClosed(0, multipartFiles.size() - 1)
                .mapToObj(idx -> update(multipartFiles, idx, postImages))
                .map(mappable::toResponse)
                .toList();
    }

    private PostImage update(List<MultipartFile> multipartFiles, int idx, List<PostImage> postImages) {
        PostImage postImage = postImages.get(idx);
        MultipartFile multipartFile = multipartFiles.get(idx);

        String updateFileName = fileProcessor.update(multipartFile);

        postImage.updateOriginFileName(updateFileName);
        return postImage;
    }

    private PostImage savePostImage(String fileName, Post savePost) {
        PostImage postImage = PostImage.builder()
                .originFileName(fileName)
                .post(savePost)
                .build();

        return postImageRepository.save(postImage);
    }

    private byte[] imageToByteArray(BufferedImage bufferedImage, String format) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
