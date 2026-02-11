package dev.worldgen.wikiful.impl;

import dev.worldgen.wikiful.api.client.BodyElementRegistry;
import dev.worldgen.wikiful.impl.client.body.*;
import dev.worldgen.wikiful.impl.wiki.body.*;

public class WikifulClient {
    public static void init() {
        BodyElementRegistry.register(EmptyBody.CODEC, EmptyBodyElement::create);
        BodyElementRegistry.register(TextBody.CODEC, TextBodyElement::create);
        BodyElementRegistry.register(ItemBody.CODEC, ItemBodyElement::create);
        BodyElementRegistry.register(SpriteBody.CODEC, SpriteBodyElement::create);
        BodyElementRegistry.register(HorizontalLineBody.CODEC, HorizontalLineBodyElement::create);
    }
}
