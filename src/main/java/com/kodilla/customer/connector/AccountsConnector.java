package com.kodilla.customer.connector;

import com.kodilla.customer.configuration.RibbonConfiguration;
import com.kodilla.customer.connector.response.GetAccountsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

//@RibbonClient(name = "accounts", configuration = RibbonConfiguration.class)
@FeignClient(name = "accounts", fallback = AccountsConnectorFallback.class)
public interface AccountsConnector {

    @GetMapping("/v1/accounts")
    GetAccountsResponse getAccounts(@RequestParam("customerId") Long customerId);
}

@Slf4j
@Component
class AccountsConnectorFallback implements AccountsConnector {

    @Override
    public GetAccountsResponse getAccounts(Long customerId) {
        log.warn("Unable to get accounts for customerId {}", customerId);
        return new GetAccountsResponse(Collections.emptyList());
    }
}
