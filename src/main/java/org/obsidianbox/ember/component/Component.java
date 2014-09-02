package org.obsidianbox.ember.component;

import java.lang.reflect.Field;
import java.util.Optional;

public abstract class Component<T extends IComponentHolder> {
    public static final Field FIELD_HOLDER;

    static {
        try {
            FIELD_HOLDER = Component.class.getDeclaredField("holder");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<T> holder = Optional.empty();

    public Optional<T> getHolder() {
        return holder;
    }

    protected abstract void attach(T holder);

    protected abstract void detach();
}
