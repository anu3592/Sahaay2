/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author Anurag
 */

@Entity
public class Authority {
    
    @Id
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String email;
    
    @Column
    private String phone;
    
    @Column
    private String department;
    
    @Column
    private int level;
    
    @Column
    private String location;
    
    @Column
    private String password;
    
    @Column
    private int no_of_ticket_assigned;
    
    public Authority()
    {
        
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getDepartment()
    {
        return department;
    }
            
    public void setDepartment(String department)
    {
        this.department = department;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassowrd(String password)
    {
        this.password = password;
    }
    
    
    public int getNo_of_ticket_assigned()
    {
        return this.no_of_ticket_assigned;
    }
    
    public void setNo_of_ticket_assigned(int no_of_ticket_assigned)
    {
        this.no_of_ticket_assigned = no_of_ticket_assigned;
    }
    
    public String toString()
    {
        return "name = "+getName()+" email= "+getEmail()+" phone= "+getPhone()+" department= "+getDepartment()+" level= "+getLevel()+" location= "+getLocation();
    }
    
}
