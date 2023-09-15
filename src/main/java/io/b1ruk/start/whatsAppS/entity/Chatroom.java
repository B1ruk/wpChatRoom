package io.b1ruk.start.whatsAppS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chatrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "createdBy_id", nullable = false)
    private Participant chatCreatedBy;

    @Column(name = "max_participant")
    private Integer maxParticipantCount;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL)
    private List<ChatroomParticipant> chatroomParticipants;
}
