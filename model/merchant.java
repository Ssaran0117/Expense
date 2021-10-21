package Sai.Expense;

public class merchant
{
	int merchant_id;
	String merchant_name, description;
	public int getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "merchant [merchant_id=" + merchant_id + ", merchant_name=" + merchant_name + ", description="
				+ description + "]";
	}
	
}
