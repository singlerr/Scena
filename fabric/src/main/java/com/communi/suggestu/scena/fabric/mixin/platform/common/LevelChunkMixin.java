package com.communi.suggestu.scena.fabric.mixin.platform.common;

import com.communi.suggestu.scena.core.entity.block.BlockEntityChunkStorage;
import com.communi.suggestu.scena.fabric.platform.entity.IFabricBlockEntityPositionHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ProtoChunk;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;)V",
            at = @At("RETURN")
    )
    private void onUpgradeChunk(ServerLevel serverLevel, ProtoChunk protoChunk, LevelChunk.PostLoadProcessor postLoadProcessor, CallbackInfo ci) {
        final IFabricBlockEntityPositionHolder protoHolder = (IFabricBlockEntityPositionHolder) protoChunk;
        final IFabricBlockEntityPositionHolder holder = (IFabricBlockEntityPositionHolder) this;

        holder.scena$getBlockEntityPositions().clear();
        holder.scena$getBlockEntityPositions().putAll(protoHolder.scena$getBlockEntityPositions());
    }

    @Inject(method = "setBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;setLevel(Lnet/minecraft/world/level/Level;)V", shift = At.Shift.BEFORE))
    private void onSetBlockEntity(BlockEntity blockEntity, CallbackInfo ci){
        BlockEntityChunkStorage chunkStorage = (BlockEntityChunkStorage) blockEntity;
        chunkStorage.setOwnerChunk((ChunkAccess) (Object) this);
    }

}
