package com.library.library_microservice.utility;

import com.library.library_microservice.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoanSchedular {
    private final LoanService loanService;


    /**
     * Scheduled task to update overdue loans daily.
     * Runs after every 3 horu.
     */
  @Scheduled(cron = "0 0 */3 * * ?")
//    @Scheduled(fixedRate = 120000)
    public void processOverdueLoans() {
        loanService.updateOverdueLoans();
    }
}
