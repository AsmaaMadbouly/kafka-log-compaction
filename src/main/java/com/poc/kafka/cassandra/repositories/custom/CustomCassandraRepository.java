package com.poc.kafka.cassandra.repositories.custom;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomCassandraRepository<T, ID> extends CassandraRepository<T, ID> {
    <S extends T> S save(S entity, int ttl);
    <S extends T> List<S> insert(Iterable<S> entities, int ttl);
}
