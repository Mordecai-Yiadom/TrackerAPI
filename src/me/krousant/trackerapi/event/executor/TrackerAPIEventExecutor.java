package me.krousant.trackerapi.event.executor;

import me.krousant.trackerapi.TrackerAPI;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public abstract class TrackerAPIEventExecutor implements EventExecutor
{
    public final TrackerAPI API_INSTANCE;

    public TrackerAPIEventExecutor(TrackerAPI instance)
    {
        if(instance == null) throw new NullPointerException("TrackerAPI cannot be null.");
        this.API_INSTANCE = instance;
    }

    @Override
    public abstract void execute(Listener listener, Event event);
}
