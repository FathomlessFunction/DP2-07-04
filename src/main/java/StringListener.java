/**
 * This is a very simple interface to show how interfaces are used though out
 * Swing in order to prevent programs from becoming a mess of classes passing
 * pointers to each other.
 */

public interface StringListener {
    public void textEmitted(String text);
}
