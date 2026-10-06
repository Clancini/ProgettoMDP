package it.unicam.cs.mpgc.rpg130575.Hosts;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Game.Game;
import it.unicam.cs.mpgc.rpg130575.Input.JavaFXInputManager;
import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.JavaFXRenderer;
import it.unicam.cs.mpgc.rpg130575.Rendering.JavaFXWindow;
import it.unicam.cs.mpgc.rpg130575.Types.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.JavaFXTextureStorage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class JavaFXHost extends Application
{
    private GameHost _gameHost;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        JavaFXWindow window = new JavaFXWindow(primaryStage, "Untitled Mania Game");

        Pane pane = new Pane();
        Canvas canvas = new Canvas(window.GetWidth(), window.GetHeight());

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.getScene().setRoot(pane);

        JavaFXRenderer renderer = new JavaFXRenderer(canvas.getGraphicsContext2D());

        JavaFXInputManager inputManager = new JavaFXInputManager();

        scene.setOnKeyPressed(inputManager);

        _gameHost = new GameHost(window, renderer, inputManager, CreateGame());

        AnimationTimer gameLoop = new JavaFXLoop();
        gameLoop.start();
    }

    private CompositeLogicNode CreateGame()
    {
        Game game = new Game();

        DependencyContainer startingDependencies = new DependencyContainer();

        // Cache as the interface.
        startingDependencies.Cache(INativeTextureStorage.class, new JavaFXTextureStorage());

        game.Load(startingDependencies);

        return game;
    }

    private class JavaFXLoop extends AnimationTimer
    {
        @Override
        public void handle(long now)
        {
            _gameHost.Update(now);
        }
    }
}
