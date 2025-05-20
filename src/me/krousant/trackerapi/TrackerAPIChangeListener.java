package me.krousant.trackerapi;

public interface TrackerAPIChangeListener
{
    void trackerAdded(Tracker tracker);
    void trackerRemoved(Tracker tracker);

    void targetAdded(Target target);
    void targetRemoved(Target target);

    void instanceDestroyed();
}
