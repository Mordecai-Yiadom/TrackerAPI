package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackingDataChangeListener;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackerAPIManager implements Serializable
{
    private final List<TrackerAPI> API_INSTANCES;

    protected TrackerAPIManager()
    {
        API_INSTANCES = new ArrayList<>();
    }

    public TrackerAPI createInstance(Plugin plugin, TrackerAPISettings settings, TrackingDataChangeListener listener)
    {
        TrackerAPI apiInstance = new TrackerAPI(plugin, settings, null);
        API_INSTANCES.add(apiInstance);
        return apiInstance;
    }

    public boolean destroyInstance(TrackerAPI apiInstance)
    {
        apiInstance.destroy();
        return API_INSTANCES.remove(apiInstance);
    }
}
