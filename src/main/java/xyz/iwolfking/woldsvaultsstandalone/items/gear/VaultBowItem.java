//package xyz.iwolfking.woldsvaultsstandalone.items.gear;
//
//import com.google.common.collect.Multimap;
//import iskallia.vault.dynamodel.DynamicModel;
//import iskallia.vault.gear.VaultGearClassification;
//import iskallia.vault.gear.VaultGearHelper;
//import iskallia.vault.gear.VaultGearRarity;
//import iskallia.vault.gear.VaultGearState;
//import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
//import iskallia.vault.gear.crafting.ProficiencyType;
//import iskallia.vault.gear.data.VaultGearData;
//import iskallia.vault.gear.item.VaultGearItem;
//import iskallia.vault.gear.tooltip.GearTooltip;
//import iskallia.vault.init.ModConfigs;
//import iskallia.vault.init.ModGearAttributes;
//import iskallia.vault.util.MiscUtils;
//import net.minecraft.core.NonNullList;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.stats.Stats;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attribute;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.event.ForgeEventFactory;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//public class VaultBowItem extends BowItem implements VaultGearItem, DyeableLeatherItem {
//    public VaultBowItem(ResourceLocation id, Properties builder) {
//        /*  49 */     super(builder);
//        /*  50 */     setRegistryName(id);
//        /*     */   }
//
//    @Override
//    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
//        if (p_40669_ instanceof Player player) {
//            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
//            ItemStack itemstack = player.getProjectile(p_40667_);
//            int i = this.getUseDuration(p_40667_) - p_40670_;
//            i = ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);
//            if (i < 0) {
//                return;
//            }
//
//            if (!itemstack.isEmpty() || flag) {
//                if (itemstack.isEmpty()) {
//                    itemstack = new ItemStack(Items.ARROW);
//                }
//
//                float f = getPowerForTime(i);
//                if (!((double)f < 0.1)) {
//                    boolean flag1 = player.getAbilities().instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player);
//                    if (!p_40668_.isClientSide) {
//                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
//                        AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, itemstack, player);
//                        abstractarrow = this.customArrow(abstractarrow);
//                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
//                        if (f == 1.0F) {
//                            abstractarrow.setCritArrow(true);
//                        }
//
//                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
//                        if (j > 0) {
//                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5 + 0.5);
//                        }
//
//                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
//                        if (k > 0) {
//                            abstractarrow.setKnockback(k);
//                        }
//
//                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
//                            abstractarrow.setSecondsOnFire(100);
//                        }
//
//                        p_40667_.hurtAndBreak(1, player, (p_40665_) -> {
//                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
//                        });
//                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
//                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
//                        }
//
//                        p_40668_.addFreshEntity(abstractarrow);
//                    }
//
//                    p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//                    if (!flag1 && !player.getAbilities().instabuild) {
//                        itemstack.shrink(1);
//                        if (itemstack.isEmpty()) {
//                            player.getInventory().removeItem(itemstack);
//                        }
//                    }
//
//                    player.awardStat(Stats.ITEM_USED.get(this));
//                }
//            }
//        }
//    }
//
//    @Override
//    public int getUseDuration(ItemStack p_40680_) {
//        return super.getUseDuration(p_40680_);
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
//        return VaultGearHelper.rightClick(p_40672_, p_40673_, p_40674_, super.use(p_40672_, p_40673_, p_40674_));
//    }
//
//    @Override
//    public int getDefaultProjectileRange() {
//        return super.getDefaultProjectileRange();
//    }
//
//    @NotNull
//    @Override
//    public VaultGearClassification getClassification(ItemStack itemStack) {
//        return VaultGearClassification.AXE;
//    }
//
//    @NotNull
//    @Override
//    public ProficiencyType getCraftingProficiencyType(ItemStack itemStack) {
//        return ProficiencyType.AXE;
//    }
//
//    @Nullable
//    @Override
//    public EquipmentSlot getIntendedSlot(ItemStack itemStack) {
//        return EquipmentSlot.MAINHAND;
//    }
//
//    @Override
//    public Optional<? extends DynamicModel<?>> resolveDynamicModel(ItemStack stack, ResourceLocation key) {
//        /*  66 */     return Bows.REGISTRY.get(key);
//        /*     */   }
//
//    @Override
//    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
//        /* 148 */     super.inventoryTick(stack, world, entity, itemSlot, isSelected);
//        /* 149 */     if (entity instanceof ServerPlayer) { ServerPlayer player = (ServerPlayer)entity;
//            /* 150 */       vaultGearTick(stack, player); }
//        /*     */
//        /*     */   }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    /*     */   public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
//        /* 157 */     super.appendHoverText(stack, world, tooltip, flag);
//        /* 158 */     tooltip.addAll(createTooltip(stack, GearTooltip.itemTooltip()));
//        /*     */   }
//
//    @Nullable
//    @Override
//    public ResourceLocation getRandomModel(ItemStack itemStack, Random random) {
//        VaultGearData gearData = VaultGearData.read(itemStack);
//        /*  57 */     VaultGearRarity rarity = gearData.getRarity();
//        /*  58 */     EquipmentSlot intendedSlot = getIntendedSlot(itemStack);
//        /*  59 */     ResourceLocation possibleIds = ModConfigs.GEAR_MODEL_ROLL_RARITIES.getRandomRoll(this.defaultItem(), gearData, intendedSlot, random);
//        /*     */
//        /*  61 */     return (ResourceLocation) MiscUtils.getRandomEntry(possibleIds, random);
//    }
//    @Override
//   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
//        /* 101 */     return VaultGearHelper.getModifiers(stack, slot);
//        /*     */   }
//    /*     */
//    /*     */
//                @Override
//    /*     */   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
//        /* 106 */     return VaultGearHelper.shouldPlayGearReequipAnimation(oldStack, newStack, slotChanged);
//        /*     */   }
//    /*     */
//    /*     */
//                @Override
//    /*     */   public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
//        /* 111 */     if (allowdedIn(group)) {
//            /* 112 */       items.add(defaultItem());
//            /*     */     }
//        /*     */   }
//    /*     */
//    /*     */
//
//                @Override
//    /*     */   public int getDefaultTooltipHideFlags(@NotNull ItemStack stack) {
//        /* 118 */     return super.getDefaultTooltipHideFlags(stack) | ItemStack.TooltipPart.MODIFIERS.getMask();
//        /*     */   }
//    /*     */
//    /*     */
//
//                @Override
//    /*     */   public boolean isRepairable(ItemStack stack) {
//        /* 123 */     return false;
//        /*     */   }
//    /*     */
//    /*     */
//                @Override
//    /*     */   public boolean isDamageable(ItemStack stack) {
//        /* 128 */     return (VaultGearData.read(stack).getState() == VaultGearState.IDENTIFIED);
//        /*     */   }
//    /*     */
//    /*     */   @Override
//    /*     */   public int getMaxDamage(ItemStack stack) {
//        /* 133 */     return ((Integer)VaultGearData.read(stack).get(ModGearAttributes.DURABILITY, VaultGearAttributeTypeMerger.intSum())).intValue();
//        /*     */   }
//    /*     */
//
//    /*     */
//                @Override
//    /*     */   public Component getName(ItemStack stack) {
//        /* 138 */     return VaultGearHelper.getDisplayName(stack, super.getName(stack));
//        /*     */   }
//}
