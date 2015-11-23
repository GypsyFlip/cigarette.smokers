import java.util.concurrent.Semaphore;

public class CounterTest {
	public final Semaphore paper, spark, tobacco, select;
	public final Semaphore[] semArray;
	private int t;
	AgentTest agatha = new AgentTest("Agatha", this);
	private SmokerTest horacio = new SmokerTest("Horacio", Item.TOBACCO, this, 0, 300);
	private SmokerTest arthur = new SmokerTest("Arthur", Item.PAPER, this,100,400);
	private SmokerTest edgar = new SmokerTest("Edgar", Item.SPARK, this,300,600);
	
	private int numberOfSuccess = 0;

	public CounterTest() {
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
		if (t > 0 && t <= 6)
			semArray[t].release();
		notifyAll();
	}

	public synchronized void decrease() {
		t = 0;
		notifyAll();
	}

	public synchronized void killSmokers() {
		Smoker.running = false;
		try {
			horacio.join(100);
			arthur.join(100);
			edgar.join(100);
			horacio.interrupt();
			arthur.interrupt();
			edgar.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		if (Agent.smokeOrder.toString().equalsIgnoreCase(Smoker.smokeOrder.toString())) {
			System.out.println(++numberOfSuccess);
			System.out.println("=============   Great Success   ==============");
		} else {
			System.out.println("Not so great...");
			System.out.println(Agent.smokeOrder.toString());
			System.out.println(Smoker.smokeOrder.toString());

		}
	}

	public static void main(String[] args) {
		new CounterTest();
		

	}

}