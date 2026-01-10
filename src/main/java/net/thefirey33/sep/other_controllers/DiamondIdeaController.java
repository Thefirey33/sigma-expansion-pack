/**
 * IDEA By: friedchickeneater4000
 * sigma block should give 3000 (30, cause 3000 is too much) diamonds every time you say sigma in chat.
 */


package net.thefirey33.sep.other_controllers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.registries.ModBlocks;

public class DiamondIdeaController implements ControllerInterface{
    public static final String MESSAGE_SHOULD_CONTAIN = "sigma";
    public static final Integer DIAMOND_MAIN_RATE_LIMIT = 5000; // ticks
    public static final Integer AMOUNT_OF_DIAMONDS_TO_GIVE = 30;

    public static final Identifier CHAT_MESSAGE_READER_IDENTIFIER = Identifier.of(Sep.SEP_MOD_ID, "chat-listener");
    public static final Identifier TICK_IDENTIFIER = Identifier.of(Sep.SEP_MOD_ID, "rate-limit-ticker");
    public static Integer DIAMOND_RATE_LIMIT = 0;

    @Override
    public void RegisterModController() {
        ServerMessageEvents.CHAT_MESSAGE.register(CHAT_MESSAGE_READER_IDENTIFIER, (signedMessage, serverPlayerEntity, parameters) -> {
            ItemStack currentStack = serverPlayerEntity.getHandItems().iterator().next();
            String messageContext = signedMessage.getContent().getString();
            if (DIAMOND_RATE_LIMIT <= 0) {
                if (currentStack.getItem() == ModBlocks.SIGMA_BLOCK.asItem() && messageContext.contains(MESSAGE_SHOULD_CONTAIN)) {
                    // Give too many diamonds.
                    serverPlayerEntity.giveItemStack(new ItemStack(Items.DIAMOND, AMOUNT_OF_DIAMONDS_TO_GIVE));
                    DIAMOND_RATE_LIMIT = DIAMOND_MAIN_RATE_LIMIT;
                }
            }
            else {
                // Give warning if in rate-limit.
                serverPlayerEntity.sendMessage(
                        Text.translatable("msg.sep.rate_limit")
                                .setStyle(Style.EMPTY.withBold(true)
                                        .withColor(Formatting.RED)
                                )
                );
                Sep.LOGGER.info("Diamond Rate Limit: {}/{}", DIAMOND_MAIN_RATE_LIMIT, DIAMOND_RATE_LIMIT);
            }
        });
        ServerTickEvents.START_SERVER_TICK.register(TICK_IDENTIFIER, minecraftServer -> DIAMOND_RATE_LIMIT = Math.max(0, DIAMOND_RATE_LIMIT - 1));
    }
}
