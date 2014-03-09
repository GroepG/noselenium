package be.kdg.spacecrack.controllers;/* Git $Id
 *
 * Project Application Development
 * Karel de Grote-Hogeschool
 * 2013-2014
 *
 */

import be.kdg.spacecrack.model.Game;
import be.kdg.spacecrack.model.Player;
import be.kdg.spacecrack.model.Profile;
import be.kdg.spacecrack.model.User;
import be.kdg.spacecrack.repositories.IMapFactory;
import be.kdg.spacecrack.services.IAuthorizationService;
import be.kdg.spacecrack.services.IGameService;
import be.kdg.spacecrack.services.IProfileService;
import be.kdg.spacecrack.utilities.FirebaseUtil;
import be.kdg.spacecrack.utilities.IFirebaseUtil;
import be.kdg.spacecrack.viewmodels.GameActivePlayerWrapper;
import be.kdg.spacecrack.viewmodels.GameParameters;
import be.kdg.spacecrack.viewmodels.GameViewModel;
import be.kdg.spacecrack.utilities.IViewModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth/game")
public class GameController {


    public static final String GAMESUFFIX = "/gameName/";
    @Autowired
    private IAuthorizationService authorizationService;

    @Autowired
    private IGameService gameService;

    @Autowired
    IProfileService profileService;

    @Autowired
    IViewModelConverter viewModelConverter;

    @Autowired
    IFirebaseUtil firebaseUtil;

    @Autowired
    IMapFactory mapFactory;

    public GameController() {
    }

    @PostConstruct
    private void createMap() {

        mapFactory.createPlanets();
    }

    public GameController(IAuthorizationService authorizationService, IGameService gameService, IProfileService profileService, IViewModelConverter viewModelConverter, IFirebaseUtil firebaseUtil) {
        this.viewModelConverter = viewModelConverter;
        this.authorizationService = authorizationService;
        this.firebaseUtil = firebaseUtil;
        this.gameService = gameService;
        this.profileService = profileService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String createGame(@CookieValue("accessToken") String accessTokenValue, @RequestBody @Valid GameParameters gameData) {
        User user = authorizationService.getUserByAccessTokenValue(accessTokenValue);
        int profileId;

        profileId = gameData.getOpponentProfileId();


        Profile opponentProfile = profileService.getProfileByProfileId(profileId);
        int gameId = gameService.createGame(user.getProfile(), gameData.getGameName(), opponentProfile);
        return gameId + "";
    }

    @RequestMapping(value = "/specificGame/{gameId}", method = RequestMethod.GET)
    @ResponseBody
    public GameActivePlayerWrapper getGameByGameId(@CookieValue("accessToken") String accessTokenValue, @PathVariable String gameId) {
        Game game = gameService.getGameByGameId(Integer.parseInt(gameId));
        User user = authorizationService.getUserByAccessTokenValue(accessTokenValue);
        Player player = gameService.getActivePlayer(user, game);
        GameViewModel gameViewModel = viewModelConverter.convertGameToViewModel(game);
        GameActivePlayerWrapper gameActivePlayerWrapper = new GameActivePlayerWrapper(gameViewModel, player.getPlayerId(), FirebaseUtil.FIREBASEURLBASE + GAMESUFFIX + game.getName());
        firebaseUtil.setValue(GAMESUFFIX + game.getName(), gameViewModel);
        return gameActivePlayerWrapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<GameViewModel> getGamesByAccessToken(@CookieValue("accessToken") String accessTokenValue) {
        User user = authorizationService.getUserByAccessTokenValue(accessTokenValue);
        List<Game> games = gameService.getGames(user);
        List<GameViewModel> gameViewModels = new ArrayList<GameViewModel>();
        for (Game g : games) {
            GameViewModel gameViewModel = new GameViewModel();
            gameViewModel.setName(g.getName());
            gameViewModel.setGameId(g.getGameId());
            gameViewModels.add(gameViewModel);
        }
        return gameViewModels;
    }

}
