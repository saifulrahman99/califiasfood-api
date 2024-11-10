package com.rahmandev.califiasfood.entity;
import com.rahmandev.califiasfood.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.CHAT_MESSAGE)
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Column(name = "content",nullable = false)
    private String content;
    @Column(name = "sender",nullable = false)
    private String sender;
    @Column(name = "receiver",nullable = false)
    private String receiver;
    @Column(name = "sent_at",nullable = false)
    private Timestamp sentAt;
    @Column(name = "is_read",nullable = false)
    private Boolean isRead;
}
