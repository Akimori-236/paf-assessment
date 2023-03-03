package nus.iss.tfip.pafassessment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nus.iss.tfip.pafassessment.exception.TransferException;
import nus.iss.tfip.pafassessment.model.Account;
import nus.iss.tfip.pafassessment.model.Transfer;
import nus.iss.tfip.pafassessment.repository.AccountsRepository;

@Service
public class FundsTransferService {

    @Autowired
    private AccountsRepository accRepo;

    public List<Account> getAllAccounts() {
        return accRepo.getAllAccounts();
    }

    public Boolean isAccountExist(String accountId) {
        return accRepo.isAccountExist(accountId);
    }

    public Double getBalanceById(String accountId) {
        return accRepo.getBalanceById(accountId);
    }

    @Transactional(rollbackFor = TransferException.class)
    public Transfer transferFunds(Transfer transfer) throws TransferException {
        // generate transaction id
        String txnId = UUID.randomUUID().toString().substring(0, 8);
        transfer.setId(txnId);
        // Transaction
        Boolean isWithdrawSuccess = accRepo.withdrawFunds(transfer.getFromAccount(), transfer.getAmount());
        if (!isWithdrawSuccess)
            throw new TransferException("Error withdrawing funds");
        Boolean isDepositSuccess = accRepo.depositFunds(transfer.getToAccount(), transfer.getAmount());
        if (!isDepositSuccess)
            throw new TransferException("Error depositing funds");
        // populate account names
        transfer.setFromAccountName(accRepo.getNameById(transfer.getFromAccount()));
        transfer.setToAccountName(accRepo.getNameById(transfer.getToAccount()));
        return transfer;
    }
}
