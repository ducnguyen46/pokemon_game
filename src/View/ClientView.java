/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ClientControl;
import Game.Algorithm;
import Game.MyMain;
import Model.User;
import TCPRun.ClientRun;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * pul
 *
 * @author nguye
 */
public class ClientView extends javax.swing.JFrame {

    private User user, user1;
    private DefaultTableModel tmOnline;
    private ClientControl clientControl;

    ArrayList<User> ar = null;

    /**
     * Creates new form ClientView
     */
    public ClientView(ClientControl clientControl, User user) {
        this.clientControl = clientControl;
        this.user = user;
        initComponents();
        initTable();
        loadOnlineList();
        clientControl.setClientView(this);
        clientControl.startThread();
    }

    private void initTable() {
        String[] cols1 = {"Username", "Score", "State"};
        tmOnline = new DefaultTableModel(cols1, 0);
        tblOnlineList.setModel(tmOnline);
    }

    private void loadOnlineList() {

        ArrayList<User> a = clientControl.loadOnlineList(user);
        System.out.println("chay ne");
        if (a != null) {
            tmOnline.setRowCount(0);
            System.out.println(a.size());
            for (User i : a) {
                if (!(i.getUsername().equalsIgnoreCase(user.getUsername()))) {
                    tmOnline.addRow(i.toObject());
                }
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
        tblOnlineList = new javax.swing.JTable();
        btnRanking = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        btnInvite = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblOnlineList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Username", "Score", "State"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOnlineList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOnlineListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOnlineList);

        btnRanking.setText("Ranking");
        btnRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRankingActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("PIKACHU");

        btnLogOut.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        btnLogOut.setText("Log out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnInvite.setText("Invite");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(234, 234, 234)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRanking, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnInvite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnLogOut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInvite)
                        .addGap(18, 18, 18)
                        .addComponent(btnRanking)
                        .addContainerGap(381, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(26, 26, 26))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        //thay đổi state trước
        boolean rs = clientControl.logOut(user);
        if (rs) {
            showMessageDialog("Đã thoát");
            // đóng kết nối
            clientControl.closeConnection();
//            new ClientRun();
        } else {
            showMessageDialog("Chưa thoát được rồi,"
                    + " ở lại chơi thêm với chúng mình!");
        }
        //

        this.dispose();
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void tblOnlineListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOnlineListMouseClicked
        int row = tblOnlineList.getSelectedRow();
        user1 = new User();
        user1.setUsername(tmOnline.getValueAt(row, 0).toString());
        user1.setState(tmOnline.getValueAt(row, 2).toString().equalsIgnoreCase("Đang bận") ? 2 : 1);

        System.out.println(user1.toString());
    }//GEN-LAST:event_tblOnlineListMouseClicked

    private void btnRankingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRankingActionPerformed
        new RankingView(clientControl).setVisible(true);
        
    }//GEN-LAST:event_btnRankingActionPerformed

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        if (user1 == null) {
            showMessageDialog("Trước tiên, hãy chọn đối thủ của bạn");
        } else {
            if (user1.getState() == 2) {
                showMessageDialog("Đối thủ của bạn đang bận");
            } else {
                ObjectOutputStream oosUser1 = null;
                showMessageDialog("Hãy đợi chúng tôi gửi lời thách đấu đến " + user1.getUsername() + "!");
                clientControl.sendRequest(user, user1);
            }
        }
    }//GEN-LAST:event_btnInviteActionPerformed

    public void showMessageDialog(String mes){
        JOptionPane.showMessageDialog(this, mes);
    }
    
    public void showGame(Algorithm algorithm){
        System.out.println("chay game");
        new MyMain(algorithm);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnRanking;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOnlineList;
    // End of variables declaration//GEN-END:variables
}
