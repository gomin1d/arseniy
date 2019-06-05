package artest;

public class Operator {
    private static int idCounter = 0;

    // номер оператора
    private int id = idCounter++;

    public Operator() {
    }

    @Override
    public String toString() {
        return "Operator{" +
                "id=" + id +
                '}';
    }
}
