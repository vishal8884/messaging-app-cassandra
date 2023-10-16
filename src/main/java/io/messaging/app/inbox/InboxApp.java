package io.messaging.app.inbox;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import io.messaging.app.inbox.folders.Folder;
import io.messaging.app.inbox.folders.FolderRepository;

@SpringBootApplication
@RestController
public class InboxApp {
	
	@Autowired
	private FolderRepository folderRepository;

	public static void main(String[] args) {
		SpringApplication.run(InboxApp.class, args);
	}

	
	@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
	
	
	@PostConstruct
	public void init() {
		folderRepository.save(new Folder("vishal8884","Inbox", "blue"));
		folderRepository.save(new Folder("vishal8884","Sent", "orange"));
		folderRepository.save(new Folder("vishal8884","Important", "yellow"));
	}
	

}
