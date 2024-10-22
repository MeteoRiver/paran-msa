package com.paranmanzang.fileservice.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {


    @Value("${cloud.s3.access-key}")
    private String s3AccessKey;

    @Value("${cloud.s3.access-secret-key}")
    private String s3SecretKey;

    @Bean
    public AmazonS3 amazonS3() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setMaxConnections(200);  // 최대 연결 수 설정 (기본값: 50)
        clientConfig.setConnectionTimeout(10000);  // 연결 타임아웃 설정 (밀리초 단위)
        clientConfig.setSocketTimeout(10000);  // 소켓 타임아웃 설정 (밀리초 단위)
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(s3AccessKey, s3SecretKey)
                        )
                )
                .withClientConfiguration(clientConfig)
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }
}
