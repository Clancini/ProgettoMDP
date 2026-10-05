package it.unicam.cs.mpgc.rpg130575.Game;

import it.unicam.cs.mpgc.rpg130575.Nodes.LogicNode;

public class RightMovingRectangle extends LogicNode
{
    @Override
    public void Update()
    {
        if (Transform.GetPositionX() < 1000)
            Transform.SetPositionX(Transform.GetPositionX() + 1);
    }
}
