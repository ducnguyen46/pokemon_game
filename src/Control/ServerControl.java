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
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
//        getDBConnection("pikachu", "root", "root");
        getDBConnection("pikachu", "root", "Dangtiendat1999!");
        openServer();
        serverRunning = true;

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && serverRunning) {
            listening();
        }
    }

    private void getDBConnection(String dbName, String username, String password) {
        //sua cong
//        String dbUrl = "jdbc:mysql://localhost:3307/" + dbName;
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;

        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Không kết nối được với CSDL");
            e.printStackTrace();
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

    private String createNewGame() {
        Algorithm algorithm = new Algorithm(12, 12);
        int[][] matrix = algorithm.getMatrix();
        String matrixLine = "";
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                matrixLine += matrix[row][col] + ",";
            }
        }
        System.out.println(matrixLine);
        return matrixLine;
    }

}
