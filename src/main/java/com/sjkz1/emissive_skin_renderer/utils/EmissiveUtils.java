package com.sjkz1.emissive_skin_renderer.utils;

import boon4681.ColorUtils.DeltaE;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class EmissiveUtils {

    public static ResourceLocation getBlockResourceLocation(Block block) {
        return new ResourceLocation(Registry.BLOCK.getKey(block).getNamespace(), "textures/block/" + Registry.BLOCK.getKey(block).getPath() + ".png");
    }

    public static ResourceLocation getItemResourceLocation(Item item) {
        return new ResourceLocation(Registry.ITEM.getKey(item).getNamespace(), "textures/item/" + Registry.ITEM.getKey(item).getPath() + ".png");
    }

    public static HashMap<Block, Integer> BLOCK_PALLETS_LESS_THAN = Util.make(Maps.newHashMap(), blockIntegerHashMap -> {
        blockIntegerHashMap.put(Blocks.DIAMOND_ORE, 85);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_DIAMOND_ORE, 75);
        blockIntegerHashMap.put(Blocks.REDSTONE_ORE, 80);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_REDSTONE_ORE, 80);
        blockIntegerHashMap.put(Blocks.LAPIS_ORE, 78);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_LAPIS_ORE, 78);
        blockIntegerHashMap.put(Blocks.EMERALD_ORE, 81);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_EMERALD_ORE, 81);
        blockIntegerHashMap.put(Blocks.GOLD_ORE, 81);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_GOLD_ORE, 81);
        blockIntegerHashMap.put(Blocks.COPPER_ORE, 81);
        blockIntegerHashMap.put(Blocks.DEEPSLATE_COPPER_ORE, 79);
    });

    public static HashMap<Block, Integer> BLOCK_PALLETS_GREATER_THAN = Util.make(Maps.newHashMap(), blockIntegerHashMap -> {
        blockIntegerHashMap.put(Blocks.COAL_ORE, 65);
    });

    public static void getImageFromBlock(Block block) {
        Resource resource;
        try {
            resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(EmissiveUtils.getBlockResourceLocation(block));
            try (InputStream inputStream = resource.open();) {
                NativeImage nativeImage = NativeImage.read(inputStream);
                nativeImage.writeToFile("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.BLOCK.getKey(block).getPath() + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getImageFromItem(Item item) {
        Resource resource;
        try {
            resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(EmissiveUtils.getItemResourceLocation(item));
            try (InputStream inputStream = resource.open();) {
                NativeImage nativeImage = NativeImage.read(inputStream);
                nativeImage.writeToFile("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.ITEM.getKey(item).getPath() + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getColorWithLessThan() {
        BLOCK_PALLETS_LESS_THAN.keySet().forEach(block1 -> {
            System.out.println(Registry.BLOCK.getKey(block1).getPath());
            try {
                BufferedImage image = ImageIO.read(new File("C:/Users/" + System.getProperty("user.name") + "/Desktop/" + Registry.BLOCK.getKey(block1).getPath() + ".png"));
                BufferedImage resizedImage = ColorMatching.resize(image, 4, 4);
                ArrayList<Color> colors = new ArrayList<>();
                for (int y = 0; y < resizedImage.getHeight(); y++) {
                    for (int x = 0; x < resizedImage.getWidth(); x++) {
                        colors.add(new Color(resizedImage.getRGB(x, y), false));
                    }
                }
                ArrayList<Color> pallets = ColorMatching.find(colors);

                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) <= BLOCK_PALLETS_LESS_THAN.get(block1)) {
                            image.setRGB(x, y, Transparency.TRANSLUCENT);
                        }
                    }
                }
                ImageIO.write(image, "png", new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.BLOCK.getKey(block1).getPath() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void getColorWithGreaterThan() {
        BLOCK_PALLETS_GREATER_THAN.keySet().forEach(block1 -> {
            try {
                BufferedImage image = ImageIO.read(new File("C:/Users/" + System.getProperty("user.name") + "/Desktop/" + Registry.BLOCK.getKey(block1).getPath() + ".png"));
                BufferedImage resizedImage = ColorMatching.resize(image, 4, 4);
                ArrayList<Color> colors = new ArrayList<>();
                for (int y = 0; y < resizedImage.getHeight(); y++) {
                    for (int x = 0; x < resizedImage.getWidth(); x++) {
                        colors.add(new Color(resizedImage.getRGB(x, y), false));
                    }
                }
                ArrayList<Color> pallets = ColorMatching.find(colors);
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        System.out.println(DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)));
                        if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) >= BLOCK_PALLETS_GREATER_THAN.get(block1)) {
                            image.setRGB(x, y, Transparency.TRANSLUCENT);
                        }
                    }
                }
                ImageIO.write(image, "png", new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.BLOCK.getKey(block1).getPath() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
