package com.paranmanzang.fileservice.controller;

import com.paranmanzang.fileservice.model.domain.FileDeleteModel;
import com.paranmanzang.fileservice.service.impl.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Tag(name = "01. File")
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileServiceImpl fileService;

    @GetMapping("/list")
    @Operation(summary = "리스트 조회", description = "type의 refId인 파일 path 리스트를 조회합니다.", tags = {"01. File",})
    public ResponseEntity<?> findByRefId(@RequestParam("refIds") List<Long> refIds, @RequestParam("type") String type) {
        return ResponseEntity.ok(fileService.findByRefIds(refIds, type));
    }

    @GetMapping("")
    @Operation(summary = "파일 로드", description = "path에 해당하는 파일을 불러옵니다.")
    public ResponseEntity<?> getFile(@RequestParam("path") String path) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileService.getFile(path));
    }

    @GetMapping("/{refId}")
    @Operation(summary = "파일 로드", description = "type의 refId에 해당하는 파일을 불러옵니다.")
    public ResponseEntity<?> findFileByeRefId(@PathVariable("refId") Long refId, @RequestParam String type) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileService.findFileByRefId(refId, type));
    }

    @PostMapping("")
    @Operation(summary = "파일 저장", description = "파일을 업로드하고 파일정보를 db에 저장합니다.")
    public ResponseEntity<?> insert(MultipartFile file, String type, Long refId) {
        return ResponseEntity.ok(fileService.insert(file, type, refId));
    }

    @DeleteMapping("")
    @Operation(summary = "파일 삭제", description = "파일 클라우드 상에서 삭제하고 db 정보도 삭제합니다.")
    public ResponseEntity<?> delete(@RequestBody FileDeleteModel model) {
        return ResponseEntity.ok(fileService.delete(model));
    }

}