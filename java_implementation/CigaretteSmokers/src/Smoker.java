import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Smoker extends Thread {

    private final Set<Item> itemsNeeded;
    private int valueNeeded;
    private final Counter counter;
    private final Random rand = new Random();
    public static StringBuilder smokeOrder = new StringBuilder();
    public static boolean running = true;
    private final SmokeTimeRange str;
    private int smokeCounter;

    public Smoker(String name, Item i, Counter counter, SmokeTimeRange str) {
	super(name);
	this.str = str;
	itemsNeeded = new HashSet<Item>(4);
	for (Item foo : Item.values()) {
	    if (foo.equals(i)) continue;
	    else {
		valueNeeded += foo.getValue();
		itemsNeeded.add(foo);
	    }
	}
	this.counter = counter;
    }

    @Override
    public void run() {
	while (running) {
	    try {
		counter.semArray[valueNeeded].acquire();
		counter.decrease(valueNeeded);
		for (Item item : itemsNeeded) {
		    switch (item) {
		    case PAPER:
			counter.paper.acquire();
			break;
		    case SPARK:
			counter.spark.acquire();
			break;
		    case TOBACCO:
			counter.tobacco.acquire();
			break;
		    default:
			break;
		    }
		}
		smokeCounter++;
		smokeOrder.append(getName() + ",");	// will test at end to see if this list agrees with the list the agent makes
		System.out.print(getName() + " begins smoke .... ");
		sleep(rand.nextInt(str.max - str.min + 1) + str.min); // smoke for a random amount of time
		System.out.print(" " + getName() + " ended smoking." + "\n");
		counter.select.release();
	    } catch (InterruptedException e) {
		System.out.println(getName() + " was waiting, but didn't get his last smoke...");
	    }
	}
    }

    public int getSmokeCounter() {
	return smokeCounter;
    }
}