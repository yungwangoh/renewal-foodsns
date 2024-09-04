package mubex.renewal_foodsns.common.config.ffmpeg;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.common.property.FFMpegProperties;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FFMpegConfig {

    private final FFMpegProperties ffmpegProperties;

    @Bean
    public FFmpeg ffMpeg() {
        try {
            log.info("ffmpeg path = {}", ffmpegProperties.path());
            return new FFmpeg(ffmpegProperties.path());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public FFprobe ffProbe() {
        try {
            log.info("ffprobe path = {}", ffmpegProperties.path());
            return new FFprobe(ffmpegProperties.probePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
