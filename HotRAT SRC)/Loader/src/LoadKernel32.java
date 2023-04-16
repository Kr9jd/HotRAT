import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;

public interface LoadKernel32 extends Library {
    LoadKernel32 instance = (LoadKernel32) Native.loadLibrary("Kernel32.dll", LoadKernel32.class);
    int GetWindowsDirectoryW(char[] chars,int size);
}