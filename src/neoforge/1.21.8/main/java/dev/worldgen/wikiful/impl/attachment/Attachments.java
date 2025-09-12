package dev.worldgen.wikiful.impl.attachment;

import dev.worldgen.wikiful.impl.WikifulEntrypoint;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;

public interface Attachments {
    AttachmentType<List<ResourceLocation>> UNLOCKED_PAGES = createAttachment("unlocked_pages");
    AttachmentType<List<ResourceLocation>> UNLOCKED_SECTIONS = createAttachment("unlocked_sections");
    AttachmentType<List<ResourceLocation>> UNLOCKED_TIPS = createAttachment("unlocked_tips");

    static AttachmentType<List<ResourceLocation>> createAttachment(String name) {
        var attachment = AttachmentType.<List<ResourceLocation>>builder(() -> List.of()).serialize(UnlockedInfo.CODEC).sync(new UnlockedSyncHandler()).copyOnDeath().build();
        WikifulEntrypoint.ATTACHMENT_TYPES.put(name, attachment);
        return attachment;
    }

    static void init() {

    }
}
