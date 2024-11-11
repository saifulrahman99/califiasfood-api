package com.rahmandev.califiasfood.repository;

import com.rahmandev.califiasfood.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
}
