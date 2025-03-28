package me.krousant.trackerapi;

import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackerAPIManager implements Serializable
{
    private List<TrackerAPI> API_INSTANCES;

    public TrackerAPIManager()
    {
        API_INSTANCES = new ArrayList<>();
    }

    public TrackerAPI createInstance(Plugin plugin, TrackerAPISettings settings)
    {
        TrackerAPI apiInstance = new TrackerAPI(plugin, settings);
        API_INSTANCES.add(apiInstance);
        return apiInstance;
    }

    public boolean destroyInstance(TrackerAPI apiInstance) {return API_INSTANCES.remove(apiInstance);}
}
