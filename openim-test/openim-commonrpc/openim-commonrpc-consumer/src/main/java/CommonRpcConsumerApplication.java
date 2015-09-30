import com.openim.commonrpc.common.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by shihuacai on 2015/9/29.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.openim")
@ImportResource({"classpath:consumer.xml"})
public class CommonRpcConsumerApplication  implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CommonRpcConsumerApplication.class);
        //springApplication.addListeners(new ApplicationContextAware());
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        IDemoService demoService = (IDemoService) context.getBean("demoServiceClient"); //
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String result = demoService.sayDemo("okok");
            System.out.println(result);
        }

        long end1 = System.currentTimeMillis();
        System.out.println("完成时间1:" + (end1 - time1));

    }
}

