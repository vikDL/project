package backend.FTW.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.*;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import backend.FTW.dao.ProductDao;
import backend.FTW.models.product;;



@Configuration
@ComponentScan("backend.FTW")
@EnableTransactionManagement
public class AppConfig {
	
	public DataSource getH2DataSource()
	{
		System.out.println("Data Source Method");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
		dataSource.setUsername("wik");
		dataSource.setPassword("wik");
		System.out.println("Data Source Created");
		return dataSource;
	}
	public Properties getHibernateProperties()
	{
		System.out.println("Hibernate Properties -Entered");
		Properties hibernate_prop=new Properties();
		hibernate_prop.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernate_prop.put("hibernate.show_sql", "true"); //optional
		hibernate_prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		System.out.println("Hibernate Prperty Object Created");
		return hibernate_prop;
		
	}
	
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory()
	{
		System.out.println("SessionFactory Method-Entered");
		
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getH2DataSource());
		
		sessionBuilder.addProperties(getHibernateProperties());
		
		sessionBuilder.addAnnotatedClass(product.class);
		
		
		SessionFactory sessionfactory=sessionBuilder.buildSessionFactory();
		System.out.println("SessionFactory Object Created");
		return sessionfactory;
	}
	
	
	@Bean(name="productDAO")
	public ProductDao getProductDAO(SessionFactory sessionFactory)
	{
		return new ProductDao(sessionFactory);
	}
	

	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		System.out.println("Transaction Manager");
		HibernateTransactionManager transactionManager=new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	
	
	
	

}
