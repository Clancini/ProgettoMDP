package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;

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
    }

    @Override
    public void PreChildrenUpdate()
    {
        SetPositionX(LocalTransform.GetPositionX() + 0.1f);
    }
}
