package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.TrackerAPI;
import org.bukkit.event.Event;

public interface CompassActionListener
{
    void actionPerformed(TrackerAPI apiInstance, Event event);
}
