package com.communi.suggestu.scena.forge.mixin.platform.client;

import com.communi.suggestu.scena.core.client.models.data.IBlockModelData;
import com.communi.suggestu.scena.core.entity.block.IBlockEntityWithModelData;
import com.communi.suggestu.scena.forge.platform.client.model.data.ForgeBlockModelDataPlatformDelegate;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelDataManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ModelDataManager.class, remap = false)
public abstract class ModelDataManagerMixin
{
    @Redirect(
      method = "refreshAt",
      at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/world/level/block/entity/BlockEntity;getModelData()Lnet/minecraftforge/client/model/data/ModelData;"
      )
    )
    private ModelData getModelDataRetrieval(final BlockEntity blockEntity) {
        if (blockEntity instanceof IBlockEntityWithModelData blockEntityWithModelData) {
            final IBlockModelData blockModelData = blockEntityWithModelData.getBlockModelData();
            if (!(blockModelData instanceof ForgeBlockModelDataPlatformDelegate platformDelegate)) {
                throw new IllegalStateException("Block model data is not compatible with the current platform.");
            }

            return platformDelegate.getDelegate();
        }

        return blockEntity.getModelData();
    }
}
