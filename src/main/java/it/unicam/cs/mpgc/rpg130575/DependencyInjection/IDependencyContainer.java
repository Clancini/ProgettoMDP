package it.unicam.cs.mpgc.rpg130575.DependencyInjection;

public interface IDependencyContainer extends IReadOnlyDependencyContainer
{
    public <T> void Cache(Class<T> type, T instance);
}
