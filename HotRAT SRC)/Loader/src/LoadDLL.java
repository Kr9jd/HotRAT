import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;

public interface LoadDLL extends Library {
    LoadDLL instance = (LoadDLL) Native.loadLibrary("resources/CppUtils.dll", LoadDLL.class);
    boolean RunAsAdmin(WString path, WString command);
}