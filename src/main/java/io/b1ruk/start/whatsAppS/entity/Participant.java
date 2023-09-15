package io.b1ruk.start.whatsAppS.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_id", nullable = false, unique = true)
    private String phoneId;

    @OneToMany(mappedBy = "chatCreatedBy")
    private List<Chatroom> createdChatRooms;
}

