package me.krousant.trackerapi.event.executor;

import me.krousant.trackerapi.TrackerAPI;
import me.krousant.trackerapi.TrackerAPIPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public abstract class TrackerAPIEventExecutor implements EventExecutor, Listener
{
    public final TrackerAPI API_INSTANCE;
    private final HandlerList HANDLER_LIST;
    private final RegisteredListener REGISTERED_LISTENER;

    public TrackerAPIEventExecutor(TrackerAPI instance, HandlerList handlerList, EventPriority priority, boolean ignoreCancelled)
    {
        if(instance == null) throw new NullPointerException("TrackerAPI cannot be null.");
        this.API_INSTANCE = instance;
        HANDLER_LIST = handlerList;

        REGISTERED_LISTENER = new RegisteredListener(this, this,
                priority, TrackerAPIPlugin.plugin(), ignoreCancelled);
    }

    public HandlerList handlerList()
    {
        return HANDLER_LIST;
    }

    public RegisteredListener registeredListener()
    {
        return REGISTERED_LISTENER;
    }

    @Override
    public abstract void execute(Listener listener, Event event);
}
