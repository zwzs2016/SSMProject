import com.bamboo.BamBooApplication;
import com.bamboo.entity.User;
import com.bamboo.mapper.UserMapper;
import com.bamboo.util.handler.LongModTableNameHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {BamBooApplication.class})
@RunWith(SpringRunner.class)
public class ShardingJdbcApplicationTests {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void test01() {
        User user = new User();
        user.setName("usertest1");
        user.setPassword("zw123456");
        user.setAge(17);
        user.setSex(1);

        //动态处理表名
        LongModTableNameHandler.setData(2L);
        userMapper.insert(user);
        //插后即焚，将ThreadLocal当前请求线程的数据移除
        LongModTableNameHandler.removeData();
    }
}
