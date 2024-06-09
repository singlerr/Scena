package com.communi.suggestu.scena.fabric.mixin.platform.common;

import com.communi.suggestu.scena.core.entity.block.BlockEntityChunkStorage;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements BlockEntityChunkStorage {

    @Unique
    private ChunkAccess chunk;

    @Override
    public ChunkAccess getOwnerChunk() {
        return chunk;
    }

    @Override
    public void setOwnerChunk(ChunkAccess access) {
        this.chunk = access;
    }

}
