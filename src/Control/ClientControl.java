package Control;

import Game.Algorithm;
import Model.User;
import View.ClientView;
import View.InvitePlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientControl {

    private Socket mySocket;
//    private String serverHost = "192.168.43.215";
    private String serverHost = "localhost";
    private int serverPort = 9876;

    ObjectOutputStream oos;
    ObjectInputStream ois;
    Thread thread;

    private ClientView clientView;

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

    public void startThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread chay ne");
                while (!Thread.currentThread().isInterrupted()) {
                    receiveInvite();
                }
            }
        });
        thread.start();
    }

    public void receiveInvite() {
        try {
            System.out.println("doc 1");
            Object o = ois.readObject();
            if (o instanceof String) {
                String request = (String) o;

                //dươc moi choi
                if (request.equalsIgnoreCase("!invited")) {
                    Object userObject = ois.readObject();
                    if (userObject instanceof User) {
                        User userInvite = (User) userObject;
                        System.out.println("gui yeu cau tu: " + userInvite.toString());
                        InvitePlayer dialogInvite = new InvitePlayer(clientView, true);
                        dialogInvite.setVisible(true);
                        boolean acceptInvite = dialogInvite.getAccept();

                        if (acceptInvite) {
                            oos.writeObject("!acceptInvite");
                            oos.writeObject(userInvite);
                        } else {
                            oos.writeObject("!denyInvite");
                            oos.writeObject(userInvite);
                        }
                    }
                }
            }

            //bi tu choi
            if (o instanceof String) {
                String denyInvition = (String) o;
                if (denyInvition.equalsIgnoreCase("!invite-deny")) {
                    clientView.showMessageDialog("Lời mời bị từ chối!");
                }
            }

            //duoc dong y
            if (o instanceof String) {
                String denyInvition = (String) o;
                if (denyInvition.equalsIgnoreCase("!invite-accept")) {
                    System.out.println("Loi moi duoc chap nhan");
                }
            }

            //nhan duoc Algorithm
            if (o instanceof String) {
                String startGame = (String) o;
                if (startGame.equalsIgnoreCase("!startGame")) {
                    System.out.println("start game");
                    Object alObject = ois.readObject();
                    if (alObject instanceof Algorithm) {
                        Algorithm algorithm = (Algorithm) alObject;
                        clientView.showGame(algorithm);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkLogin(User user) {
        try {
            //send data
            oos.writeObject(new String("!login"));
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
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public ArrayList<User> loadRanking_Score() {
        ArrayList<User> kq = null;
        try {
            //send data
            oos.writeObject(new String("!sendRankingScore"));
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

    public ArrayList<User> loadRanking_AvgScore() {
        ArrayList<User> kq = null;
        try {
            //send data
            oos.writeObject(new String("!sendRankingAvgScore"));
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

    public ArrayList<Vector> loadRanking_AvgTime() {
        ArrayList<Vector> kq = null;
        try {
            //send data
            oos.writeObject(new String("!sendRankingAvgTime"));
            // recieve data
            Object o = ois.readObject();
            if (o instanceof ArrayList) {
                kq = (ArrayList<Vector>) o;

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

    public void sendRequest(User user, User user1) {
        try {
            ArrayList users = new ArrayList<>();
            users.add(user);
            users.add(user1);
            oos.writeObject("!invitePlayer");
            oos.writeObject(users);
            System.out.println(users.toString());

        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    //get set
    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

}
