package io.messaging.app.inbox.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import io.messaging.app.inbox.emailList.EmailListItem;
import io.messaging.app.inbox.emailList.EmailListItemRepository;
import io.messaging.app.inbox.folders.Folder;
import io.messaging.app.inbox.folders.FolderRepository;
import io.messaging.app.inbox.folders.FolderService;
import io.netty.util.internal.StringUtil;

@Controller
public class InboxController {
	
	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private EmailListItemRepository emailRepo;

	@GetMapping("/")
	public String homePage(@AuthenticationPrincipal OAuth2User oauth2User,Model model) { 
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
		
		//Fetch Messages
		String label = "Inbox";
		List<EmailListItem> emailList = emailRepo.findAllByKey_IdAndKey_Label(userId, label);
		
		PrettyTime prettyTime = new PrettyTime();
		
		emailList.stream().forEach(emailItem -> {
			UUID timeUUID = emailItem.getKey().getTimeUUID();
			Date emailDateTime = new Date(Uuids.unixTimestamp(timeUUID));
			
			emailItem.setAgoTime(prettyTime.format(emailDateTime));
		});
		
		model.addAttribute("emailList", emailList);
		
		return "inbox-page";
	}
}
