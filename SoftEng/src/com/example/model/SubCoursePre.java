/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.model;

/**
 *
 * @author shortcut
 */
public class SubCoursePre {
    private int subid;
    private String subname;
   
    private String subdescr;
    private String prereqname;
    private int sbyear;
    private int subsem;
    private int subunits;
 
   
    private String coursename;

    public SubCoursePre() {
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

    public int getSubsem() {
        return subsem;
    }

    public void setSubsem(int subsem) {
        this.subsem = subsem;
    }

    public void setSubdescr(String subdescr) {
        this.subdescr = subdescr;
    }

    public int getSubunits() {
        return subunits;
    }

    public String getPrereqname() {
        return prereqname;
    }

    public void setPrereqname(String prereqname) {
        this.prereqname = prereqname;
    }

    public void setSubunits(int subunits) {
        this.subunits = subunits;
    }

    public int getSbyear() {
        return sbyear;
    }

    public void setSbyear(int sbyear) {
        this.sbyear = sbyear;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    
    }

    @Override
    public String toString() {
        return "SubCoursePre{" + "subid=" + subid + ", subname=" + subname + ", subdescr=" + subdescr + ", prereqname=" + prereqname + ", sbyear=" + sbyear + ", subsem=" + subsem + ", subunits=" + subunits + ", coursename=" + coursename + '}';
    }
    
}
    
