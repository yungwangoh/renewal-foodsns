package mubex.renewal_foodsns.application.processor.multipart.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public class CustomMultipartFile implements MultipartFile {

    private final InputStream inputStream;
    private final String name;
    private final String originalFilename;
    private final String contentType;
    private final long size;

    public CustomMultipartFile(final InputStream inputStream, final String name, final String originalFilename,
                               final String contentType,
                               final long size) {
        this.inputStream = inputStream;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return inputStream.readAllBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public void transferTo(final File dest) throws IOException, IllegalStateException {
        Files.write(Paths.get(dest.getPath()), inputStream.readAllBytes());
    }
}
