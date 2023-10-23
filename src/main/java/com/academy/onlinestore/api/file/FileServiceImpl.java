package com.academy.onlinestore.api.file;

import com.academy.onlinestore.api.file.web.FileDto;
import com.academy.onlinestore.util.GeneratorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService{

    @Value("${file.server-path}")
    private String serverPath;
    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Value("${file.download-uri}")
    private String downloadUri;

    @Override
    public Resource downloadByName(String name) {
        // Get base uri from server
        Path path = Paths.get(serverPath + name);

        if (Files.exists(path)){
            // Start loading file by name
            Resource resource = UrlResource.from(path.toUri());
            return resource;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource file doesn't exits.");
    }

    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath + name);
        try {
            boolean isDeleted = Files.deleteIfExists(path);
            if (!isDeleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource file doesn't exits.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        Path path = Paths.get(serverPath);
        try {
            Stream<Path> paths = Files.list(path);
            List<Path> pathList = paths.toList();
            for (Path p : pathList){
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileDto> findAll() {
        Path path = Paths.get(serverPath);
        List<FileDto> fileDtoList = new ArrayList<>();
        try {
            Stream<Path> paths = Files.list(path);
            List<Path> pathList = paths.toList();
            for (Path p : pathList){
                Resource resource = UrlResource.from(p.toUri());
                fileDtoList.add(FileDto.builder()
                        .name(resource.getFilename())
                        .uri(fileBaseUri + resource.getFilename())
                        .downloadUri(downloadUri + resource.getFilename())
                        .extension(this.getExtension(this.getExtension(resource.getFilename())))
                        .size(resource.contentLength())
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileDtoList;
    }

    @Override
    public FileDto findByName(String name) throws IOException {
        // Get base uri from server
        Path path = Paths.get(serverPath + name);

        if (Files.exists(path)){
            // Start loading file by name
            Resource resource = UrlResource.from(path.toUri());
            return FileDto.builder()
                    .name(name)
                    .uri(fileBaseUri + name)
                    .downloadUri(downloadUri + name)
                    .extension(this.getExtension(name))
                    .size(resource.contentLength())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource file doesn't exits.");
    }
    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return this.saveFile(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        return files.stream()
                .map(file -> this.saveFile(file))
                .collect(Collectors.toList());
    }

    private FileDto saveFile(MultipartFile file){
        // Generate file name
        String name = GeneratorUtil.generateUniqueFileName() + "." + this.getExtension(file.getOriginalFilename());
        String uri = fileBaseUri + name;
        Long size = file.getSize();
        String contentType = file.getContentType();

        // Create absolute file path
        Path path = Paths.get(serverPath + name);

        // Copy file server directory
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileDto.builder()
                .name(name)
                .uri(uri)
                .extension(this.getExtension(file.getOriginalFilename()))
                .size(size)
                .build();
    }

    // Get file extension
    private String getExtension(String name){
        int lastDotIndex = name.lastIndexOf(".");
        return name.substring(lastDotIndex + 1);
    }
}
