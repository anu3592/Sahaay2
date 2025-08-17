/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Base64;

/**
 *
 * @author Anurag
 */

@Entity
@Table(name="tickets", schema = "ticketapplication")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private long id = (long)(Math.random()*100000000);
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String title;
    
    @Column
    private String phone;
    
    @Column
    private String category;
    
    @Column
    private String address;
    
    @Column(name="problem_desc")
    private String problemDesc;
    
    @Column
    private String status;
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ")
    //private LocalDateTime created_at = LocalDateTime.now();
    private OffsetDateTime created_at = OffsetDateTime.now();
    
    @Column
    private long assigned_to;
    
    @Column
    private int user_id;
    
    
    //
    //@Column(name="image", columnDefinition="LONGBLOB")
//    @Lob
    @Column(name="image")
//    @Basic(fetch = FetchType.LAZY)
    private byte[] image;
    
    public Ticket() {}

    // ✅ Getters and setters for ALL fields
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public long getId()
    {
        return this.id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getCategory(){
        return this.category;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getAddress()
    {
        return this.address;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getPhone()
    {
        return this.phone;
    }
    
    public void setProblemDesc(String problemDesc)
    {
        this.problemDesc = problemDesc;
    }
    
    public String getProblemDesc()
    {
        return this.problemDesc;
    }
    
    public String getStatus()
    {
        return this.status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    public OffsetDateTime getCreated_at()
    {
        return this.created_at;
    }
    
    public void setCreated_at(OffsetDateTime created_at)
    {
        this.created_at = created_at;
    }
    
    public long getAssigned_to()
    {
        return assigned_to;
    }
    
    public void setAssigned_to(long assigned_to)
    {
        this.assigned_to= assigned_to;
    }
    
    public int getUser_id()
    {
        return user_id;
    }
    
    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }
    
    public String getImage()
    {
        //return this.image;
        return Base64.getEncoder().encodeToString(this.image);
    }
    
    public void setImage(byte[] image)
    {
        this.image = image;
    }
    
    public String toString()
    {
        return "id = "+id+" name = "+name+" phone = "+phone+" category = "+category+" address = "+address+" problem Description = "+problemDesc;
    }
}
