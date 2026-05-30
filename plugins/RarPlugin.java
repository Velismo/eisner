package com.velismo.eisner.plugins;
import android.util.Base64;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
@CapacitorPlugin(name = "RarPlugin")
public class RarPlugin extends Plugin {
    private static final List<String> IMG = Arrays.asList("jpg","jpeg","png","webp","gif","bmp");
    @PluginMethod
    public void extractImages(PluginCall call) {
        String path = call.getString("path");
        if (path == null) { call.reject("No path"); return; }
        try {
            Archive archive = new Archive(new File(path));
            List<FileHeader> all = archive.getFileHeaders();
            List<FileHeader> imgs = new ArrayList<>();
            for (FileHeader h : all) {
                if (!h.isDirectory()) {
                    String n = h.getFileName().toLowerCase();
                    String ext = n.contains(".") ? n.substring(n.lastIndexOf(".")+1) : "";
                    if (IMG.contains(ext)) imgs.add(h);
                }
            }
            imgs.sort((a,b) -> {
                String na = a.getFileName().replaceAll("[^0-9]","");
                String nb = b.getFileName().replaceAll("[^0-9]","");
                if (!na.isEmpty() && !nb.isEmpty()) {
                    try { return Integer.parseInt(na)-Integer.parseInt(nb); } catch(Exception e){}
                }
                return a.getFileName().compareTo(b.getFileName());
            });
            JSArray pages = new JSArray();
            for (FileHeader h : imgs) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                archive.extractFile(h, baos);
                String b64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                String nm = h.getFileName();
                String ext = nm.contains(".") ? nm.substring(nm.lastIndexOf(".")+1).toLowerCase() : "jpg";
                String mime = ext.equals("png") ? "image/png" : ext.equals("webp") ? "image/webp" : "image/jpeg";
                JSObject pg = new JSObject();
                pg.put("name", nm); pg.put("data", b64); pg.put("mime", mime);
                pages.put(pg);
            }
            archive.close();
            JSObject res = new JSObject();
            res.put("pages", pages); res.put("count", imgs.size());
            call.resolve(res);
        } catch (Exception e) { call.reject("RAR failed: " + e.getMessage()); }
    }
}
