package com.revature.services;

import com.revature.models.Accounts.Account;
import com.revature.models.Accounts.CheckingAccount;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.AccountRepository;
import com.revature.util.AppState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;

public class AccountServiceTests {

    private AccountService sut;
    private AccountService sutSpy;
    private AccountRepository mockUserRepo = Mockito.mock(AccountRepository.class);
    private AppState mockApp = Mockito.mock(AppState.class);

    // AccountService is also being mocked because methods within the class depend on eachother.
    // For example, depositIntoAccount depends on getAccountsOfCurrentUsers
    private AccountService mockAccoutService = Mockito.mock(AccountService.class);


    CheckingAccount acct1;
    HashSet<Account> mockAccountHashSet = new HashSet<Account>();

    AppUser appUser = new AppUser(1, "Pepita", "Delos", "Palotes", "ppalotes", "contrasena123", "ppalotes@pepamail.com", Role.LOCKED);

    private AppUser currentUser = appUser;


    @Before
    public void setup(){
        sut = new AccountService(mockUserRepo);
        acct1 = new CheckingAccount(3, 50.0);
        mockAccountHashSet.add(acct1);
        sutSpy = Mockito.spy(sut);
    }

    @After
    public void tearDown(){
        sut = null;
        acct1 = null;
        mockAccountHashSet = null;
        sutSpy = null;
    }

    @Test
    public void ceroIsNotPositive() {
        boolean expectedResult = false;
        boolean actualResult = AccountService.isAmountPositive(0);
        Assert.assertEquals(expectedResult, actualResult);

    }


    @Test
    public void FiftyisPositive() {
        boolean expectedResult = true;
        boolean actualResult = AccountService.isAmountPositive(50);
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void accountsWithFiftyInFoundsCannotWithdrawalSixty() {
        boolean expectedResult = false;
        boolean actualResult = AccountService.accountHasSufficientFounds(60, 50);
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void accountsWithFiftyInFoundsCannWithdrawalThirty() {
        boolean expectedResult = true;
        boolean actualResult = AccountService.accountHasSufficientFounds(30, 50);
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void cannotDepositeNegativeAmount(){
        boolean expectedResult = false;
        boolean actualResult = sut.depositIntoAccount(-8, appUser);
        Assert.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void cannotWithdrawNegativeAmount(){
        boolean expectedResult = false;
        boolean actualResult = sut.withdrawFromAccount(-8, appUser);
        Assert.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void loggingOutSuccessfullySetsCurrentUserAndCurrentUserAccountsToNull(){
        boolean expectedResult = true;
        boolean actualResult = AccountService.logOut();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void InBalance50depositAmount5NewBalance55AllAsDoubles(){
        double expectedResult = 55.0;
        double actualResult = sut.calculateNewBalance(acct1, 5.0);
        Assert.assertEquals(expectedResult, actualResult, 0.001);

    }

    @Test
    public void getFirstAccounfOfUserSuccesful(){
        CheckingAccount expectedResult = acct1;
        Mockito.doReturn(mockAccountHashSet).when(sutSpy).getAccountsOfCurrentUsers(appUser);
        CheckingAccount actualResult = sutSpy.getFirstAccountOfUser(appUser);
        Assert.assertEquals(expectedResult, actualResult);
    }


}
