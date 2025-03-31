package me.krousant.trackerapi.event.action;

import me.krousant.trackerapi.*;
import me.krousant.trackerapi.event.listener.CompassActionListener;
import org.bukkit.event.Event;

public class NullAction implements CompassActionListener
{
    public static final NullAction GET = new NullAction();
    private NullAction(){}
    @Override
    public void actionPerformed(TrackerAPI apiInstance, Event event) {}
}
