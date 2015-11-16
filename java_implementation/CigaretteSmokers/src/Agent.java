import java.util.Random;

public class Agent extends Thread {
    public static final int doFor = 10000;
    private final Counter counter;
    public static StringBuilder smokeOrder = new StringBuilder();

    public Agent(String name, Counter counter) {
	super(name);
	this.counter = counter;
    }

    @Override
    public void run() {
	Random rand = new Random();
	Double dbl;
	int i = 0;
	while (i < doFor) {
	    dbl = rand.nextDouble();
	    if (dbl <= 1f / 3f) try {
		smokeOrder.append("Horacio,");
		counter.select.acquire();
		counter.paper.release();
		counter.increase(Item.PAPER);
		counter.spark.release();
		counter.increase(Item.SPARK);
		i++;
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	    else if (dbl <= 2f / 3f) try {
		smokeOrder.append("Arthur,");
		counter.select.acquire();
		counter.spark.release();
		counter.increase(Item.SPARK);
		counter.tobacco.release();
		counter.increase(Item.TOBACCO);
		i++;
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
		i++;
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	}
	counter.killSmokers();
    }

}