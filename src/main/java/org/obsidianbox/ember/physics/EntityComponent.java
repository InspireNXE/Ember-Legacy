package org.obsidianbox.ember.physics;

import org.obsidianbox.ember.component.Component;
import org.obsidianbox.ember.event.ComponentEvent;

import java.util.Optional;

public abstract class EntityComponent extends Component<Entity> {
    @Override
    protected final void attach(Entity holder) {
        FIELD_HOLDER.setAccessible(true);
        try {
            FIELD_HOLDER.set(this, Optional.of(holder));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FIELD_HOLDER.setAccessible(false);
        holder.game.getEventManager().callEvent(new ComponentEvent.Attached(holder.game, this));
    }

    @Override
    protected final void detach() {
        final Entity holder = getHolder().get();
        getHolder().get().game.getEventManager().callEvent(new ComponentEvent.Detach(holder.game, this));

        FIELD_HOLDER.setAccessible(true);
        try {
            FIELD_HOLDER.set(this, Optional.empty());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        FIELD_HOLDER.setAccessible(false);
    }
}
