package Sai.Expense;

public class display {
	String name;
	int count;
	float amount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "display [name=" + name + ", count=" + count + ", amount=" + amount + "]";
	}
	
}
