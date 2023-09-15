package io.b1ruk.start.whatsAppS.repository;


import io.b1ruk.start.whatsAppS.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
