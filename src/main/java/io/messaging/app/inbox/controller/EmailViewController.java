package io.messaging.app.inbox.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.messaging.app.inbox.email.Email;
import io.messaging.app.inbox.email.EmailRepository;
import io.messaging.app.inbox.emailList.EmailListItem;
import io.messaging.app.inbox.emailList.EmailListItemRepository;
import io.messaging.app.inbox.folders.Folder;
import io.messaging.app.inbox.folders.FolderRepository;
import io.messaging.app.inbox.folders.FolderService;

@Controller
public class EmailViewController {
	
	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private EmailRepository emailRepo;

	@GetMapping("/emails/{id}")
	public String emailView(@AuthenticationPrincipal OAuth2User oauth2User,Model model,@PathVariable UUID id) { 
		//oauth2User contains info about currently loggedin user
		if(oauth2User == null || !StringUtils.hasText(oauth2User.getAttribute("login"))) {
			return "index";
		}
		
		//Fetch Folders
		String userId = oauth2User.getAttribute("login");
		List<Folder> defaultFolders  = folderService.fetchDefaultFolders(userId);
		List<Folder> userFolders  = folderRepository.findAllById(userId);
				
		model.addAttribute("defaultFolders",defaultFolders);
		model.addAttribute("userFolders",userFolders);
		
		
		Optional<Email> optionalEmail = emailRepo.findById(id);
		if(optionalEmail.isEmpty())
			return "inbox-page";
		
		model.addAttribute("email", optionalEmail.get());
		
		return "email-page";
	}
}
