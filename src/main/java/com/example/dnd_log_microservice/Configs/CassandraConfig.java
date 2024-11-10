package com.example.dnd_log_microservice.Configs;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.metadata.EndPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


import java.net.InetSocketAddress;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.dnd_log_microservice")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "my_keyspace";
    }

    @Override
    public CassandraClusterFacto ryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("127.0.0.1");  // Adjust to your Cassandra host
        cluster.setJmxReportingEnabled(false);
        return cluster;
    }

    @Override
    public CassandraTemplate cassandraTemplate(CqlSession session) {
        return new CassandraTemplate(session);
    }

    @Bean
    public CqlSession session() {
        return CqlSession.builder().addContactPoint(new InetSocketAddress("127.0.0.1", 9042)).build(); // Adjust IP and port
    }
}

