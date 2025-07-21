/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.service;

import com.ticketApplication.DAO.TicketDao;
import com.ticketApplication.entity.Authority;
import com.ticketApplication.entity.EscalationLogs;
import com.ticketApplication.entity.Ticket;
import com.ticketApplication.entity.Users;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Anurag
 */

@Service
public class TicketService {
    
    @Autowired
    TicketDao ticketDao;
    
    
    public void createTicket(Ticket ticket)
    {
        this.ticketDao.insert(ticket);
        
    }
    
    public Users getUser(String email, String password)
    {
        
        List<Users> user = this.ticketDao.getUser(email, password);
        
        return user.get(0);
        
    }
    
    public void registerUser(Users user)
    {
        this.ticketDao.registerUser(user);
    }
    
    public List<Ticket> getAllTickets(long id)
    {
        List<Ticket> tickets = ticketDao.getAllTicket(id);
        return tickets;
    }
    
    public void registerAuthority(Authority authority)
    {
        long id = (long) (Math.random()*(999999-100000 + 1) + 100000);
        authority.setId(id);
        
        ticketDao.registerAuthority(authority);
    }
    
    public Authority loginAuthority(String email, String password)
    {
        List<Authority> authority = ticketDao.loginAuthority(email, password);
        return authority.get(0);
    }
    
    public List<Ticket> getAuthorityTickets(long id)
    {
        return ticketDao.getAuthorityTickets(id);
    }
    
    public void escalateTable()
    {
        ticketDao.escalateTable();
    }
    
    public void closeTicket(long id)
    {
        ticketDao.closeTicket(id);
    }
    
    public void escalateTicketById(long id, EscalationLogs escalation_logs)
    {
        ticketDao.escalateTicketById(id, escalation_logs);
    }
    
    public List<Authority> getAuthorityInfo(long id)
    {
        return ticketDao.getAuthorityInfo(id);
    }
    
    public void updateComplaint(Ticket ticket)
    {
        ticketDao.updateComplaint(ticket);
    }
    
    public Ticket getSearchedTicket(long id)
    {
        return ticketDao.getSearchedTicket(id);
    }
    
    public void setTicketDao(TicketDao ticketDao)
    {
        this.ticketDao = ticketDao;
    }
    
    public TicketDao getTicketDao()
    {
        return this.ticketDao;
    }
    
    
}
