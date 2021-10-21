package Sai.Expense;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class expense {
	int expense_id, merchant_id, user_id, category_id, curr_id;
	float amount;
	String reference;
	public int getExpense_id() {
		return expense_id;
	}
	public void setExpense_id(int expense_id) {
		this.expense_id = expense_id;
	}
	public int getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getCurr_id() {
		return curr_id;
	}
	public void setCurr_id(int curr_id) {
		this.curr_id = curr_id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	@Override
	public String toString() {
		return "expense [expense_id=" + expense_id + ", merchant_id=" + merchant_id + ", user_id=" + user_id
				+ ", category_id=" + category_id + ", curr_id=" + curr_id + ", amount=" + amount + ", reference="
				+ reference + "]";
	}
	
}
