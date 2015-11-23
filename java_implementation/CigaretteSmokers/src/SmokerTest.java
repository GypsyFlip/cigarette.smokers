import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SmokerTest extends Thread {

	private final Set<Item> itemsNeeded;
	private int valueNeeded;
	private final CounterTest counter;
	private Random rand = new Random();
	public static StringBuilder smokeOrder = new StringBuilder();
	public static boolean running = true;
	private int smokeRangeStart;
	private int smokeRangeEnd;

	public SmokerTest(String name, Item i, CounterTest counter, int startTime, int endTime) {
		super(name);
		itemsNeeded = new HashSet<Item>(4);
		for (Item foo : Item.values()) {
			if (foo.equals(i))
				continue;
			else {
				valueNeeded += foo.getValue();
				itemsNeeded.add(foo);
			}
		}
		this.counter = counter;
		this.smokeRangeStart = startTime;
		this.smokeRangeEnd = endTime;
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
				
				smokeOrder.append(getName() + ",");
				System.out.print("\n" + getName() + " begins smoke .... ");
				
				int randomSmokeTime = rand.nextInt((smokeRangeEnd - smokeRangeStart) + 1) + smokeRangeStart;
				sleep(randomSmokeTime); // smoke for a random amount of
				
				System.out.print(" " + getName() + " ended smoking." +"\n");
				counter.decrease();
				counter.select.release();
			} catch (InterruptedException e) {
				System.out.println(getName()
						+ " was waiting, but didn't get his last smoke...");
			}

		}

	}

}