package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.Target;
import me.krousant.trackerapi.Tracker;

public interface TrackerAPIChangeListener
{
    void trackerAdded(Tracker tracker);
    void trackerRemoved(Tracker tracker);

    void targetAdded(Target target);
    void targetRemoved(Target target);

    void instanceDestroyed();
}
