package com.lucas.mailsender_ms.domains.mail;

import com.lucas.mailsender_ms.domains.mail.enums.MailType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "mails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String from;
    private String to;
    private String subject;
    private String content;

    @Column(name = "user_id")
    private String userId;

    private MailType type;

    @Column(name = "sended_at")
    private LocalDateTime sendedAt;

    public Mail(String from, String to, String subject, String content, String userId, MailType type, LocalDateTime sendedAt) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.userId = userId;
        this.type = type;
        this.sendedAt = sendedAt;
    }
}

