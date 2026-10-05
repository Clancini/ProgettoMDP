package it.unicam.cs.mpgc.rpg130575.DependencyInjection;

public interface IReadOnlyDependencyContainer
{
    public <T> T Get(Class<T> type);
}
