package io.messaging.app.inbox.email;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "message_by_id")
public class Email {
	
	@Id
	@PrimaryKeyColumn(name = "created_time_uuid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID timeUUID;

	@CassandraType(type = Name.TEXT)
	private String from;
	
	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;
	
	@CassandraType(type = Name.TEXT)
	private String subject;
	
	@CassandraType(type = Name.TEXT)
	private String body;

	public UUID getTimeUUID() {
		return timeUUID;
	}

	public void setTimeUUID(UUID timeUUID) {
		this.timeUUID = timeUUID;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
