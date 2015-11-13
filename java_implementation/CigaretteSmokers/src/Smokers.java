import java.util.concurrent.Semaphore;


public class Smokers extends Thread {

	private final Item item;
	private final Item[] itemsNeeded = new Item[2];
	private final Item[] itemsAcquired;
	
	
	public Smokers(final String name, final Item i, final Item[] requires) {
		super(name);
		this.item = i;
		this.itemsAcquired = new Item[requires.length];
		
		for (int j = 0; j < requires.length; j++) {
			itemsNeeded[j] = requires[j];
		}
	}
	
	/* Get smokers item */
	public Item getItem() {
		return this.item;
	}
	
	
	//give item to this smoker
	public void obtainItem(Item i) {
		
		if (itemsAcquired[0] == null) {
			itemsAcquired[0] = i; 
		}
		
		else if (itemsAcquired[1] == null) {
			itemsAcquired[1] = i;
		}
		
		else {
			System.out.println("DERP I ("+this.getName()+") already have all items I need");
		}
	}
	
	
	//if it has all required items, make cigarette and smoke it
	public boolean makeCigarette() {
		
		
		if (itemsAcquired[0] != null && itemsAcquired[1] != null) {
			System.out.println(this.getName() + " rolls cigaratte and smokes...");
			return true;
		}
		
		else {
			System.out.println("I cant smoke yet...");
		}
		
		return false;
	}
	
	
}
