package io.messaging.app.inbox.emailList;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmailListItemRepository extends CassandraRepository<EmailListItem, EmailListItemKey>{

	List<EmailListItem> findAllByKey_IdAndKey_Label(String id,String label);
}
