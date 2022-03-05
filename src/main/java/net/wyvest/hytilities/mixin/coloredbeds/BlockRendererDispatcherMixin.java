/*
 * Hytilities Reborn - Hypixel focused Quality of Life mod.
 * Copyright (C) 2022  W-OVERFLOW
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.wyvest.hytilities.mixin.coloredbeds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.wyvest.hytilities.hooks.ExtendedBlockModelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockRendererDispatcher.class)
public class BlockRendererDispatcherMixin {
    @Redirect(method = "renderBlockDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelShapes;getModelForState(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    private IBakedModel redirect1(BlockModelShapes instance, IBlockState state, IBlockState inaccurate, BlockPos pos, TextureAtlasSprite texture) {
        return ((ExtendedBlockModelShapes) instance).getModelFromPos(state, pos);
    }

    @Redirect(method = "getBakedModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelShapes;getModelForState(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    private IBakedModel redirect2(BlockModelShapes instance, IBlockState state, IBlockState inaccurate, BlockPos pos) {
        return ((ExtendedBlockModelShapes) instance).getModelFromPos(state, pos);
    }

    @Redirect(method = "getModelFromBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelShapes;getModelForState(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    private IBakedModel redirect3(BlockModelShapes instance, IBlockState state, IBlockState inaccurate, IBlockAccess worldIn, BlockPos pos) {
        return ((ExtendedBlockModelShapes) instance).getModelFromPos(state, pos);
    }
}
