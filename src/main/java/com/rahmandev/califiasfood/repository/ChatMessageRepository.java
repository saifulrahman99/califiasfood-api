package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
}
