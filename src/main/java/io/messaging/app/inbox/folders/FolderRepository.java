package io.messaging.app.inbox.folders;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CassandraRepository<Folder, String>{

	//@Query(value = "select * from folders_by_user where user_id=?1")
	public List<Folder> findAllById(String userId);
}
