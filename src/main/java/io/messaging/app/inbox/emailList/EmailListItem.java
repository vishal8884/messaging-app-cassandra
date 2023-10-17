package io.messaging.app.inbox.emailList;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "messages_by_user_folder")
public class EmailListItem {

	@PrimaryKey
	private EmailListItemKey key; //id,label,timeUUID are keys
	
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;
	
	@CassandraType(type = Name.TEXT)
	private String subject;
	
	@CassandraType(type = Name.BOOLEAN)
	private boolean isUnread;
	
	@Transient
	private String agoTime;
	
	

	public String getAgoTime() {
		return agoTime;
	}

	public void setAgoTime(String agoTime) {
		this.agoTime = agoTime;
	}

	public EmailListItemKey getKey() {
		return key;
	}

	public void setKey(EmailListItemKey key) {
		this.key = key;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isUnread() {
		return isUnread;
	}

	public void setUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}

	
}
