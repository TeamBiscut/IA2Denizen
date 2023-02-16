package net.biscut.ia2denizen.bridges;

import com.denizenscript.denizencore.objects.ObjectFetcher;
import com.denizenscript.denizencore.tags.TagManager;
import net.biscut.ia2denizen.Bridge;
import net.biscut.ia2denizen.objects.itemsadder.ItemsAdderTag;

public class ItemsAdderBridge extends Bridge {
    public static ItemsAdderBridge instance;
    @Override
    public void init() {
        instance = this;
        ObjectFetcher.registerWithObjectFetcher(ItemsAdderTag.class, ItemsAdderTag.tagProcessor);

        TagManager.registerTagHandler(ItemsAdderTag.class, "ia", (attribute -> {
            if (!attribute.hasParam()) {
                attribute.echoError("[IA2Denizen] tag base must have input.");
                return null;
            }

            return ItemsAdderTag.valueOf(attribute.getParam(), attribute.context);
        }));
    }
}
