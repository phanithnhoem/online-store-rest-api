package com.academy.onlinestore.api.file;

import com.academy.onlinestore.api.file.web.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    Resource downloadByName(String name);
    void deleteByName(String name);
    void deleteAll();
    List<FileDto> findAll();
    FileDto findByName(String name) throws IOException;
    FileDto uploadSingle(MultipartFile file);
    List<FileDto> uploadMultiple(List<MultipartFile> files);
}
