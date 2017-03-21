package com.github.shevchuk;

import com.github.shevchuk.clients.client.dao.DAOClient;
import com.github.shevchuk.clients.client.dao.SimpleDAOClient;
import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.visit.dao.DAOVisit;
import com.github.shevchuk.clients.visit.dao.SimpleDAOVisit;
import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.config.JerseyConfig;
import com.github.shevchuk.contorllers.SessionController;
import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.dao.SimpleDAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
@ComponentScan
public class HunkApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(HunkApplication.class, args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/root-config.xml");

        DAOClient personDAO = (DAOClient) context.getBean("personDAO");
        DAOLocker daoLocker = (DAOLocker) context.getBean("daoLocker");
        DAOVisit visitDAO = (DAOVisit) context.getBean("visitDAO");

        SessionController sessionController = context.getBean(SessionController.class);

//		{
//			Client client = new Client();
//			client.setSex("none");
//
//			IntStream.range( 0, 200 ).forEach(i ->{
//				client.setName("vazgen_" + i);
//                personDAO.addClient(client);
//			} );
//
//			List<Locker> lockers = new ArrayList<>();
//
//			Locker locker0 = new Locker();
//			locker0.setNumber(0);
//
//			Locker locker1 = new Locker();
//			locker1.setNumber(1);
//			Locker locker2 = new Locker();
//			locker2.setNumber(2);
//			Locker locker3 = new Locker();
//			locker3.setNumber(3);
//			Locker locker4 = new Locker();
//			locker3.setNumber(4);
//
//			lockers.add(locker1);
//			lockers.add(locker2);
//			lockers.add(locker3);
//			lockers.add(locker4);
//			daoLocker.addLocker(locker1);
//			daoLocker.addLocker(locker2);
//			daoLocker.addLocker(locker3);
//			daoLocker.addLocker(locker4);
//
//			locker0.setNeighbors(lockers);
//
//			daoLocker.addLocker(locker0);
//
//			IntStream.range( 0, 7 ).forEach(i ->{
//				Visit visit = new Visit();
//				visit.setStart(new Date());
//				visit.setLocker(lockers.get(i/2));
//				visit.setClient(personDAO.getClientById(10));
//				if (i % 2 == 0) {
//					visit.setFinish(new Date());
//					visit.setLocker(null);
//				}
//                visitDAO.addVisit(visit);
//			} );
//
//			Visit visit = new Visit();
//			visit.setStart(new Date());
//			visit.setLocker(locker0);
//			visit.setClient(personDAO.getClientById(10));
//
//            visitDAO.addVisit(visit);
//		}


        System.out.println(sessionController.getLockerById(1));
        List<Locker> lockerss = sessionController.getReservedLockers();
//        List<Locker> lockerss = daoLocker.getReservedLockers();
        lockerss.forEach(locker -> System.out.println(locker.getLockerId() + " - " + locker.getNumber()));
        context.close();
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }
}
