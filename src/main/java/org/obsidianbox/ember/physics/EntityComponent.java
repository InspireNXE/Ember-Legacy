package org.obsidianbox.ember.physics;

import org.obsidianbox.ember.component.Component;
import org.obsidianbox.ember.event.ComponentEvent;

import java.util.Optional;

public class EntityComponent implements Component<Entity> {
    private Optional<Entity> holder = Optional.empty();

    @Override
    public final Optional<Entity> getHolder() {
        return holder;
    }

    @Override
    public final void attach(Entity holder) {
        this.holder = Optional.of(holder);
        holder.game.getEventManager().registerEvents(this, this);
        holder.game.getEventManager().callEvent(new ComponentEvent.Attached(holder.game, this));
    }

    @Override
    public final void detach() {
        holder.get().game.getEventManager().callEvent(new ComponentEvent.Detach(holder.get().game, this));
        holder.get().game.getEventManager().unRegisterEventsByListener(this);
    }
}
