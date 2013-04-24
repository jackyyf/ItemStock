package com.jackyyf.ItemStock;

/**
 * Author: Jack-YYF
 * Created at: 4/26/13 8:00 PM
 * Published under GNU Public License V3(http://www.gnu.org/licenses/gpl-3.0.txt)
 */
public enum TaskType {
	CreateAccount, GetBalance, GetItems, Deposit, Withdraw, Buy, Sell, Unknown;

	public static TaskType get(String key) {
		try {
			return valueOf(key);
		} catch (IllegalArgumentException e) {
			return Unknown;
		}
	}
}
