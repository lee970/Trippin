package server;

import javafx.beans.property.SimpleStringProperty;

public class Budget {

	private SimpleStringProperty item;
	private SimpleStringProperty amount;

	public Budget(String item, int amount) {
		this.item = new SimpleStringProperty(item);
		this.amount = new SimpleStringProperty("$" + amount);
	}

	public String getItem() {
		return item.get();
	}

	public String getAmount() {
		return amount.get();
	}
}