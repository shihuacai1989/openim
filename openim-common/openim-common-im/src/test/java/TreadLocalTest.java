import java.util.HashMap;

/**
 * Created by shihuacai on 2015/10/8.
 */
public class TreadLocalTest {

    /*
    输出解释；

Thread-1:start
Thread-2:start
Thread-0:start
Thread-2initialValue
Thread-1initialValue
Thread-0initialValue
Thread-1:{0=100, 1=101, 2=102, 3=103, 4=104, 5=105, 6=106, 7=107, 8=108, 9=109}
Thread-2:{0=200, 1=201, 2=202, 3=203, 4=204, 5=205, 6=206, 7=207, 8=208, 9=209}
Thread-0:{0=0, 1=1, 2=2, 3=3, 4=4, 5=5, 6=6, 7=7, 8=8, 9=9}

可以看到map0 虽然是个静态变量，但是initialValue被调用了三次，通过debug发现，initialValue是从map0.get处发起的。而且每个线程都有自己的map，虽然他们同时执行。

进入Theadlocal代码，可以发现如下的片段；

public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null)
                return (T)e.value;
        }
        return setInitialValue();
    }

这说明ThreadLocal确实只有一个变量，但是它内部包含一个map，针对每个thread保留一个entry，如果对应的thread不存在则会调用initialValue。


     */
    static ThreadLocal<HashMap> map0 = new ThreadLocal<HashMap>() {
        @Override
        protected HashMap initialValue() {
            System.out.println(Thread.currentThread().getName() + "initialValue");
            return new HashMap();
        }
    };

    public void run() {
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new T1(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    public static class T1 implements Runnable {
        int id;

        public T1(int id0) {
            id = id0;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            HashMap map = map0.get();
            for (int i = 0; i < 10; i++) {
                map.put(i, i + id * 100);
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
            System.out.println(Thread.currentThread().getName() + ':' + map);
        }
    }

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        TreadLocalTest test = new TreadLocalTest();
        test.run();
    }
}
