package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Types.INativeTextureStorage;
import it.unicam.cs.mpgc.rpg130575.Types.TextureInfo;

public class Game extends CompositeLogicNode
{
    public Game()
    {

    }

    @Override
    public void OnLoad()
    {
        LogicNode whiteBox = new LogicNode();
        whiteBox.LocalTransform.SetWidth(50);
        whiteBox.LocalTransform.SetHeight(50);

        Add(whiteBox);

        CompositeLogicNode compo = new CompositeLogicNode();
        compo.LocalTransform.SetPositionX(100);
        compo.LocalTransform.SetPositionY(100);

        Add(compo);

        LogicNode whiteBox2 = new LogicNode();
        whiteBox2.LocalTransform.SetWidth(100);
        whiteBox2.LocalTransform.SetHeight(50);

        compo.Add(whiteBox2);

        LocalTransform.SetPositionY(50);

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
        sprite.LocalTransform.SetPositionX(150);
    }

    @Override
    public void PreChildrenUpdate(double deltaSeconds)
    {
        LocalTransform.SetPositionX(LocalTransform.GetPositionX() + (50 * (float)deltaSeconds));
    }
}
