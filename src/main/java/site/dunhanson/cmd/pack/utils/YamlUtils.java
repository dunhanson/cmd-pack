package site.dunhanson.cmd.pack.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dunhanson
 * @date 2020.03.20
 * @description YAML工具类
 */
@Slf4j
public class YamlUtils {
    private static Map<String, Map<String, Object>> basicMap = new HashMap<>();

    /**
     * 加载YAML文件
     * @param path
     * @return
     */
    public static Map<String, Object> loadFile(String path) {
        Map<String, Object> map = basicMap.get(path);
        if(map == null) {
            Yaml yaml = new Yaml();
            String localPath = YamlUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            localPath = localPath.substring(1);
            localPath = localPath.substring(0, localPath.lastIndexOf("/") + 1);
            log.info("localPath : " + localPath);
            File localFile = new File(localPath + path);
            if(localFile.exists()) {
                try(InputStream inputStream = new FileInputStream(localFile)) {
                    basicMap.put(path, yaml.load(inputStream));
                    map = basicMap.get(path);
                } catch (Exception e) {
                    log.error("yaml file load fail " + e.getMessage());
                }
            } else {
                try(InputStream inputStream = YamlUtils.class.getClassLoader().getResourceAsStream(path)) {
                    basicMap.put(path, yaml.load(inputStream));
                    map = basicMap.get(path);
                } catch (Exception e) {
                    log.error("yaml file load fail " + e.getMessage());
                }
            }
        }
        return map;
    }

    /**
     * 获取实体对象
     * @param clazz
     * @param path
     * @param keyArr
     * @param <T>
     * @return
     */
    public static <T> T getEntity(Class<T> clazz, String path, String...keyArr) {
        Map<String, Object> map = getMap(path, keyArr);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }

    /**
     * 获取Map对象
     * @param path
     * @param keyArr
     * @return
     */
    public static Map<String, Object> getMap(String path, String...keyArr) {
        Map<String, Object> map = YamlUtils.loadFile(path);
        for(int i = 0; i < keyArr.length; i++) {
            String key = keyArr[i];
            Object value = map.get(key);
            if(value instanceof Map) {
                map = (Map<String, Object>)value;
            }
        }
        return map;
    }

    /**
     * 获取Object
     * @param path
     * @param keyArr
     * @return
     */
    public static Object getValue(String path, String...keyArr) {
        Object value = null;
        Map<String, Object> map = YamlUtils.loadFile(path);
        for(int i = 0; i < keyArr.length; i++) {
            String key = keyArr[i];
            value = map.get(key);
            if(value instanceof Map) {
                map = (Map<String, Object>)value;
            }
        }
        return value;
    }

    /**
     * 获取String
     * @param path
     * @param keyArr
     * @return
     */
    public static String getValueToString(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (String)value;
    }

    /**
     * 获取List
     * @param path
     * @param keyArr
     * @return
     */
    public static List<String> getValueToList(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (List<String>)value;
    }

    /**
     * 获取Integer
     * @param path
     * @param keyArr
     * @return
     */
    public static Integer getValueToInteger(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (Integer)value;
    }

    /**
     * 获取Double
     * @param path
     * @param keyArr
     * @return
     */
    public static Double getValueToDouble(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (Double)value;
    }

    /**
     * 获取Long
     * @param path
     * @param keyArr
     * @return
     */
    public static Long getValueToLong(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (Long)value;
    }

    /**
     * 获取Boolean
     * @param path
     * @param keyArr
     * @return
     */
    public static Boolean getValueToBoolean(String path, String...keyArr) {
        Object value = getValue(path, keyArr);
        return value == null ? null : (Boolean)value;
    }
}