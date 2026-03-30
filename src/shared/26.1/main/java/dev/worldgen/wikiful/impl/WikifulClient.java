package dev.worldgen.wikiful.impl;

import com.mojang.blaze3d.platform.InputConstants;
import dev.worldgen.wikiful.api.client.BodyElementRegistry;
import dev.worldgen.wikiful.impl.client.body.*;
import dev.worldgen.wikiful.impl.wiki.body.*;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class WikifulClient {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Wikiful.id("wikiful"));
    public static final KeyMapping DISMISS_TIP = new KeyMapping("key.wikiful.dismiss_tip", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, CATEGORY);
    
    public static void init() {
        BodyElementRegistry.register(EmptyBody.CODEC, EmptyBodyElement::create);
        BodyElementRegistry.register(TextBody.CODEC, TextBodyElement::create);
        BodyElementRegistry.register(ItemBody.CODEC, ItemBodyElement::create);
        BodyElementRegistry.register(SpriteBody.CODEC, SpriteBodyElement::create);
        BodyElementRegistry.register(HorizontalLineBody.CODEC, HorizontalLineBodyElement::create);
    }
}
