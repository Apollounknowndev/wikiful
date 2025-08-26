package dev.worldgen.wikiful.impl.wiki.body;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record ItemBody(List<ItemStack> item, boolean showDecorations, boolean showSlot, boolean centered) implements Body {
    public static final MapCodec<ItemBody> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ExtraCodecs.nonEmptyList(ExtraCodecs.compactListCodec(ItemStack.STRICT_CODEC)).fieldOf("item").forGetter(ItemBody::item),
        Codec.BOOL.optionalFieldOf("show_decorations", true).forGetter(ItemBody::showDecorations),
        Codec.BOOL.optionalFieldOf("show_slot", true).forGetter(ItemBody::showSlot),
        Codec.BOOL.fieldOf("centered").orElse(true).forGetter(ItemBody::centered)
    ).apply(instance, ItemBody::new));

    @Override
    public MapCodec<? extends Body> codec() {
        return CODEC;
    }
}
