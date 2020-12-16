package Control;

/**
 *
 * @author 503
 */
import Game.Algorithm;
import Model.User;
import View.ServerView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl implements Runnable {

    private Connection con;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    boolean serverRunning;

    
    //user 1
    private ObjectOutputStream oosUser1;
            
            
    public ServerControl(Socket clientSocket) {
        this.clientSocket = clientSocket;

        //sua pass
        getDBConnection("pikachu", "root", "root");
//        getDBConnection("pikachu", "root", "Dangtiendat1999!");
        openServer();
        serverRunning = true;

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && serverRunning) {
            System.out.println("thread main server");
            listening();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        //sua cong
        String dbUrl = "jdbc:mysql://localhost:3307/" + dbName;
//        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;

        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Không kết nối được với CSDL");
        }
    }

    private void openServer() {
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println("Khong tao duoc server");
            e.printStackTrace();
        }
    }

    private void listening() {
        System.out.println("run run");
        try {
            Object o = ois.readObject();
            if (o instanceof String) {
                String request = (String) o;

                //dang nhap
                if (request.equalsIgnoreCase("!login")) {
                    Object loginOject = ois.readObject();
                    if (loginOject instanceof User) {
                        User loginUser = (User) loginOject;
                        if (checkLogin(loginUser)) {
                            oos.writeObject(loginUser);
                            updateStateLogin(loginUser);
                            ServerView.userMap.put(loginUser.getUsername(), oos);
                            ArrayList<String> list = new ArrayList<>(ServerView.userMap.keySet());
                            for (String str : list) {
                                System.out.println(str);
                            }
                        } else {
                            oos.writeObject(new User(-1, null, null, null, -1, -1));
                        }
                    }
                }
            }

            if (o instanceof String) {
                String request = (String) o;
                System.out.println(request);

                //send data
                if (request.equalsIgnoreCase("!sendOnlineList")) {
                    Object loginOject = ois.readObject();
                    if (loginOject instanceof User) {
                        User loginUser = (User) loginOject;
                        oos.writeObject(readDB(loginUser));
                    }
                }

            }

            if (o instanceof String) {
                String request = (String) o;
                System.out.println(request);

                //xep hang tong diem
                if (request.equalsIgnoreCase("!sendRankingScore")) {
                    oos.writeObject(loadRankingScore());
                }
            }

            if (o instanceof String) {
                String request = (String) o;
                System.out.println(request);

                //xep hang trung binh diem doi thu
                if (request.equalsIgnoreCase("!sendRankingAvgScore")) {
                    oos.writeObject(loadRankingAvgScore());
                }
            }
            
            if (o instanceof String) {
                String request = (String) o;
                System.out.println(request);

                //xep hang trung binh thoi gian thang
                if (request.equalsIgnoreCase("!sendRankingAvgTime")) {
                    oos.writeObject(loadRankingAvgTime());
                }
            }

            if (o instanceof String) {
                String request = (String) o;

                //dang ki
                if (request.equalsIgnoreCase("!signUp")) {
                    Object signUpOject = ois.readObject();
                    if (signUpOject instanceof User) {
                        User signUpUser = (User) signUpOject;
                        boolean kq = signUp(signUpUser);
                        if (kq) {
                            oos.writeObject(new String("SignUpOK"));
                        } else {
                            oos.writeObject(new String("SignUpNotOK"));
                        }
                    }
                }

                //logout
                if (request.equalsIgnoreCase("!logout")) {
                    Object logOutObject = ois.readObject();
                    if (logOutObject instanceof User) {
                        User logOutUser = (User) logOutObject;
                        boolean kq = logOut(logOutUser);

                    }
                }
            }
            //moi nguoi choi
            if (o instanceof String) {
                String invitePlayer = (String) o;
                if (invitePlayer.equalsIgnoreCase("!invitePlayer")) {
                    Object usersObject = ois.readObject();
                    ArrayList<User> users = (ArrayList<User>) usersObject;
                    User user = users.get(0);
                    User user1 = users.get(1);
                    System.out.println("user nhan duoc server: " + user1.toString());

                    oosUser1 = ServerView.userMap.get(user1.getUsername());
                    oosUser1.writeObject("!invited");
                    oosUser1.writeObject(user);
                }
            }
            
            //dong y
            if (o instanceof String){
                String denyInvite = (String) o;
                if(denyInvite.equalsIgnoreCase("!acceptInvite")){
                    Object userInviteObject = ois.readObject();
                    User userInvite = (User)userInviteObject;
                    
                    ObjectOutputStream oosUserInvite = ServerView.userMap.get(userInvite.getUsername());
                    oosUserInvite.writeObject("!invite-accept");
                    
                    Algorithm algorithm = new Algorithm(12, 12);
                    oos.writeObject("!startGame");
                    oos.writeObject(algorithm);
                    oosUserInvite.writeObject("!startGame");
                    oosUserInvite.writeObject(algorithm);
                }
            }
            
            //duoc dong y
            if (o instanceof String){
                String denyInvition= (String) o;
                if(denyInvition.equalsIgnoreCase("!invite-accept")){
                    oos.writeObject("!invite-accept");
//                    Algorithm algorithm = new Algorithm(12, 12);
//                    
//                    oosUser1.writeObject("!startGame");
//                    oosUser1.writeObject(algorithm);
//                    System.out.println("");
//                    oos.writeObject("!startGame");
//                    oos.writeObject(algorithm);
                }
            }
            
            
            //tu choi
            if (o instanceof String){
                String denyInvite = (String) o;
                if(denyInvite.equalsIgnoreCase("!denyInvite")){
                    Object userInviteObject = ois.readObject();
                    User userInvite = (User)userInviteObject;
                    
                    ObjectOutputStream oosUserInvite = ServerView.userMap.get(userInvite.getUsername());
                    oosUserInvite.writeObject("!invite-deny");
                }
            }
            
            //bi tu choi
            if (o instanceof String){
                String denyInvition= (String) o;
                if(denyInvition.equalsIgnoreCase("!invite-deny")){
                    oos.writeObject("!invite-deny");
                }
            }

            // create new game
            if (o instanceof String) {
                String createNewGame = (String) o;
                if (createNewGame.equalsIgnoreCase("!NewGame")) {
                    Algorithm algorithm = new Algorithm(12, 12);
                    oos.writeObject(algorithm);

                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLogin(User user) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setScore(rs.getDouble("score"));

                System.out.println(user.toString());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean updateStateLogin(User user) {
        String sql = "UPDATE user SET state = 1 WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());

            int rs = ps.executeUpdate();
            if (rs != 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean signUp(User user) {
        String sqlCheck = "SELECT * FROM user WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sqlCheck);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            } else {
                String sqlInsert = "INSERT INTO user VALUES(0, ?, ?, ?, 0,0);";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, user.getName());
                psInsert.setString(2, user.getUsername());
                psInsert.setString(3, user.getPassword());

                int kq = psInsert.executeUpdate();
                if (kq != 0) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean logOut(User user) {
        if (updateStateLogOut(user)) {
            try {
                //remove user in userMap
                ServerView.userMap.remove(user);

                oos.writeObject(new String("logOutOK"));
                ois.close();
                oos.close();
                clientSocket.close();
                System.out.println("Server Control: " + user.getName() + " logout");
                serverRunning = false;
                return true;
            } catch (IOException ex) {
                try {
                    oos.writeObject(new String("logOutNotOK"));
                    Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex1) {
                    Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return false;
    }

    private boolean updateStateLogOut(User user) {
        String sql = "UPDATE user SET state = 0 WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());

            int rs = ps.executeUpdate();
            if (rs != 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private ArrayList readDB(User user) {
        ArrayList ul = new ArrayList<>();
        String sql = "SELECT username, score, state FROM user WHERE state != 0 AND username != ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    double score = rs.getDouble("score");
                    int state = rs.getInt("state");

                    ul.add(new User(username, score, state));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ul;
    }

    private ArrayList loadRankingScore() {
        ArrayList rscr = new ArrayList<>();
        String sql = "SELECT username, score FROM user ORDER BY score DESC";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    double score = rs.getDouble("score");

                    rscr.add(new User(username, score));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rscr;
    }

    private ArrayList loadRankingAvgScore() {
        ArrayList rscr = new ArrayList<>();
        String sql = "SELECT a.username, AVG(b.score) AS dtb FROM user a, user b WHERE a.ID = ? AND b.ID IN ("
                + "	SELECT IDPlayer FROM game_detail"
                + "	WHERE IDGame IN (SELECT IDGame FROM game_detail WHERE IDPlayer = ?)"
                + "						AND IDPlayer != ?"
                + "	GROUP BY IDPlayer)";
        String sql_1 = "SELECT COUNT(*) AS num_user FROM user";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps_1 = con.prepareStatement(sql_1);
            ResultSet rs_1 = ps_1.executeQuery();
            rs_1.next();
            int cnt = rs_1.getInt("num_user");
            int i = 1;
            while (i <= cnt) {
                ps.setInt(1, i);
                ps.setInt(2, i);
                ps.setInt(3, i);
                ResultSet rs = ps.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        String username = rs.getString("username");
                        double score = rs.getDouble("dtb");

                        rscr.add(new User(username, score));
                    }
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rscr;
    }

    private ArrayList loadRankingAvgTime() {
        ArrayList rscr = new ArrayList<>();
        String sql = "SELECT username, SEC_TO_TIME(AVG(TIME_TO_SEC(timefinish))) AS tgtb"
                + " FROM user, game_detail, game"
                + " WHERE user.ID = game_detail.IDPlayer AND game_detail.IDGame = game.ID AND game_detail.Point = '1'"
                + " GROUP BY username"
                + " ORDER BY TGTB";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    Time timeFinish = rs.getTime("tgtb");
                    Vector v = new Vector();
                    v.add(username);
                    v.add(timeFinish);                           
                    rscr.add(v);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rscr;
    }

}
