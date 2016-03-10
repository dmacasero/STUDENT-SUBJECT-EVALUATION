/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.dao.StudentDAO;
import com.example.dao.SubjectDAO;
import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.SubCoursePre;
import com.example.model.Subject;
import com.example.model.User;
import com.example.view.AddPreR;
import com.example.view.AddSubject;
import com.example.view.DeanPreRequisiteManage;
import com.example.view.DeanSubjectManage;
import com.example.view.Login;
import com.example.view.StudentEvaluation;
import com.example.view.UpdateSubject;
import com.example.view.ViewEval;
import com.example.view.ViewStudentSubEval;
import com.example.view.ViewStudentUneval;
import com.example.viewinterface.ViewInterface;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author FRIENDLY
 */
public class Controller {

    int count = 0;
    private List<ViewInterface> view;
    private UserDAO userdao;
    private SubjectDAO subjectdao;
    private StudentDAO studentdao;
    ArrayList<SubCoursePre> rs = null;
    int year;

    public SubjectDAO getSubjectdao() {
        return subjectdao;
    }

    public void setSubjectdao(SubjectDAO subjectdao) {
        this.subjectdao = subjectdao;
    }

    public StudentDAO getStudentdao() {
        return studentdao;
    }

    public void setStudentdao(StudentDAO studentdao) {
        this.studentdao = studentdao;
    }

    public UserDAO getUserdao() {
        return userdao;
    }

    public void setUserdao(UserDAO userdao) {
        this.userdao = userdao;
    }

    public List<ViewInterface> getView() {
        return view;
    }

    public void setView(List<ViewInterface> view) {
        this.view = view;
    }

