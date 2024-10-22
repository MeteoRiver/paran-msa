package com.paranmanzang.fileservice.service;

import com.paranmanzang.fileservice.model.domain.FileDeleteModel;
import com.paranmanzang.fileservice.model.domain.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileModel insert(MultipartFile file, String type, Long refId) throws IOException;
    List<?> findByRefIds(List<Long> refIds, String type);
    byte[] getFile(String path) throws IOException;
    byte[] findFileByRefId(Long refId, String type) throws IOException;
    Boolean delete(FileDeleteModel model);
}
