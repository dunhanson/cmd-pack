package site.dunhanson.cmd.pack.entity;

import lombok.Data;

import java.util.List;

/**
 * 配置信息
 * @author dunhanson
 * @date 2020.04.30
 */
@Data
public class Basic {
    private List<Project> projects;
    private Git git;
}
