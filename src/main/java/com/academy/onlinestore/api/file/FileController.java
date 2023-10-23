package com.academy.onlinestore.api.file;

import com.academy.onlinestore.api.file.web.FileDto;
import com.academy.onlinestore.base.BaseApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/download/{name}")
    public ResponseEntity<?> downloadByName(@PathVariable String name){
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @DeleteMapping
    public void deleteAll(){
        fileService.deleteAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name){
        fileService.deleteByName(name);
    }

    @GetMapping
    public List<FileDto> getAllFiles(){
        return fileService.findAll();
    }

    @GetMapping("/{name}")
    public FileDto getByName(@PathVariable String name) throws IOException {
        return fileService.findByName(name);
    }

    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileDto> uploadMultipleFiles(@RequestPart List<MultipartFile> files){
        return fileService.uploadMultiple(files);
    }

    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseApiResponse<FileDto>> uploadSingleFile(@RequestPart @Valid MultipartFile file){
        FileDto fileDto = fileService.uploadSingle(file);
        BaseApiResponse response = BaseApiResponse.builder()
                .message("File uploaded successfully.")
                .code(2000)
                .status(HttpStatus.OK)
                .data(fileDto)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

}
