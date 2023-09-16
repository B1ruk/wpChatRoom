package io.b1ruk.start.whatsAppS.controller.dto;

import io.b1ruk.start.whatsAppS.entity.MediaAttachment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class MessageDTO {

    private Long id;

    private Long chatroomId;
    private Long senderId;
    private Date timestamp;
    private String text;
    private List<MediaAttachment> mediaAttachments;
}
