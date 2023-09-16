package io.b1ruk.start.whatsAppS.service.message;

import io.b1ruk.start.whatsAppS.entity.MediaAttachment;
import io.b1ruk.start.whatsAppS.entity.MediaType;
import io.b1ruk.start.whatsAppS.entity.Message;
import io.b1ruk.start.whatsAppS.repository.MediaAttachmentRepository;
import io.b1ruk.start.whatsAppS.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class MediaAttachmentService {

    @Autowired
    private MediaAttachmentRepository mediaAttachmentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Value("${file.dir.video}")
    private String videoUploadDirectory;


    @Value("${file.dir.picture}")
    private String pictureUploadDirectory;

    @EventListener(ApplicationReadyEvent.class)
    public void createUploadDirectory() throws IOException {
        createFolder(Path.of(videoUploadDirectory));
        createFolder(Path.of(pictureUploadDirectory));
    }


    @Transactional
    public ResponseEntity<MediaAttachment> uploadMediaAsset(Long messageId, MultipartFile file, MediaType mediaType) throws IOException {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("unable to find file"));

        var filePath=uploadFile(file, mediaType);

        MediaAttachment mediaAttachment = MediaAttachment.builder()
                .message(message)
                .url(filePath)
                .type(mediaType)
                .timestamp(new Date())
                .build();

        mediaAttachmentRepository.save(mediaAttachment);

        return ResponseEntity.ok().body(mediaAttachment);
    }

    public String uploadFile(MultipartFile file, MediaType mediaType) throws IOException {
        var uploadDirectory = mediaType.equals(MediaType.PICTURE) ? pictureUploadDirectory : videoUploadDirectory;
        Path destination = Path.of(uploadDirectory, file.getOriginalFilename());
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return destination.toFile().getAbsolutePath();
    }

    private static void createFolder(Path directoryPath) throws IOException {
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
            System.out.println("Upload directory created: " + directoryPath);
        }
    }
}
