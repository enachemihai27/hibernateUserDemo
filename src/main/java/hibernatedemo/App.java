package hibernatedemo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import users.User;


public class App 
{	private static SessionFactory factory; 
    public static void main( String[] args )
    {
    	try {
            factory = new Configuration().configure().buildSessionFactory();
         } catch (Throwable ex) { 
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
         }
         
         App app = new App();

      
//         app.addUser("Zara", "Ali@email.com", "1000");
//         app.addUser("Daisy", "Das@email.com", "5000");
//         app.addUser("John", "Paul@email.com", "10000");

         
         app.listUsers();

      }
      
      public Integer addUser(String name, String email, String password){
         Session session = factory.openSession();
         Transaction tx = null;
         Integer userID = null;
         
         try {
            tx = session.beginTransaction();
            User user = new User(name, email, password);
            userID = (Integer) session.save(user); 
            tx.commit();
         } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
         } finally {
            session.close(); 
         }
         return userID;
      }
      
      public void listUsers( ){
          Session session = factory.openSession();
          Transaction tx = null;
          
          try {
             tx = session.beginTransaction();
             List users = session.createQuery("FROM User").list(); 
             System.out.println("id   Nume      Email        Password");   
             for (Iterator iterator = users.iterator(); iterator.hasNext();){
                User user = (User) iterator.next();                     
                System.out.print(user.getId() + "    "); 
                System.out.print(user.getName() + "    "); 
                System.out.print(user.getEmail() + "    "); 
                System.out.println(user.getPassword() + "    "); 
             }
             tx.commit();
          } catch (HibernateException e) {
             if (tx!=null) tx.rollback();
             e.printStackTrace(); 
          } finally {
             session.close(); 
          }
       }
      
      }
      
