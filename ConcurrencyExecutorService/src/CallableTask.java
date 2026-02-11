import java.util.Random;
import java.util.concurrent.Callable;

// As runnable dont return anything to return something we can use Callable
public class CallableTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        return new Random().nextInt();
    }
}
