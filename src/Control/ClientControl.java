
package Control;

import Model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    private Socket mySocket;
//    private String serverHost = "192.168.43.216";
    private String serverHost = "localhost";

    private int serverPort = 9876;

    public ClientControl() {
    }

    public Socket openConnection() {
        try {
            mySocket = new Socket(serverHost, serverPort);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return mySocket;
    }

    public boolean checkLogin(User user) {
        try {
            //send data
            ObjectOutputStream oos
                    = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(new String("!login"));
            new Thread().sleep(500);
            oos.writeObject(user);
            //
            System.out.println("user gửi đi: " + user.toString());
            
            // recieve data
            ObjectInputStream ois
                    = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof User) {
                User resultUser = (User) o;
                //
                System.out.println("user nhận về: " + resultUser.toString());
                if(resultUser.getId() == -1){
                    System.out.println("Sai thông tin");
                    return false;
                } else {
                   return true; 
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean signUp(User user){
        try {
            //send data
            ObjectOutputStream oos
                    = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(new String("!signUp"));
            new Thread().sleep(500);
            oos.writeObject(user);
            //
            System.out.println("user gửi đi: " + user.toString());
            
            // recieve data
            ObjectInputStream ois
                    = new ObjectInputStream(mySocket.getInputStream());
            Object o = ois.readObject();
            if (o instanceof String) {
                String kq = (String)o;
                if(kq.equalsIgnoreCase("SignUpOK")){
                    return true;
                } else if(kq.equalsIgnoreCase("SignUpNotOK")){
                    return false;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
     return false;   
    }

    public boolean closeConnection() {
        try {
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
