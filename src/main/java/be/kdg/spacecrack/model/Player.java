package be.kdg.spacecrack.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/* Git $Id$
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */
@Entity
@Table(name = "T_Player")
public class Player {
    @Id
    @GeneratedValue
    private int playerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profileId")
    private Profile profile;

    @OneToMany
    private List<Colony> colonies;
    @OneToMany
    private List<Ship> ships;

    public Player() {
    }

    public Player(Profile profile) {
        this.profile = profile;

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setColonies(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public List<Colony> getColonies() {
        if(colonies == null)
        {
            colonies = new ArrayList<Colony>();
        }
        return colonies;
    }

    public List<Ship> getShips() {
        if(ships == null){ships = new ArrayList<Ship>();}
        return ships;
    }
}
