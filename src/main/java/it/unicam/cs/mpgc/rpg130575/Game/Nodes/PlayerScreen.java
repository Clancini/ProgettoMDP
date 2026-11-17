package it.unicam.cs.mpgc.rpg130575.Game.Nodes;

import it.unicam.cs.mpgc.rpg130575.Game.Albums.*;

import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mpgc.rpg130575.Game.Behavior.*;
import it.unicam.cs.mpgc.rpg130575.Game.SampleStrings;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Screens.Screen;
import it.unicam.cs.mpgc.rpg130575.Nodes.Screens.ScreenStack;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Rendering.IWindow;
import it.unicam.cs.mpgc.rpg130575.Types.Audio.ISample;
import it.unicam.cs.mpgc.rpg130575.Types.Audio.ISampleStorage;
import it.unicam.cs.mpgc.rpg130575.Types.Layout.Transform;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.INativeTextureStore;
import it.unicam.cs.mpgc.rpg130575.Types.Textures.TextureInfo;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionXTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TransformPositionYTween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;

public class PlayerScreen extends Screen
{
    private final ChartInfo _chartInfo;

    private INativeTextureStore _textureStore;
    private ISampleStorage _sampleStore;
    private ScreenStack _screenStack;
    private TweenManager _tweenManager;

    private ISample _musicTrack;
    private ISample _hitSound;
    private ISample _missSound;

    private Clock _clock;
    private PlayerDirector _director;
    private NoteHitChecker _noteHitChecker;

    private float _windowWidth;
    private float _windowHeight;

    private final int _laneCount = 4;
    private final int _laneToLaneDistance = 200;
    private final int _laneLineWidth = 4;
    private float _lanesLeftX;

    private CompositeNode _lanesContainer;

    private final int _hitLineThickness = 6;
    private final int _hitLineBottomMargin = 150;
    private float _hitLineCenterY;

    private SpriteNode _hitLine;

    private final int _noteWidth = 100;
    private final int _noteHeight = 24;

    private final List<VisualNote> _visualNotes = new ArrayList<>();
    private final List<VisualNote> _pendingNoteRemovals = new ArrayList<>();

    private final double _introWaitSeconds = 5;
    private final double _lanesSlideInSeconds = 0.5;
    private final double _hitLineSlideInSeconds = 0.5;

    private Tween _introWaitTween;
    private Tween _lanesSlideInTween;
    private Tween _hitLineSlideInTween;

    private Tween _lanesPauseTween;
    private Tween _hitLinePauseTween;

    private final double _holdToQuitSeconds = 0.5;
    private final double _quitTextSlideInSeconds = 0.2;

    private SpriteNode _holdEscToQuitText;

    private float _quitTextHiddenX;
    private float _quitTextVisibleX;

    private boolean _isHoldingEsc;
    private boolean _isExiting;

    private Tween _quitTextSlideInTween;
    private Tween _quitHoldTween;
    private Tween _quitTextSlideOutTween;

    private SpriteNode _storyBoardSprite;

    private record VisualNote(ChartNoteInfo _chartNoteInfo, SpriteNode _sprite) { }

    public PlayerScreen(ChartInfo chartInfo)
    {
        _chartInfo = chartInfo;
    }

