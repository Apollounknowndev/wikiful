package dev.worldgen.wikiful.impl;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = Wikiful.MOD_ID, dist = Dist.CLIENT)
public class WikifulClientEntrypoint {
    public WikifulClientEntrypoint(IEventBus bus) {
        WikifulClient.init();
    }
}
