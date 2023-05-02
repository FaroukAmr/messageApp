package com.FAM.messageApp.dao;

import com.FAM.messageApp.model.Chat;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ChatRepository extends CassandraRepository<Chat,Integer> {
}