    @Override
    public void OnLoad()
    {
        _textureStore = GetDependencyContainer().Get(INativeTextureStore.class);
        _sampleStore = GetDependencyContainer().Get(ISampleStorage.class);
        _screenStack = GetDependencyContainer().Get(ScreenStack.class);
        _tweenManager = GetDependencyContainer().Get(TweenManager.class);
        IWindow window = GetDependencyContainer().Get(IWindow.class);

        _windowWidth = window.GetWidth();
        _windowHeight = window.GetHeight();

        int lanesWidth = (_laneCount - 1) * _laneToLaneDistance + _laneLineWidth;

        _lanesLeftX = (_windowWidth - lanesWidth) / 2f;
        _hitLineCenterY = _windowHeight - _hitLineBottomMargin;

        float scale = 0.75f;
        float textWidth = 363 * scale;

        _quitTextHiddenX = _windowWidth - (-350) - textWidth;
        _quitTextVisibleX = _windowWidth - 35 - textWidth;

        TextureInfo bgInfo = new TextureInfo(_chartInfo.BackgroundPath(), window.GetWidth(), window.GetHeight());
        _textureStore.AddTexture(bgInfo, _chartInfo.BackgroundLookup(), true);

        TextureInfo laneInfo = new TextureInfo("/MainMenu/Rectangle.png", _laneLineWidth, (int) _windowHeight);
        _textureStore.AddTexture(laneInfo, "PlayerLane", true);

        TextureInfo hitLineInfo = new TextureInfo("/MainMenu/Rectangle.png", (int) _windowWidth, _hitLineThickness);
        _textureStore.AddTexture(hitLineInfo, "PlayerHitLine", true);

        TextureInfo noteInfo = new TextureInfo("/MainMenu/Rectangle.png", _noteWidth, _noteHeight);
        _textureStore.AddTexture(noteInfo, "PlayerNote", true);

        for (IChartEventInfo event : _chartInfo.Events())
        {
            if (!(event instanceof AssetChartEventInfo assetEvent))
                continue;

            System.out.println("Loading storyboard asset: " + assetEvent.AssetPath());

            TextureInfo assetInfo = new TextureInfo(assetEvent.AssetPath(), assetEvent.Width(), assetEvent.Height());
            _textureStore.AddTexture(assetInfo, assetEvent.AssetPath(), true);
        }

        _sampleStore.AddSample(_chartInfo.TrackPath(), _chartInfo.TrackLookup(), true);
        _sampleStore.AddSample("/MainMenu/osu.Hit.wav", "PlayerHit", true);
        _sampleStore.AddSample("/MainMenu/osu.Miss.mp3", "PlayerMiss", true);

        _musicTrack = _sampleStore.GetSample(_chartInfo.TrackLookup());
        _hitSound = _sampleStore.GetSample("PlayerHit");
        _missSound = _sampleStore.GetSample("PlayerMiss");

        _clock = new Clock();
        _director = new PlayerDirector(_chartInfo, _clock);
        _noteHitChecker = new NoteHitChecker(_clock);
        ScoreKeeper _scoreKeeper = new ScoreKeeper();

        _director.AddNoteCreatedListener(this::OnNoteSpawned);
        _director.AddNoteCreatedListener(_noteHitChecker::OnNoteSpawned);
        _director.AddEventListener(this::OnChartEvent);

        _noteHitChecker.AddNoteDestroyListener(this::OnNotesDestroyed);
        _noteHitChecker.AddResultListener(this::OnNoteResult);

        _noteHitChecker.AddResultListener(_scoreKeeper::OnNoteResult);

        SpriteNode background = new SpriteNode();
        Add(background);
        background.SetTexture(_chartInfo.BackgroundLookup(), true);

        _holdEscToQuitText = new SpriteNode();
        Add(_holdEscToQuitText);
        _holdEscToQuitText.SetTexture("HoldEscToQuitLeft", true);

        Transform holdEscToQuitTransform = _holdEscToQuitText.GetLayoutNode().LocalTransform;
        holdEscToQuitTransform.SetPositionX(_quitTextHiddenX);
        holdEscToQuitTransform.SetPositionY(_windowHeight - 59 - 35);
        holdEscToQuitTransform.SetScale(scale);

        _lanesContainer = new CompositeNode();
        Add(_lanesContainer);

        Transform lanesTransform = _lanesContainer.GetLayoutNode().LocalTransform;
        lanesTransform.SetPositionX(_lanesLeftX);
        lanesTransform.SetPositionY(_windowHeight);

        for (int i = 0; i < _laneCount; i++)
        {
            SpriteNode lane = new SpriteNode();
            _lanesContainer.Add(lane);
            lane.SetTexture("PlayerLane", true);
            lane.GetLayoutNode().LocalTransform.SetPositionX(i * _laneToLaneDistance);
        }

        _hitLine = new SpriteNode();
        Add(_hitLine);
        _hitLine.SetTexture("PlayerHitLine", true);

        Transform hitLineTransform = _hitLine.GetLayoutNode().LocalTransform;
        hitLineTransform.SetPositionX(_windowWidth);
        hitLineTransform.SetPositionY(_hitLineCenterY - _hitLineThickness / 2f);

        _storyBoardSprite = new SpriteNode();
        Add(_storyBoardSprite);
    }

