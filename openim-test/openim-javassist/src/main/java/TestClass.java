/**
 * Created by shihuacai on 2015/11/15.
 */
public class TestClass {
    Service service;
    public TestClass(){
        this.service = new Service();
    }
    public void test(String ta1) {
        service.print(ta1);
        System.out.print(ta1);
    }

    public void test2(String ta2) {
        //System.out.print("");
    }

    public void test3(String ta3) {

    }


}
