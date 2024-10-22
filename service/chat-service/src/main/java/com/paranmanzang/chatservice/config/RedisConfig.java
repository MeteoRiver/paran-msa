package com.paranmanzang.chatservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranmanzang.chatservice.model.domain.message.ChatMessageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig {


    private final ObjectMapper objectMapper;

    @Bean
    public ReactiveRedisMessageListenerContainer reactiveRedisMessageListenerContainer(ReactiveRedisConnectionFactory factory) {
        var container = new ReactiveRedisMessageListenerContainer(factory);

        container.receive(new PatternTopic("room:*"))
                .doOnNext(redisMessage -> {
                    try {
                        ChatMessageModel chatMessage = objectMapper.readValue((String) redisMessage.getMessage(), ChatMessageModel.class);
                        log.info("Received message in channel {}: {}", redisMessage.getChannel(), chatMessage.getMessage());
                    } catch (Exception e) {
                        log.error("Failed to process Redis message: {}", (String) redisMessage.getMessage(), e);
                    }
                })
                .subscribe();

        return container;
    }

    @Bean
    public ReactiveRedisTemplate<String, ChatMessageModel> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(
                factory,
                RedisSerializationContext.<String, ChatMessageModel>newSerializationContext(new StringRedisSerializer())
                        .value(new Jackson2JsonRedisSerializer<>(ChatMessageModel.class))
                        .build()
        );
    }
}
