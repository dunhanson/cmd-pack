import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import site.dunhanson.cmd.pack.entity.Basic;
import site.dunhanson.cmd.pack.entity.Git;
import site.dunhanson.cmd.pack.entity.Project;
import site.dunhanson.cmd.pack.utils.CmdUtils;
import site.dunhanson.cmd.pack.utils.GitUtils;
import site.dunhanson.cmd.pack.utils.YamlUtils;

import java.util.ArrayList;
import java.util.List;

public class TestDemo {
    @Test
    public void test() {
        Basic basic = YamlUtils.getEntity(Basic.class, "cmd-pack.yaml", "basic");

        Git git = basic.getGit();
        //git命令
        String gitCommand = GitUtils.getCommand(git);
        //cmd命令
        List<String> commandBuilder = new ArrayList();
        commandBuilder.add("cmd.exe /c cd " + " && cd E:\\Programming\\Code\\Company\\bxkc-pc");
        commandBuilder.add(gitCommand);
        String command = StringUtils.join(commandBuilder, " & ");
        //执行cmd
        String result = CmdUtils.execGetResult(command);
        System.out.println(result);
    }

    @Test
    public void test2() {
        String path = "/D:/Test/cmd-pack/target/classes/";
        System.out.println(path.substring(1));
    }
}
