package org.zqf.aop.AspectJService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:springAop-applicationContext.xml"})
public class AccountServiceTest{
    @Autowired
    private AccountService accountService;

    @Test
    public void testCalculateAccount() {
        accountService.calculateAccount(new Random().ints(10).toString(), 100);
    }
}