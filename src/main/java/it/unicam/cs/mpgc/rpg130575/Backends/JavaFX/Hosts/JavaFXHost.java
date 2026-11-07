package it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Hosts;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Game.Game;
import it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Input.JavaFXInputManager;
import it.unicam.cs.mpgc.rpg130575.Hosts.GameHost;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;
import it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Rendering.JavaFXRenderer;
import it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Rendering.JavaFXWindow;
import it.unicam.cs.mpgc.rpg130575.Types.Audio.ISampleStorage;
import it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Types.Audio.JavaFXSampleStore;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStore;
import it.unicam.cs.mpgc.rpg130575.Backends.JavaFX.Types.Textures.JavaFXTextureStore;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class JavaFXHost extends Application
{
    private JavaFXWindow _window;

    private GameHost _gameHost;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Canvas canvas = new Canvas(JavaFXWindow.DefaultWidth * 3, JavaFXWindow.DefaultHeight * 3);

        _window = new JavaFXWindow(primaryStage, canvas, "Untitled Mania Game", JavaFXWindow.DefaultWidth * 3, JavaFXWindow.DefaultHeight * 3);

        Pane pane = new Pane();

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.getScene().setRoot(pane);

        JavaFXRenderer renderer = new JavaFXRenderer(canvas.getGraphicsContext2D());

        JavaFXInputManager inputManager = new JavaFXInputManager();

        scene.setOnKeyPressed(inputManager);
        scene.setOnKeyReleased(inputManager);

        _gameHost = new GameHost(_window, renderer, inputManager, CreateGame());

        AnimationTimer gameLoop = new JavaFXLoop();
        gameLoop.start();
    }

    private CompositeNode CreateGame()
    {
        Game game = new Game();

        DependencyContainer startingDependencies = new DependencyContainer(DependencyContainer.Empty);

        // Cache as the interface.
        startingDependencies.Cache(INativeTextureStore.class, new JavaFXTextureStore());
        startingDependencies.Cache(ISampleStorage.class, new JavaFXSampleStore());

        startingDependencies.Cache(IWindow.class, _window);

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
