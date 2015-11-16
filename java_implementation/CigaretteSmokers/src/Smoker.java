import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Smoker extends Thread {

    private final Set<Item> itemsNeeded;
    private int valueNeeded;
    private final Counter counter;
    private Random rand = new Random();
    public static StringBuilder smokeOrder = new StringBuilder();
    public static boolean running = true;

    public Smoker(String name, Item i, Counter counter) {
	super(name);
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
		for (Item item : itemsNeeded) {
		    switch (item) {
		    case PAPER:
			counter.paper.acquire();
			if (counter.semArray[Item.PAPER.getValue()].availablePermits() == 1) {
			    counter.semArray[Item.PAPER.getValue()].acquire();
			}
			break;
		    case SPARK:
			counter.spark.acquire();
			if (counter.semArray[Item.SPARK.getValue()].availablePermits() == 1) {
			    counter.semArray[Item.SPARK.getValue()].acquire();
			}
			break;
		    case TOBACCO:
			counter.tobacco.acquire();
			if (counter.semArray[Item.TOBACCO.getValue()].availablePermits() == 1) {
			    counter.semArray[Item.TOBACCO.getValue()].acquire();
			}
			break;
		    default:
			break;
		    }
		}
		sleep(Math.abs(rand.nextLong() % 5));	// smoke for a random amount of time [0..4ms]
		smokeOrder.append(getName() + ",");
		System.out.println(getName() + " smokes");
		counter.select.release();
		counter.decrease();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	}

    }

}