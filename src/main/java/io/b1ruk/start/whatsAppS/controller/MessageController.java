package io.b1ruk.start.whatsAppS.controller;

import io.b1ruk.start.whatsAppS.controller.dto.MessageDTO;
import io.b1ruk.start.whatsAppS.entity.MediaAttachment;
import io.b1ruk.start.whatsAppS.entity.MediaType;
import io.b1ruk.start.whatsAppS.repository.MediaAttachmentRepository;
import io.b1ruk.start.whatsAppS.service.message.MediaAttachmentService;
import io.b1ruk.start.whatsAppS.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("messageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MediaAttachmentService mediaAttachmentService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(MessageDTO messageDTO){
        return messageService.createMessage(messageDTO);
    }

    @PostMapping("uploadMediaAsset")
    public ResponseEntity<MediaAttachment> uploadMediaAsset(@RequestParam("messageId") Long messageId, @RequestPart("file") MultipartFile file, @RequestParam("mediaType")MediaType mediaType) throws IOException {
        return mediaAttachmentService.uploadMediaAsset(messageId,file,mediaType);
    }
}
