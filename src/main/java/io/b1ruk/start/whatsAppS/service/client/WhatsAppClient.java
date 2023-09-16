package io.b1ruk.start.whatsAppS.service.client;

import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import io.b1ruk.start.whatsAppS.entity.ChatroomEntity;
import io.b1ruk.start.whatsAppS.entity.Participant;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.contact.ContactJid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.http.Part;

@Service
@Slf4j
public class WhatsAppClient {

    @Value("${wp.token}")
    private String TOKEN;

    @Bean
    public WhatsappBusinessCloudApi businessCloudApi(){
        return WhatsappApiFactory.newInstance(TOKEN)
                .newBusinessCloudApi();
    }

    public void createGroup(String groupName, Participant participant){
        Whatsapp.customBuilder()
                .build()
                .createGroup(groupName, ContactJid.of(participant.getPhoneId()))
                .thenRunAsync(()->{
                    log.info("Group created");
                });
    }

    public void leaveGroup(ChatroomEntity chatroom) {
        ContactJid group = ContactJid.of(chatroom.getId());

        Whatsapp.customBuilder()
                .build()
                .leaveGroup(group)
                .thenRunAsync(()->{
                   log.info("leaveGroup {}",chatroom.getName());
                });
    }

    public void addParticipant(ChatroomEntity chatroom, Participant participant) {
        ContactJid group = ContactJid.of(chatroom.getId());
        ContactJid participantId = ContactJid.of(participant.getPhoneId());


        Whatsapp.customBuilder()
                .build()
                .addGroupParticipant(group,participantId)
                .thenRunAsync(()->{
                    log.info("Add participant {} to Group {}",participant.getPhoneId(),chatroom.getName());
                });
    }
}
