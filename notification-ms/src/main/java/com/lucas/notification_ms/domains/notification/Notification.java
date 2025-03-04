package com.lucas.notification_ms.domains.notification;

import com.lucas.notification_ms.domains.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "order_id")
    private Long orderId;

    private NotificationType type;

    public Notification(String content, String userId, LocalDateTime createdAt, Long orderId, NotificationType type) {
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
        this.orderId = orderId;
        this.type = type;
    }
}



