package com.paranmanzang.fileservice.model.repository;

import com.paranmanzang.fileservice.model.entity.File;
import com.paranmanzang.fileservice.model.repository.custom.FileCustomRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends ReactiveMongoRepository<File, String>, FileCustomRepository {
}
