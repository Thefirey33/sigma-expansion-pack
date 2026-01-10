package net.thefirey33.sep.client.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DrawSomethingWithTuff extends Screen {
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void init() {
        super.init();
    }

    protected DrawSomethingWithTuff(Text title) {
        super(title);
    }
}
