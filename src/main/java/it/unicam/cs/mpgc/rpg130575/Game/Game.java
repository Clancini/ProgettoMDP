package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.SpriteNode;
import it.unicam.cs.mpgc.rpg130575.Types.JavaFXTexture;

public class Game extends CompositeLogicNode
{
    public Game()
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
        sprite.SetTexture(new JavaFXTexture("/compassion.jpg", 320 / 3, 318 / 3), true);
        sprite.LocalTransform.SetPositionX(150);

        compo.Add(sprite);
    }

    @Override
    public void PreChildrenUpdate(double deltaSeconds)
    {
        LocalTransform.SetPositionX(LocalTransform.GetPositionX() + (50 * (float)deltaSeconds));
    }
}
