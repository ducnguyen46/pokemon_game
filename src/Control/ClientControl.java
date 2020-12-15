package Control;

import Game.Algorithm;
import Model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    //public ArrayList<User> a = new ArrayList<>();
    private Socket mySocket;
//    private String serverHost = "192.168.43.215";
    private String serverHost = "localhost";

    private int serverPort = 9876;

    //
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ClientControl() {
    }

    public Socket openConnection() {
        try {
            mySocket = new Socket(serverHost, serverPort);
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            ois = new ObjectInputStream(mySocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return mySocket;
    }

    public boolean checkLogin(User user) {
        try {
            //send data
            oos.writeObject(new String("!login"));
            new Thread().sleep(500);
            oos.writeObject(user);
            //
            System.out.println("user gửi đi: " + user.toString());

            // recieve data
            Object o = ois.readObject();
            if (o instanceof User) {
                User resultUser = (User) o;
                //
                System.out.println("user nhận về: " + resultUser.toString());
                if (resultUser.getId() == -1) {
                    System.out.println("Sai thông tin");
                    return false;
                } else {
                    user = resultUser;
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

    public boolean signUp(User user) {
        try {
            //send data
            oos.writeObject(new String("!signUp"));
            new Thread().sleep(500);
            oos.writeObject(user);
            //
            System.out.println("user gửi đi: " + user.toString());

            // recieve data
            Object o = ois.readObject();
            if (o instanceof String) {
                String kq = (String) o;
                if (kq.equalsIgnoreCase("SignUpOK")) {
                    return true;
                } else if (kq.equalsIgnoreCase("SignUpNotOK")) {
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

    public ArrayList<User> loadOnlineList(User user) {
        ArrayList<User> kq = null;
        try {
            //send data
            oos.writeObject(new String("!sendOnlineList"));
            oos.writeObject(user);
            // recieve data
            Object o = ois.readObject();
            if (o instanceof ArrayList) {
                kq = (ArrayList<User>) o;

            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return kq;
    }

    public boolean logOut(User user) {
        try {
            //send data
            oos.writeObject(new String("!logout"));
            new Thread().sleep(500);
            oos.writeObject(user);
            //
            System.out.println("user gửi đi-logout: " + user.toString());

            // recieve data
            Object o = ois.readObject();
            if (o instanceof String) {
                String kq = (String) o;
                if (kq.equalsIgnoreCase("logOutOK")) {
                    return true;
                } else if (kq.equalsIgnoreCase("logOutNotOK")) {
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

    public Algorithm createNewGame() {
        Algorithm algorithm = null;
        try {
            //send data
            oos.writeObject(new String("!NewGame"));
            // recieve data

            Object o = ois.readObject();
            if (o instanceof Algorithm) {
                algorithm = (Algorithm) o;

            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return algorithm;
    }

    public boolean closeConnection() {
        try {
            ois.close();
            oos.close();
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
