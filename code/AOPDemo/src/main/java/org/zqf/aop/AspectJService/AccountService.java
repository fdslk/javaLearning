package org.zqf.aop.AspectJService;

import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class AccountService {
    public Integer calculateAccount(final String accountId, final Integer accountMoney) {
        System.out.println(MessageFormat.format("accountId:{0}, and available money:{1}", accountId, accountMoney));
        return accountMoney;
    }
}
