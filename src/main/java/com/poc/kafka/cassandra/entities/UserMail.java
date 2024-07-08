package com.poc.kafka.cassandra.entities;

import java.time.Instant;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.poc.kafka.utils.Constants.ColumnsName;
import com.poc.kafka.utils.Constants.TablesName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = TablesName.USER_MAIL)
public class UserMail {
    @PrimaryKeyColumn(name = ColumnsName.EMAIL, ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String email;

    @Column(value = ColumnsName.USERNAME)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String username;

    @Column(value = ColumnsName.PASSWORD)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String password;

    @Column(value = ColumnsName.ROLE)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String role;

    @Column(value = ColumnsName.USER_CREATION_TIMESTAMP)
    @CassandraType(type = CassandraType.Name.DATE)
    private Instant userCreationTimestamp;


}
