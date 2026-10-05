package it.unicam.cs.mpgc.rpg130575.Nodes;

public class LogicNode
{
    public final Transform Transform = new Transform();

    public void Update() { }

    public DrawNode GetDrawNode() { return new DrawNode(this); }
}
