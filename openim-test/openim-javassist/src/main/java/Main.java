import java.lang.reflect.Method;

/**
 * Created by shihuacai on 2015/11/15.
 */
public class Main {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        
        Method[] methods = testClass.getClass().getDeclaredMethods();
        for (Method method : methods) {

            String[] arr = JavassistUtil.getMethodParamNames(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
            if (arr != null && arr.length > 0) {
                System.out.print(method.getName() + ": ");
                for (String item : arr) {
                    System.out.print(item + " ");
                }
                System.out.println("");
            } else {
                System.out.println("no arguments");
            }

        }
        //JavassistUtil.
    }
}
