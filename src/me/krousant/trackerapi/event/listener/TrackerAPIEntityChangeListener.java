package me.krousant.trackerapi.event.listener;

public interface TrackerAPIEntityChangeListener
{
    void entityChanged(Object oldValue, Object newValue);
    void entityChanged(Object... changes);
}
