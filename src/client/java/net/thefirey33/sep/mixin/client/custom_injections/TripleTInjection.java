/**
 * IDEA By: Cuddles
 * Every tool item is triple t baby.
 */


package net.thefirey33.sep.mixin.client.custom_injections;

import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteDimensions;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.thefirey33.sep.Sep;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(SpriteLoader.class)
public class TripleTInjection {
    @Unique
    private static List<Identifier> ALL_TOOLS;

    @Unique
    private static final String TEXTURE_DATA_PATH = "texture_override/%s.png";
    /**
     * @author Thefirey33
     * @reason Overwrite the loader, so i can mess with it and turn it into tung tung tung sahur.
     */
    @Overwrite
    public static @Nullable SpriteContents load(Identifier id, Resource resource){

        AnimationResourceMetadata animationResourceMetadata;
        try {
            animationResourceMetadata = resource.getMetadata().decode(AnimationResourceMetadata.READER).orElse(AnimationResourceMetadata.EMPTY);
        } catch (Exception var8) {
            Sep.LOGGER.error("Unable to parse metadata from {}", (Object)id);
            return null;
        }

        NativeImage nativeImage;
        try {

            InputStream inputStream;
            if (id.getPath().contains("wooden")) {
                // Override the item loader.
                String pathInformation = TEXTURE_DATA_PATH.formatted(id.getPath());
                inputStream = Sep.class.getClassLoader().getResourceAsStream(pathInformation);
                Sep.LOGGER.info("Importing sprite {} as TRIPLE T BABEH.", id);
            }
            else
                inputStream = resource.getInputStream();


            try {
                assert inputStream != null;
                nativeImage = NativeImage.read(inputStream);
            } catch (Throwable error) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var7) {
                        error.addSuppressed(var7);
                    }
                }

                throw error;
            }

            inputStream.close();
        } catch (IOException var10) {
            Sep.LOGGER.error("Using missing texture, unable to load {}", (Object)id);
            return null;
        }

        SpriteDimensions spriteDimensions = animationResourceMetadata.getSize(nativeImage.getWidth(), nativeImage.getHeight());
        if (MathHelper.isMultipleOf(nativeImage.getWidth(), spriteDimensions.width()) && MathHelper.isMultipleOf(nativeImage.getHeight(), spriteDimensions.height())) {
            return new SpriteContents(id, spriteDimensions, nativeImage, animationResourceMetadata);
        } else {
            Sep.LOGGER.error("Image {} size {},{} is not multiple of frame size {},{}", id, nativeImage.getWidth(), nativeImage.getHeight(), spriteDimensions.width(), spriteDimensions.height());
            nativeImage.close();
            return null;
        }
    }

}
