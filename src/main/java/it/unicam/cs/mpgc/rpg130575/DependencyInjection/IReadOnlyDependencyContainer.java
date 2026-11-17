package it.unicam.cs.mpgc.rpg130575.DependencyInjection;

/// A read-only view of an IDependencyContainer.
public interface IReadOnlyDependencyContainer
{
    public <T> T Get(Class<T> type);
}
