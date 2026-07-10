//? if neoforge {
/*package dev.worldgen.wikiful.impl.platform.neoforge;

import dev.worldgen.wikiful.impl.Wikiful;
import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public interface WikifulAttachments {
    AttachmentType<List<Identifier>> UNLOCKED_PAGES = createAttachment("unlocked_pages");
    AttachmentType<List<Identifier>> UNLOCKED_SECTIONS = createAttachment("unlocked_sections");
    AttachmentType<List<Identifier>> UNLOCKED_TIPS = createAttachment("unlocked_tips");

    static AttachmentType<List<Identifier>> createAttachment(String name) {
        var attachment = AttachmentType.<List<Identifier>>builder(() -> List.of()).serialize(UnlockedInfo.CODEC).sync(UnlockedInfo.STREAM_CODEC).copyOnDeath().build();
        Wikiful.REGISTRAR.register(NeoForgeRegistries.ATTACHMENT_TYPES, name, attachment);
        return attachment;
    }

    static void init() {

    }
}
*///? }