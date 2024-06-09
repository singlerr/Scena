package com.communi.suggestu.scena.core.entity.block;

import net.minecraft.world.level.chunk.ChunkAccess;

/***
 * Storage for saving {@link ChunkAccess} at chunk loading stage
 */
public interface BlockEntityChunkStorage {

    /***
     * Store {@link ChunkAccess} that owns {@link net.minecraft.world.level.block.entity.BlockEntity}
     * @param access {@link ChunkAccess} to save
     */
    void setOwnerChunk(ChunkAccess access);

    /***
     * Get {@link ChunkAccess} that owns {@link net.minecraft.world.level.block.entity.BlockEntity}
     * @return {@link ChunkAccess} that owns {@link net.minecraft.world.level.block.entity.BlockEntity}
     */
    ChunkAccess getOwnerChunk();
}
