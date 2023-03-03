package nus.iss.tfip.pafassessment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nus.iss.tfip.pafassessment.exception.TransferException;
import nus.iss.tfip.pafassessment.model.Account;
import nus.iss.tfip.pafassessment.model.Transfer;
import nus.iss.tfip.pafassessment.repository.SqlRepository;

@Service
public class FundsTransferService {

    @Autowired
    private SqlRepository sqlRepo;

    public List<Account> getAllAccounts() {
        return sqlRepo.getAllAccounts();
    }

    public Boolean isAccountExist(String accountId) {
        return sqlRepo.isAccountExist(accountId);
    }

    public Double getBalanceById(String accountId) {
        return sqlRepo.getBalanceById(accountId);
    }

    @Transactional(rollbackFor = TransferException.class)
    public Transfer transferFunds(Transfer startTransfer) throws TransferException {
        // generate transaction id
        String txnId = UUID.randomUUID().toString().substring(0, 8);
        startTransfer.setId(txnId);
        // Transaction
        Boolean isWithdrawSuccess = sqlRepo.withdrawFunds(startTransfer.getFromAccount(), startTransfer.getAmount());
        if (!isWithdrawSuccess)
            throw new TransferException("Error withdrawing funds");
        Boolean isDepositSuccess = sqlRepo.depositFunds(startTransfer.getToAccount(), startTransfer.getAmount());
        if (!isDepositSuccess)
            throw new TransferException("Error depositing funds");
        return startTransfer;
    }
}
