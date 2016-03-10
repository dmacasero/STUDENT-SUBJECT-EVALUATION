/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import com.example.model.User;
import java.sql.ResultSet;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 *
 * @author FRIENDLY
 */
public class UserDAO {
       private JdbcTemplate jdbcTemplate;  

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<User> getAllUser(String usern,String pass)
    {
        String query ="select * from user where username='"+usern+"' and userpass='"+pass+"'";
   
       List<User> user = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
           User u = new User();
           u.setUserid(rs.getInt("UserID"));
           u.setUsername(rs.getString("UserName"));
           u.setUserpass(rs.getString("UserPass"));
           u.setUsertype(rs.getString("UserType"));
           return u;
        });
        return user;
    }
}
