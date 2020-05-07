
import org.junit.Test;
import site.dunhanson.cmd.pack.entity.Basic;
import site.dunhanson.cmd.pack.utils.PackUtils;
import site.dunhanson.cmd.pack.utils.YamlUtils;

public class TestDemo {
    @Test
    public void yaml() {
        Basic basic = YamlUtils.getEntity(Basic.class, "cmd-pack.yaml", "basic");
        System.out.println(basic);
    }

    @Test
    public void start() {
        PackUtils.doing();
    }
}
