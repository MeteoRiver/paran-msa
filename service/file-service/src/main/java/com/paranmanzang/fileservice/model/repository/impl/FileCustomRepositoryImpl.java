package com.paranmanzang.fileservice.model.repository.impl;

import com.paranmanzang.fileservice.model.entity.File;
import com.paranmanzang.fileservice.model.repository.custom.FileCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FileCustomRepositoryImpl implements FileCustomRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    
    @Override
    public Mono<File> findByRefId(Long refId, int type) {
        return reactiveMongoTemplate.findOne(Query.query(Criteria.where("refId").is(refId).and("type").is(type)), File.class);
    }

    @Override
    public Mono<?> findByPath(String path) {
        return reactiveMongoTemplate.findOne(Query.query(Criteria.where("path").is(path)), File.class);
    }

    @Override
    public Mono<Boolean> deleteByPath(String path) {
        return findByPath(path).flatMap(file ->
                        reactiveMongoTemplate.remove(Query.query(
                                Criteria.where("path").is(path)
                        ), File.class))
                .flatMap(deleteResult -> Mono.just(deleteResult.getDeletedCount() > 0))
                .defaultIfEmpty(false);
    }
}
