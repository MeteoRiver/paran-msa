package com.paranmanzang.fileservice.service.impl;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.paranmanzang.fileservice.model.domain.FileDeleteModel;
import com.paranmanzang.fileservice.model.domain.FileModel;
import com.paranmanzang.fileservice.model.entity.File;
import com.paranmanzang.fileservice.model.enums.FileType;
import com.paranmanzang.fileservice.model.repository.FileRepository;
import com.paranmanzang.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;


    @Value("${cloud.s3.bucket}")
    private String s3bucket;
    private final AmazonS3 amazonS3;

    public FileModel insert(MultipartFile file, String type, Long refId) {
        String folderName = type + "s/";
        String fileName = file.getOriginalFilename();
        String uploadName = folderName + UUID.randomUUID() + Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));

        return Optional.of(new PutObjectRequest(s3bucket, folderName, new ByteArrayInputStream(new byte[0]), new ObjectMetadata()))
                .map(this::createFolder)
                .map(__ -> uploadFileToStorage(file, uploadName))
                .map(__ -> saveFileMetadata(uploadName, refId, type))
                .map(this::convertToFileModel)
                .orElseThrow(() -> new RuntimeException("Failed to upload file"));
    }

    private PutObjectResult createFolder(PutObjectRequest request) {
        try {
            return amazonS3.putObject(request);
        } catch (SdkClientException e) {
            throw new RuntimeException("Failed to create folder: " + e.getMessage(), e);
        }
    }

    private PutObjectResult uploadFileToStorage(MultipartFile file, String uploadName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            return amazonS3.putObject(s3bucket, uploadName, file.getInputStream(), metadata);
        } catch (IOException | SdkClientException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    private File saveFileMetadata(String uploadName, Long refId, String type) {
        return Optional.of(File.builder()
                        .path(uploadName)
                        .refId(refId)
                        .type(FileType.fromType(type).getCode())
                        .uploadAt(LocalDateTime.now())
                        .build())
                .map(file -> reactiveMongoTemplate.insert(file).block())
                .orElseThrow(() -> new RuntimeException("Failed to save file metadata"));
    }


    @Override
    public List<FileModel> findByRefIds(List<Long> refIds, String type) {
        return Flux.fromIterable(refIds)
                .flatMap(refId -> fileRepository.findByRefId(refId, FileType.fromType(type).getCode())
                        .map(this::convertToFileModel))
                .collectList()
                .block();
    }

    public FileModel findByRefId(Long refId, String type) {
        return fileRepository.findByRefId(refId, FileType.fromType(type).getCode())
                .map(this::convertToFileModel)
                .block();
    }

    @Override
    public byte[] getFile(String path) throws IOException {
        return IOUtils.toByteArray(amazonS3
                .getObject(s3bucket, path)
                .getObjectContent());
    }

    public byte[] findFileByRefId(Long refId, String type) throws IOException {
        return IOUtils.toByteArray(amazonS3.getObject(s3bucket, findByRefId(refId, type).getPath()).getObjectContent());
    }

    @Override
    public Boolean delete(FileDeleteModel model) {
        amazonS3.deleteObject(s3bucket, model.getPath());
        fileRepository.deleteByPath(model.getPath()).block();
        return Boolean.TRUE;
    }

    private FileModel convertToFileModel(File file) {
        return Optional.ofNullable(file)
                .map(f -> FileModel.builder()
                        .id(f.getId())
                        .path(f.getPath())
                        .refId(f.getRefId())
                        .type(FileType.fromCode(f.getType()).getType())
                        .uploadAt(f.getUploadAt())
                        .build())
                .orElseThrow(() -> new RuntimeException("Failed to convert to FileModel"));
    }

}
