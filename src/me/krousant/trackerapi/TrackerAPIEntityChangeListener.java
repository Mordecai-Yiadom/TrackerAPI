package me.krousant.trackerapi;

public interface TrackerAPIEntityChangeListener
{
    void entityChanged(Object oldValue, Object newValue);
    void entityChanged(Object... changes);
}
