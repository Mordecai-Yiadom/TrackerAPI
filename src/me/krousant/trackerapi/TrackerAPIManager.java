package me.krousant.trackerapi;

import me.krousant.trackerapi.event.listener.TrackingDataChangeListener;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrackerAPIManager implements Serializable
{
    private final LinkedList<TrackerAPI> API_INSTANCES;

    protected TrackerAPIManager()
    {
        API_INSTANCES = new LinkedList<>();
    }

    public TrackerAPI createInstance(TrackerAPISettings settings, TrackingDataChangeListener listener)
    {
        TrackerAPI apiInstance = new TrackerAPI(settings);
        API_INSTANCES.add(apiInstance);
        return apiInstance;
    }

    public boolean destroyInstance(TrackerAPI apiInstance)
    {
        apiInstance.destroy();
        return API_INSTANCES.remove(apiInstance);
    }
}
