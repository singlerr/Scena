package com.communi.suggestu.scena.fabric.platform.creativetab;

import com.communi.suggestu.scena.core.creativetab.ICreativeTabManager;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public final class FabricCreativeTabManager implements ICreativeTabManager {
    private static final FabricCreativeTabManager INSTANCE = new FabricCreativeTabManager();

    private FabricCreativeTabManager() {
    }

    public static FabricCreativeTabManager getInstance() {
        return INSTANCE;
    }

    @Override
    public Supplier<CreativeModeTab> register(@NotNull Consumer<CreativeModeTab.Builder> configurator, @NotNull ResourceLocation name, @NotNull List<Object> afters, @NotNull List<Object> befores) {
        final CreativeModeTab.Builder builder = FabricItemGroup.builder(name);
        configurator.accept(builder);
        final CreativeModeTab tab = builder.build();

        return () -> tab;
    }

    @Override
    public void modifyTab(Supplier<CreativeModeTab> tabSupplier, DisplayItemsAdapter adapterConsumer) {
        ItemGroupEvents.modifyEntriesEvent(tabSupplier.get()).register(entries -> adapterConsumer.accept(entries.getEnabledFeatures(), new Adapter(entries), entries.shouldShowOpRestrictedItems()));
    }

    public static final class Adapter implements ICreativeTabManager.CreativeModeTabPopulator {
        private final FabricItemGroupEntries delegate;

        public Adapter(FabricItemGroupEntries delegate) {
            this.delegate = delegate;
        }

        @Override
        public void prepend(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
            delegate.prepend(stack, visibility);
        }

        @Override
        public void addAfter(ItemStack stack, CreativeModeTab.TabVisibility visibility, ItemStack after) {
            delegate.addAfter(after, List.of(stack), visibility);
        }

        @Override
        public void addBefore(ItemStack stack, CreativeModeTab.TabVisibility visibility, ItemStack before) {
            delegate.addBefore(before, List.of(stack), visibility);
        }
    }
}
