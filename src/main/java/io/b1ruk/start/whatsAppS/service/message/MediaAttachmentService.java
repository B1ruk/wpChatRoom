package io.b1ruk.start.whatsAppS.service.message;

import io.b1ruk.start.whatsAppS.repository.MediaAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaAttachmentService {

    @Autowired
    private MediaAttachmentRepository mediaAttachmentRepository;
}
