/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.controller;

import com.ticketApplication.entity.Authority;
import com.ticketApplication.entity.EscalationLogs;
import com.ticketApplication.entity.Ticket;
import com.ticketApplication.entity.Users;
import com.ticketApplication.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Anurag
 */

@Controller
@RequestMapping("/")
@CrossOrigin(origins= "https://sahaay-eosin.vercel.app/")

public class HomeController {
    
    @Autowired
    TicketService ticketService;
    
    @ResponseBody
    @GetMapping
    public String first()
    {
        System.out.println("Api is called");
        return "Hello";
    }
    
    @ResponseBody
    @PostMapping(value="/complaint", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Ticket getTicket(@RequestParam("name") String name, 
            @RequestParam("title") String title,
            @RequestParam("phone") String phone,
            @RequestParam("category") String category,
            @RequestParam("address") String address,
            @RequestParam("problemDesc") String problemDesc,
            @RequestParam("status") String status,
            @RequestParam("user_id") int id,
            @RequestParam("image")MultipartFile imageFile,
            HttpServletRequest req, HttpServletResponse res)
    {
        System.out.println("Ticket is registered");
        Ticket ticket = new Ticket();
        try {
        
        ticket.setName(name);
        ticket.setTitle(title);
        ticket.setPhone(phone);
        ticket.setCategory(category);
        ticket.setAddress(address);
        ticket.setProblemDesc(problemDesc);
        ticket.setStatus(status);
        ticket.setUser_id(id);
        ticket.setImage(imageFile.getBytes());
        ticketService.createTicket(ticket);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println(tk);
        
//        try{
//            System.out.println(tk);
//            ticketService.createTicket(tk);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
        return ticket;
    }
    
    @ResponseBody
    @PostMapping
    public Authority enterAuthority(@RequestBody Authority auth, HttpServletRequest req, HttpServletResponse res)
    {
        
        return auth;
    }
    
    @ResponseBody
    @PostMapping("/registerUser")
    public Users enterUser(@RequestBody Users user, HttpServletRequest req, HttpServletResponse res)
    {
        
        System.out.println("User is registered");
        System.out.println(user);
        
        long value = (long) (Math.random()*(99999-10000 + 1) + 10000);
        
        user.setId(value);
        
        try{
            ticketService.registerUser(user);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return user;
    }
    
    @ResponseBody
    @PostMapping("/loginUser")
    public Users loginUser(@RequestBody Users user, HttpServletRequest req, HttpServletResponse res)
    {
        
        System.out.println(ticketService.getUser(user.getEmail(), user.getPassword()));
        return ticketService.getUser(user.getEmail(), user.getPassword());
    }
    
    @ResponseBody
    @PostMapping("/authoritySignup")
    public Authority registerAuthority(@RequestBody Authority authority, HttpServletRequest req, HttpServletResponse res)
    {
        System.out.println(authority);
        try{
        ticketService.registerAuthority(authority);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return authority;
    }
    
    @ResponseBody
    @PostMapping("/loginAuthority")
    public Authority loginAuthority(@RequestBody Authority authority, HttpServletRequest req, HttpServletResponse res)
    {
        
        return ticketService.loginAuthority(authority.getEmail(), authority.getPassword());
    }
    
    @ResponseBody
    @GetMapping("/getTickets/{id}")
    public List<Ticket> sendTickets(@PathVariable("id") long id)
    {
        List<Ticket> tickets = ticketService.getAllTickets(id);
        System.out.println("List of tickets in view ->" + tickets);
        return tickets;
    }
    
    
    @ResponseBody
    @GetMapping("/getAuthorityTickets/{userId}")
    public List<Ticket> getAuthorityTickets(@PathVariable("userId")long id)
    {
        /*System.out.println(id);
        List<Ticket> tickets = ticketService.getAuthorityTickets(id);
        System.out.println(tickets);
        return tickets;*/
        try {
        System.out.println("Fetching tickets for userId: " + id);
        List<Ticket> tickets = ticketService.getAuthorityTickets(id);
        System.out.println("Tickets fetched: " + tickets);
        //return ResponseEntity.ok(tickets);
        return tickets;
    } catch (Exception e) {
        e.printStackTrace(); // log in backend
        return Collections.emptyList(); // or return proper error response
        /*return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("Error fetching tickets: " + e.getMessage());*/
    }
    }
    
    
    @ResponseBody
    @GetMapping("/close/{ticketId}")
    public void closeTicket(@PathVariable("ticketId") long id)
    {
        System.out.println("Close ticket id -> "+id);
        ticketService.closeTicket(id);
    }
    
    @ResponseBody
    @PostMapping("/escalate/{ticketId}")
    public void esclateTicketById(@RequestBody EscalationLogs escalation_logs, @PathVariable("ticketId") long id)
    {
        System.out.println("Escalation_logs-> "+escalation_logs);
        ticketService.escalateTicketById(id, escalation_logs);
    }
    
    
    @ResponseBody
    @GetMapping("/getAuthorityInfo/{ticketId}")
    public List<Authority> getAuhtorityInfo(@PathVariable("ticketId") long id)
    {
        System.out.println("Passing authorities -> "+ticketService.getAuthorityInfo(id));
        return ticketService.getAuthorityInfo(id);
    }
    
    @ResponseBody
    @PutMapping("/updateComplaint")
    public void updateComplaint(@RequestBody Ticket ticket)
    {
        ticketService.updateComplaint(ticket);
    }
    
    @ResponseBody
    @GetMapping("/search/{ticketId}")
    public Ticket getSearchedTicket(@PathVariable("ticketId") long id)
    {
        return ticketService.getSearchedTicket(id);
    }
    
}
