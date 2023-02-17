package net.biscut.ia2denizen.bridges;

import com.denizenscript.denizencore.objects.ObjectFetcher;
import net.biscut.ia2denizen.Bridge;
import net.biscut.ia2denizen.objects.itemsadder.ItemsAdderTag;

public class ItemsAdderBridge extends Bridge {
    public static ItemsAdderBridge instance;
    @Override
    public void init() {
        instance = this;
        ObjectFetcher.registerWithObjectFetcher(ItemsAdderTag.class, ItemsAdderTag.tagProcessor);

    }
}
