package be.kdg.spacecrack.viewmodels;/* Git $Id
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GameParameters {
    @NotNull @NotEmpty @Pattern(message = "The game name should only contain letters and/or numbers",regexp = "^[a-zA-Z0-9]+$")
    private String gameName;
    @NotNull
    private int opponentProfileId;

    public GameParameters() {}

    public GameParameters(String gameName, int opponentProfileId) {
        this.gameName = gameName;
        this.opponentProfileId = opponentProfileId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getOpponentProfileId() {
        return opponentProfileId;
    }

    public void setOpponentProfileId(int opponentProfileId) {
        this.opponentProfileId = opponentProfileId;
    }
}
