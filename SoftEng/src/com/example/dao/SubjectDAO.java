/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import com.example.model.Course;
import com.example.model.SubCoursePre;
import com.example.model.Subject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author FRIENDLY
 */
public class SubjectDAO {
         private JdbcTemplate jdbcTemplate;  
         private DataSource dataSource;
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

     
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   
    
      
      public List<Subject> getAllSub()
    {
        String query ="select * from subject order by SubID";
       

       List<Subject> user = jdbcTemplate.query(query, (ResultSet rs, int rowNum) -> {
           Subject u = new Subject();
           u.setSubid(rs.getInt("SubID"));
           return u;
        });
        return user;
    }
      public void saveSubject(Subject subject) {
           
          String query = "insert into subject (SubName,SubDescr,SubYear,SubSem,SubUnits,coursename) values (?,?,?,?,?,?)";
        
         Object[] args = new Object[] {subject.getSubname(),subject.getSubdescr(),subject.getSubyear(),subject.getSubsem(),subject.getSubunits(),subject.getCoursename()};
        int out = jdbcTemplate.update(query, args);
     
        if(out !=0){
            System.out.println("Subject saved with id="+subject.getSubid());
        }else System.out.println("Subject save failed with id="+subject.getSubid());
    }
    public ResultSet viewTable() throws SQLException{
         ResultSet rs=null;
        if(dataSource!=null){
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
       String sql="select * from subject ORDER BY `subyear` ASC";
        rs=st.executeQuery(sql);}
        return rs;
    }
    public ResultSet searchTable(int edp) throws SQLException{
        ResultSet rs=null;
        if(dataSource!=null)
        {
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
        String sql="select * from subject where subid="+edp+" ORDER BY `subyear`";
                rs=st.executeQuery(sql);
        }
        return rs; 
      }
    


  public ResultSet searchTable2(String subname) throws SQLException{
        ResultSet rs=null;
        if(dataSource!=null)
        {
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
       String sql="select * from subject where subname='"+subname+"' ORDER BY `subyear`";
        rs=st.executeQuery(sql);
        }
        return rs; 
      }
  public void updateSubject(Subject subject) {
       
          System.out.println(subject);
           
          String query = " update subject set SubName=?,SubDescr=?,SubYear=?,SubSem=?,SubUnits=? ,coursename=? where subid=?";
        
         Object[] args = new Object[] {subject.getSubname(),subject.getSubdescr(),subject.getSubyear(),subject.getSubsem(),subject.getSubunits(),subject.getCoursename(),subject.getSubid()};
        int out = jdbcTemplate.update(query, args);        
        if(out !=0){
            System.out.println("Subject saved with id="+subject.getSubid());
        }else System.out.println("Subject save failed with id="+subject.getSubid());
    }
  
  public void deleteById(int id) {
 
        String query = "delete from subject where subid=?";
       
        int out = jdbcTemplate.update(query, id);
        if(out !=0){
            System.out.println("Subject deleted with id="+id);
        }else System.out.println("No Subject found with id="+id);
    }
  
  public ResultSet viewPrereq() throws SQLException{
         ResultSet rs=null;
        if(dataSource!=null){
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
           String sql="select subname,subdescr,prereqname,subyear from subject order by subyear";     
           rs=st.executeQuery(sql);
        }
        return rs;
  }
   public ResultSet searchPrereq(String subname) throws SQLException{
        ResultSet rs=null;
        if(dataSource!=null) 
        {
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
        String sql="select subname,subdescr,prereqname,subyear  from subject where subname='"+subname+"' order by subyear";
        rs=st.executeQuery(sql);
        }
        return rs; 
      }
   public ResultSet searchAllPrereq() throws SQLException{
        ResultSet rs=null;
        if(dataSource!=null)
        {
        Connection con=dataSource.getConnection();
        Statement st=con.createStatement();
       String sql="select * from prerequisite";
       rs=st.executeQuery(sql);
        }
        return rs; 
      }
   public int addPrerequisite(SubCoursePre subject) throws SQLException{
       String query = "update subject set prereqname=? where subname=?";
        int out=0;
   
         Object[] args = new Object[] {subject.getPrereqname(),subject.getSubname()};
          if(!subject.getPrereqname().equals(subject.getSubname()))
            out = jdbcTemplate.update(query, args);
     
         if(out!=0){
            System.out.println("Subject saved with name="+subject.getSubname());
        }
        else {
         System.out.println("Subject save failed with name="+subject.getSubname());
        }
         return out;
   }

    
     
   
      
    public void deletePrerequisit(String subname){
           String query = "update subject set prereqname=? where subname=?";
       
        int out = jdbcTemplate.update(query,new Object[]{null,subname});
        if(out !=0){
            System.out.println("Subject deleted with subname="+subname);
        }else System.out.println("No Subject found with subname="+subname);
    }
}