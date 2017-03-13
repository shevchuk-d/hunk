package com.github.shevchuk;

import com.github.shevchuk.clients.client.dao.SimpleDAOClient;
import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.client.model.SimpleClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@SpringBootApplication(exclude = {JndiConnectionFactoryAutoConfiguration.class,DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan
public class HunkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HunkApplication.class, args);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/root-config.xml");

		SimpleDAOClient daoClient = context.getBean(SimpleDAOClient.class);

		Client client = new SimpleClient();
		client.setName("Pankaj");
		client.setSex("bi");

		daoClient.addClient(client);

		System.out.println("Person:: "+daoClient.getClientById(1).getName());

//		List<Person> list = daoClient.list();
//
//		for(Person p : list){
//			System.out.println("Person List::"+p);
//		}

		context.close();


	}
}
