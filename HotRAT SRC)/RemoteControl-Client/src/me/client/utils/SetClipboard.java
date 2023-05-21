package me.client.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.net.Socket;

public class SetClipboard{
    Socket socket;
    public SetClipboard(Socket socket) {
        this.socket = socket;
    }
    public void setClipboard(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, null);
    }

    public void getClipboard() throws IOException, UnsupportedFlavorException {
        String str = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        if(transferable!=null) {
            if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                str = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        SendMessage.send(MessageFlags.GET_CLIPBORAD,str.getBytes("GBK"),socket);
    }
}