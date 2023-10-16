package io.messaging.app.inbox.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping("/")
	public String homePage(@AuthenticationPrincipal OAuth2User oauth2User,Model model) { 
		//oauth2User contains info about currently loggedin user
		if(oauth2User == null || !StringUtils.hasText(oauth2User.getAttribute("login"))) {
			return "index";
		}
		
		String userId = oauth2User.getAttribute("login");
		List<Folder> defaultFolders  = folderService.fetchDefaultFolders(userId);
		
		model.addAttribute("defaultFolders",defaultFolders);
		model.addAttribute("userFolders",null);
		return "inbox-page";
	}
}
