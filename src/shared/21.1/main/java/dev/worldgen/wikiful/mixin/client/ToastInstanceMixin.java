package dev.worldgen.wikiful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.worldgen.wikiful.impl.duck.TipToastDuck;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToastComponent.ToastInstance.class)
public abstract class ToastInstanceMixin<T extends Toast> {
    @Shadow @Final ToastComponent this$0;
    @Shadow @Final private T toast;

    @WrapOperation(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lorg/joml/Matrix3x2fStack;translate(FF)Lorg/joml/Matrix3x2f;"
        )
    )
    private Matrix3x2f shiftNormalToasts(Matrix3x2fStack matrix, float x, float y, Operation<Matrix3x2f> operation) {
        return operation.call(matrix, x, y + ((TipToastDuck)this$0).getTipYOffset(toast));
    }
}
