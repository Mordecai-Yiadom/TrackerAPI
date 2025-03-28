package me.krousant.trackerapi;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class TrackerAPIPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        sendConsoleMessage(ConsoleMessageType.SUCCESS, "TrackerAPI has been enabled.");
    }

    @Override
    public void onDisable()
    {
        sendConsoleMessage(ConsoleMessageType.NEUTRAL, "TrackerAPI has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return true;
    }

    //Convenience Methods(s) for Plugin
    public static void sendConsoleMessage(ConsoleMessageType messageType, String message)
    {
        if(message == null) return;
        Bukkit.getConsoleSender()
                .sendMessage(String.format("%s[TrackerAPI] (%s): %s", messageType.color, messageType.prefix, message));
    }

    public enum ConsoleMessageType
    {
        ERROR(ChatColor.RED, "Error"),
        SUCCESS(ChatColor.GREEN, "Success"),
        NEUTRAL(ChatColor.GRAY, ""),
        WARNING(ChatColor.YELLOW, "Warning");

        private ChatColor color;
        private String prefix;

        ConsoleMessageType(ChatColor color, String prefix)
        {
            this.color = color;
            this.prefix = prefix;
        }
    }

    protected static void saveInstance()
    {

    }
}
