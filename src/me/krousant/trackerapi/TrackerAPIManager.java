package me.krousant.trackerapi;

import java.io.Serializable;
import java.util.LinkedList;

public class TrackerAPIManager implements Serializable
{
    private final LinkedList<TrackerAPI> API_INSTANCES;

    protected TrackerAPIManager()
    {
        API_INSTANCES = new LinkedList<>();
    }

    public TrackerAPI createInstance(TrackerAPISettings settings)
    {
        if(settings == null) throw new NullPointerException("TrackerAPISettings cannot be null.");
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
