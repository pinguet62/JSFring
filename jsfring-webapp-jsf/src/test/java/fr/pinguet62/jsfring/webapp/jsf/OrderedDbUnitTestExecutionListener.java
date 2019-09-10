package fr.pinguet62.jsfring.webapp.jsf;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;

public class OrderedDbUnitTestExecutionListener extends DbUnitTestExecutionListener {

    @Override
    public int getOrder() {
        return new WithSecurityContextTestExecutionListener().getOrder();
    }

}
