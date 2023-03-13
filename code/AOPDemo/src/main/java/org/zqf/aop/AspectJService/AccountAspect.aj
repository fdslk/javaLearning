package org.zqf.aop.AspectJService;

import java.text.MessageFormat;

public aspect AccountAspect {
    final int MIN_BALANCE = 10;

    pointcut callWithDraw(int amount, Account acc) :
     call(boolean Account.withdraw(int)) && args(amount) && target(acc);

    before(int amount, Account acc) : callWithDraw(amount, acc) {
            System.out.println(MessageFormat.format("Balance before withdrawal: {0}", acc.balance));
            System.out.println(MessageFormat.format("Account address: {0}", acc.toString()));
            System.out.println(MessageFormat.format("Withdraw ammout: {0}", amount));
    }

    boolean around(int amount, Account acc) :
      callWithDraw(amount, acc) {
        if (acc.balance < amount) {
            System.out.println("Withdrawal Rejected!");
            return false;
        }
        return proceed(amount, acc);
    }

    after(int amount, Account balance) : callWithDraw(amount, balance) {
         System.out.println(MessageFormat.format("Balance after withdrawal : {0}", balance.balance));
    }
}
