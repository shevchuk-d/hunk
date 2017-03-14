package com.github.shevchuk;

import com.github.shevchuk.locker.dao.SimpleDAOLocker;
import com.github.shevchuk.locker.model.LockerSingleton;
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
import java.util.List;

@SpringBootApplication(exclude = {JndiConnectionFactoryAutoConfiguration.class,DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan
public class HunkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HunkApplication.class, args);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/root-config.xml");

		SimpleDAOLocker daoLocker = context.getBean(SimpleDAOLocker.class);

//		Client client = new SimpleClient();
//		client.setName("Pankaj");
//		client.setSex("bi");
//
//		Client client2 = new SimpleClient();
//		client2.setName("vazgen" + (long) (Math.random() * 100));
//		client2.setSex("gay");
//
//		IntStream.range( 0, 200 ).forEach(i ->{
//			client.setName("vazgen_" + i);
//			daoClient.addClient(client);
//		} );
//
//		daoClient.updateClient((long) (Math.random() * 200), client2);
//		System.out.println("Updated client: " + daoClient.getClientById(7).getName());
//		daoClient.deleteClient((long) (Math.random() * 200) );

		List<LockerSingleton> lockers = new ArrayList<>();
//		LockerSingleton lockersA = new ArrayList<>(Arrays.asList(new Long[]{1L,5L,8L,6L,47L}));

		LockerSingleton locker0 = new LockerSingleton();
		locker0.setNumber(0);

		LockerSingleton locker1 = new LockerSingleton();
		locker1.setNumber(1);
		LockerSingleton locker2 = new LockerSingleton();
		locker2.setNumber(2);
		LockerSingleton locker3 = new LockerSingleton();
		locker3.setNumber(3);

		lockers.add(locker1);
		lockers.add(locker2);
		lockers.add(locker3);

		locker0.setNeighbors(lockers);

		daoLocker.addLocker(locker0);

		context.close();
	}
}
