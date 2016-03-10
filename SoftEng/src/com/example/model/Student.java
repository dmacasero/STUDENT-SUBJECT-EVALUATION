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
public class Student {

    public int getStudid() {
        return studid;
    }

    public void setStudid(int studid) {
        this.studid = studid;
    }

    public String getStudlname() {
        return studlname;
    }

    public void setStudlname(String studlname) {
        this.studlname = studlname;
    }

    public String getStudfname() {
        return studfname;
    }

    public void setStudfname(String studfname) {
        this.studfname = studfname;
    }

    public String getStudmi() {
        return studmi;
    }

    public void setStudmi(String studmi) {
        this.studmi = studmi;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getStudyear() {
        return studyear;
    }

    public void setStudyear(int studyear) {
        this.studyear = studyear;
    }

    public int getStudsem() {
        return studsem;
    }

    public void setStudsem(int studsem) {
        this.studsem = studsem;
    }

    public int getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(int evaluated) {
        this.evaluated = evaluated;
    }

    public int getStudstat() {
        return studstat;
    }

    public void setStudstat(int studstat) {
        this.studstat = studstat;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }
    int studid;
    String studlname,studfname,studmi,coursename;
    int studyear,studsem,evaluated,studstat,courseid;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" + "studid=" + studid + ", studlname=" + studlname + ", studfname=" + studfname + ", studmi=" + studmi + ", coursename=" + coursename + ", studyear=" + studyear + ", studsem=" + studsem + ", evaluated=" + evaluated + ", studstat=" + studstat + ", courseid=" + courseid + '}';
    }
    
}
