package com.capg.test.bankapp;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.capg.bankapp.dao.IAccountDao;
import com.capg.bankapp.model.Account;
import com.capg.bankapp.model.Address;
import com.capg.bankapp.model.Customer;
import com.capg.bankapp.service.AccountServiceImpl;
import com.capg.bankapp.service.IAccountService;
import com.capg.bankapp.util.InsufficientOpeningBalanceException;

public class BankAppTestCases {
	
	@Mock
	private IAccountDao accountDao;
	private IAccountService accountService;
	private Customer customer;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		accountService=new AccountServiceImpl(accountDao);
	}

	@Test
	public void test_createAccountMethod_withValidCustomerAndBalance() throws InsufficientOpeningBalanceException {
		
		customer=new Customer();
		customer.setAddress(new Address("12/A, North Avvenue", "Chennai"));
		customer.setCustomerName("Tom");
		customer.setCustomerId(1234);
		
		Account account=new Account();
		account.setAccountNo(123);
		account.setCustomer(customer);
		account.setOpeningBalance(2300);
		
		//dummy declaration
		Mockito.when(accountDao.saveAccount(account)).thenReturn(true);
		//Mockito.when(accountDao.saveAccount(Mockito.any(Account.class))).thenReturn(true);
		
		
			
		//Actual Logic
		Account account1=accountService.createAccount(customer,2300.0);
		
		
		Mockito.verify(accountDao).saveAccount(account);
		
		//validation
		assertEquals(2300, account1.getOpeningBalance(), 0.0);
		
	}

	
	
	@Test(expected=IllegalArgumentException.class)
	public void test_CreateAccountMethod_with_null_customer() throws InsufficientOpeningBalanceException {
		
		accountService.createAccount(customer, 1000);
	}
	
	

	@Test(expected=InsufficientOpeningBalanceException.class)
	public void test_CreateAccountMethod_with_Invalid_openingBalance() throws InsufficientOpeningBalanceException {
		//accountService=new AccountServiceImpl();
		customer=new Customer();
		customer.setAddress(new Address("12/A, North Avvenue", "Chennai"));
		customer.setCustomerName("Tom");
		customer.setCustomerId(1234);
		
		accountService.createAccount(customer, 100);
	}
	
	
	@Test
	public void test_depositMethod_with_valid_openingBalance() {
		
		Account account=new Account();
			account.setAccountNo(123);
			account.setCustomer(customer);
			account.setOpeningBalance(2300);
			
			Mockito.when(accountDao.findAccount(123)).thenReturn(account);
			
			
		Account account2=accountService.deposit(account,4000.0);
		
		Mockito.verify(accountDao).findAccount(123);
		
		assertEquals(6300.0, account2.getOpeningBalance(),0.0);
		
		
	}
	
	
	
	@Test(timeout=2)
	public void test_loop() {
		int sum=0;
		for(int i=0;i<10000000000l;i++) {
			sum+=i;
			//System.out.println(sum);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
