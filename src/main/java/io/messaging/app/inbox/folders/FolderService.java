package io.messaging.app.inbox.folders;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FolderService {

	public List<Folder> fetchDefaultFolders(String userId){
		
		return Arrays.asList(new Folder("vishal8884","Inbox", "blue"),
				new Folder("vishal8884","Sent", "orange"),
				new Folder("vishal8884","Important", "yellow"));
	}
}
