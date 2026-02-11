package dev.worldgen.wikiful.impl.attachment;

import dev.worldgen.wikiful.impl.event.UnlockedInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnlockedSyncHandler implements AttachmentSyncHandler<List<Identifier>> {
    @Override
    public void write(RegistryFriendlyByteBuf buf, List<Identifier> entries, boolean initalSync) {
        UnlockedInfo.STREAM_CODEC.encode(buf, entries);
    }

    @Override
    public @Nullable List<Identifier> read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable List<Identifier> previousValue) {
        return UnlockedInfo.STREAM_CODEC.decode(buf);
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}
