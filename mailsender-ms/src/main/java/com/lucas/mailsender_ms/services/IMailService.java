package com.lucas.mailsender_ms.services;

import com.lucas.mailsender_ms.domains.mail.Mail;
import com.lucas.mailsender_ms.domains.mail.dto.DeleteMailDto;
import com.lucas.mailsender_ms.domains.mail.dto.SendMailDto;

import java.util.List;

public interface IMailService {

    void send(SendMailDto data);
    void delete(DeleteMailDto data);
    List<Mail> getAll();

}
