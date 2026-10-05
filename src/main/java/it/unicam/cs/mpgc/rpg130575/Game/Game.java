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
        whiteBox.SetWidth(100);
        whiteBox.SetHeight(100);

        Add(whiteBox);

        CompositeLogicNode compo = new CompositeLogicNode();
        compo.SetPositionX(100);
        compo.SetPositionY(100);

        Add(compo);

        LogicNode whiteBox2 = new LogicNode();
        whiteBox2.SetWidth(50);
        whiteBox2.SetHeight(50);

        compo.Add(whiteBox2);

        LocalTransform.SetPositionY(50);

        compo.Add(new InputHandlingNode());

        SpriteNode sprite = new SpriteNode();
        sprite.SetTexture(new JavaFXTexture("/compassion.jpg", 320 / 3, 318 / 3));
        sprite.SetWidth(sprite.GetTexture().GetWidth());
        sprite.SetHeight(sprite.GetTexture().GetHeight());

        compo.Add(sprite);
    }

    @Override
    public void PreChildrenUpdate(double deltaSeconds)
    {
        //SetPositionX(LocalTransform.GetPositionX() + (50 * (float)deltaSeconds));
    }
}
