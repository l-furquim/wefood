package com.lucas.mailsender_ms.repositories;

import com.lucas.mailsender_ms.domains.mail.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
}
