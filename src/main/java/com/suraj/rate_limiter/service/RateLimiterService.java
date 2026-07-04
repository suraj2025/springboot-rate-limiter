package com.suraj.rate_limiter.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.suraj.rate_limiter.model.RequestInfo;

import jakarta.annotation.PostConstruct;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    // private final ConcurrentHashMap<String, RequestInfo> requestCounts = new
    // ConcurrentHashMap<>();

    // private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SIZE_MS = 60_000;
    // Fixed window rate limiting implementation
    // public boolean allowRequest(String clientIp) {

    // long currentWindow = System.currentTimeMillis() / WINDOW_SIZE_MS;

    // RequestInfo requestInfo = requestCounts.get(clientIp);

    // // Case 1 - First Request
    // if (requestInfo == null) {

    // RequestInfo request = new RequestInfo();
    // request.setWindow(currentWindow);
    // request.setRequestCount(1);

    // requestCounts.put(clientIp, request);

    // return true;
    // }

    // // Case 2 - Same Window
    // if (requestInfo.getWindow() == currentWindow) {

    // if (requestInfo.getRequestCount() >= MAX_REQUESTS) {
    // return false;
    // }

    // requestInfo.setRequestCount(
    // requestInfo.getRequestCount() + 1);

    // return true;
    // }

    // // Case 3 - New Window
    // requestInfo.setWindow(currentWindow);
    // requestInfo.setRequestCount(1);

    // return true;
    // }
    // ------Rate Limiter Implementation using Sliding Window Algorithm------ with
    // No Redis -----
    // private static final long WINDOW_SIZE_MS = 60_000;
    // private final ConcurrentHashMap<String, Queue<Long>> requestCounts = new
    // ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 5;

    // public boolean allowRequest(String clientIp) {
    // long currentTime = System.currentTimeMillis();
    // Queue<Long> clientRequests = requestCounts.get(clientIp);
    // System.out.println("Client Requests: " + clientRequests);
    // if (clientRequests == null) {
    // clientRequests = new LinkedList<>();
    // clientRequests.add(currentTime);
    // requestCounts.put(clientIp, clientRequests);
    // return true;
    // }

    // while (!clientRequests.isEmpty() && currentTime - clientRequests.peek() >
    // WINDOW_SIZE_MS) {
    // clientRequests.poll();
    // }

    // if (clientRequests.size() < MAX_REQUESTS) {
    // clientRequests.add(currentTime);
    // return true;
    // }
    // return false;
    // }
     // ------Rate Limiter Implementation using Fixed Window Algorithm------ with Redis
    // private final StringRedisTemplate redisTemplate;

    // public RateLimiterService(StringRedisTemplate redisTemplate) {
    // this.redisTemplate = redisTemplate;
    // }

    // @PostConstruct
    // public void testRedis() {

    // redisTemplate.opsForValue().set("name", "Suraj");

    // String value = redisTemplate.opsForValue().get("name");

    // System.out.println(value);
    // }

    // public boolean allowRequest(String clientIp) {

    // String key = "rate_limit:" + clientIp;

    // Long requestCount = redisTemplate
    // .opsForValue()
    // .increment(key);
    // System.out.println("Request Count for " + key + ": " + requestCount + " time
    // remaining: " + redisTemplate.getExpire(key));
    // if (requestCount == 1) {
    // redisTemplate.expire(key, Duration.ofMinutes(1));
    // }

    // return requestCount <= MAX_REQUESTS;
    // }
    
    // ------Rate Limiter Implementation using Sliding Window Algorithm------ with Redis + Lua Script(Atomic Operations)
    private final StringRedisTemplate redisTemplate;

    private final RedisScript<Long> slidingWindowScript;

    public RateLimiterService(
            StringRedisTemplate redisTemplate,
            RedisScript<Long> slidingWindowScript) {

        this.redisTemplate = redisTemplate;
        this.slidingWindowScript = slidingWindowScript;
    }

    public boolean allowRequest(String clientIp) {

        String key = "rate_limit:" + clientIp;

        long now = System.currentTimeMillis();

        String member = now + ":" + UUID.randomUUID();

        Long result = redisTemplate.execute(

                slidingWindowScript,

                List.of(key),

                String.valueOf(now),
                String.valueOf(WINDOW_SIZE_MS),
                String.valueOf(MAX_REQUESTS),
                member);

        return result != null && result == 1;
    }

}
