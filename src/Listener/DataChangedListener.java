package Listener;

/**
 * Interface for the Observer design pattern.
 * Windows will implement this to be notified when data in the Group changes.
 */
public interface DataChangedListener {
    void onDataChanged();
}