    @Override
    public void OnScreenStart()
    {
        Transform lanesTransform = _lanesContainer.GetLayoutNode().LocalTransform;
        Transform hitLineTransform = _hitLine.GetLayoutNode().LocalTransform;

        _introWaitTween = new TransformPositionYTween(lanesTransform, _introWaitSeconds, _windowHeight, _windowHeight)
                .Then(() -> {
                    _lanesSlideInTween = new TransformPositionYTween(lanesTransform, _lanesSlideInSeconds, _windowHeight, 0)
                            .Then(() -> {
                                _clock.Start();
                                _musicTrack.Play(0.25f);
                            });

                    _hitLineSlideInTween = new TransformPositionXTween(hitLineTransform, _hitLineSlideInSeconds, _windowWidth, 0);

                    _tweenManager.AddTween(_hitLineSlideInTween);
                    _tweenManager.AddTween(_lanesSlideInTween);
                });

        _tweenManager.AddTween(_introWaitTween);
    }

    @Override
    public void OnScreenQuit() { }

    @Override
    public void PreChildrenUpdate(double deltaSeconds)
    {
        _clock.Update(deltaSeconds);

        _director.Update();

        UpdateNotes();

        _noteHitChecker.Update();
    }

    private void UpdateNotes()
    {
        double currentTime = _clock.GetTime();

        _pendingNoteRemovals.clear();

        for (VisualNote visualNote : _visualNotes)
        {
            ChartNoteInfo note = visualNote._chartNoteInfo;
            SpriteNode sprite = visualNote._sprite;

            double spawnTime = note.PressTime() - PlayerDirector.NoteTravelSeconds;
            double progress = (currentTime - spawnTime) / PlayerDirector.NoteTravelSeconds;

            progress = Math.clamp(progress, 0.0, 1.0);

            float startY = -_noteHeight;
            float targetY = _hitLineCenterY - _noteHeight / 2f;

            float currentY = (float) (startY + (targetY - startY) * progress);

            sprite.GetLayoutNode().LocalTransform.SetPositionY(currentY);

            if (currentTime > note.PressTime() + NoteHitChecker.HitWindowSeconds)
                _pendingNoteRemovals.add(visualNote);
        }

        for (VisualNote visualNote : _pendingNoteRemovals)
            _lanesContainer.Remove(visualNote._sprite);

        _visualNotes.removeAll(_pendingNoteRemovals);
    }

    private void OnNoteSpawned(ChartNoteInfo note)
    {
        if (note.Lane() < 0 || note.Lane() >= _laneCount)
            return;

        SpriteNode noteSprite = new SpriteNode();
        _lanesContainer.Add(noteSprite);
        noteSprite.SetTexture("PlayerNote", true);

        float laneX = note.Lane() * _laneToLaneDistance;

        Transform transform = noteSprite.GetLayoutNode().LocalTransform;
        transform.SetPositionX(laneX + (_laneLineWidth - _noteWidth) / 2f);
        transform.SetPositionY(-_noteHeight);

        _visualNotes.add(new VisualNote(note, noteSprite));

        System.out.println("NOTE CREATED at lane " + note.Lane());
    }

    private void OnNotesDestroyed(ChartNoteInfo note)
    {
        for (VisualNote visualNote : _visualNotes)
        {
            if (visualNote._chartNoteInfo.equals(note))
            {
                _lanesContainer.Remove(visualNote._sprite);
                _visualNotes.remove(visualNote);
                break;
            }
        }
    }

    private void OnNoteResult(NoteHitResult result)
    {
        switch (result)
        {
            case HIT:
                _hitSound.Play(0.25f);
                break;

            case MISS:
                _missSound.Play(0.25f);
                break;
        }
    }

    private void OnChartEvent(IChartEventInfo event)
    {
        System.out.println("Running chart event of type " + event.Type());

        switch (event.Type())
        {
            case PAUSE:
            case RESUME:
                AnimatePause(event.Type());
                break;

            case IMAGE_SHOW:
                ShowStoryboardImage(event);
                break;

            case IMAGE_HIDE:
                HideStoryboardImage();
                break;

            default:
                break;
        }
    }

