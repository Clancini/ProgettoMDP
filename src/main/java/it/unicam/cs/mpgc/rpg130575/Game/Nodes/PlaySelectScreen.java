package it.unicam.cs.mpgc.rpg130575.Game.Nodes;

import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Game.Albums.AlbumInfo;
import it.unicam.cs.mpgc.rpg130575.Game.Albums.AlbumsStore;
import it.unicam.cs.mpgc.rpg130575.Game.SampleStrings;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.DrawNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeNode;
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
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.Tween;
import it.unicam.cs.mpgc.rpg130575.Types.Tweening.TweenManager;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;

public class PlaySelectScreen extends Screen
{
    private IWindow _window;
    private INativeTextureStore _textureStore;
    private ISampleStorage _sampleStorage;
    private ScreenStack _screenStack;
    private TweenManager _tweenManager;

    private ISample _hoverAlbumSample;

    private AlbumsStore _albumsStore;
    private CompositeNode _albumsContainer;
    private SpriteNode _albumSelectionPointer;
    private int _hoveredAlbumIndex = -1;

    private Transform _selectAnAlbumTextTransform;
    private DrawNode _selectAnAlbumTextDrawNode;
    private Tween _selectAnAlbumTextSlideInTween;

    private Tween _screenSlideTween;

    @Override
    public IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parent)
    {
        DependencyContainer newContainer = new DependencyContainer(parent);

        _albumsStore = new AlbumsStore();

        newContainer.Cache(AlbumsStore.class, _albumsStore);

        return newContainer;
    }

    @Override
    protected void OnLoad()
    {
        _window = GetDependencyContainer().Get(IWindow.class);
        _textureStore = GetDependencyContainer().Get(INativeTextureStore.class);
        _sampleStorage = GetDependencyContainer().Get(ISampleStorage.class);
        _screenStack = GetDependencyContainer().Get(ScreenStack.class);
        _tweenManager = GetDependencyContainer().Get(TweenManager.class);

        TextureInfo mainMenuBGInfo = new TextureInfo("/MainMenu/PlaySelectBackground.png", _window.GetWidth(), _window.GetHeight());
        _textureStore.AddTexture(mainMenuBGInfo, "PlaySelectBackground", true);

        TextureInfo selectAnAlbumInfo = new TextureInfo("/MainMenu/SelectAnAlbum.png", 357, 58);
        _textureStore.AddTexture(selectAnAlbumInfo, "SelectAnAlbum", true);

        TextureInfo selectionPointerInfo = new TextureInfo("/MainMenu/SelectionPointer.png", 44, 41);
        _textureStore.AddTexture(selectionPointerInfo, "SelectionPointerRight", true);

        _sampleStorage.AddSample("/MainMenu/osu.EnterPlay.wav", SampleStrings.EnterPlay, true);

        _hoverAlbumSample = _sampleStorage.GetSample(SampleStrings.Hover);

        SpriteNode background = new SpriteNode();
        Add(background);
        background.SetTexture("PlaySelectBackground", true);

        SpriteNode selectAnAlbumText = new SpriteNode();
        Add(selectAnAlbumText);
        selectAnAlbumText.SetTexture("SelectAnAlbum", true);

        _selectAnAlbumTextTransform = selectAnAlbumText.GetLayoutNode().LocalTransform;
        _selectAnAlbumTextTransform.SetScale(1.5f);
        _selectAnAlbumTextTransform.SetPositionY(50);
        _selectAnAlbumTextDrawNode = selectAnAlbumText.GetDrawNode();

        _albumsContainer = new CompositeNode();
        Add(_albumsContainer);

        _albumSelectionPointer = new SpriteNode();
        Add(_albumSelectionPointer);
        _albumSelectionPointer.SetTexture("SelectionPointerRight", true);
        _albumSelectionPointer.GetLayoutNode().LocalTransform.SetPositionX(50);

        try
        {
            CreateAlbums();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        HoverAlbum(0);
    }

    private void CreateAlbums() throws URISyntaxException, IOException, ParserConfigurationException, SAXException
    {
        _albumsStore.ReloadAllAlbums(_albumsStore.FindAlbums());

        for (AlbumInfo info : _albumsStore.GetLoadedAlbums())
        {
            TextureInfo albumListItemInfo = new TextureInfo(info.ListItemPath(), 783, 192);
            _textureStore.AddTexture(albumListItemInfo, info.ListItemLookup(), true);

            SpriteNode albumListItem = new SpriteNode();
            _albumsContainer.Add(albumListItem);
            albumListItem.SetTexture(info.ListItemLookup(), true);

            Transform albumListItemTransform = albumListItem.GetLayoutNode().LocalTransform;
            albumListItemTransform.SetPositionX(150);
            albumListItemTransform.SetPositionY(50 + (150 * _albumsContainer.GetChildrenCount()));
            albumListItemTransform.SetScale(0.65f);
        }
    }

    @Override
    public void OnScreenStart()
    {
        if (_screenSlideTween != null)
            _screenSlideTween.Cancel();

        if (_selectAnAlbumTextSlideInTween != null)
            _selectAnAlbumTextSlideInTween.Cancel();

        _selectAnAlbumTextTransform.SetPositionX(-500);
        _selectAnAlbumTextDrawNode.SetIsVisible(false);

        _screenSlideTween = new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.2, 1000, 0)
                .Then(() -> {
                    _selectAnAlbumTextDrawNode.SetIsVisible(true);

                    _selectAnAlbumTextSlideInTween = new TransformPositionXTween(_selectAnAlbumTextTransform, 0.15, -500, 100);
                    _tweenManager.AddTween(_selectAnAlbumTextSlideInTween);
                });

        _tweenManager.AddTween(_screenSlideTween);
    }

    @Override
    public void OnScreenQuit()
    {
        _tweenManager.AddTween(new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.2, 2000));
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        switch (event.Key)
        {
            case Esc:

                if (event.IsRepeat)
                    return false;

                if (_screenSlideTween != null)
                    _screenSlideTween.Cancel();

                _screenSlideTween = new TransformPositionXTween(GetLayoutNode().LocalTransform, 0.2, 0, 2000)
                        .Then(() -> _screenStack.Pop());

                _tweenManager.AddTween(_screenSlideTween);

                _sampleStorage.GetSample(SampleStrings.MenuExit).Play(0.5f);

                return true;

            case LeftArrow:
            case UpArrow:

                HoverAlbum(GetNextHoverFromDirection(-1));
                return true;

            case RightArrow:
            case DownArrow:

                HoverAlbum(GetNextHoverFromDirection(+1));
                return true;

            case Enter:
            case Space:

                if (event.IsRepeat)
                    return false;

                PlayerScreen playerScreen = new PlayerScreen(_albumsStore.GetLoadedAlbums()[_hoveredAlbumIndex].Charts().get("Tutorial"));

                _screenStack.Push(playerScreen);

                _tweenManager.AddTween(new TransformPositionXTween(playerScreen.GetLayoutNode().LocalTransform, 0.2, 2000, 0));

                _sampleStorage.GetSample(SampleStrings.EnterPlay).Play(0.5f);

                return true;
        }

        return false;
    }

    public int GetNextHoverFromDirection(int direction)
    {
        if (_hoveredAlbumIndex + direction < 0)
            return _albumsContainer.GetChildrenCount() - 1;

        if (_hoveredAlbumIndex + direction > _albumsContainer.GetChildrenCount() - 1)
            return 0;

        return _hoveredAlbumIndex + direction;
    }

    private void HoverAlbum(int index)
    {
        if (index == _hoveredAlbumIndex)
            return;

        _albumSelectionPointer.GetLayoutNode().LocalTransform
                .SetPositionY(50 + 150 * (index + 1) + (192 * 0.65f) / 2 - 41f / 2);

        _hoveredAlbumIndex = index;

        _hoverAlbumSample.Play(0.15f);
    }
}
