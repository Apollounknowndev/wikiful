package dev.worldgen.wikiful.impl.attachment;

import dev.worldgen.wikiful.impl.WikifulEntrypoint;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;

public interface Attachments {
    AttachmentType<List<Identifier>> UNLOCKED_PAGES = createAttachment("unlocked_pages");
    AttachmentType<List<Identifier>> UNLOCKED_SECTIONS = createAttachment("unlocked_sections");
    AttachmentType<List<Identifier>> UNLOCKED_TIPS = createAttachment("unlocked_tips");

    static AttachmentType<List<Identifier>> createAttachment(String name) {
        var attachment = AttachmentType.<List<Identifier>>builder(() -> List.of()).serialize(UnlockedInfo.CODEC.codec()).sync(new UnlockedSyncHandler()).copyOnDeath().build();
        WikifulEntrypoint.ATTACHMENT_TYPES.put(name, attachment);
        return attachment;
    }

    static void init() {

    }
}
