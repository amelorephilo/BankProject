package com.capg.bankapp.service;

import com.capg.bankapp.dao.AccountDaoImpl;
import com.capg.bankapp.dao.IAccountDao;
import com.capg.bankapp.model.Account;
import com.capg.bankapp.model.Customer;
import com.capg.bankapp.util.AccountUtil;
import com.capg.bankapp.util.InsufficientOpeningBalanceException;

public class AccountServiceImpl implements IAccountService{
	
	private IAccountDao accountDao;
	
	public AccountServiceImpl() {
		accountDao=new AccountDaoImpl();
	}

	public AccountServiceImpl(IAccountDao accountDao) {
		this.accountDao=accountDao;
	}

	@Override
	public Account createAccount(Customer customer, double amount) throws InsufficientOpeningBalanceException {
		if(customer==null)
			throw new IllegalArgumentException("Invalid Customer!");
		if(amount<500)
			throw new InsufficientOpeningBalanceException("Sorry! Not Sufficient opening Balance!");
		
		Account account=new Account();
		account.setAccountNo(AccountUtil.generateAccountNo());
		account.setCustomer(customer);
		account.setOpeningBalance(amount);
		
		boolean flag=accountDao.saveAccount(account);
		if(flag)
				return account;
		
		return null;
	}

	@Override
	public Account deposit(Account account, double amount) {
		
		Account account2=accountDao.findAccount(account.getAccountNo());
		
		if(account2==null)
			throw new IllegalArgumentException();
		
		account2.setOpeningBalance(account2.getOpeningBalance()+amount);
		
		return account2;
		
	}
	
	
	public Account findAccount(int accountNo) {
		return accountDao.findAccount(accountNo);
	}
	

}
