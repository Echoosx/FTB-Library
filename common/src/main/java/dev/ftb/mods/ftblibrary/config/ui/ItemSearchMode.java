package dev.ftb.mods.ftblibrary.config.ui;

import com.google.common.base.MoreObjects;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ItemSearchMode {

	/**
	 * The icon used to represent this mode, for example on buttons and other widgets.
	 */
	Icon getIcon();

	/**
	 * The name used to describe this mode.
	 */
	MutableComponent getDisplayName();

	/**
	 * Gets an *unfiltered* collection of all items available in the current search mode.
	 */
	Collection<ItemStack> getAllItems();

	ItemSearchMode ALL_ITEMS = new ItemSearchMode() {
		private List<ItemStack> allItemsCache = null;

		@Override
		public Icon getIcon() {
			return ItemIcon.getItemIcon(Items.COMPASS);
		}

		@Override
		public MutableComponent getDisplayName() {
			return Component.translatable("ftblibrary.select_item.list_mode.all");
		}

		@Override
		public Collection<ItemStack> getAllItems() {
			if (allItemsCache == null) {
				allItemsCache = CreativeModeTabs.allTabs().stream()
						.flatMap(tab -> tab.getDisplayItems().stream())
						.toList();

//				List<ItemStack> stacks = new ArrayList<>(BuiltInRegistries.ITEM.keySet().size() + 100);
//				for (var item : BuiltInRegistries.ITEM) {
//					NonNullList<ItemStack> list = NonNullList.create();
//					var category = item.getItemCategory();
//					item.fillItemCategory(MoreObjects.firstNonNull(category, CreativeModeTab.TAB_SEARCH), list);
//					if (list.isEmpty()) {
//						stacks.add(item.getDefaultInstance());
//						continue;
//					}
//					stacks.addAll(list);
//				}
//				allItemsCache = stacks;
			}
			return allItemsCache;
		}
	};

	ItemSearchMode INVENTORY = new ItemSearchMode() {
		@Override
		public Icon getIcon() {
			return ItemIcon.getItemIcon(Items.CHEST);
		}

		@Override
		public MutableComponent getDisplayName() {
			return Component.translatable("ftblibrary.select_item.list_mode.inv");
		}

		@Override
		public Collection<ItemStack> getAllItems() {
			Player player = Minecraft.getInstance().player;

			if (player == null) {
				return Collections.emptySet();
			}

			var inv = player.getInventory().getContainerSize();
			List<ItemStack> items = new ArrayList<>(inv);
			for (var i = 0; i < inv; i++) {
				var stack = Minecraft.getInstance().player.getInventory().getItem(i);

				if (!stack.isEmpty()) {
					items.add(stack);
				}
			}
			return items;
		}
	};

}
