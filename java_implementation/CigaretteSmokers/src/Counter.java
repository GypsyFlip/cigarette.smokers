import java.util.concurrent.Semaphore;

public class Counter {
    public final Semaphore paper, spark, tobacco, select;
    public final Semaphore[] semArray;
    private int t;
    private Agent agatha;
    private Smoker horacio;
    private Smoker arthur;
    private Smoker edgar;

    public Counter(SmokeTimeRange str1, SmokeTimeRange str2, SmokeTimeRange str3, int agentDoFor) {
	t = 0;
	paper = new Semaphore(0);
	spark = new Semaphore(0);
	tobacco = new Semaphore(0);
	select = new Semaphore(1);
	semArray = new Semaphore[7];
	for (int i = 1; i < semArray.length; i++)
	    semArray[i] = new Semaphore(0);

	agatha = new Agent("Agatha", this, agentDoFor);
	horacio = new Smoker("Horacio", Item.TOBACCO, this, str1);
	arthur = new Smoker("Arthur", Item.PAPER, this, str2);
	edgar = new Smoker("Edgar", Item.SPARK, this, str3);
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

    public synchronized void killSmokers() {
	Smoker.running = false;
	try {
	    horacio.join(100);
	    arthur.join(100);
	    edgar.join(100);
	    horacio.interrupt();
	    arthur.interrupt();
	    edgar.interrupt();
	    Thread.sleep(10);	// tiny wait period for each smoker thread to end (not necessary to do this)
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println();
	if (Agent.smokeOrder.toString().equalsIgnoreCase(Smoker.smokeOrder.toString())) {// test if the list the smokers made agrees with the list the agent made
	    System.out.println("Great Success");
	    System.out.println(agatha.getName() + " put ingredients down " + agatha.getDoFor() + " times.");
	    System.out.print(horacio.getName() + " smoked \t" + horacio.getSmokeCounter() + " times. -> ");
	    System.out.println(String.valueOf(100 * horacio.getSmokeCounter() / (double) agatha.getDoFor()) + "%");

	    System.out.print(arthur.getName() + " smoked \t" + arthur.getSmokeCounter() + " times. -> ");
	    System.out.println(String.valueOf(100 * arthur.getSmokeCounter() / (double) agatha.getDoFor()) + "%");

	    System.out.print(edgar.getName() + " smoked \t" + edgar.getSmokeCounter() + " times. -> ");
	    System.out.println(String.valueOf(100 * edgar.getSmokeCounter() / (double) agatha.getDoFor()) + "%");

	    System.out.println("Total Smokes: \t" + String.valueOf(horacio.getSmokeCounter() + arthur.getSmokeCounter() + edgar.getSmokeCounter()));
	} else {
	    System.out.println("Not so great...");
	    System.out.println(Agent.smokeOrder.toString());
	    System.out.println(Smoker.smokeOrder.toString());
	}
    }

    /*
     * Create an instance of Counter which itself spawns 3 smoker threads (Horacio, Agatha, Edgar) that smoke for random amounts of time (in ms) within the given
     * SmokeTimeRange. Counter also starts 1 agent thread (Agatha) that places ingredients on the table the given number of times. Note: with max smokeTime ~ 7ms and
     * agent doFor ~10000 the completion time is ~33s so increase the smokeTimeRange and agent doFor at your own peril ;)
     */
    public static void main(String[] args) {
	new Counter(new SmokeTimeRange(0, 5), new SmokeTimeRange(2, 4), new SmokeTimeRange(1, 7), 10000);
    }

}