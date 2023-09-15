package io.b1ruk.start.whatsAppS.service.client;

import com.whatsapp.api.WhatsappApiFactory;
import com.whatsapp.api.impl.WhatsappBusinessCloudApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppClient {

    @Value("${wp.token}")
    private String TOKEN;

    @Bean
    public WhatsappBusinessCloudApi businessCloudApi(){
        return WhatsappApiFactory.newInstance(TOKEN)
                .newBusinessCloudApi();
    }
}
