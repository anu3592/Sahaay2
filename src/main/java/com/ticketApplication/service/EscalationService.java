/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Anurag
 */

@Component
public class EscalationService {
    
    @Autowired
    private TicketService ticketService;
    
    
    public EscalationService(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }
    
    @Scheduled(fixedRate = 60000)
    public void escalateOldTickets()
    {
        System.out.println("Running escalation job");
        ticketService.escalateTable();
        
    }
    
    public void setTicketService(TicketService ticketService)
    {
        this.ticketService = ticketService;
    }
    
    public TicketService getTicketService()
    {
        return ticketService;
    }
}
