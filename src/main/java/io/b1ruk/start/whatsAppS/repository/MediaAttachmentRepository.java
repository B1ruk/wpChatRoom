package io.b1ruk.start.whatsAppS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import io.b1ruk.start.whatsAppS.entity.MediaAttachment;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaAttachmentRepository extends JpaRepository<MediaAttachment, Long> {
}
