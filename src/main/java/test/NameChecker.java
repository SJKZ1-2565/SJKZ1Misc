package test;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NameChecker {

    public static void main(String[] arg)
    {
        try {
            Desktop.getDesktop().open(MinecraftClient.getInstance().runDirectory);
        } catch (IOException e) {
            e.printStackTrace();

        }
//            for(SpecialMember values : SpecialMember.VALUES)
//            {
//                try {
//
//
//                    if(!getName(values.getUuid()).equals(values.getName()))
//                    {
//                        System.out.println("Found not matched, Replace " + values.getName() + " with " + getName(values.getUuid()) +" instead");
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
    }

    private static String getName(String uuid) throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names");
        JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
        String name = array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
        return name;
    }


}
