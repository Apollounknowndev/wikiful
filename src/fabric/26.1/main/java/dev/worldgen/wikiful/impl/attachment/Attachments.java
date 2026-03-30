package dev.worldgen.wikiful.impl.attachment;

import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

import java.util.List;

public interface Attachments {
    AttachmentType<List<Identifier>> UNLOCKED_PAGES = createAttachment("unlocked_pages");
    AttachmentType<List<Identifier>> UNLOCKED_SECTIONS = createAttachment("unlocked_sections");
    AttachmentType<List<Identifier>> UNLOCKED_TIPS = createAttachment("unlocked_tips");

    static AttachmentType<List<Identifier>> createAttachment(String name) {
        return AttachmentRegistry.create(Wikiful.id(name), builder -> builder
            .persistent(UnlockedInfo.CODEC.codec())
            .initializer(List::of)
            .syncWith(UnlockedInfo.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
        );
    }

    static void init() {

    }
}
