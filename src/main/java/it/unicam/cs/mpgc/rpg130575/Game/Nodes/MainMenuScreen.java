package it.unicam.cs.mpgc.rpg130575.Game.Nodes;

import it.unicam.cs.mpgc.rpg130575.Game.SampleStrings;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Screens.Screen;
import it.unicam.cs.mpgc.rpg130575.Nodes.Screens.ScreenStack;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;
import it.unicam.cs.mpgc.rpg130575.Types.Audio.ISampleStorage;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStore;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.TextureInfo;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionXTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;

public class MainMenuScreen extends Screen
{
    private IWindow _window;
    private INativeTextureStore _textureStore;
    private ISampleStorage _sampleStorage;
    private ScreenStack _screenStack;
    private TweenManager _tweenManager;

    private PlaySelectScreen _playSelectScreen;

    private SpriteNode _holdEscToQuitText;

    private final double _holdQuitSeconds = 0.5;
    private final double _holdQuitTextInSeconds = 0.2;

    private boolean _isQuitting;
    private Tween _quittingTween;
    private Tween _quittingReturnTween;

    private Tween _quittingTextTween;
    private Tween _quittingTextTrackTween;
    private Tween _quittingTextReturnTween;

    @Override
    protected void OnLoad()
    {
        _window = GetDependencyContainer().Get(IWindow.class);
        _textureStore = GetDependencyContainer().Get(INativeTextureStore.class);
        _sampleStorage = GetDependencyContainer().Get(ISampleStorage.class);
        _screenStack = GetDependencyContainer().Get(ScreenStack.class);
        _tweenManager = GetDependencyContainer().Get(TweenManager.class);

        TextureInfo mainMenuBGInfo = new TextureInfo("/MainMenu/Background.png", _window.GetWidth(), _window.GetHeight());
        _textureStore.AddTexture(mainMenuBGInfo, "MainMenuBackground", true);

        TextureInfo holdEscToQuitInfo = new TextureInfo("/MainMenu/HoldEscToQuit.png", 363, 59);
        _textureStore.AddTexture(holdEscToQuitInfo, "HoldEscToQuitLeft", true);

        _sampleStorage.AddSample("/MainMenu/osu.MainMenuBack.wav", SampleStrings.MenuExit, true);
        _sampleStorage.AddSample("/MainMenu/osu.ButtonSelectMedium.wav", SampleStrings.ButtonSelectMedium, true);
        _sampleStorage.AddSample("/MainMenu/osu.Hover.wav", SampleStrings.Hover, true);

        SpriteNode background = new SpriteNode();
        Add(background);
        background.SetTexture("MainMenuBackground", true);

        _holdEscToQuitText = new SpriteNode();
        Add(_holdEscToQuitText);
        _holdEscToQuitText.SetTexture("HoldEscToQuitLeft", true);
        Transform holdEscToQuitTransform = _holdEscToQuitText.GetLayoutNode().LocalTransform;
        holdEscToQuitTransform.SetPositionY(_window.GetHeight() - holdEscToQuitInfo.GetHeight() - 35);
        holdEscToQuitTransform.SetPositionX(-350);
        holdEscToQuitTransform.SetScale(0.75f);

        _playSelectScreen = new PlaySelectScreen();
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        switch (event.Key)
        {
            case ESCAPE:

                if (!_isQuitting)
                {
                    if (_quittingReturnTween != null)
                        _quittingReturnTween.Cancel();

                    if (_quittingTextReturnTween != null)
                        _quittingTextReturnTween.Cancel();

                    _isQuitting = true;

                    _quittingTween = new TransformPositionXTween(GetLayoutNode().LocalTransform, _holdQuitSeconds, -200)
                            .Then(() -> {
                                _sampleStorage.GetSample(SampleStrings.MenuExit).Play(0.5f);

                                _tweenManager.AddTween(new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.2, 0, -2000)
                                        .Then(() -> {
                                            _screenStack.Pop();

                                            SetAcceptsInput(false);
                                            SetIgnoreSubtreeForInput(true);

                                            System.exit(0);
                                        }));
                            });

                    Transform textTransform = _holdEscToQuitText.GetLayoutNode().LocalTransform;

                    _quittingTextTween = new TransformPositionXTween(textTransform, _holdQuitTextInSeconds, 115)
                            .Then(() -> {
                                _quittingTextTrackTween = new TransformPositionXTween(textTransform, _holdQuitSeconds - _holdQuitTextInSeconds, 115, 235);
                                _tweenManager.AddTween(_quittingTextTrackTween);
                            });

                    _tweenManager.AddTween(_quittingTween);
                    _tweenManager.AddTween(_quittingTextTween);
                }

                return true;

            case ENTER:
            case SPACE:

                StopQuitting();

                _screenStack.Push(_playSelectScreen);

                _sampleStorage.GetSample(SampleStrings.ButtonSelectMedium).Play(0.5f);

                return true;
        }

        return false;
    }

    @Override
    public boolean OnKeyboardKeyUp(CKeyboardKeyEvent event)
    {
        switch (event.Key)
        {
            case ESCAPE:

                StopQuitting();

                return true;
        }

        return false;
    }

    private void StopQuitting()
    {
        if (_isQuitting)
        {
            _isQuitting = false;

            _quittingTween.Cancel();
            _quittingTextTween.Cancel();

            _quittingReturnTween = new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.1, 0);

            _quittingTextReturnTween = new TransformPositionXTween(_holdEscToQuitText.GetLayoutNode().LocalTransform, 0.3, -350);

            _tweenManager.AddTween(_quittingReturnTween);
            _tweenManager.AddTween(_quittingTextReturnTween);
        }
    }

    @Override
    public void OnScreenStart() { }

    @Override
    public void OnScreenQuit() { }
}
