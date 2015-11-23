import java.util.Random;

public class Agent extends Thread {
    private final int doFor;
    private final Counter counter;
    public static StringBuilder smokeOrder = new StringBuilder();// list the order in which smokers should smoke based off of what ingredients agent puts down

    public Agent(String name, Counter counter, int x) {
	super(name);
	if (x <= 0) throw new IllegalArgumentException("Agent must run a positive number of times");
	doFor = x;
	this.counter = counter;
    }

    @Override
    public void run() {
	Random rand = new Random();
	int decision;
	int i = 0;
	while (i < doFor) {
	    decision = rand.nextInt(3);
	    if (decision == 0) try {
		smokeOrder.append("Horacio,");
		counter.select.acquire();
		counter.paper.release();
		counter.increase(Item.PAPER);
		counter.spark.release();
		counter.increase(Item.SPARK);
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	    else if (decision == 1) try {
		smokeOrder.append("Arthur,");
		counter.select.acquire();
		counter.spark.release();
		counter.increase(Item.SPARK);
		counter.tobacco.release();
		counter.increase(Item.TOBACCO);
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	    else try {
		smokeOrder.append("Edgar,");
		counter.select.acquire();
		counter.tobacco.release();
		counter.increase(Item.TOBACCO);
		counter.paper.release();
		counter.increase(Item.PAPER);
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	    i++;
	}
	counter.killSmokers();
    }

    public int getDoFor() {
	return doFor;
    }

}