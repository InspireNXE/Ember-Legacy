package org.obsidianbox.ember;

import com.flowpowered.events.EventHandler;
import org.obsidianbox.ember.event.ComponentEvent;
import org.obsidianbox.ember.event.GameEvent;
import org.obsidianbox.ember.physics.EntityComponent;

/**
 * TODO Test this when I have time
 */
public class ComponentTest {

    class TestEntityComponent extends EntityComponent {
        @EventHandler
        public void onAttached(ComponentEvent.Attached event) {

        }

        @EventHandler
        public void onTick(GameEvent.Tick event) {
            
        }

        @EventHandler
        public void onDetach(ComponentEvent.Detach event) {

        }
    }
}
