package test;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class TestLog {
	private static final Logger LOGGER = Logger.getLogger(TestLog.class.getName());

	@Test
	public void log() {

		try {
			FileHandler fh = new FileHandler("C:/var/Doc/MyLogFile.log");
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// the following statement is used to log any messages

			LOGGER.info(" QQQQQ ");
		} catch (Exception e) {

		}
	}

	@Test
	public void testDB() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		new SessionFactory ().getCurrentSession();
//		Session session = SessionFactory; 
				
//				HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Message message = new Message("Hello World");
		System.out.println(message.getText());

		// Configuration configuration = new Configuration();
		// configuration.configure();
		// ServiceRegistry sr = new
		// ServiceRegistryBuilder().applySettings(configuration.getProperties())
		// .buildServiceRegistry();
		// SessionFactory sf = configuration.buildSessionFactory(sr);
		// //
		// Message user1 = new Message();
		// user1.setUserName("Arpit");
		// user1.setUserMessage("Hello world from arpit");
		//
		// User user2=new User();
		// user2.setUserName("Ankita");
		// user2.setUserMessage("Hello world from ankita");
		// Session ss=sf.openSession();
		// ss.beginTransaction();
		// //saving objects to session
		// ss.save(user1);
		// ss.save(user2);
		// ss.getTransaction().commit();
		// ss.close();
		//
		// Read more at
		// http://www.java2blog.com/2013/01/hibernate-hello-world-example-in-eclipse.html#oESdIZbU6zEe09xJ.99
	}

}
