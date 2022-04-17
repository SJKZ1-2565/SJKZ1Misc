package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.VillagerDataContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity
        implements InteractionObserver,
        VillagerDataContainer {

    @Shadow
    public abstract Brain<VillagerEntity> getBrain();

    private TargetPredicate targetingConditions = TargetPredicate.createAttackable().setBaseMaxDistance(16).setPredicate(livingEntity -> this.isPlayerStaring((PlayerEntity) livingEntity));


    VillagerEntityMixin() {
        super(null, null);
    }

    @Override
    public boolean damage(DamageSource damageSource, float f) {
        Entity entity = damageSource.getAttacker();
        if (entity instanceof PlayerEntity && Minetils.CONFIG.main.IgnoreHittingVillager) {
            return false;
        }
        return super.damage(damageSource, f);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (this.targetingConditions != null) {
            PlayerEntity player = this.world.getClosestPlayer(this.targetingConditions, this);
            if (player != null) {
                if (isPlayerStaring(player)) {
                    Brain<VillagerEntity> brain = this.getBrain();
                    brain.getOptionalMemory(MemoryModuleType.JOB_SITE).ifPresent(globalPos -> {
                        if (globalPos != null) {
                            if(Minetils.showPost.wasPressed())
                            {
                                SJKZ1Helper.sendChat("This Villager Job Block Position");
                            }
                            for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                                world.addParticle(ParticleTypes.LAVA, (double) globalPos.getPos().getX() + 0.5, (double) globalPos.getPos().getY() + 0.5, (double) globalPos.getPos().getZ() + 0.5, random.nextFloat() / 2.0f, 5.0E-5, random.nextFloat() / 2.0f);
                            }
                        }
                    });
                }
            }
        }
    }

    boolean isPlayerStaring(PlayerEntity playerEntity) {
        Vec3d vec3d = playerEntity.getRotationVec(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - playerEntity.getX(), this.getEyeY() - playerEntity.getEyeY(), this.getZ() - playerEntity.getZ());
        double d = vec3d2.length();
        double e = vec3d.dotProduct(vec3d2.normalize());
        if (e > 1.0 - 0.025 / d) {
            return playerEntity.canSee(this);
        }
        return false;
    }
}