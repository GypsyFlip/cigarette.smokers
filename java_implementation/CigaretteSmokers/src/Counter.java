import java.util.concurrent.Semaphore;

public class Counter {
    public final Semaphore paper, spark, tobacco, select;
    public final Semaphore[] semArray;
    private int t;
    Agent agatha = new Agent("Agatha", this);
    private Smoker horacio = new Smoker("Horacio", Item.TOBACCO, this);
    private Smoker arthur = new Smoker("Arthur", Item.PAPER, this);
    private Smoker edgar = new Smoker("Edgar", Item.SPARK, this);

    public Counter() {
	t = 0;
	paper = new Semaphore(0);
	spark = new Semaphore(0);
	tobacco = new Semaphore(0);
	select = new Semaphore(1);
	semArray = new Semaphore[7];
	for (int i = 1; i < semArray.length; i++)
	    semArray[i] = new Semaphore(0);

	agatha.start();
	horacio.start();
	arthur.start();
	edgar.start();
    }

    public synchronized void increase(Item i) {
	t += i.getValue();
	if (t > 0 && t <= 6) semArray[t].release();
	notifyAll();
    }

    public synchronized void decrease() {
	t = 0;
	notifyAll();
    }

    @SuppressWarnings("deprecation")
    public synchronized void killSmokers() {
	Smoker.running = false;
	try {
	    horacio.join(500);
	    arthur.join(500);
	    edgar.join(500);
	    horacio.stop();
	    arthur.stop();
	    edgar.stop();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println();
	if (Agent.smokeOrder.toString().equalsIgnoreCase(Smoker.smokeOrder.toString())) {
	    System.out.println("Great Success");
	} else {
	    System.out.println("Not so great...");
	    System.out.println(Agent.smokeOrder.toString());
	    System.out.println(Smoker.smokeOrder.toString());

	}
    }

    public static void main(String[] args) {
	new Counter();
    }

}