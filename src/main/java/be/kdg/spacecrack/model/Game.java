package be.kdg.spacecrack.model;/* Git $Id
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */

import javax.persistence.*;

@Entity
@Table(name = "T_Game")
public class Game {

    @Id
    @GeneratedValue
    private int gameId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "playerId")
    private Player player1;

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
