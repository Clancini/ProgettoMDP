package it.unicam.cs.mpgc.rpg130575.DependencyInjection;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer implements IDependencyContainer
{
    public final static DependencyContainer Empty = new DependencyContainer();

    private final Map<Class<?>, Object> _registeredDependencies = new HashMap<>();

    private final IReadOnlyDependencyContainer _parent;

    public DependencyContainer()
    {
        this(null);
    }

    public DependencyContainer(IReadOnlyDependencyContainer parent)
    {
        _parent = parent;
    }

    @Override
    public <T> void Cache(Class<T> type, T instance)
    {
        if (_registeredDependencies.containsKey(type))
            throw new UnsupportedOperationException("An instance of the passed type is already cached in this container");

        _registeredDependencies.put(type, instance);
    }

    @Override
    public <T> T Get(Class<T> type)
    {
        Object found = _registeredDependencies.get(type);

        if (found != null)
            return type.cast(found);

        if (_parent != null)
            found = _parent.Get(type);

        // Give up with null if after climbing the parents we still can't find it.
        if (found == null)
            throw new IllegalStateException("Couldn't find dependency of type " + type);

        return type.cast(found);
    }
}
