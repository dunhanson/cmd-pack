package site.dunhanson.cmd.pack.entity;

import lombok.Data;

/**
 * 配置信息
 * @author dunhanson
 * @date 2020.04.30
 */
@Data
public class Basic {
    private Project project;
    private Git git;
}
