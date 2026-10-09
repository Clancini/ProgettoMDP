import it.unicam.cs.mpgc.rpg130575.DependencyInjection.DependencyContainer;
import it.unicam.cs.mpgc.rpg130575.DependencyInjection.IReadOnlyDependencyContainer;
import it.unicam.cs.mpgc.rpg130575.Nodes.Composite.CompositeLogicNode;
import it.unicam.cs.mpgc.rpg130575.Nodes.Base.LogicNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LogicNodeDependencyInjectionTests
{
    @Test
    public void ChildNodeGetsDependencyFromContainer()
    {
        final TestDependency topmostDependency = new TestDependency("Topmost");

        TestContainer topmostContainer = new TestContainer(topmostDependency);

        topmostContainer.Load(DependencyContainer.Empty);

        TestNode immediateChild = new TestNode();

        topmostContainer.Add(immediateChild);

        assertEquals(immediateChild.GetDependency(TestDependency.class).Content, topmostDependency.Content);
    }

    @Test
    public void DependencyContainerIsOverridden()
    {
        final TestDependency topmostDependency = new TestDependency("Topmost");
        final TestContainer topmostContainer = new TestContainer(topmostDependency);

        topmostContainer.Load(DependencyContainer.Empty);

        TestNode immediateChild = new TestNode();

        topmostContainer.Add(immediateChild);

        final TestDependency nestedDependency = new TestDependency("Nested");
        final TestContainer nestedContainer = new TestContainer(nestedDependency);

        topmostContainer.Add(nestedContainer);

        TestNode nestedChild = new TestNode();

        nestedContainer.Add(nestedChild);

        assertEquals(nestedChild.GetDependency(TestDependency.class).Content, nestedDependency.Content);
    }

    @Test
    public void DependenciesArePassedDownDeepNesting()
    {
        final TestDependency dependency = new TestDependency("Topmost");
        final TestContainer firstContainer = new TestContainer(dependency);
        final TestContainer secondContainer = new TestContainer(null);
        final TestContainer thirdContainer = new TestContainer(null);

        final TestNode child = new TestNode();

        firstContainer.Load(DependencyContainer.Empty);

        firstContainer.Add(secondContainer);
        secondContainer.Add(thirdContainer);
        thirdContainer.Add(child);

        assertEquals(child.GetDependency(TestDependency.class).Content, dependency.Content);
    }

    private class TestDependency
    {
        public final String Content;

        public TestDependency(String content)
        {
            Content = content;
        }
    }

    private class TestNode extends LogicNode
    {
        public <T> T GetDependency(Class<T> type) { return GetDependencyContainer().Get(type); }
    }

    private class TestContainer extends CompositeLogicNode
    {
        public final TestDependency DependencyToContribute;

        public TestContainer(TestDependency dependencyToContribute)
        {
            DependencyToContribute = dependencyToContribute;
        }

        @Override
        protected IReadOnlyDependencyContainer CreateChildDependencies(IReadOnlyDependencyContainer parentContainer)
        {
            if (DependencyToContribute == null)
                return parentContainer;

            DependencyContainer childContainer = new DependencyContainer(parentContainer);

            childContainer.Cache(TestDependency.class, DependencyToContribute);

            return childContainer;
        }
    }
}
