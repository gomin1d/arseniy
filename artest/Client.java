package artest;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client {
    private static int idCounter = 0;
    private static Random random = new Random();

    // номер клиента
    private int id = idCounter++;

    // таймаут ожидания оператора (10-20 секунд)
    private int timeoutWaitOperatorSeconds;

    // операторы
    private BlockingQueue<Operator> operators;

    public Client(BlockingQueue<Operator> operators, int timeoutWaitOperatorSeconds) {
        this.operators = operators;
        this.timeoutWaitOperatorSeconds = timeoutWaitOperatorSeconds;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                '}';
    }

    public void asyncCall() {
        System.out.println("Звоним в call центер " + this);
        Thread thread = new Thread(() -> {
            try {
                Operator operator = operators.poll(timeoutWaitOperatorSeconds, TimeUnit.SECONDS);
                if (operator == null) {
                    System.out.println("Не дозвонились, бросили трубку " + this);
                    this.maybeCallYouBack(); // думаем, будем ли перезванивать
                } else {
                    int callDurationSeconds = random.nextInt(30) + 30;
                    System.out.println("Дозвонились " + this + " оператору " + operator + ". Будем говорить " + callDurationSeconds + " секунд");
                    TimeUnit.SECONDS.sleep(callDurationSeconds);
                    System.out.println("Поговорили " + this + " с оператором " + operator + ", кладем трубку.");
                    operators.add(operator);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void maybeCallYouBack() throws InterruptedException {
        // с шансом 40% перезвоним
        if (random.nextInt(100) >= 60) {

            int callBackThroughSeconds = random.nextInt(20) + 10;
            System.out.println("Решили " + this + " перезвонить через " + callBackThroughSeconds);
            TimeUnit.SECONDS.sleep(callBackThroughSeconds);

            System.out.println("Перезваниваем " + this);
            this.asyncCall();
        }
    }
}
