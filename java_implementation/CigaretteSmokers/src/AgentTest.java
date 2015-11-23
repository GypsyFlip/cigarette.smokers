import java.util.Random;

public class AgentTest extends Thread {
	
	private final CounterTest counter;
	public static StringBuilder smokeOrder = new StringBuilder();

	private int doFor = 20;
	public AgentTest(String name, CounterTest counter) {
		super(name);
		this.counter = counter;

	}

	@Override
	public void run() {
		Random rand = new Random();
		Double dbl;

		for (int i = 0; i < doFor; i++) {
//  		while (i < doFor) {
  			dbl = rand.nextDouble();
  			if (dbl <= 1f / 3f)
  				try {
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
  			else if (dbl <= 2f / 3f)
  				try {
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
  			else
  				try {
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
//  		} while loop bracket
		}
		counter.killSmokers();
	}

}