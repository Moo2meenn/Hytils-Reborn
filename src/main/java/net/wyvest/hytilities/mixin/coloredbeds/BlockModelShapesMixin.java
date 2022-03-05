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
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.util.BlockPos;
import net.wyvest.hytilities.hooks.ColoredBedsManager;
import net.wyvest.hytilities.hooks.ExtendedBlockModelShapes;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(BlockModelShapes.class)
public abstract class BlockModelShapesMixin implements ExtendedBlockModelShapes {
    @Shadow
    public abstract IBakedModel getModelForState(IBlockState state);

    @Shadow
    @Final
    private ModelManager modelManager;

    @Shadow
    @Final
    private Map<IBlockState, IBakedModel> bakedModelStore;

    public IBakedModel getModelFromPos(IBlockState state, BlockPos pos) {
        if (pos != null) {
            if (pos.getX() == 1 && pos.getZ() == 1 && pos.getY() == 100) {
                System.out.println(bakedModelStore.get(state).getClass().getName());
                IBakedModel model = bakedModelStore.get(state);
                model.getGeneralQuads().replaceAll((a) -> new ColoredBedsManager.TintedBakedQuad(a.getVertexData(), a.getFace()));
                System.out.println(model.getGeneralQuads());
                GL11.glLineStipple();
                return model;
            }
        }
        return getModelForState(state);
    }
}
