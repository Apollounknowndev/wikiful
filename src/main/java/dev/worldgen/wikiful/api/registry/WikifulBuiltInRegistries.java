package dev.worldgen.wikiful.api.registry;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.api.event.EventTriggerType;
import dev.worldgen.wikiful.impl.wiki.body.Body;
import dev.worldgen.wikiful.api.wiki.page.WikiPage;
import net.minecraft.core.Registry;

import net.minecraft.resources.ResourceKey;

//? if fabric {
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
//? } else {
/*import dev.worldgen.wikiful.impl.Wikiful;
import net.neoforged.neoforge.registries.DeferredRegister;
import dev.worldgen.wikiful.impl.platform.neoforge.WikifulNeoforge;
*///? }

public interface WikifulBuiltInRegistries {
    Registry<EventTriggerType<?>> EVENT_TRIGGER_TYPE = createRegistry(WikifulRegistries.EVENT_TRIGGER_TYPE);
    Registry<MapCodec<? extends Body>> BODY_TYPE = createRegistry(WikifulRegistries.BODY_TYPE);
    Registry<MapCodec<? extends WikiPage>> WIKI_PAGE_TYPE = createRegistry(WikifulRegistries.WIKI_PAGE_TYPE);
    
    
    static <T> Registry<T> createRegistry(ResourceKey<Registry<T>> key) {
        //? if fabric {
        return FabricRegistryBuilder.create(key).buildAndRegister();
        //? } else {
        /*var registry = DeferredRegister.create(key, Wikiful.MOD_ID).makeRegistry(b -> {});
        WikifulNeoforge.REGISTRIES.add(registry);
        return registry;
        *///? }
    }
    
    static void init() {
    }
}
