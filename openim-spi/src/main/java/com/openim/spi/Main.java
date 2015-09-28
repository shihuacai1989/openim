package com.openim.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by shihuacai on 2015/9/25.
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<IHello> s = ServiceLoader.load(IHello.class);
        Iterator<IHello> searchs = s.iterator();
        while (searchs.hasNext()) {
            IHello curSearch = searchs.next();

            String result = curSearch.hello("test");
            System.out.println(result);
        }
    }
}
