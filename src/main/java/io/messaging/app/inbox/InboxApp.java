package io.messaging.app.inbox;

import java.nio.file.Path;
import java.util.Arrays;

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
import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.messaging.app.inbox.email.Email;
import io.messaging.app.inbox.email.EmailRepository;
import io.messaging.app.inbox.emailList.EmailListItem;
import io.messaging.app.inbox.emailList.EmailListItemKey;
import io.messaging.app.inbox.emailList.EmailListItemRepository;
import io.messaging.app.inbox.folders.Folder;
import io.messaging.app.inbox.folders.FolderRepository;

@SpringBootApplication
@RestController
public class InboxApp {
	
	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private EmailListItemRepository emailListItemRepository;
	
	@Autowired
	private EmailRepository emailRepository;

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
		String userid = "vishal8884";
		
		folderRepository.save(new Folder(userid,"Inbox", "blue"));
		folderRepository.save(new Folder(userid,"Sent", "orange"));
		folderRepository.save(new Folder(userid,"Important", "yellow"));
		
		for(int i=0;i<10;i++) {
			EmailListItemKey emailListItemKey = new EmailListItemKey();
			emailListItemKey.setId(userid);
			emailListItemKey.setLabel("Inbox");
			emailListItemKey.setTimeUUID(Uuids.timeBased());
			
			EmailListItem emailListItem = new EmailListItem();
			emailListItem.setKey(emailListItemKey);
			emailListItem.setSubject("subject "+i);
			emailListItem.setTo(Arrays.asList("vishal2@gmai.com","test@gmail.com"));
			emailListItem.setUnread(true);
			
			emailListItemRepository.save(emailListItem);
			
			Email email = new Email();
			email.setTimeUUID(emailListItemKey.getTimeUUID());
			email.setBody("body :: "+i);
			email.setFrom(userid);
			email.setTo(emailListItem.getTo());
			email.setSubject(emailListItem.getSubject());
			
			emailRepository.save(email);
		}
		
	}
	

}
