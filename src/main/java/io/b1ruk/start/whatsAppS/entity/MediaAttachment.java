package io.b1ruk.start.whatsAppS.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "media_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "url", nullable = false)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
}
