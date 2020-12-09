package Control;

/**
 *
 * @author 503
 */
import Model.User;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerControl {

    private Connection con;
    private ServerSocket myServer;
    private final int serverPort = 9876;

    public ServerControl() throws Exception {
        getDBConnection("pikachu", "root", "Dangtiendat1999!");
        openServer(serverPort);
        while (true) {
            listenning();
        }
    }

    private void getDBConnection(String dbName, String username,
            String password) throws Exception {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenning() {
        try {
            Socket stuSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(stuSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(stuSocket.getOutputStream());
            Object o = ois.readObject();
            if (o instanceof User) {
                User user = (User) o;
                if (checkLogin(user)) {
                    oos.writeObject(user);
                } else {
                    oos.writeObject(new User(-1, null, null, null, -1, -1));
                }
            }
            if(o instanceof String){
                String request = (String)o;
                
                //dang ki
                if(request.equalsIgnoreCase("!signUp")){
                    Object signUpOject = ois.readObject();
                    if(signUpOject instanceof User){
                        User signUpUser = (User)signUpOject;
                        boolean kq = signUp(signUpUser);
                        if(kq){
                            oos.writeObject(new String("SignUpOK"));
                        } else {
                            oos.writeObject(new String("SignUpNotOK"));
                        }
                    }
                }
            }
        } catch (Exception e) {
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
                user.setScore(rs.getInt("score"));

                System.out.println(user.toString());
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
            
            if(rs.next()){
                return false;
            } else {
                String sqlInsert = "INSERT INTO user VALUES(1, ?, ?, ?, 0);";
                PreparedStatement psInsert = con.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, user.getName());
                psInsert.setString(2, user.getUsername());
                psInsert.setString(3, user.getPassword());
                
                int kq = psInsert.executeUpdate();
                if(kq != 0) return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
