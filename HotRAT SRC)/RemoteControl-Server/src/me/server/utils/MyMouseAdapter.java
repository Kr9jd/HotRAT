package me.server.utils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter {
    JPopupMenu popupMenu;
    public MyMouseAdapter(JPopupMenu popupMenu)
    {
        this.popupMenu=popupMenu;
    }
    public void mousePressed(MouseEvent e)
    {
        showPopupMenu(e);
    }
    public void mouseReleased(MouseEvent e)
    {
        showPopupMenu(e);
    }
    private void showPopupMenu(MouseEvent e)
    {
        if(e.isPopupTrigger())
        {
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
        }
    }
}
