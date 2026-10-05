package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.CompositeLogicNode;

public class Game extends CompositeLogicNode
{
    public Game()
    {
        RightMovingRectangle rectangle = new RightMovingRectangle();
        rectangle.Transform.SetWidth(100);
        rectangle.Transform.SetHeight(100);

        Add(rectangle);

        RightMovingRectangle rectangle2 = new RightMovingRectangle();
        rectangle2.Transform.SetWidth(100);
        rectangle2.Transform.SetHeight(100);
        rectangle2.Transform.SetPositionY(150);

        Add(rectangle2);
    }
}
