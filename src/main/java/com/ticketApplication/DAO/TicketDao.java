/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.DAO;

import com.ticketApplication.entity.Authority;
import com.ticketApplication.entity.EscalationLogs;
import com.ticketApplication.entity.Ticket;
import com.ticketApplication.entity.Users;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Anurag
 */
public class TicketDao {

    SessionFactory factory;

    @Transactional
    public void registerUser(Users user) {
        Session session = factory.getCurrentSession();
        session.save(user);
    }

    @Transactional
    public List<Users> getUser(String email, String password) {
        Session session = factory.getCurrentSession();
        Query q = session.createQuery("from Users where email= :email and password= :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List<Users> user = q.getResultList();
        return user;
    }

    @Transactional
    public void insert(Ticket ticket) {
        Session session = factory.getCurrentSession();

        String category = ticket.getCategory();
        System.out.println(category);
        Query q2 = session.createQuery("from Authority where level=1 and department = :category order by no_of_ticket_assigned ASC");
        q2.setParameter("category", category);

        List<Authority> authority = q2.getResultList();
        System.out.println(authority);

        ticket.setAssigned_to(authority.get(0).getId());
        session.save(ticket);
        long id = authority.get(0).getId();
        Query q = session.createQuery("from Authority where id = :id");
        q.setParameter("id", id);
        List<Authority> authority2 = q.getResultList();
        authority2.get(0).setNo_of_ticket_assigned(authority2.get(0).getNo_of_ticket_assigned() + 1);
        updateAuthority(authority2.get(0));
    }

    @Transactional
    public void delete(long id) {
        Session session = factory.getCurrentSession();
        Ticket ticket = (Ticket) session.load(Ticket.class, id);
        session.delete(ticket);
    }

    @Transactional
    public void update(Ticket ticket) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(ticket);
    }

    @Transactional
    public List<Ticket> getAllTicket(long id) {
        Session session = factory.getCurrentSession();

        // it is using entity class not table name here
        Query q = (Query) session.createQuery("from Ticket where user_id= :id");
        q.setParameter("id", id);
        List<Ticket> tickets = q.getResultList();
        System.out.println("List of tickets in view ->" + tickets);
        return tickets;
    }

    public Ticket getTicket(long id) {
        Session session = factory.getCurrentSession();
        Ticket ticket = (Ticket) session.load(Ticket.class, id);
        return ticket;
    }

    @Transactional
    public void registerAuthority(Authority authority) {
        Session session = factory.getCurrentSession();
        session.save(authority);

    }

