package io.b1ruk.start.whatsAppS.service.chatroom;

import io.b1ruk.start.whatsAppS.controller.dto.ChatParticipantDTO;
import io.b1ruk.start.whatsAppS.controller.dto.ChatRoomDTO;
import io.b1ruk.start.whatsAppS.entity.ChatroomEntity;
import io.b1ruk.start.whatsAppS.entity.ChatroomParticipant;
import io.b1ruk.start.whatsAppS.entity.Participant;
import io.b1ruk.start.whatsAppS.repository.ChatroomParticipantRepository;
import io.b1ruk.start.whatsAppS.repository.ChatroomRepository;
import io.b1ruk.start.whatsAppS.repository.ParticipantRepository;
import io.b1ruk.start.whatsAppS.service.client.WhatsAppClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private WhatsAppClient whatsAppClient;

    @Autowired
    private ChatroomParticipantRepository chatroomParticipantRepository;

    @Transactional
    public ResponseEntity<ChatRoomDTO> createChatRoom(ChatRoomDTO chatRoomDTO) {
        Participant createdBy = participantRepository.findById(chatRoomDTO.createdById()).orElseThrow(() -> new RuntimeException("Participant not found"));

        var chatRoomModel = new ChatroomEntity(chatRoomDTO.name(), createdBy, chatRoomDTO.maxCount());
        chatRoomModel = chatroomRepository.save(chatRoomModel);


        whatsAppClient.createGroup(chatRoomDTO.name(), createdBy);

        return ResponseEntity.ok().body(new ChatRoomDTO(chatRoomModel.getId(), chatRoomDTO.name(), chatRoomDTO.maxCount(), createdBy.getId()));

    }

    @Transactional
    public ResponseEntity<ChatParticipantDTO> leaveChatRoom(ChatRoomDTO chatRoomDTO, long participant) {
        ChatroomEntity chatroomEntity = chatroomRepository.findById(chatRoomDTO.id()).orElseThrow(() -> new RuntimeException("chatroom not found"));
        Participant participantModel = participantRepository.findById(participant).orElseThrow(() -> new RuntimeException("Participant not found"));

        ChatroomParticipant chatroomParticipant = chatroomParticipantRepository.findByChatroomIdAndParticipantId(chatroomEntity.getId(), participantModel.getId()).orElseThrow(() -> new RuntimeException("chatroom not found"));

        ChatParticipantDTO chatParticipantDTO = new ChatParticipantDTO(chatroomParticipant.getId(), chatroomEntity.getName(), participantModel.getId());

        whatsAppClient.leaveGroup(chatroomEntity);

        chatroomParticipantRepository.delete(chatroomParticipant);

        return ResponseEntity.ok().body(chatParticipantDTO);
    }

    @Transactional
    public ResponseEntity<ChatParticipantDTO> addParticipant(ChatRoomDTO chatroom, long participant) {
        ChatroomEntity chatroomEntity = chatroomRepository.findById(chatroom.id()).orElseThrow(() -> new RuntimeException("chatroom not found"));
        Participant participantModel = participantRepository.findById(participant).orElseThrow(() -> new RuntimeException("Participant not found"));

        var chatRoomParticipant = new ChatroomParticipant(chatroomEntity, participantModel);

        chatroomParticipantRepository.save(chatRoomParticipant);

        whatsAppClient.addParticipant(chatroomEntity, participantModel);

        ChatParticipantDTO chatParticipantDTO = new ChatParticipantDTO(chatRoomParticipant.getId(), chatroomEntity.getName(), participantModel.getId());

        return ResponseEntity.ok().body(chatParticipantDTO);
    }
}
