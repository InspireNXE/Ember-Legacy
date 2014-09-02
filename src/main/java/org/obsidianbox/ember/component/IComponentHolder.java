package org.obsidianbox.ember.component;

import org.obsidianbox.ember.IGameObject;

import java.util.Collection;
import java.util.Optional;

public interface IComponentHolder<T extends Component> extends IGameObject {
    default Optional<T> add(Class<? extends Component> clazz) {
        final Optional<T> existing = getComponents().stream().filter(en -> en.getClass().equals(clazz)).findAny();
        if (existing.isPresent()) {
            return existing;
        }

        try {
            T instanced = (T) clazz.newInstance();
            getComponents().add(instanced);
            instanced.attach(this);
            return Optional.of(instanced);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    default Optional<T> get(Class<? extends Component> clazz) {
        return getComponents().stream().filter(en -> en.getClass().equals(clazz)).findFirst();
    }

    default Optional<T> remove(Class<? extends Component> clazz) {
        Optional<T> found = getComponents().stream().filter(en -> en.getClass().equals(clazz)).findAny();
        if (found.isPresent()) {
            getComponents().remove(found.get());
            found.get().detach();
        }
        return found;
    }

    public Collection<T> getComponents();
}
