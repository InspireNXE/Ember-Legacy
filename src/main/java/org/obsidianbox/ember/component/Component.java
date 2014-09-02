package org.obsidianbox.ember.component;

import java.util.Optional;

public interface Component<T extends IComponentHolder> {
    public Optional<T> getHolder();

    public void attach(T holder);

    public void detach();
}
