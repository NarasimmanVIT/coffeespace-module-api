package com.coffeespace.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PresenceService {

    private final Map<Long, Boolean> onlineStatus = new ConcurrentHashMap<>();
    private final Map<Long, LocalDateTime> lastSeen = new ConcurrentHashMap<>();

    public void setOnline(Long profileId) {
        onlineStatus.put(profileId, true);
        log.info("✅ User {} is online", profileId);
    }

    public void setOffline(Long profileId) {
        onlineStatus.put(profileId, false);
        lastSeen.put(profileId, LocalDateTime.now());
        log.info("❌ User {} went offline at {}", profileId, lastSeen.get(profileId));
    }

    public boolean isOnline(Long profileId) {
        return onlineStatus.getOrDefault(profileId, false);
    }

    public LocalDateTime getLastSeen(Long profileId) {
        return lastSeen.get(profileId);
    }
}
