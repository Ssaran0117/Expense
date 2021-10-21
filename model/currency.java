package Sai.Expense;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class currency {
	int curr_id;
	String curr_code, curr_symbol;
	public int getCurr_id() {
		return curr_id;
	}
	public void setCurr_id(int curr_id) {
		this.curr_id = curr_id;
	}
	public String getCurr_code() {
		return curr_code;
	}
	public void setCurr_code(String curr_code) {
		this.curr_code = curr_code;
	}
	public String getCurr_symbol() {
		return curr_symbol;
	}
	public void setCurr_symbol(String curr_symbol) {
		this.curr_symbol = curr_symbol;
	}
	@Override
	public String toString() {
		return "currency [curr_id=" + curr_id + ", curr_code=" + curr_code + ", curr_symbol=" + curr_symbol + "]";
	}
	
}
