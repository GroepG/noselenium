package be.kdg.spacecrack.model;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

/* Git $Id$
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */
@Entity
@Table(name="T_User")
public class User implements Serializable{

    @Id
    @GeneratedValue
    private int userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    //@OneToOne(mappedBy = "user")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="accesstokenid", nullable = true)
    @JsonManagedReference
    private AccessToken token;

    public User() {
        System.out.println("lol");
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String pw, String pwherhalen, String email) {
        if(pw.equals(pwherhalen)){
            this.username = username;
            this.password = pw;
            this.email = email;
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccessToken getToken() {
        return token;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }
}
