package com.sjkz1.minetils.mixin;

import java.util.Objects;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.sjkz1.minetils.Minetils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.util.Identifier;

@Mixin(CapeFeatureRenderer.class)
public class CapeLayerMixin {


	@Redirect(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/render/RenderLayer;getEntitySolid(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    public RenderLayer render(Identifier identifier)
    {
        RenderLayer renderLayer = null;
        if(Minetils.CONFIG.getConfig().SpecialCape)
        {
            for(String name : Minetils.SPECIAL_MEMBER)
            {
                if(Objects.requireNonNull(MinecraftClient.getInstance().player).getName().getString().contains(name))
                {
                    renderLayer = RenderLayer.getEntityAlpha(identifier);
                }
                else {
                    renderLayer = RenderLayer.getEntitySolid(identifier);
                }
                break;
            }
        }
        else
        {
            renderLayer = RenderLayer.getEntitySolid(identifier);
        }
        return renderLayer;
    }
}
