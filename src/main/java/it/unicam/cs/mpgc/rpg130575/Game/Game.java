package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKey;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.TextureInfo;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionXTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;


public class Game extends CompositeLogicNode
{
    private final TweenManager _tweenManager = new TweenManager();

    public Game()
    {

    }

    @Override
    protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer container)
    {
        DependencyContainer newDeps = new DependencyContainer(container);

        newDeps.Cache(TweenManager.class, _tweenManager);
        return newDeps;
    }

    @Override
    public void OnLoad()
    {
        GetDependencyContainer().Get(IWindow.class).SetWidth(2048);
        GetDependencyContainer().Get(IWindow.class).SetHeight(1024);

        GetDependencyContainer().Get(INativeTextureStorage.class).AddTexture(
            new TextureInfo("/MainMenu/Backgrounds/MainMenuBackground1.png", 2048, 1024),
            "MainMenuBackground1",
            true);

        SpriteNode bg = new SpriteNode();
        Add(bg);

        bg.SetTexture("MainMenuBackground1", true);
    }

    @Override
    public void PostChildrenUpdate(double deltaSeconds)
    {
        _tweenManager.Update(deltaSeconds);
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        return false;
    }
}
