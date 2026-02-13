package dev.worldgen.wikiful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(ToastComponent.ToastInstance.class)
public abstract class ToastInstanceMixin<T extends Toast> {
    @Shadow @Final ToastComponent this$0;
    @Shadow @Final private T toast;

    @WrapOperation(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
        )
    )
    private void shiftNormalToasts(PoseStack stack, float x, float y, float z, Operation<Void> operation) {
        operation.call(stack, x, y + ((TipToastDuck)this$0).getTipYOffset(toast), z);
    }
}
