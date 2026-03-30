package dev.worldgen.wikiful.api.client;

import com.mojang.serialization.MapCodec;
import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.wiki.body.Body;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BodyElementRegistry {
    private static final Map<MapCodec<? extends Body>, Function<Body, BodyElement>> BODY_ELEMENTS = new HashMap<>();

    public static <T extends Body> void register(MapCodec<T> body, Function<Body, BodyElement> element) {
        if (BODY_ELEMENTS.containsKey(body)) {
            Wikiful.LOGGER.error("Tried to register duplicate body element!");
        } else {
            BODY_ELEMENTS.put(body, element);
        }
    }

    public static BodyElement createElement(Body body) {
        return BODY_ELEMENTS.get(body.codec()).apply(body);
    }
}
