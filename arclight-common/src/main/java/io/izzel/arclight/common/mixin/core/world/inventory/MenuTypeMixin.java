package io.izzel.arclight.common.mixin.core.world.inventory;

import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MenuType.class)
public abstract class MenuTypeMixin<T extends AbstractContainerMenu> implements FeatureElement {

    @Shadow
    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> constructor, FeatureFlag... flags) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/world/inventory/MenuType$MenuSupplier;)Lnet/minecraft/world/inventory/MenuType;", cancellable = true, at = @At("HEAD"))
    private static <T extends AbstractContainerMenu> void arclight$replaceLectern(String name, MenuType.MenuSupplier<T> constructor, CallbackInfoReturnable<MenuType<T>> cir) {
        if (name.equals("lectern")) {
            cir.setReturnValue((MenuType<T>) register("lectern", (containerId, inventory) ->  {
                LecternMenu container = new LecternMenu(containerId);
                container.bridge$setPlayerInventory(inventory);
                return container;
            }));
        }
    }
}
