/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.sql.Date;

/**
 *
 * @author FRIENDLY
 */
public class Subject {

    private int subid;
    private String subname;
    private String subdescr;
    private int subyear;
    private int subsem;
    private int subunits;
    private int courseid;
    private String coursename;
    public Subject(){}

    public Subject(int subid, String subname, String subdescr,int subyear, int subsem, int subunits, String coursename) {
        this.subid = subid;
        this.subname = subname;
        this.subdescr = subdescr;
        this.subyear = subyear;
        this.subsem = subsem;
        this.subunits = subunits;
        this.coursename = coursename;
    }
    

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSubdescr() {
        return subdescr;
    }

    public void setSubdescr(String subdescr) {
        this.subdescr = subdescr;
    }

    public int getSubyear() {
        return subyear;
    }

    public void setSubyear(int subyear) {
        this.subyear = subyear;
    }

   

    public int getSubsem() {
        return subsem;
    }

    public void setSubsem(int subsem) {
        this.subsem = subsem;
    }

    public int getSubunits() {
        return subunits;
    }

    public void setSubunits(int subunits) {
        this.subunits = subunits;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    @Override
    public String toString() {
        return "Subject{" + "subid=" + subid + ", subname=" + subname + ", subdescr=" + subdescr + ", subdate=" + subyear + ", subsem=" + subsem + ", subunits=" + subunits + ", courseid=" + courseid + ", coursename=" + coursename + '}';
    }
   
    
}
