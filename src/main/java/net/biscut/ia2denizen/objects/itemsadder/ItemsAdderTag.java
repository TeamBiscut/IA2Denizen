package net.biscut.ia2denizen.objects.itemsadder;

import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderTag implements ObjectTag {
    public static ItemsAdderTag valueOf(String item) {
        return ItemsAdderTag.valueOf(item, null);
    }

    @Fetchable("ia")
    public static ItemsAdderTag valueOf(String item, TagContext ctx) {
        if (item == null || item.length() == 0) {
            return null;
        }

        item = item.replace("ia@", "").replace("!", ":");
        CustomStack customStack = CustomStack.getInstance(item);

        if (customStack == null) {
            return null;
        }

        return new ItemsAdderTag(customStack);
    }

    public static boolean matches(String arg) {
        arg = arg.replace("ia@", "");
        return ItemsAdderTag.valueOf(arg) != null;
    }

    public static ObjectTagProcessor<ItemsAdderTag> tagProcessor = new ObjectTagProcessor<>();
    private CustomStack customItemStack;
    private String prefix = "ia";

    public ItemsAdderTag(CustomStack customStack) {
        if (customStack != null) {
            this.customItemStack = customStack;
        } else {
            Debug.echoError("[IA2Denizen] item referenced is invalid!");
        }
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String identify() {
        return "ia@" + customItemStack.getId();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public ObjectTag setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public String toString() {
        return identify();
    }

    public CustomStack getCustomItemStack() {
        return customItemStack;
    }

    public ItemStack getItemStack() {
        return customItemStack.getItemStack();
    }

    public static void register() {
        tagProcessor.registerTag(ItemTag.class, "item_stack", ((attribute, object) -> {
            return new ItemTag(object.getItemStack());
        }));
    }


    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {
        return tagProcessor.getObjectAttribute(this, attribute);

    }
}
