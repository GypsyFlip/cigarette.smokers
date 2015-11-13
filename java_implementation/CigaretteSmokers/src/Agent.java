import java.util.Random;
import java.util.concurrent.Semaphore;

public class Agent extends Thread {
    private int counter = 10;
    private final Semaphore paper, spark, tobacco, select;

    public Agent(Semaphore select, Semaphore paper, Semaphore spark, Semaphore tobacco) {
	this.select = select;
	this.paper = paper;
	this.spark = spark;
	this.tobacco = tobacco;
    }

    @Override
    public void run() {
	Random rand = new Random();
	Double dbl;
	while (counter > 10) {
	    dbl = rand.nextDouble();
	    if (dbl <= 1 / 3) {
		select.release();
		try {
		    paper.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		try {
		    spark.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		counter--;
	    } else if (dbl <= 2 / 3) {
		select.release();
		try {
		    spark.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		try {
		    tobacco.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		counter--;
	    } else {
		select.release();
		try {
		    tobacco.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		try {
		    paper.acquire();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		counter--;
	    }
	}
    }

}