    @Transactional
    public List<Authority> loginAuthority(String email, String password) {
        Session session = factory.getCurrentSession();
        Query q = session.createQuery("from Authority where email= :email and password= :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List<Authority> authority = q.getResultList();
        return authority;
    }

    @Transactional
    public void updateAuthority(Authority authority) {
        Session session = factory.getCurrentSession();
        session.saveOrUpdate(authority);
    }

    @Transactional
    public List<Ticket> getAuthorityTickets(long id) {
        Session session = factory.getCurrentSession();
        Query q = session.createQuery("from Ticket where assigned_to= :id and status=:status");
        q.setParameter("id", id);
        q.setParameter("status", "pending");
        List<Ticket> tickets = q.getResultList();
        return tickets;
    }

    @Transactional
    public void escalateTable() {
        Session session = factory.getCurrentSession();
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

Query query = session.createQuery(
    "FROM Ticket WHERE status = :status AND created_at < :twoDaysAgo", Ticket.class
);
        //Query q = session.createQuery("from Ticket where status = :status and TIMESTAMPDIFF(DAY, created_at, NOW()) > 2");
//        Query q = session.createQuery(
//                "SELECT t FROM Ticket t WHERE t.status = :status AND now() - t.createdAt > INTERVAL '2 days'",
//                Ticket.class
//        );
        //q.setParameter("status", "pending");
        
        query.setParameter("status", "Pending");
query.setParameter("twoDaysAgo", twoDaysAgo);

        List<Ticket> tickets = query.getResultList();

        for (int i = 0; i < tickets.size(); i++) {
            long authorityId = tickets.get(i).getAssigned_to();

            Authority authority = (Authority) session.load(Authority.class, authorityId);

            int level = (int) authority.getLevel();

            if (level < 3) {
                System.out.println("inside level block---");
                Query q2 = session.createQuery("from Authority where level>1 order by no_of_ticket_assigned ASC");
                List<Authority> auth = q2.getResultList();
                System.out.println("Authority ->");
                System.out.println(auth);

                for (int j = 0; j < auth.size(); j++) {

                    //System.out.println(auth.get(j).getDepartment()+" "+tickets.get(i).getCategory());
                    if (auth.get(j).getDepartment().equalsIgnoreCase(tickets.get(i).getCategory())) {
                        System.out.println("inside auth block----");
                        Query q3 = session.createQuery("update Ticket set assigned_to= :authId where id = :id");
                        q3.setParameter("authId", auth.get(j).getId());
                        q3.setParameter("id", tickets.get(i).getId());

                        q3.executeUpdate();
                        System.out.println("updated Successfully!");
                    }
                }

            }

        }

        System.out.println(tickets);

    }

    @Transactional
    public void closeTicket(long id) {
        Session session = factory.getCurrentSession();
        Query q = session.createQuery("update Ticket set status = 'approved' where id =:id");
        q.setParameter("id", id);
        q.executeUpdate();

    }

    @Transactional
    public void escalateTicketById(long id, EscalationLogs escalation_logs) {
        Session session = factory.getCurrentSession();

        //Query q = session.createQuery("from ");
        Ticket ticket = session.load(Ticket.class, id);
        long authorityId = ticket.getAssigned_to();

        Authority auth = session.load(Authority.class, authorityId);

        int level = auth.getLevel();

        if (level < 3) {
            Query q = session.createQuery("from Authority where department =:department and level>1 order by no_of_ticket_assigned ASC");
            q.setParameter("department", auth.getDepartment());
            List<Authority> authorities = q.getResultList();
            System.out.println("Escalated authorities-> " + authorities);
            Query q2 = session.createQuery("update Ticket set assigned_to = :firstAuthId where id=:id");
            q2.setParameter("firstAuthId", authorities.get(0).getId());
            q2.setParameter("id", id);

            q2.executeUpdate();

            escalation_logs.setTo_authority_id(authorities.get(0).getId());
            System.out.println("final escalation_logs -> " + escalation_logs);
            try {
                session.save(escalation_logs);
                session.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Query q5 = session.createQuery("update escalation_logs setto_authority_id = :authorityId where ticket_id = ticketId");
//            q5.setParameter("authorityId", authorities.get(0).getId());
//            q5.setParameter("ticketId", escalation_logs.getTicketId());
//            
//            q5.executeUpdate();

            int no_of_ticket_assigned = auth.getNo_of_ticket_assigned() - 1;

            Query q3 = session.createQuery("update Authority set no_of_ticket_assigned= :no_of_ticket_assigned where id  = :id");
            q3.setParameter("no_of_ticket_assigned", no_of_ticket_assigned);
            q3.setParameter("id", authorityId);

            q3.executeUpdate();

            int no_of_ticket_assigned2 = authorities.get(0).getNo_of_ticket_assigned() + 1;
            Query q4 = session.createQuery("update Authority set no_of_ticket_assigned = :no_of_ticket_assigned where id= :id");
            q4.setParameter("no_of_ticket_assigned", no_of_ticket_assigned2);
            q4.setParameter("id", authorities.get(0).getId());

            q4.executeUpdate();
        }

    }

    @Transactional
    public List<Authority> getAuthorityInfo(long id) {
        Session session = factory.getCurrentSession();

        Query q1 = session.createQuery("from EscalationLogs where ticketId = :ticketId");
        q1.setParameter("ticketId", id);
        List<EscalationLogs> escalation_logs = q1.getResultList();
        List<Authority> authorities = new ArrayList<Authority>();

        if (!escalation_logs.isEmpty()) {
            long id1 = escalation_logs.get(0).getFrom_authority_id();
            Authority authority1 = (Authority) session.get(Authority.class, id1);

            long id2 = escalation_logs.get(0).getTo_authority_id();
            Authority authority2 = (Authority) session.get(Authority.class, id2);
            System.out.println("Info authority -> " + authority1);
            authorities.add(authority1);
            authorities.add(authority2);
            return authorities;
        } else {
            Ticket ticket = session.load(Ticket.class, id);
            long id3 = ticket.getAssigned_to();

            Authority authority3 = (Authority) session.get(Authority.class, id3);
            System.out.println("Info authority -> " + authority3);
            authorities.add(authority3);
            return authorities;
        }

    }

    @Transactional
    public void updateComplaint(Ticket ticket) {
        Session session = factory.getCurrentSession();

        boolean name = false;
        boolean title = false;
        boolean category = false;
        boolean phone = false;
        boolean address = false;
        boolean problemDesc = false;

        if (ticket.getName() != null) {
            name = true;
        }
        if (ticket.getAddress() != null) {
            address = true;
        }
        if (ticket.getCategory() != null) {
            category = true;
        }
        if (ticket.getPhone() != null) {
            phone = true;
        }
        if (ticket.getProblemDesc() != null) {
            problemDesc = true;
        }
        if (ticket.getTitle() != null) {
            title = true;
        }

        String sqlstmt = "update Ticket set ";

        if (name) {
            sqlstmt = sqlstmt + "name =:name, ";
        }
        if (address) {
            sqlstmt = sqlstmt + "address=:address, ";
        }
        if (title) {
            sqlstmt = sqlstmt + "title=:title, ";
        }
        if (category) {
            sqlstmt = sqlstmt + "category=:category, ";
        }
        if (phone) {
            sqlstmt = sqlstmt + "phone=:phone, ";
        }
        if (problemDesc) {
            sqlstmt = sqlstmt + "problemDesc=:problemDesc ";
        }

        sqlstmt = sqlstmt + "where id=:id";

        System.out.println(sqlstmt);
        Query q = session.createQuery(sqlstmt);

        if (name) {
            q.setParameter("name", ticket.getName());
        }
        if (address) {
            q.setParameter("address", ticket.getAddress());
        }
        if (title) {
            q.setParameter("title", ticket.getTitle());
        }
        if (category) {
            q.setParameter("category", ticket.getCategory());
        }
        if (phone) {
            q.setParameter("phone", ticket.getPhone());
        }
        if (problemDesc) {
            q.setParameter("problemDesc", ticket.getProblemDesc());
        }
        q.setParameter("id", ticket.getId());
        System.out.println("Ticket id -> " + ticket.getId());
        q.executeUpdate();

    }

    @Transactional
    public Ticket getSearchedTicket(long id) {
        Session session = factory.getCurrentSession();
        Ticket ticket = (Ticket) session.get(Ticket.class, id);
        return ticket;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
