package io.b1ruk.start.whatsAppS.controller;

import io.b1ruk.start.whatsAppS.controller.dto.MessageDTO;
import io.b1ruk.start.whatsAppS.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(MessageDTO messageDTO){
        return messageService.createMessage(messageDTO);
    }
}
