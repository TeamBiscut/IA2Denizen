package net.biscut.ia2denizen.bridges;

import com.denizenscript.denizen.objects.ItemTag;
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
            if (attribute.hasParam()) {
                return ItemsAdderTag.valueOf(attribute.getParam(), attribute.context);
            }
            return null;
        }));

        TagManager.registerTagHandler(ItemTag.class, "ia_item", (attribute -> {
            if (attribute.hasParam()) {
                return new ItemTag(ItemsAdderTag.valueOf(attribute.getParam(), attribute.context).getItemStack());
            }
            return null;
        }));

    }
}
