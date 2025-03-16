package com.lucas.mailsender_ms.services.impl;

import com.lucas.mailsender_ms.domains.mail.Mail;
import com.lucas.mailsender_ms.domains.mail.dto.DeleteMailDto;
import com.lucas.mailsender_ms.domains.mail.dto.SendMailDto;
import com.lucas.mailsender_ms.domains.mail.exceptions.InvalidDataCreateMail;
import com.lucas.mailsender_ms.domains.mail.exceptions.MailNotFoundException;
import com.lucas.mailsender_ms.repositories.MailRepository;
import com.lucas.mailsender_ms.services.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MailServiceImpl implements IMailService {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(SendMailDto data) {
       try{
           if(data.content() == null || data.content().isEmpty() ||
                   data.to() == null || data.to().isEmpty() ||
                   data.from() == null || data.from().isEmpty() ||
                   data.userId() == null || data.userId().isEmpty() ||
                   data.type() == null){

               throw new InvalidDataCreateMail("Invalid data during creation of email");
           }

           var mail = Mail
                   .builder()
                   .to(data.to())
                   .from(data.from())
                   .subject(data.subject())
                   .type(data.type())
                   .content(data.content())
                   .sendedAt(LocalDateTime.now())
                   .userId(data.userId())
                   .build();

           mailRepository.save(mail);

           SimpleMailMessage message = new SimpleMailMessage();

           message.setFrom(data.from());
           message.setTo(data.to());
           message.setSubject(data.subject());
           message.setTo(data.content());

           javaMailSender.send(message);

           log.info("Email enviado com sucesso. {}", mail);
       }catch (Exception e){
           log.error("Erro ao enviar o email {}", e.getMessage());
       }
    }

    @Override
    public void delete(DeleteMailDto data) {
        if(data.mailId() == null || data.userId() == null || data.userId().isEmpty()){
            throw new InvalidDataCreateMail("Invalid data during deletion of email");
        }

        var mail = mailRepository.findById(data.mailId());

        if(mail.isEmpty()){
            throw new MailNotFoundException("Cannot find the mail by the id");
        }

        mailRepository.delete(mail.get());
    }

    @Override
    public List<Mail> getAll() {
        return mailRepository.findAll();
    }
}
