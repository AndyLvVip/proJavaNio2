package andy.ch01;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: andy.lv
 * @Date: created on 2018/1/22
 * @Description:
 */
public class TestPaths {

    @Test
    public void pathFromUri() throws URISyntaxException {
        Path path = Paths.get(this.getClass().getResource("test.txt").toURI());
        Assert.assertNotNull(path);
        System.out.println(path);
    }

    @Test
    public void normalize() {
        Path path = Paths.get("D:/hello/./world/../welcome");
        System.out.println(path);
        System.out.println(path.normalize());
    }

    @Test
    public void iterate() {
        Path path = Paths.get("D:/hello/world/welcome/abc.txt");
        for(Path name : path)
            System.out.println(name);
    }
}
