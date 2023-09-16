package io.b1ruk.start.whatsAppS.service.message;

import com.whatsapp.api.domain.messages.ImageMessage;
import com.whatsapp.api.domain.messages.TextMessage;
import com.whatsapp.api.domain.messages.VideoMessage;
import com.whatsapp.api.domain.messages.response.MessageResponse;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import io.b1ruk.start.whatsAppS.controller.dto.MessageDTO;
import io.b1ruk.start.whatsAppS.entity.*;
import io.b1ruk.start.whatsAppS.repository.ChatroomRepository;
import io.b1ruk.start.whatsAppS.repository.MediaAttachmentRepository;
import io.b1ruk.start.whatsAppS.repository.MessageRepository;
import io.b1ruk.start.whatsAppS.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MediaAttachmentRepository mediaAttachmentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private WhatsappBusinessCloudApi whatsappBusinessCloudApi;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Transactional
    public ResponseEntity<MessageDTO> createMessage(MessageDTO messageDTO) {
        Participant participantModel = participantRepository.findById(messageDTO.getSenderId()).orElseThrow(() -> new RuntimeException("Participant not found"));
        ChatroomEntity chatroomEntity = chatroomRepository.findById(messageDTO.getChatroomId()).orElseThrow(() -> new RuntimeException("chatroom not found"));

        var message = Message.builder()
                .text(messageDTO.getText())
                .chatroomId(messageDTO.getChatroomId())
                .senderId(messageDTO.getSenderId())
                .timestamp(new Date())
                .build();

        messageRepository.save(message);

        if (!messageDTO.getMediaAttachments().isEmpty()) {
            sendMediaAttachments(messageDTO, participantModel, chatroomEntity);
        } else {
            sendMessage(messageDTO, participantModel, chatroomEntity);
        }

        messageDTO.setId(message.getId());

        return ResponseEntity.ok().body(messageDTO);
    }

    private void sendMediaAttachments(MessageDTO messageDTO, Participant participantModel, ChatroomEntity chatroomEntity) {
        messageDTO.getMediaAttachments()
                .forEach(mediaAttachment -> {
                    if (mediaAttachment.getType().equals(MediaType.PICTURE)) {
                        sendImage(mediaAttachment, participantModel, chatroomEntity);
                    } else {
                        sendVideo(mediaAttachment, participantModel, chatroomEntity);
                    }
                });
    }

    private void sendImage(MediaAttachment mediaAttachment, Participant participantModel, ChatroomEntity chatroomEntity) {

        MediaAttachment attachmentModel = mediaAttachmentRepository.save(mediaAttachment);

        var imageMessage = new ImageMessage()
                .setId(attachmentModel.getId().toString());

        var message = com.whatsapp.api.domain.messages.Message.MessageBuilder.builder()
                .setTo(participantModel.getPhoneId())
                .buildImageMessage(imageMessage);


        MessageResponse messageResponse = whatsappBusinessCloudApi.sendMessage(chatroomEntity.getName(), message);
    }

    private void sendVideo(MediaAttachment mediaAttachment, Participant participantModel, ChatroomEntity chatroomEntity) {

        MediaAttachment attachmentModel = mediaAttachmentRepository.save(mediaAttachment);

        var videoMessage = new VideoMessage()
                .setId(attachmentModel.getId().toString());

        var message = com.whatsapp.api.domain.messages.Message.MessageBuilder.builder()//
                .setTo(participantModel.getPhoneId())
                .buildVideoMessage(videoMessage);


        MessageResponse messageResponse = whatsappBusinessCloudApi.sendMessage(chatroomEntity.getName(), message)
    }

    public void sendMessage(MessageDTO messageDTO, Participant participantModel, ChatroomEntity chatroomEntity) {

        var message = com.whatsapp.api.domain.messages.Message.MessageBuilder.builder()
                .setTo(participantModel.getPhoneId())
                .buildTextMessage(new TextMessage()
                        .setBody(messageDTO.getText())
                        .setPreviewUrl(false));


        whatsappBusinessCloudApi.sendMessage(chatroomEntity.getName(), message);
    }
}
