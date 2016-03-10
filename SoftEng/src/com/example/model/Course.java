/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

/**
 *
 * @author FRIENDLY
 */
public class Course {
    private int courseid;
    private String coursename;
    private String coursedescr;

    public Course(int courseid, String coursename, String coursedescr) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.coursedescr = coursedescr;
    }

    public Course() {
     }

    public String getCoursedescr() {
        return coursedescr;
    }

    public void setCoursedescr(String coursedescr) {
        this.coursedescr = coursedescr;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    @Override
    public String toString() {
        return "Course{" + "courseid=" + courseid + ", coursename=" + coursename + ", coursedescr=" + coursedescr + '}';
    }
    
}
