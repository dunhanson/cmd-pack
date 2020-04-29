package site.dunhanson.cmd.pack.entity;

import lombok.Data;

/**
 * git配置信息
 * @author dunhanson
 * @date 2020.04.30
 */
@Data
public class Git {
    private String author;
    private String after;
    private String before;
}
