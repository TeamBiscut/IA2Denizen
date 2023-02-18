package net.biscut.ia2denizen;

import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.denizenscript.denizencore.utilities.debugging.DebugSubmitter;
import net.biscut.ia2denizen.bridges.ItemsAdderBridge;
import net.biscut.ia2denizen.utils.BridgeLoadException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class IA2Denizen extends JavaPlugin {

    public static IA2Denizen instance;
    public HashMap<String, Supplier<Bridge>> allBridges = new HashMap<>();
    public HashMap<String, Bridge> loadedBridges = new HashMap<>();

    @Override
    public void onEnable() {
        Debug.log("IA2Denizen loading...");
        saveDefaultConfig();
        instance = this;
        registerCoreBridges();
        for (Map.Entry<String, Supplier<Bridge>> bridge : allBridges.entrySet()) {
            loadBridge(bridge.getKey(), bridge.getValue());
        }
        DebugSubmitter.debugHeaderLines.add(() -> "IA2Denizen Bridges loaded (" + loadedBridges.size() + "): " + ChatColor.DARK_GREEN + String.join(", ", loadedBridges.keySet()));
        Debug.log("IA2Denizen loaded! <A>" + loadedBridges.size() + "<W> plugin bridge(s) loaded (of <A>" + allBridges.size() + "<W> available)");
    }

    @Override
    public void onDisable() {
        // :)
    }

    public void loadBridge(String name, Supplier<Bridge> bridgeSupplier) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
        if (plugin == null) {
            return;
        }
        Bridge newBridge;
        try {
            newBridge = bridgeSupplier.get();
        }
        catch (Throwable ex) {
            Debug.echoError("Cannot load IA2Denizen bridge for '" + name + "': fundamental loading error:");
            Debug.echoError(ex);
            return;
        }
        newBridge.name = name;
        newBridge.plugin = plugin;
        try {
            newBridge.init();
        }
        catch (BridgeLoadException ex) {
            Debug.echoError("Cannot load IA2Denizen bridge for '" + name + "': " + ex.getMessage());
            return;
        }
        catch (Throwable ex) {
            Debug.echoError("Cannot load IA2Denizen bridge for '" + name + "': Internal exception was thrown!");
            Debug.echoError(ex);
            return;
        }
        loadedBridges.put(name, newBridge);
        Debug.log("Loaded bridge for '" + name + "'!");
    }
    public void registerCoreBridges() {
        registerBridge("ItemsAdder", ItemsAdderBridge::new);
    }

    public void registerBridge(String name, Supplier<Bridge> bridgeSupplier) {
        allBridges.put(name, bridgeSupplier);
    }
}
