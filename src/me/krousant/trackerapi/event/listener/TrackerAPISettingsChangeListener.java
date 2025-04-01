package me.krousant.trackerapi.event.listener;

import me.krousant.trackerapi.TrackerAPISettings;

import java.io.Serializable;

public interface TrackerAPISettingsChangeListener extends Serializable
{
    void settingChanged(TrackerAPISettings.Option option, boolean oldValue, boolean newValue);
}
