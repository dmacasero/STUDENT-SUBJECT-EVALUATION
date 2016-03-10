/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author shortcut
 */
public class StudentDAO {

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

    public ResultSet viewEvalStud() throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            String sql ="SELECT StudID, StudLName, StudFName, StudMI, StudSem, StudYear, StudStat, CourseName FROM student WHERE evaluated=1";
                    rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet viewEvalStud(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
           String sql="SELECT StudID, StudLName, StudFName, StudMI, StudSem, StudYear, StudStat, CourseName FROM student WHERE evaluated=1 and studid="+id;
            rs = st.executeQuery(sql);
        }
        return rs;
    }
  public ResultSet viewNonEvalStud() throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            String sql ="SELECT StudID, StudLName, StudFName, StudMI, StudSem, StudYear, StudStat, CourseName FROM student WHERE evaluated=0";   
                    rs = st.executeQuery(sql);
        }
        return rs;
    }
  public ResultSet viewStudentEval(int id) throws SQLException{
    ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            String sql ="SELECT * from student where studid="+id;   
                    rs = st.executeQuery(sql);
        }
        return rs;
  }
  
    public ResultSet viewNonEvalStud(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
             String sql="SELECT StudID, StudLName, StudFName, StudMI, StudSem, StudYear, StudStat, CourseName FROM student WHERE evaluated=0 and studid="+id;
             rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet firstyear1sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=1 and sub.subyear=1 and studsub.studid="+id;
            rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet firstyear2sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

             String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=2 and sub.subyear=1 and studsub.studid="+id;
             rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet secondyear1sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

            String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=1 and sub.subyear=2 and studsub.studid="+id;
            rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet secondyear2sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

             String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=2 and sub.subyear=2 and studsub.studid="+id;
              rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet thirdyear1sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

             String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=1 and sub.subyear=3 and studsub.studid="+id;
           rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet thirdyear2sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

              String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=2 and sub.subyear=3 and studsub.studid="+id;
           rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet fourthyear1sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();

             String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=1 and sub.subyear=4 and studsub.studid="+id;
           rs = st.executeQuery(sql);
        }
        return rs;
    }

    public ResultSet fourthyear2sem(int id) throws SQLException {
        ResultSet rs = null;
        if (dataSource != null) {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
  String sql = "SELECT studsub.subid ,sub.subname,sub.subunits,studsub.stud_subgrade,sub.prereqname,sub.subsem,sub.subyear from student stud, stud_sub studsub,subject sub where studsub.studid=stud.studid and sub.subid=studsub.subid and sub.subsem=2 and sub.subyear=4 and studsub.studid="+id;
          rs = st.executeQuery(sql);
        }
        return rs;
    }
}
