package org.obsidianbox.ember.gui.appender;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.obsidianbox.ember.gui.Frontend;

import java.io.Serializable;

public class JavaFXAppender extends AbstractAppender {
    private final Frontend frontend;

    public JavaFXAppender(Frontend frontend, Filter filter, Layout<? extends Serializable> layout) {
        super("GUI", filter, layout);
        this.frontend = frontend;
    }

    @Override
    public void append(LogEvent event) {
        //TODO Grinch, send the event to the GUI control
    }
}
