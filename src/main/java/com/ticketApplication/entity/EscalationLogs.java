/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author Anurag
 */

@Entity
@Table(name="escalation_logs", schema = "ticketapplication")
public class EscalationLogs {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="ticket_id")
    private long ticketId;
    
    @Column
    private long from_authority_id;
    
    @Column
    private long to_authority_id;
    
    @Column
    private LocalDateTime escalated_at = LocalDateTime.now();
    
    @Column
    private String reason;
    
    
    public long getId()
    {
        return this.id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public long getTicketId()
    {
        return this.ticketId;
    }
    
    public void setTicketId(long ticketId)
    {
        this.ticketId = ticketId;
    }
    
    public long getFrom_authority_id()
    {
        return this.from_authority_id;
    }
    
    public void setFrom_authority_id(long from_authority_id)
    {
        this.from_authority_id = from_authority_id;
    }
    
    public long getTo_authority_id()
    {
        return this.to_authority_id;
    }
    
    public void setTo_authority_id(long to_authority_id)
    {
        this.to_authority_id = to_authority_id;
    }
    
    public LocalDateTime getEscalated_at()
    {
        return this.escalated_at;
    }
    
    public void setEscalated_at(LocalDateTime escalated_at)
    {
        this.escalated_at = escalated_at;
    }
    
    public String getReason()
    {
        return this.reason;
    }
    
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    
    public String toString()
    {
        return "id = "+id+" ticked id = "+ticketId + " from_authority_id = "+from_authority_id+" to_authority_id = "+to_authority_id+" escalated_at = "+escalated_at + " reason = "+reason;
    }
}
