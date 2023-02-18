package net.biscut.ia2denizen.objects.itemsadder;

import com.denizenscript.denizen.objects.ItemTag;
import com.denizenscript.denizencore.objects.Adjustable;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.properties.PropertyParser;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderTag implements ObjectTag, Adjustable {
    public static ItemsAdderTag valueOf(String p0) {
        return ItemsAdderTag.valueOf(p0, null);
    }

    @Fetchable("ia")
    public static ItemsAdderTag valueOf(String p0, TagContext p1) {
        Debug.log("Running value of command: " + p0);

        if (p0.startsWith("ia@")) {
            p0 = p0.substring("ia@".length());
            Debug.log("Starts with ia@ -> " + p0);
        }

        CustomStack customStack = CustomStack.getInstance(p0);

        if (customStack == null) {
            Debug.log("Invalid item name " + p0);
            return null;
        }

        Debug.log("Returning item " + customStack.getDisplayName());
        return new ItemsAdderTag(customStack);
    }


    public static boolean matches(String arg) {
        return valueOf(arg) != null;
    }

    /*
        Constructor
     */

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

    /*
        Instance Fields
     */

    public CustomStack getCustomItemStack() {
        return customItemStack;
    }

    public ItemStack getItemStack() {
        return customItemStack.getItemStack();
    }

    /*
        Object Tag
     */

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public ObjectTag setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
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
    public String toString() {
        return identify();
    }

    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {
        return tagProcessor.getObjectAttribute(this, attribute);
    }

    @Override
    public void applyProperty(Mechanism mechanism) {
        mechanism.echoError("Cannot apply properties to a Items Adder Item!");
    }

    public static void register() {
        PropertyParser.registerPropertyTagHandlers(ItemsAdderTag.class, tagProcessor);

        tagProcessor.registerTag(ItemTag.class, "as_item_tag", ((attribute, object) -> new ItemTag(object.getItemStack())));
    }

    @Override
    public void adjust(Mechanism mechanism) {
        tagProcessor.processMechanism(this, mechanism);
        CoreUtilities.autoPropertyMechanism(this, mechanism);
    }
}
