package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKey;
import it.unicam.cs.mpgc.rpg130575.Input.CKeyboardKeyEvent;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LayoutNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Types.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.TextureInfo;

public class Game extends CompositeLogicNode
{
    CompositeLogicNode compo;

    public Game()
    {

    }

    @Override
    public void OnLoad()
    {
        LogicNode whiteBox = new LogicNode();
        LayoutNode layoutNode = whiteBox.GetLayoutNode();
        layoutNode.LocalTransform.SetWidth(50);
        layoutNode.LocalTransform.SetHeight(10);

        Add(whiteBox);

        compo = new CompositeLogicNode();
        layoutNode = compo.GetLayoutNode();
        layoutNode.LocalTransform.SetPositionX(100);
        layoutNode.LocalTransform.SetPositionY(100);

        Add(compo);

        LogicNode whiteBox2 = new LogicNode();
        layoutNode = whiteBox2.GetLayoutNode();
        layoutNode.LocalTransform.SetWidth(100);
        layoutNode.LocalTransform.SetHeight(70);
        layoutNode.LocalTransform.SetPositionY(100);

        compo.Add(whiteBox2);

        layoutNode = GetLayoutNode();
        layoutNode.LocalTransform.SetPositionY(50);

        compo.Add(new InputHandlingNode());

        SpriteNode sprite = new SpriteNode();

        GetDependencyContainer()
                .Get(INativeTextureStorage.class)
                .AddTexture(
                        new TextureInfo("/compassion.jpg", 320 / 3, 318 / 3),
                        "compassion",
                        true);

        compo.Add(sprite);

        sprite.SetTexture("compassion", true);
        layoutNode = sprite.GetLayoutNode();
        layoutNode.LocalTransform.SetPositionX(150);
    }

    @Override
    public void PreChildrenUpdate(double deltaSeconds)
    {
        GetLayoutNode().LocalTransform.SetPositionX(GetLayoutNode().LocalTransform.GetPositionX() + (50 * (float)deltaSeconds));
    }

    @Override
    public boolean OnKeyboardKeyDown(CKeyboardKeyEvent event)
    {
        if (event.Key == CKeyboardKey.Q)
        {
            GetDrawNode().SetIsVisible(false);
            return true;
        }

        if (event.Key == CKeyboardKey.W)
        {
            GetDrawNode().SetIsVisible(true);
            return true;
        }

        return false;
    }
}
