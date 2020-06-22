package site.dunhanson.cmd.pack.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import site.dunhanson.cmd.pack.entity.Git;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * git工具类
 * @author dunhanson
 * @date 2020.04.30
 */
@Slf4j
public class GitUtils {

    /**
     * 获取日志清单
     * @return
     */
    public static Set<String> listLogPath(String source, Git git) {
        //git命令
        String gitCommand = GitUtils.getCommand(git);
        //cmd命令
        List<String> commandBuilder = new ArrayList();
        //盘符
        String drive = source.substring(0, source.indexOf(":") + 1);
        //cmd
        commandBuilder.add("cmd.exe /c cd /d " + drive);
        commandBuilder.add("cd " + source);
        commandBuilder.add(gitCommand);
        String command = StringUtils.join(commandBuilder, " & ");
        //执行cmd
        String result = CmdUtils.execGetResult(command, "UTF-8");
        //结果处理
        Set<String> set = new HashSet<>();
        if(StringUtils.isNotBlank(result)) {
            Collections.addAll(set, result.split("\n"));
        }
        if(!set.isEmpty()) {
            set = set.stream().filter(
                    str->str.startsWith("src/main/java") || str.startsWith("src/main/resources") || str.startsWith("src/main/webapp/")
            ).collect(Collectors.toSet());
        }
        return set;
    }

    /**
     * 获取命令
     * @return
     */
    public static String getCommand(Git git) {
        //日期格式化模板
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //git
        String author = git.getAuthor();
        String after = git.getAfter();
        String before = git.getBefore();

        //command
        List<String> commandBuilder = new ArrayList();
        commandBuilder.add("git log");
        commandBuilder.add("--name-only");
        commandBuilder.add("--pretty=oneline");
        commandBuilder.add("--format=\"\"");
        if(StringUtils.isNoneBlank(author)) {
            commandBuilder.add("--author=" + author);
        }
        //after
        if(StringUtils.isBlank(after)) {
            after = formatter.format(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        }
        commandBuilder.add("--after=\"" + after + "\"");
        //before
        if(StringUtils.isBlank(before)) {
            before = formatter.format(LocalDateTime.now());
        }
        commandBuilder.add("--before=\"" + before + "\"");
        String command = StringUtils.join(commandBuilder, " ");
        log.info("git command : " + command);
        return command;
    }
}
