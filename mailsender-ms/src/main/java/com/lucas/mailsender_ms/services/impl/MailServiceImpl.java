package com.lucas.mailsender_ms.services.impl;

import com.lucas.mailsender_ms.domains.mail.Mail;
import com.lucas.mailsender_ms.domains.mail.dto.DeleteMailDto;
import com.lucas.mailsender_ms.domains.mail.dto.SendMailDto;
import com.lucas.mailsender_ms.domains.mail.exceptions.ErrorWhileSendingMail;
import com.lucas.mailsender_ms.domains.mail.exceptions.InvalidDataCreateMail;
import com.lucas.mailsender_ms.domains.mail.exceptions.MailNotFoundException;
import com.lucas.mailsender_ms.repositories.MailRepository;
import com.lucas.mailsender_ms.services.IMailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {

    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;

    @Value("${profile-ms.url}")
    private String url;

    private static final Pattern CODE_PATTERN = Pattern.compile("Aqui seu código: (\\d{5})");

    @Override
    public void send(SendMailDto data) {
        validateSendMailDto(data);

        var mail = saveMail(data);
        String code = extractCodeFromContent(data.content());

        sendEmail(data.from(), data.to(), data.subject(), getHtmlTemplate(data.to(), code));

        log.info("Email enviado com sucesso. {}", mail);
    }

    @Override
    public void delete(DeleteMailDto data) {
        validateDeleteMailDto(data);

        var mail = mailRepository.findById(data.mailId())
                .orElseThrow(() -> new MailNotFoundException("Cannot find the mail by the id"));

        mailRepository.delete(mail);
    }

    @Override
    public List<Mail> getAll() {
        return mailRepository.findAll();
    }

    private void validateSendMailDto(SendMailDto data) {
        if (data.content() == null || data.content().isEmpty() ||
                data.to() == null || data.to().isEmpty() ||
                data.from() == null || data.from().isEmpty() ||
                data.userId() == null || data.userId().isEmpty() ||
                data.type() == null) {
            throw new InvalidDataCreateMail("Invalid data during creation of email");
        }
    }

    private void validateDeleteMailDto(DeleteMailDto data) {
        if (data.mailId() == null || data.userId() == null || data.userId().isEmpty()) {
            throw new InvalidDataCreateMail("Invalid data during deletion of email");
        }
    }

    private Mail saveMail(SendMailDto data) {
        var mail = Mail.builder()
                .to(data.to())
                .from(data.from())
                .subject(data.subject())
                .type(data.type())
                .content(data.content())
                .sendedAt(LocalDateTime.now())
                .userId(data.userId())
                .build();

        return mailRepository.save(mail);
    }

    private String extractCodeFromContent(String content) {
        Matcher matcher = CODE_PATTERN.matcher(content);
        if (!matcher.find()) {
            throw new ErrorWhileSendingMail("Could not send the email, the regex pattern did not find the code");
        }
        return matcher.group(1);
    }

    private void sendEmail(String from, String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Erro ao enviar o email: {}", e.getMessage());
            throw new ErrorWhileSendingMail("Erro ao enviar o email");
        }
    }

    private String getHtmlTemplate(String email, String code) {
        String formattedUrl = url.concat("confirm/" + code + "/" + email);

        log.info("String formatada {}", formattedUrl);

        return String.format(
                """
                <!DOCTYPE html>
                <html lang="pt-br">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Código de Confirmação</title>
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      background-color: #f2f2f2;
                      margin: 0;
                      padding: 0;
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      height: 100vh;
                    }
                    .container {
                      background-color: #fff;
                      padding: 40px;
                      border-radius: 8px;
                      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                      max-width: 600px;
                      width: 100%%;
                      text-align: center;
                    }
                    h1 {
                      color: #3C2A21;
                    }
                    p {
                      font-size: 16px;
                      color: #555;
                    }
                    .code {
                      display: inline-block;
                      padding: 15px 30px;
                      font-size: 24px;
                      font-weight: bold;
                      background-color: #3C2A21;
                      color: #fff;
                      border-radius: 5px;
                      margin: 20px 0;
                    }
                    .footer {
                      font-size: 12px;
                      color: #888;
                      margin-top: 20px;
                    }
                  </style>
                </head>
                <body>
                  <div class="container">
                    <h1>Confirmação de E-mail</h1>
                    <p>Olá,</p>
                    <p>Estamos muito felizes de saber que você quer fazer parte do Wefood!</p>
                    <p>Para confirmar seu e-mail, por favor clique no link abaixo:</p>
                    <div class="code">
                    <a href="%s">Confirmar</a>
                    </div>
                    <p>Este código é válido por 15 minutos.</p>
                    <div class="footer">
                      <p>Se você não solicitou essa verificação, pode ignorar este e-mail.</p>
                    </div>
                  </div>
                </body>
                </html>
                """, formattedUrl
        );
    }
}
