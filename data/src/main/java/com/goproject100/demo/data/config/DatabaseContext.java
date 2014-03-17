package com.goproject100.demo.data.config;

import com.goproject100.demo.data.dao.DataPacketDAO;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

/**
 * This {@link Configuration} handles the database initialization for this application.
 */
@Configuration
public class DatabaseContext {

    @Bean
    public DataSource dataSource() throws SQLException {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(EmbeddedDatabaseType.H2);
        builder.addScript("classpath:/schema/create.sql");
        builder.addScript("classpath:/schema/populate.sql");
        return builder.build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws SQLException {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperFactoryBean<DataPacketDAO> dataPacketMapper() throws Exception {
        final MapperFactoryBean<DataPacketDAO> mapper = new MapperFactoryBean<>();
        mapper.setMapperInterface(DataPacketDAO.class);
        mapper.setSqlSessionFactory(sqlSessionFactory().getObject());
        return mapper;
    }

}
