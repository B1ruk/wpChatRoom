package io.b1ruk.start.whatsAppS.controller;

import io.b1ruk.start.whatsAppS.controller.dto.ChatParticipantDTO;
import io.b1ruk.start.whatsAppS.controller.dto.ChatRoomDTO;
import io.b1ruk.start.whatsAppS.service.chatroom.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomDTO> create(@RequestBody ChatRoomDTO chatRoomDTO) {
        return chatRoomService.createChatRoom(chatRoomDTO);
    }

    @PutMapping("leaveChatRoom")
    public ResponseEntity<ChatParticipantDTO> leaveChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
     return chatRoomService.leaveChatRoom(chatRoomDTO);
    }


    @PostMapping("addParticipant")
    public ResponseEntity<ChatParticipantDTO> addParticipant(@RequestBody ChatRoomDTO chatRoomDTO,@RequestParam("participantId") Long participantId){
        return chatRoomService.addParticipant(chatRoomDTO,participantId);
    }
}
