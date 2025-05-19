package me.krousant.trackerapi;

public interface TrackerChangeListener
{
    void targetChanged(Target oldTarget, Target newTarget);
}
