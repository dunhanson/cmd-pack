package site.dunhanson.cmd.pack.utils;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import site.dunhanson.cmd.pack.entity.Basic;
import site.dunhanson.cmd.pack.entity.Git;
import site.dunhanson.cmd.pack.entity.Project;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 打包工具类
 * @author dunhanson
 * @date 2020.04.30
 */
@Slf4j
public class PackUtils {

    /**
     * 打包操作
     */
    public static void doing() {
        Basic basic = YamlUtils.getEntity(Basic.class, "cmd-pack.yaml", "basic");
        List<Project> projects = basic.getProjects();
        projects.forEach(project->{
            if(project.getStart()) {
                log.info("project->" + project.getName());
                project(project, basic.getGit());
            }
        });
        log.info("package finish...");
    }

    /**
     * project
     * @param project
     * @param git
     */
    public static void project(Project project, Git git) {
        //项目路径
        String source = project.getSource();
        if(StringUtils.isBlank(source)) {
            log.error("project->source is null");
            return;
        }
        //项目名称
        String name = project.getName();
        if(StringUtils.isBlank(name)) {
            log.error("project->name is null");
            return;
        }
        //输出路径
        String output = project.getOutput();
        if(StringUtils.isBlank(output)) {
            log.error("project->output is null");
            return;
        }
        //清空&删除
        FileUtils.deleteQuietly(new File(output + "/" + project));
        //git文件路径
        Set<String> set = GitUtils.listLogPath(source, git);
        set = getRealPath(set, source, name);
        if(!set.isEmpty()) {
            set.forEach(realPath->{
                //输出路径
                String exportPath = getExportPath(realPath, output);
                //复制文件
                copy(realPath, exportPath);
            });
            //压缩目录
            try {
                String path = output + "/" + name;
                new ZipFile(path + ".zip").addFolder(new File(path));
            } catch (ZipException e) {
                log.error("zip error " + e.getMessage());
            }
        }
    }

    /**
     * 获取类似的class
     * @param file
     * @return
     */
    public static List<String> getLikeClassFiles(File file) {
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
        List<String> paths = new ArrayList<>();
        File[] files = file.getParentFile().listFiles();
        for(File get : files) {
            if(get.getName().startsWith(fileName + "$")) {
                paths.add(get.getAbsolutePath().replaceAll("\\\\", "/"));
            }
        }
        return paths;
    }


    /**
     * 复制文件
     * @param realPath
     * @param exportPath
     */
    public static void copy(String realPath, String exportPath) {
        try {
            File realFile = new File(realPath);
            if(realFile.exists()) {
                FileUtils.copyFile(realFile, new File(exportPath), true);
            } else {
                log.warn("file not found " + realPath);
            }
        } catch (IOException e) {
            log.error("copy fial " + e.getMessage());
        }
    }

    /**
     *
     * @param realPath
     * @param output
     * @return
     */
    public static String getExportPath(String realPath, String output) {
        if(StringUtils.isBlank(realPath) || StringUtils.isBlank(output)) {
            return "";
        }
        return output.replaceAll("\\\\", "/") + realPath.substring(realPath.lastIndexOf("/target/") + 7);
    }

    /**
     * 获取真实的路径
     * @param set
     * @param source
     * @param name
     * @return
     */
    public static Set<String> getRealPath(Set<String> set, String source, String name) {
        Optional<Set<String>> optional = Optional.ofNullable(set);
        Set<String> newSet = new HashSet<>();
        if(!set.isEmpty()) {
            for(String str : set) {
                String path = PackUtils.getRealPath(source, name, str);
                if(StringUtils.isNoneBlank(path)) {
                    newSet.add(path);
                    if(path.endsWith(".class")) {
                        newSet.addAll(getLikeClassFiles(new File(path)));
                    }
                }
            }
        }
        return newSet;
    }

    /**
     * 获取真实的路径
     * @param source
     * @param name
     * @param gitFilePath
     * @return
     */
    public static String getRealPath(String source, String name, String gitFilePath) {
        source = source.replaceAll("\\\\", "/");
        String commonFilePath = source + "/target/" + name;
        String classAndResourceFilePath = commonFilePath + "/WEB-INF/classes/";
        String realPath = "";
        if(gitFilePath.endsWith(".java")) {
            //java文件
            realPath = classAndResourceFilePath + getClassPath(gitFilePath);
        } else if (gitFilePath.startsWith("src/main/resources")) {
            //配置文件
            realPath = classAndResourceFilePath + getResourcePath(gitFilePath);
        } else {
            //普通文件
            realPath = commonFilePath + "/" + getCommonPath(gitFilePath);
        }
        return realPath;
    }

    /**
     * 获取class路径
     * @param classPath
     * @return
     */
    public static String getClassPath(String classPath) {
        return classPath.replaceAll("\\.java", ".class")
                .replaceAll("src\\/main\\/java\\/", "");
    }

    /**
     * 获取resource路径
     * @param classPath
     * @return
     */
    public static String getResourcePath(String classPath) {
        return classPath.replaceAll("src\\/main\\/resources\\/", "");
    }

    /**
     * 普通文件
     * @param classPath
     * @return
     */
    public static String getCommonPath(String classPath) {
        return classPath.replaceAll("src\\/main\\/webapp\\/", "");
    }
}
