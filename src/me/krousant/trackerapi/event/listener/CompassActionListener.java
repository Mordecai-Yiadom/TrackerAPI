package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.TrackerAPI;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

import java.io.Serializable;

public interface CompassActionListener extends Serializable
{
    void actionPerformed(TrackerAPI apiInstance, Event event);
}
