package net.biscut.ia2denizen;

import org.bukkit.plugin.Plugin;

public abstract class Bridge {

    public String name;

    public Plugin plugin;

    public abstract void init();
}