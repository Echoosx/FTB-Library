package dev.ftb.mods.ftbguilibrary.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbguilibrary.icon.Icon;
import dev.ftb.mods.ftbguilibrary.misc.NordColors;
import dev.ftb.mods.ftbguilibrary.utils.TooltipList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public abstract class NordButton extends SimpleTextButton {
	public NordButton(Panel panel, Component txt, Icon icon) {
		super(panel, txt, icon);
		setHeight(16);
	}

	@Override
	public void addMouseOverText(TooltipList list) {
	}

	@Override
	public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
		(isMouseOver() ? NordColors.POLAR_NIGHT_4 : NordColors.POLAR_NIGHT_2).draw(matrixStack, x, y, w, h);
	}

	@Override
	public void draw(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
		this.drawBackground(matrixStack, theme, x, y, w, h);
		int s = h >= 20 ? 16 : 8;
		int off = (h - s) / 2;
		FormattedText title = getTitle();
		int textY = y + (h - theme.getFontHeight() + 1) / 2;
		int sw = theme.getStringWidth(title);
		int mw = w - (hasIcon() ? off + s : 0) - 6;

		if (sw > mw) {
			sw = mw;
			title = theme.trimStringToWidth(title, mw);
		}

		int textX = x + 4;

		if (hasIcon()) {
			drawIcon(matrixStack, theme, x + off, y + off, s, s);
			textX += off + s;
		}

		theme.drawString(matrixStack, title, (float) textX, (float) textY, isMouseOver() ? NordColors.SNOW_STORM_3 : NordColors.SNOW_STORM_1, 0);
	}
}
