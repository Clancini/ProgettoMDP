package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;

public class RightMovingRectangle extends LogicNode
{
    @Override
    public void Update(double deltaSeconds)
    {
        if (WorldTransform.GetPositionX() < 1000)
            WorldTransform.SetPositionX(WorldTransform.GetPositionX() + 1);
    }
}
