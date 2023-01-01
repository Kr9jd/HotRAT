package me.client.filecopy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JarUtil {
    private String jarName;
    private String jarPath;

    public JarUtil(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File jarFile = new File(path);
        this.jarName = jarFile.getName();

        File parent = jarFile.getParentFile();
        if (parent != null) {
            this.jarPath = parent.getAbsolutePath();
        }
    }

    public String getJarName() {
        try {
            return URLDecoder.decode(this.jarName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public String getJarPath() {
        try {
            return URLDecoder.decode(this.jarPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
