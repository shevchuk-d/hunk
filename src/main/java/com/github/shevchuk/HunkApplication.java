package com.github.shevchuk;

import com.github.shevchuk.clients.client.dao.DAOClient;
import com.github.shevchuk.clients.client.dao.SimpleDAOClient;
import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.visit.dao.DAOVisit;
import com.github.shevchuk.clients.visit.dao.SimpleDAOVisit;
import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.dao.SimpleDAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = {JndiConnectionFactoryAutoConfiguration.class,DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan
public class HunkApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(HunkApplication.class, args);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/root-config.xml");

		DAOClient daoClient = context.getBean(SimpleDAOClient.class);
		DAOLocker daoLocker = context.getBean(SimpleDAOLocker.class);
		DAOVisit daoVisit = context.getBean(SimpleDAOVisit.class);

		Client client = new Client();
		client.setName("Pankaj");
		client.setSex("bi");

		IntStream.range( 0, 200 ).forEach(i ->{
			client.setName("vazgen_" + i);
			daoClient.addClient(client);
		} );
//
//		daoClient.updateClient((long) (Math.random() * 200), client2);
//		System.out.println("Updated client: " + daoClient.getClientById(7).getName());
//		daoClient.deleteClient((long) (Math.random() * 200) );

		List<Locker> lockers = new ArrayList<>();

		Locker locker0 = new Locker();
		locker0.setNumber(0);

		Locker locker1 = new Locker();
		locker1.setNumber(1);
		Locker locker2 = new Locker();
		locker2.setNumber(2);
		Locker locker3 = new Locker();
		locker3.setNumber(3);
//
		lockers.add(locker1);
		lockers.add(locker2);
		lockers.add(locker3);

		locker0.setNeighbors(lockers);

		daoLocker.addLocker(locker0);

		Visit visit = new Visit();
		visit.setStart(new Date());
//		visit.setClientId(5);
		visit.setLockerId(4);
		visit.setClient(daoClient.getClientById(10));
//		Thread.sleep(10000L);
//		visit.setFinish(new Date());

//		SELECT *
//				FROM visits
//		WHERE start NOTNULL
//		AND finish ISNULL;

		daoVisit.addVisit(visit);

		context.close();
	}
}
