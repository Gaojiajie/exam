package gjj.lock;

import java.io.Serializable;

public class Pwd implements Serializable{
    private int id;
    private String name;
    private String userName;
    private String password;

    public Pwd(){}
    public Pwd(int id, String name, String userName, String password ) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public Pwd(String name, String userName) {
        this.name = name;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){return password;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
}