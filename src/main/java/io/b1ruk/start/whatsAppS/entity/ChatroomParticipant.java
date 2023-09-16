package io.b1ruk.start.whatsAppS.entity;


import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "chatroom_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatroomEntity chatroom;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    public ChatroomParticipant(ChatroomEntity chatroom, Participant participant) {
        this.chatroom = chatroom;
        this.participant = participant;
    }
}
