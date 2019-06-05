package artest;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int phoneOperator = 4;

        BlockingQueue<Operator> operators = new ArrayBlockingQueue<>(4);
        for (int i = 0; i < phoneOperator; i++) {
            operators.add(new Operator());
        }

        Random random = new Random();

        while (true) {
            // таймаут ожидания оператора (10-20 секунд) этим клиентом
            int timeoutWaitOperatorSeconds = random.nextInt(20) + 10;

            Client client = new Client(operators, timeoutWaitOperatorSeconds);
            client.asyncCall();

            TimeUnit.SECONDS.sleep(10); // каждые 10 сек новый звонок
        }
    }
}