    private void AnimatePause(ChartEventType event)
    {
        CancelPauseTweens();

        Transform lanesTransform =
                _lanesContainer.GetLayoutNode().LocalTransform;

        Transform hitLineTransform =
                _hitLine.GetLayoutNode().LocalTransform;

        float lanesStart = event == ChartEventType.PAUSE ? 0 : _windowHeight;
        float lanesEnd = event == ChartEventType.PAUSE ? _windowHeight : 0;

        float hitLineStart = event == ChartEventType.PAUSE ? 0 : _windowWidth;
        float hitLineEnd = event == ChartEventType.PAUSE ? _windowWidth : 0;

        _lanesPauseTween = new TransformPositionYTween(
                lanesTransform, _lanesSlideInSeconds, lanesStart, lanesEnd
        );

        _hitLinePauseTween = new TransformPositionXTween(
                hitLineTransform, _hitLineSlideInSeconds, hitLineStart, hitLineEnd
        );

        _tweenManager.AddTween(_lanesPauseTween);
        _tweenManager.AddTween(_hitLinePauseTween);
    }

    private void CancelPauseTweens()
    {
        if (_lanesPauseTween != null)
            _lanesPauseTween.Cancel();

        if (_hitLinePauseTween != null)
            _hitLinePauseTween.Cancel();
    }

    private void ShowStoryboardImage(IChartEventInfo event)
    {
        if (!(event instanceof AssetChartEventInfo assetEvent))
            return;

        _storyBoardSprite.SetTexture(assetEvent.AssetPath(), true);
        _storyBoardSprite.GetDrawNode().SetIsVisible(true);

        Transform storyBoardTransform = _storyBoardSprite.GetLayoutNode().LocalTransform;

        storyBoardTransform.SetPositionX(_windowWidth / 2 - storyBoardTransform.GetWidth() / 2);
        storyBoardTransform.SetPositionY(_windowHeight / 2 - storyBoardTransform.GetHeight() / 2);
    }

    private void HideStoryboardImage()
    {
        _storyBoardSprite.GetDrawNode().SetIsVisible(false);
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        switch (event.Key)
        {
            case ESCAPE:

                if (!_isHoldingEsc && !_isExiting)
                {
                    if (_quitTextSlideOutTween != null)
                        _quitTextSlideOutTween.Cancel();

                    _isHoldingEsc = true;

                    Transform textTransform = _holdEscToQuitText.GetLayoutNode().LocalTransform;

                    _quitTextSlideInTween = new TransformPositionXTween(textTransform, _quitTextSlideInSeconds, _quitTextVisibleX)
                            .Then(() -> {
                                _quitHoldTween = new TransformPositionXTween(textTransform, _holdToQuitSeconds - _quitTextSlideInSeconds, _quitTextVisibleX, _quitTextVisibleX)
                                        .Then(this::FinishExit);

                                _tweenManager.AddTween(_quitHoldTween);
                            });

                    _tweenManager.AddTween(_quitTextSlideInTween);
                }

                return true;

            case D:

                if (event.IsRepeat)
                    return false;

                _noteHitChecker.OnLaneHit(0);

                return true;

            case F:

                if (event.IsRepeat)
                    return false;

                _noteHitChecker.OnLaneHit(1);

                return true;

            case J:

                if (event.IsRepeat)
                    return false;

                _noteHitChecker.OnLaneHit(2);

                return true;

            case K:

                if (event.IsRepeat)
                    return false;

                _noteHitChecker.OnLaneHit(3);

                return true;
        }

        return false;
    }

    private void FinishExit()
    {
        if (_introWaitTween != null)
            _introWaitTween.Cancel();

        if (_lanesSlideInTween != null)
            _lanesSlideInTween.Cancel();

        if (_hitLineSlideInTween != null)
            _hitLineSlideInTween.Cancel();

        _isHoldingEsc = false;
        _isExiting = true;

        _sampleStore.GetSample(SampleStrings.MenuExit).Play(0.5f);

        _tweenManager.AddTween(new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.2, 0, 2000)
                .Then(() -> {
                    _screenStack.Pop();
                    _musicTrack.Stop();
                }));
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
        if (!_isHoldingEsc)
            return;

        _isHoldingEsc = false;

        _quitTextSlideInTween.Cancel();

        if (_quitHoldTween != null)
            _quitHoldTween.Cancel();

        _quitTextSlideOutTween = new TransformPositionXTween(_holdEscToQuitText.GetLayoutNode().LocalTransform, 0.3, _quitTextHiddenX);
        _tweenManager.AddTween(_quitTextSlideOutTween);
    }
}