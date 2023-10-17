package io.messaging.app.inbox.folders;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "folders_by_users")
public class Folder {

	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String id;
	
	@PrimaryKeyColumn(name = "label", ordinal = 0, type = PrimaryKeyType.CLUSTERED)  //If we give cluster all the userIdAndLabel will be in one node only instead of spreading out
	private String label;
	
	@CassandraType(type = Name.TEXT)
	private String color;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Folder(String id, String label, String color) {
		this.id = id;
		this.label = label;
		this.color = color;
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", label=" + label + ", color=" + color + "]";
	}
	
	
	 
}
