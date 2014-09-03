package org.obsidianbox.ember.physics.util;

public interface TransformProvider {
    public Transform getTransform();

    public static class NullTransformProvider implements TransformProvider {
        public static final NullTransformProvider INSTANCE = new NullTransformProvider();
        @Override
        public Transform getTransform() {
            return Transform.NONE;
        }
    }
}
