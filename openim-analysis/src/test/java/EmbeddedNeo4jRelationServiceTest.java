import com.openim.analysis.AnalysisApplication;
import com.openim.analysis.relation.IRelationService;
import com.openim.common.im.bean.ListResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by shihc on 2015/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AnalysisApplication.class)
@WebAppConfiguration
public class EmbeddedNeo4jRelationServiceTest {

    @Autowired
    IRelationService relationService;

    @Test
    public void addUser(){
        relationService.addUser("user1");
        relationService.addUser("user2");
    }

    @Test
    public void deleteUser(){
        relationService.addUser("xx");
        relationService.deleteUser("xx");

        relationService.addUser("yy");
        relationService.addUser("zz");
        relationService.addRelation("yy", "zz");
        relationService.deleteUser("yy");
    }

    @Test
    public void createRelationShip(){
        relationService.addRelation("user1", "user2");
    }


    @Test
    public void getSecondNetwork(){
        relationService.addUser("aa");
        relationService.addUser("bb");
        relationService.addUser("cc");
        relationService.addUser("dd");
        relationService.addUser("ee");
        relationService.addUser("ff");

        relationService.addRelation("aa", "ff");
        relationService.addRelation("aa", "bb");
        relationService.addRelation("bb", "cc");
        relationService.addRelation("bb", "dd");
        relationService.addRelation("cc", "ee");

        ListResult<String> result = relationService.getSecondNetwork("aa");
        System.out.println(result);
    }

    @Test
    public void deleteRelation(){
        relationService.deleteRelation("aa", "ff");
    }
}
