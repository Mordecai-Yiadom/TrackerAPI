package me.krousant.trackerapi.event.action;

import me.krousant.trackerapi.*;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class NullCompassAction implements CompassActionListener
{
    public static final NullCompassAction INSTANCE = new NullCompassAction();
    private NullCompassAction(){}

    @Override
    public void actionPerformed(TrackerAPI apiInstance, Event event) {}
}
