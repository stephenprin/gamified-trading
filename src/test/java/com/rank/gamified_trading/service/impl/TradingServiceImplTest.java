package com.rank.gamified_trading.service.impl;

import com.rank.gamified_trading.repository.AssetCatalog;
import com.rank.gamified_trading.model.User;
import com.rank.gamified_trading.dto.request.TradeRequest;
import com.rank.gamified_trading.exception.AssetNotFoundException;
import com.rank.gamified_trading.repository.PortfolioRepository;
import com.rank.gamified_trading.repository.UserRepository;
import com.rank.gamified_trading.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TradingServiceImplTest {

    private TradingService tradingService;
    private UserRepository userRepository;
    private PortfolioRepository portfolioRepository;
    private AssetCatalog assetCatalog;
    private GamificationService gamificationService;

    @BeforeEach
    void setup() {

        userRepository = new UserRepository();
        portfolioRepository = new PortfolioRepository();
        assetCatalog = new AssetCatalog();


        PortfolioService portfolioService = new PortfolioServiceImpl(portfolioRepository, assetCatalog);
        UserService userService = new UserServiceImpl(userRepository, (PortfolioServiceImpl) portfolioService);
        LeaderboardService leaderboardService = new LeaderboardServiceImpl(userRepository);
        gamificationService = new GamificationServiceImpl( (UserServiceImpl) userService, (LeaderboardServiceImpl) leaderboardService);

        // Trading Service to be tested
        tradingService = new TradingServiceImpl(portfolioRepository, (GamificationServiceImpl) gamificationService, assetCatalog);

        userRepository.save(User.create("prince"));
    }

    @Test
    void testBuyAssetAddsToPortfolio() {
        User user = userRepository.findAll().get(0);

        TradeRequest request = new TradeRequest("BTC", 2);
        tradingService.buyAsset(user.getUserId(), request);

        var portfolio = portfolioRepository.getOrCreate(user.getUserId());
        assertEquals(1, portfolio.getAssets().size());
        assertTrue(portfolio.getTotalValue() > 0);
    }

    @Test
    void testSellAssetReducesQuantity() {
        User user = userRepository.findAll().get(0);

        TradeRequest buyRequest = new TradeRequest("ETH", 10);
        tradingService.buyAsset(user.getUserId(), buyRequest);

        TradeRequest sellRequest = new TradeRequest("ETH", 4);
        tradingService.sellAsset(user.getUserId(), sellRequest);

        var portfolio = portfolioRepository.getOrCreate(user.getUserId());
        assertEquals(6, portfolio.getAssets().get(0).quantity());
    }

    @Test
    void testCannotSellMoreThanOwned() {
        User user = userRepository.findAll().get(0);
        TradeRequest buyRequest = new TradeRequest("AAPL", 2);
        tradingService.buyAsset(user.getUserId(), buyRequest);

        TradeRequest sellRequest = new TradeRequest("AAPL", 3);


        assertThrows(IllegalArgumentException.class,
                () -> tradingService.sellAsset(user.getUserId(), sellRequest));
    }

    @Test
    void testCannotBuyUnknownAsset() {
        User user = userRepository.findAll().get(0);
        TradeRequest invalidRequest = new TradeRequest("INVALID_ASSET", 5);
        assertThrows(AssetNotFoundException.class,
                () -> tradingService.buyAsset(user.getUserId(), invalidRequest));
    }
}