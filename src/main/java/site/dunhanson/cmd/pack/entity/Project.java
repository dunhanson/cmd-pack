package site.dunhanson.cmd.pack.entity;

import lombok.Data;

/**
 * 项目配置信息
 * @author dunhanson
 * @date 2020.04.30
 */
@Data
public class Project {
    private String name;
    private Boolean start;
    private String source;
    private String output;
}
