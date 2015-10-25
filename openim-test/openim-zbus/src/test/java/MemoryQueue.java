import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * Created by shihuacai on 2015/10/10.
 */
public class MemoryQueue extends AbstractQueue {
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }
}