    public void load() {

        if (this.view.get(0) instanceof Login) {
            ((Login) this.view.get(0)).getBtnlogin().addActionListener((ActionEvent e) -> {
                List<User> list = userdao.getAllUser(((Login) this.view.get(0)).getTxtuser().getText(), ((Login) this.view.get(0)).getTxtpass().getText());

                if (list.get(0).getUsername().equals(((Login) this.view.get(0)).getTxtuser().getText()) && list.get(0).getUserpass().equals(((Login) this.view.get(0)).getTxtpass().getText())) {
                    if (list.get(0).getUsertype().equals("admin")) {
                        ((Login) this.view.get(0)).setVisible(false);

                        JOptionPane.showMessageDialog(((Login) this.view.get(0)), "Login Successfully", "Login", JOptionPane.INFORMATION_MESSAGE);
                        deanview();
                    } else if (list.get(0).getUsertype().equals("user")) {
                        ((Login) this.view.get(0)).setVisible(false);
                        JOptionPane.showMessageDialog(((Login) this.view.get(0)), "Login Successfully", "Login", JOptionPane.INFORMATION_MESSAGE);
                        userview();
                        ((StudentEvaluation) view.get(10)).getBtnprint().addActionListener((ActionEvent ed) -> {
                            if (year >= 1) {
                                MessageFormat header = new MessageFormat("Student Evaluation");
                                MessageFormat footer = new MessageFormat("page{0}");
                                try {
                                    ((StudentEvaluation) view.get(10)).getTable21().print(JTable.PrintMode.FIT_WIDTH, header, footer);
                                } catch (PrinterException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(((Login) this.view.get(0)), "Login Failed", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            });
            ((Login) this.view.get(0)).getBtnexit().addActionListener((ActionEvent e) -> {

                System.exit(0);
            });

            java.awt.EventQueue.invokeLater(new ThreadDis(((Login) this.view.get(0))));
        }
    }

    private void deanview() {

        ((DeanSubjectManage) this.view.get(1)).setVisible(true);
        ((DeanSubjectManage) this.view.get(1)).getMenuexit().addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        ((DeanSubjectManage) this.view.get(1)).getMenusubmanage().addActionListener((ActionEvent e) -> {
            ((DeanSubjectManage) this.view.get(1)).setVisible(true);
        });
        ((DeanSubjectManage) this.view.get(1)).getMenuevalmanage().addActionListener((ActionEvent e) -> {
            ((ViewStudentSubEval) this.view.get(7)).setVisible(true);
            viewEval();
        });
        ((DeanSubjectManage) this.view.get(1)).getMenuunevalmanage().addActionListener((ActionEvent e) -> {
            ((ViewStudentUneval) this.view.get(9)).setVisible(true);
            viewUnEval();
        });

        ((DeanSubjectManage) this.view.get(1)).getMenupremanage().addActionListener((ActionEvent e) -> {
            ((DeanPreRequisiteManage) this.view.get(4)).setVisible(true);
            //    ((DeanSubjectManage)this.view.get(1)).setVisible(false);
            prereq();
        });
        ((DeanSubjectManage) this.view.get(1)).getBtnadd().addActionListener((ActionEvent e) -> {
            ((AddSubject) this.view.get(2)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
            addSubject();
            ((AddSubject) this.view.get(2)).setVisible(true);
        });
        ((DeanSubjectManage) this.view.get(1)).getBtnedit().addActionListener((ActionEvent e) -> {
            try {
                updateSubject();
                ((UpdateSubject) this.view.get(3)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        ((DeanSubjectManage) this.view.get(1)).getBtndelete().addActionListener((ActionEvent e) -> {
            int row = ((DeanSubjectManage) view.get(1)).getTablesubject().getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(null, "You must select an subject", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(null, "Delete this subject ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
            int s = Integer.parseInt(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 0).toString());
            subjectdao.deleteById(s);
            JOptionPane.showMessageDialog(null, "Deleted Successfully", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            ResultSet rs = null;
            try {
                rs = subjectdao.viewTable();
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((DeanSubjectManage) this.view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
        });

        ((DeanSubjectManage) this.view.get(1)).getTxtedp().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;
                int s = -999;
                try {
                    if (((DeanSubjectManage) view.get(1)).getTxtedp().getText().matches("[0-9]+")) {
                        s = Integer.parseInt(((DeanSubjectManage) view.get(1)).getTxtedp().getText());
                        rs = subjectdao.searchTable(s);
                        if (rs != null) {
                            ((DeanSubjectManage) view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
                        }
                    } else {
                        if (((DeanSubjectManage) view.get(1)).getTxtedp().getText().equals("")) {

                            try {
                                rs = subjectdao.viewTable();
                            } catch (SQLException ex) {
                                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ((DeanSubjectManage) view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
                        }
                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        ((DeanSubjectManage) this.view.get(1)).getTxtsubname().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;

                try {

                    String s = ((DeanSubjectManage) view.get(1)).getTxtsubname().getText();
                    rs = subjectdao.searchTable2(s);

                    if (rs != null) {
                        ((DeanSubjectManage) view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
                    }

                    if (s.equals("")) {

                        try {
                            rs = subjectdao.viewTable();
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ((DeanSubjectManage) view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
                    }
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        ResultSet rs = null;
        try {
            rs = subjectdao.viewTable();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((DeanSubjectManage) this.view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
    }

    private void addSubject() {
        ((AddSubject) this.view.get(2)).getBtnadd().addActionListener((ActionEvent e) -> {
            Subject subject = new Subject();
            subject.setSubname(((AddSubject) this.view.get(2)).getTxtsubname().getText());
            subject.setSubdescr(((AddSubject) this.view.get(2)).getTxtdescriptive().getText());
            subject.setSubyear(Integer.parseInt(((AddSubject) this.view.get(2)).getTxtyear().getText()));
            subject.setSubsem(Integer.parseInt(((AddSubject) this.view.get(2)).getTxtsem().getText()));
            subject.setSubunits(Integer.parseInt(((AddSubject) this.view.get(2)).getTxtunit().getText()));
            subject.setCoursename(((AddSubject) this.view.get(2)).getCbocourse().getSelectedItem().toString());

            subjectdao.saveSubject(subject);
            JOptionPane.showMessageDialog(((AddSubject) this.view.get(2)), "Added Successfully", "Added", JOptionPane.INFORMATION_MESSAGE);
            ResultSet rs = null;
            try {
                rs = subjectdao.viewTable();
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((DeanSubjectManage) this.view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
        });

    }

    private void updateSubject() throws SQLException {
        int row = ((DeanSubjectManage) view.get(1)).getTablesubject().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "You must select an subject", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!((UpdateSubject) this.view.get(3)).isVisible()) {
            ((UpdateSubject) this.view.get(3)).setVisible(true);
        }
        int s = Integer.parseInt(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 0).toString());
        ((UpdateSubject) this.view.get(3)).getTxtsubname().setText(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 1).toString());
        ((UpdateSubject) this.view.get(3)).getTxtdescriptive().setText(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 2).toString());
        ((UpdateSubject) this.view.get(3)).getTxtyear().setText(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 3).toString() + "");
        ((UpdateSubject) this.view.get(3)).getTxtunit().setText(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 5).toString() + "");
        ((UpdateSubject) this.view.get(3)).getTxtsem().setText(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 4).toString() + "");
        ((UpdateSubject) this.view.get(3)).getCbocourse().setSelectedItem(((DeanSubjectManage) view.get(1)).getTablesubject().getModel().getValueAt(row, 6).toString());
        ((UpdateSubject) this.view.get(3)).getBtnupdate().addActionListener((ActionEvent e) -> {
            Subject subject = new Subject();
            subject.setSubid(s);
            subject.setSubname(((UpdateSubject) this.view.get(3)).getTxtsubname().getText());
            subject.setSubdescr(((UpdateSubject) this.view.get(3)).getTxtdescriptive().getText());
            subject.setSubyear(Integer.parseInt(((UpdateSubject) this.view.get(3)).getTxtyear().getText()));
            subject.setSubsem(Integer.parseInt(((UpdateSubject) this.view.get(3)).getTxtsem().getText()));
            subject.setSubunits(Integer.parseInt(((UpdateSubject) this.view.get(3)).getTxtunit().getText()));
            subject.setCoursename(((UpdateSubject) this.view.get(3)).getCbocourse().getSelectedItem().toString());
            subjectdao.updateSubject(subject);
            JOptionPane.showMessageDialog(((UpdateSubject) this.view.get(3)), "Updated Successfully", "Update", JOptionPane.INFORMATION_MESSAGE);
            ResultSet rs = null;
            try {
                rs = subjectdao.viewTable();
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((DeanSubjectManage) this.view.get(1)).getTablesubject().setModel(DbUtils.resultSetToTableModel(rs));
        });

    }

    private void prereq() {

        ((DeanPreRequisiteManage) this.view.get(4)).getBtnadd().addActionListener((ActionEvent e) -> {
            ResultSet rs1 = null;
            try {
                if (((AddPreR) view.get(5)).getCboprerequisites().getItemAt(0) == null) {
                    rs1 = subjectdao.searchAllPrereq();
                    int i = 0;
                    while (rs1.next() && ((AddPreR) view.get(5)).getCboprerequisites().getItemAt(i) == null) {
                        if (((AddPreR) view.get(5)).getCboprerequisites().getItemAt(i) == null) {
                            ((AddPreR) view.get(5)).getCboprerequisites().addItem(rs1.getString(2));
                        }
                        i++;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                addPre();
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        ((DeanPreRequisiteManage) this.view.get(4)).getBtndelete().addActionListener((ActionEvent e) -> {
            int row = ((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getSelectedRow();
            ResultSet rs;
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "You must select an subject", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String subname = ((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 0).toString();
            int response = JOptionPane.showConfirmDialog(null, "Delete this  prerequisite ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
            if (((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 2) == null) {
                JOptionPane.showMessageDialog(null, "Already deleted", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            subjectdao.deletePrerequisit(subname);
            ResultSet rr = null;
            try {
                rr = subjectdao.viewPrereq();
            } catch (SQLException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().setModel(DbUtils.resultSetToTableModel(rr));
            JOptionPane.showMessageDialog(null, "Deleted Successfully", "Deleted", JOptionPane.INFORMATION_MESSAGE);
        });

        ResultSet rs = null;
        try {
            rs = subjectdao.viewPrereq();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((DeanPreRequisiteManage) this.view.get(4)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().setModel(DbUtils.resultSetToTableModel(rs));

        ((DeanPreRequisiteManage) this.view.get(4)).getTxtsubname().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;

                try {

                    String s = ((DeanPreRequisiteManage) view.get(4)).getTxtsubname().getText();
                    rs = subjectdao.searchPrereq(s);
                    if (rs != null) {

                        ((DeanPreRequisiteManage) view.get(4)).getTableprerequisites().setModel(DbUtils.resultSetToTableModel(rs));

                    }
                    if (s.trim().equals("")) {
                        rs = subjectdao.viewPrereq();

                        ((DeanPreRequisiteManage) view.get(4)).getTableprerequisites().setModel(DbUtils.resultSetToTableModel(rs));

                    }

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

    }

    private void addPre() throws SQLException {

        SubCoursePre subpre = new SubCoursePre();
        int row = ((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "You must select an subject", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        count = 0;
        if (!((AddPreR) this.view.get(5)).isVisible()) {
            ((AddPreR) this.view.get(5)).setVisible(true);
        }

        ((AddPreR) this.view.get(5)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        subpre.setSubname(((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 0).toString());
        subpre.setSubdescr(((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 1).toString());
        if (((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 2) != null) {
            subpre.setPrereqname(((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 2).toString());
        }
        subpre.setSbyear(Integer.parseInt(((DeanPreRequisiteManage) this.view.get(4)).getTableprerequisites().getModel().getValueAt(row, 3).toString()));
        ((AddPreR) this.view.get(5)).getBtnadd().addActionListener((ActionEvent e) -> {
            System.out.println(subpre);
            if (count == 0) {
                int out = 0;
                count++;
                try {
                    out = subjectdao.addPrerequisite(subpre);
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }

                ResultSet rr = null;
                try {
                    rr = subjectdao.viewPrereq();
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((DeanPreRequisiteManage) Controller.this.view.get(4)).getTableprerequisites().setModel(DbUtils.resultSetToTableModel(rr));
                if (out != 0) {
                    JOptionPane.showMessageDialog((AddPreR) Controller.this.view.get(5), "Added Successfully", "Added", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog((AddPreR) Controller.this.view.get(5), "Added Failed", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void viewEval() {
        ResultSet rs = null;
        try {
            rs = studentdao.viewEvalStud();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((ViewStudentSubEval) this.view.get(7)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ((ViewStudentSubEval) this.view.get(7)).getTableeval().setModel(DbUtils.resultSetToTableModel(rs));
        ((ViewStudentSubEval) this.view.get(7)).getTxtstudidno().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;

                try {

                    if (((ViewStudentSubEval) view.get(7)).getTxtstudidno().getText().matches("[0-9]+")) {
                        int s = Integer.parseInt(((ViewStudentSubEval) view.get(7)).getTxtstudidno().getText());
                        rs = studentdao.viewEvalStud(s);
                        if (rs != null) {

                            ((ViewStudentSubEval) view.get(7)).getTableeval().setModel(DbUtils.resultSetToTableModel(rs));

                        }

                    } else {
                        if (((ViewStudentSubEval) view.get(7)).getTxtstudidno().getText().trim().equals("")) {
                            rs = studentdao.viewEvalStud();

                            ((ViewStudentSubEval) view.get(7)).getTableeval().setModel(DbUtils.resultSetToTableModel(rs));

                        }
                    }

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ((ViewStudentSubEval) view.get(7)).getBtnview().addActionListener((ActionEvent e) -> {
            Student stud = new Student();
            int row = ((ViewStudentSubEval) view.get(7)).getTableeval().getSelectedRow();
            ResultSet rr = null;
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "You must select an subject", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ((ViewEval) view.get(8)).setVisible(true);
            ((ViewEval) this.view.get(8)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
            stud.setStudid(Integer.parseInt(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 0).toString()));
            stud.setStudlname(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 1).toString());
            stud.setStudfname(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 2).toString());
            stud.setStudmi(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 3).toString());
            stud.setCoursename(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 7).toString());
            stud.setStudyear(Integer.parseInt(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 5).toString()));
            stud.setStudsem(Integer.parseInt(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 4).toString()));
            //stud.setEvaluated(Integer.parseInt(((ViewStudentSubEval) this.view.get(7)).getTableeval().getModel().getValueAt(row, 7).toString()));
            ((ViewEval) view.get(8)).getTxtstudidno().setText(stud.getStudid() + "");
            ((ViewEval) view.get(8)).getTxtstudlast().setText(stud.getStudlname());
            ((ViewEval) view.get(8)).getTxtstudfirst().setText(stud.getStudfname());
            ((ViewEval) view.get(8)).getTxtstudmi().setText(stud.getStudmi());
            ((ViewEval) view.get(8)).getTxtstudcourse().setText(stud.getCoursename());
            ((ViewEval) view.get(8)).getTxtyear().setText(stud.getStudyear() + "");
            ResultSet rss = null;
            if (stud.getStudyear() >= 1) {
                try {
                    rss = studentdao.firstyear1sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable11().setModel(DbUtils.resultSetToTableModel(rss));
            }
            if (stud.getStudyear() >= 1) {
                ResultSet rss2 = null;
                try {
                    rss2 = studentdao.firstyear2sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable12().setModel(DbUtils.resultSetToTableModel(rss2));
            }
            if (stud.getStudyear() >= 2) {
                ResultSet rss3 = null;

                try {
                    rss3 = studentdao.secondyear1sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable21().setModel(DbUtils.resultSetToTableModel(rss3));
            }
            if (stud.getStudyear() >= 2) {
                ResultSet rss4 = null;
                try {
                    rss4 = studentdao.secondyear2sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable22().setModel(DbUtils.resultSetToTableModel(rss4));
            }
            if (stud.getStudyear() >= 3) {
                ResultSet rss5 = null;
                try {
                    rss5 = studentdao.thirdyear1sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable31().setModel(DbUtils.resultSetToTableModel(rss5));
            }
            if (stud.getStudyear() >= 3) {
                ResultSet rss6 = null;
                try {
                    rss6 = studentdao.thirdyear2sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable32().setModel(DbUtils.resultSetToTableModel(rss6));
            }
            if (stud.getStudyear() >= 4) {
                ResultSet rss7 = null;
                try {
                    rss7 = studentdao.fourthyear1sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable41().setModel(DbUtils.resultSetToTableModel(rss7));
            }

            if (stud.getStudyear() >= 4) {
                ResultSet rss8 = null;
                try {
                    rss8 = studentdao.fourthyear2sem(stud.getStudid());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((ViewEval) this.view.get(8)).getTable42().setModel(DbUtils.resultSetToTableModel(rss8));
            }
        });
    }

    private void viewUnEval() {
        ResultSet rs = null;
        try {
            rs = studentdao.viewNonEvalStud();
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ((ViewStudentUneval) this.view.get(9)).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ((ViewStudentUneval) this.view.get(9)).getTablenoneval().setModel(DbUtils.resultSetToTableModel(rs));
        ((ViewStudentUneval) this.view.get(9)).getTxtstudidno().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;

                try {

                    if (((ViewStudentUneval) view.get(9)).getTxtstudidno().getText().matches("[0-9]+")) {
                        int s = Integer.parseInt(((ViewStudentUneval) view.get(9)).getTxtstudidno().getText());
                        rs = studentdao.viewNonEvalStud(s);
                        if (rs != null) {

                            ((ViewStudentUneval) view.get(9)).getTablenoneval().setModel(DbUtils.resultSetToTableModel(rs));

                        }

                    } else {
                        if (((ViewStudentUneval) view.get(9)).getTxtstudidno().getText().trim().equals("")) {
                            rs = studentdao.viewNonEvalStud();

                            ((ViewStudentUneval) view.get(9)).getTablenoneval().setModel(DbUtils.resultSetToTableModel(rs));

                        }
                    }

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void userview() {
        ((StudentEvaluation) view.get(10)).setVisible(true);
        ((StudentEvaluation) this.view.get(10)).getTxtstudidno().addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ResultSet rs = null;
                ((StudentEvaluation) view.get(10)).getCbocourse().removeAllItems();
                ((StudentEvaluation) view.get(10)).getTxtstudlast().setText("");
                ((StudentEvaluation) view.get(10)).getTxtstudfirst().setText("");
                ((StudentEvaluation) view.get(10)).getTxtstudmi().setText("");
                try {
                    if (((StudentEvaluation) view.get(10)).getTxtstudidno().getText().matches("[0-9]+")) {
                        int s = Integer.parseInt(((StudentEvaluation) view.get(10)).getTxtstudidno().getText());
                        rs = studentdao.viewStudentEval(s);
                        if (rs.next()) {
                            ((StudentEvaluation) view.get(10)).getTxtstudlast().setText(rs.getString(2));
                            ((StudentEvaluation) view.get(10)).getTxtstudfirst().setText(rs.getString(3));
                            ((StudentEvaluation) view.get(10)).getTxtstudmi().setText(rs.getString(4));
                            if (rs.getInt(6) == 1) {
                                ((StudentEvaluation) view.get(10)).getCboyear().setSelectedItem("1");
                            } else if (rs.getInt(6) == 2) {
                                ((StudentEvaluation) view.get(10)).getCboyear().setSelectedItem("2");
                            } else if (rs.getInt(6) == 3) {
                                ((StudentEvaluation) view.get(10)).getCboyear().setSelectedItem("3");
                            } else if (rs.getInt(6) == 4) {
                                ((StudentEvaluation) view.get(10)).getCboyear().setSelectedItem("4");
                            }
                            if (rs.getInt(7) == 0) {
                                ((StudentEvaluation) view.get(10)).getCbostat().setSelectedItem("old");
                            } else if (rs.getInt(7) == 0) {
                                ((StudentEvaluation) view.get(10)).getCbostat().setSelectedItem("new");
                            }

                            ((StudentEvaluation) view.get(10)).getCbocourse().addItem(rs.getString(9));
                            ((StudentEvaluation) view.get(10)).getCbocourse().setSelectedIndex(0);
                            Student stud = new Student();
                            stud.setStudid(s);
                            stud.setStudyear(rs.getInt(6));
                            year = rs.getInt(6);
                            ResultSet rss = null;
                            if (stud.getStudyear() >= 1) {
                                try {
                                    rss = studentdao.firstyear1sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable11().setModel(DbUtils.resultSetToTableModel(rss));
                            }
                            if (stud.getStudyear() >= 1) {
                                ResultSet rss2 = null;
                                try {
                                    rss2 = studentdao.firstyear2sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable12().setModel(DbUtils.resultSetToTableModel(rss2));
                            }
                            if (stud.getStudyear() >= 2) {
                                ResultSet rss3 = null;

                                try {
                                    rss3 = studentdao.secondyear1sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable21().setModel(DbUtils.resultSetToTableModel(rss3));
                            }
                            if (stud.getStudyear() >= 2) {
                                ResultSet rss4 = null;
                                try {
                                    rss4 = studentdao.secondyear2sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable22().setModel(DbUtils.resultSetToTableModel(rss4));
                            }
                            if (stud.getStudyear() >= 3) {
                                ResultSet rss5 = null;
                                try {
                                    rss5 = studentdao.thirdyear1sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable31().setModel(DbUtils.resultSetToTableModel(rss5));
                            }
                            if (stud.getStudyear() >= 3) {
                                ResultSet rss6 = null;
                                try {
                                    rss6 = studentdao.thirdyear2sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable32().setModel(DbUtils.resultSetToTableModel(rss6));
                            }
                            if (stud.getStudyear() >= 4) {
                                ResultSet rss7 = null;
                                try {
                                    rss7 = studentdao.fourthyear1sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable41().setModel(DbUtils.resultSetToTableModel(rss7));
                            }

                            if (stud.getStudyear() >= 4) {
                                ResultSet rss8 = null;
                                try {
                                    rss8 = studentdao.fourthyear2sem(stud.getStudid());
                                } catch (SQLException ex) {
                                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ((StudentEvaluation) view.get(10)).getTable42().setModel(DbUtils.resultSetToTableModel(rss8));
                            }
                        }
                    }
                } catch (Exception ex) {
                }

            }
        });
    }

    class ThreadDis implements Runnable {

        Login view;

        public ThreadDis(Login view) {
            this.view = view;
        }

        @Override
        public void run() {
            this.view.setVisible(true);
        }

    }
}
