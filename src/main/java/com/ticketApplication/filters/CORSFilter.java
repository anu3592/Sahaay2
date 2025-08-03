/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketApplication.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Anurag
 */
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpServletRequest req = (HttpServletRequest) request;
//
//        Logger logger = Logger.getLogger(CORSFilter.class.getName());
//        logger.info("🌐 CORSFilter triggered: " + req.getMethod() + " " + req.getRequestURI());
//        // Set CORS headers
//        res.setHeader("Access-Control-Allow-Origin", "https://sahaay-eosin.vercel.app"); // Allow all origins
//        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        res.setHeader("Access-Control-Allow-Headers", "*");
//        res.setHeader("Access-Control-Max-Age", "3600"); // Cache for 1 hour
//        res.setHeader("Access-Control-Allow-Credentials", "true");
//
//        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
//            res.setStatus(HttpServletResponse.SC_OK);
//            return; // stop filter chain on preflight
//        }
//
//        chain.doFilter(request, response);
        try {
            HttpServletResponse res = (HttpServletResponse) response;

            res.setHeader("Access-Control-Allow-Origin", "https://sahaay-eosin.vercel.app");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Max-Age", "3600");

            System.out.println("CORS headers set successfully");

            chain.doFilter(request, response);
        } catch (Exception e) {
            System.err.println("Error in CORSFilter:");
            e.printStackTrace(); // this will go to Render logs
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
