package me.server.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageRendererUtils implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int rowIndex, int columnIndex) {

        if (value instanceof Image) {
            JLabel jLabel = new JLabel();
            jLabel.setLayout(new BorderLayout());
            jLabel.setIcon(new ImageIcon((Image) value));
            return jLabel;
        } else if (value instanceof File) {
            try {
                return new JLabel(new ImageIcon(ImageIO.read((File) value)));
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        } else {
            String val = String.valueOf(value);
            try {
                return new JLabel(new ImageIcon(ImageIO.read(new File(val))));
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }
}