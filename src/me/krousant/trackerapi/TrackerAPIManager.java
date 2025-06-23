package me.krousant.trackerapi;

import javax.sound.midi.Track;
import java.io.Serializable;
import java.util.LinkedList;

public class TrackerAPIManager implements Serializable
{
    private static final LinkedList<TrackerAPI> API_INSTANCES = new LinkedList<>();;
    private static final int DEFAULT_INSTANCE_ID_LENGTH = 10;
    public static TrackerAPI createInstance(TrackerAPISettings settings, TrackerAPICompassManager compassManager)
    {
        if(settings == null) throw new NullPointerException("TrackerAPISettings cannot be null.");
        TrackerAPI apiInstance = new TrackerAPI(settings, compassManager);
        API_INSTANCES.add(apiInstance);
        return apiInstance;
    }

    public static boolean destroyInstance(TrackerAPI apiInstance)
    {
        if(apiInstance == null) return false;
        apiInstance.destroy();
        return API_INSTANCES.remove(apiInstance);
    }

    public synchronized static void clearInstances()
    {
        for(TrackerAPI instance : API_INSTANCES)
            if(instance != null) instance.destroy();

        API_INSTANCES.clear();
    }

    public static int instanceCount()
    {
        return API_INSTANCES.size();
    }

    protected static String generateInstanceID(int idLength)
    {
        if(idLength < 1) return null;

        StringBuilder id = new StringBuilder();

        for(int i = 0; i < idLength; ++i)
            id.append((char) (257 * Math.random()));

        return id.toString();
    }

    protected static String generateInstanceID()
    {
        StringBuilder id = new StringBuilder();
        for(int i = 0; i < DEFAULT_INSTANCE_ID_LENGTH; ++i)
        {
            id.append((char) (257 * Math.random()));
        }

        return id.toString();
    }

    public static TrackerAPI getInstance(String id)
    {
        if(id == null) return null;

        for(TrackerAPI instance : API_INSTANCES)
            if(instance.id().equals(id)) return instance;

        return null;
    }
}
