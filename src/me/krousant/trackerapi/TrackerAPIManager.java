package me.krousant.trackerapi;

import java.io.Serializable;
import java.util.LinkedList;

public class TrackerAPIManager implements Serializable
{
    private static final LinkedList<TrackerAPI> API_INSTANCES = new LinkedList<>();;

    public static TrackerAPI createInstance(TrackerAPISettings settings, TrackerAPICompassManager compassManager)
    {
        if(settings == null) throw new NullPointerException("TrackerAPISettings cannot be null.");
        TrackerAPI apiInstance = new TrackerAPI(settings, compassManager);
        API_INSTANCES.add(apiInstance);
        return apiInstance;
    }

    public static boolean destroyInstance(TrackerAPI apiInstance)
    {
        apiInstance.destroy();
        return API_INSTANCES.remove(apiInstance);
    }

    public synchronized static void clearInstances()
    {
        for(TrackerAPI instance : API_INSTANCES)
            instance.destroy();

        API_INSTANCES.clear();
    }

    public static int instanceCount()
    {
        return API_INSTANCES.size();
    }
}
