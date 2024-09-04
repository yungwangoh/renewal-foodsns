package mubex.renewal_foodsns.application.processor.multipart.video.convertor;

import com.google.common.net.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.processor.multipart.impl.CustomMultipartFile;
import mubex.renewal_foodsns.application.processor.multipart.selector.CompressionSelector;
import mubex.renewal_foodsns.application.processor.multipart.video.VideoProcessor;
import mubex.renewal_foodsns.common.util.FileUtils;
import mubex.renewal_foodsns.common.util.FileUtils.FileExt;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegBuilder.Strict;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class MpegConvertor implements VideoProcessor {

    private final FFmpeg ffMpeg;
    private final FFprobe ffProbe;

    @Override
    public MultipartFile compress(final MultipartFile multipartFile, final CompressionSelector selector) {

        try {
            final File inputFile = File.createTempFile(
                    "input",
                    FileUtils.extractFileExt(Objects.requireNonNull(multipartFile.getOriginalFilename()))
            );

            multipartFile.transferTo(inputFile);

            final File outputFile = new File(FileUtils.changeFileExt(
                    multipartFile.getOriginalFilename(),
                    FileExt.MP4.addDotExt()));

            final FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
                    .setInput(inputFile.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(outputFile.getAbsolutePath())
                    .setFormat(FileExt.MP4.getExt())
                    .disableSubtitle()
                    .setVideoCodec("libx264")
                    .setVideoBitRate(1464800)
                    .setVideoFrameRate(30)
                    .setVideoResolution(1280, 720)
                    .setStrict(Strict.EXPERIMENTAL)
                    .done();

            final FFmpegExecutor executor = new FFmpegExecutor(ffMpeg, ffProbe);

            executor.createJob(ffmpegBuilder).run();

            return CustomMultipartFile.builder()
                    .size(outputFile.length())
                    .contentType(MediaType.MP4_VIDEO.type())
                    .inputStream(new FileInputStream(outputFile))
                    .originalFilename(outputFile.getName())
                    .name(outputFile.getName())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
