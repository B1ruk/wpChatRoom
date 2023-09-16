package io.b1ruk.start.whatsAppS.repository;


import io.b1ruk.start.whatsAppS.entity.ChatroomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatroomParticipantRepository extends JpaRepository<ChatroomParticipant, Long> {

    public Optional<ChatroomParticipant> findByChatroomIdAndParticipantId(Long chatRoomId,Long participantId);
}
