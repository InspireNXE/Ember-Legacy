package org.obsidianbox.ember;

import org.junit.Test;
import org.obsidianbox.ember.world.storage.TypedIdRegistry;

import static org.junit.Assert.assertTrue;

public class RegistryTest {
    private final TypedIdRegistry<TestA> regA = new TypedIdRegistry<>(TestA.class);

    @Test
    public void testRegistry() {
        assertTrue(regA.get(new TestA()) == TypedIdRegistry.ID_NOT_FOUND);
        final TestA testA = new TestA();
        regA.put(testA);
        assertTrue(regA.get((short) 0).equals(testA));
    }
}

final class TestA {

}
