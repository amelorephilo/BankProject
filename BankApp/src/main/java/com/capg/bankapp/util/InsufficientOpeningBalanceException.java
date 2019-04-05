package com.capg.bankapp.util;

public class InsufficientOpeningBalanceException extends Exception {

	public InsufficientOpeningBalanceException(String msg) {
		super(msg);
	}
}
