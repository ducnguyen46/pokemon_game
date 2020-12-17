/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ClientControl;
import Model.User;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class RankingView extends javax.swing.JFrame {

    private ClientControl clientControl;
    private DefaultTableModel tmScore, tmAvgScore, tmTime;

    /**
     * Creates new form RankingView
     */
    public RankingView(ClientControl clientControl) {
        this.clientControl = clientControl;
        initComponents();
        initTable1();
        loadRanking1();
    }

    private void initTable1() {
        String[] cols = {"Rank", "Username", "Score"};
        tmScore = new DefaultTableModel(cols, 0);
        tblRanking.setModel(tmScore);
    }

    private void loadRanking1() {
        ArrayList<User> a = clientControl.loadRanking_Score();

        int rank = 1;
        if (a != null) {
            tmScore.setRowCount(0);
            System.out.println(a.size());
            for (User i : a) {
                String username = i.getUsername();
                Double score = i.getScore();
                tmScore.addRow(new Object[]{rank, username, score});
                rank++;
            }
        }
    }

    private void initTable2() {
        String[] cols = {"Rank", "Username", "Avg Score"};
        tmAvgScore = new DefaultTableModel(cols, 0);
        tblRanking.setModel(tmAvgScore);
    }

    private void loadRanking2() {
        ArrayList<User> a = clientControl.loadRanking_AvgScore();

        Collections.sort(a, Comparator.comparing(User::getScore).reversed());
        int rank = 1;
        if (a != null) {
            tmAvgScore.setRowCount(0);
            System.out.println(a.size());
            for (User i : a) {
                String username = i.getUsername();
                Double score = i.getScore();
                tmAvgScore.addRow(new Object[]{rank, username, score});
                rank++;
            }

        }
    }

    private void initTable3() {
        String[] cols = {"Rank", "Username", "Avg Time"};
        tmTime = new DefaultTableModel(cols, 0);
        tblRanking.setModel(tmTime);
    }

    private void loadRanking3() {
        ArrayList<Vector> a = clientControl.loadRanking_AvgTime();

        int rank = 1;
        if (a != null) {
            tmTime.setRowCount(0);
            System.out.println(a.size());
            for (Vector i : a) {
                String username = i.get(0).toString();
                String time = i.get(1).toString();
                Time.valueOf(time);
                tmTime.addRow(new Object[]{rank, username, time});
                rank++;
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblRanking = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblRanking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblRanking);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("RANKING");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng số điểm", "Trung bình điểm đối thủ đã gặp", "Trung bình thời gian thắng" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Tiêu chí xếp hạng");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBack))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String choose = jComboBox1.getSelectedItem().toString();
        if (choose.equalsIgnoreCase("Tổng số điểm")) {
            initTable1();
            loadRanking1();
        } else {
            if (choose.equalsIgnoreCase("Trung bình điểm đối thủ đã gặp")) {
                initTable2();
                loadRanking2();
            } else if (choose.equalsIgnoreCase("Trung bình thời gian thắng")) {
                initTable3();
                loadRanking3();
            }
        }


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRanking;
    // End of variables declaration//GEN-END:variables
}
