package org.obsidianbox.ember;

public interface Scheduler {
    int scheduleWithDelay(Runnable callback, long delay);
}
