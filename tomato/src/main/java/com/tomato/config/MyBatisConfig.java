package com.tomato.config;

import javax.sql.DataSource;

import java.util.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJpaRepositories(basePackages = {"com.tomato.board.boarddao"}, 
										entityManagerFactoryRef = "baseEntityManager", 
										transactionManagerRef = "baseTransactionManager")
@Slf4j
public class MyBatisConfig {
	
	@Autowired
    Environment env;
	
	@Value("${mybatis.mapper-locations}") //mybatis.mapper-locations
	private String mapperPath;
	
	/*보안상 비공유 되어야할 소스를 분리하기 위하여 사용*/
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSourceProperties dataSourceProp() {
		return new DataSourceProperties();
	}//DataSourceProp
	
	/*데이터소스*/	
	@Bean(name="dataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return dataSourceProp().initializeDataSourceBuilder().build();
	}//dataSource
	
	/*데이터 연결속도를 위해 사용*/	
	@Bean(name="hikariCP")
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource hikariDataSource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}//hikariDataSource
	
	/*데이터베이스와의 연결과 sql의 실행에 대한 모든것을 가진 객체*/
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		//factoryBean.setDataSource(hikariDataSource());
		factoryBean.setDataSource(dataSource()); // 테스트중 데이터 직접 넣을용도로 썻습니다
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
					.getResources(mapperPath));
		//factoryBean.setTypeAliasesPackage("com.tomato.*.*"); //com.toamto.mapper.board.boardvo.BoardVO
	    return factoryBean.getObject();
	}//sqlSessionFactory
	
	/*mybatis쿼리문을 수행 하기위해 사용*/	
	@Bean(name="sqlSession")	
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		
		return new SqlSessionTemplate(sqlSessionFactory());
	}//sqlSessionTemplate
	
	@Bean
	public LocalContainerEntityManagerFactoryBean baseEntityManager() {
		
        LocalContainerEntityManagerFactoryBean em 
        = new LocalContainerEntityManagerFactoryBean();
      //  em.setDataSource(dataSource());
        em.setDataSource(hikariDataSource()); 

        // Entity Package 경로
        em.setPackagesToScan(new String[] { "com.tomato.board.boardvo" });
        em.setPersistenceUnitName("baseEntityManager");                               // 영속성 객체 이름을 지정

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Hibernate 설정
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

        em.setJpaPropertyMap(properties);

        return em;
    }
	
	
	/*트랜잭션을 자연스럽게 사용하기위해 사용*/	
//	@Bean
//    public PlatformTransactionManager getTransactionManager() {
//	        
//		return new DataSourceTransactionManager(this.hikariDataSource());
//	}//TransactionManager
	
	@Bean
    public PlatformTransactionManager baseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(baseEntityManager().getObject());
        return transactionManager;
    }
	
}

