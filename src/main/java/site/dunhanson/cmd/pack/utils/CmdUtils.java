package site.dunhanson.cmd.pack.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * cmd工具类
 * @author dunhanson
 * @date 2020.04.30
 */
@Slf4j
public class CmdUtils {

    /**
     * 执行命令
     * @param command
     * @return
     */
    public static String execGetResult(String command, String encoding) {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            try (InputStream input = process.getInputStream()) {
                result = IOUtils.toString(input, encoding);
            }
        } catch (IOException e) {
            log.error("cmd exec fail " + e.getMessage());
        }
        return result;
    }

    /**
     * 执行命令
     * @param command
     * @return
     */
    public static  String execGetResult(String command) {
        String newCommand = "cmd.exe /c " + command;
        return execGetResult(newCommand, "GBK");
    }
}
