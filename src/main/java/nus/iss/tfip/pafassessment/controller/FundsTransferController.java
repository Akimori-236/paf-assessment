package nus.iss.tfip.pafassessment.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;

import nus.iss.tfip.pafassessment.exception.TransferException;
import nus.iss.tfip.pafassessment.model.Account;
import nus.iss.tfip.pafassessment.model.Transfer;
import nus.iss.tfip.pafassessment.service.FundsTransferService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class FundsTransferController {

    @Autowired
    private FundsTransferService fundsSvc;

    @GetMapping(path = { "/", "index.html" })
    public String landingPage(Model model) {
        List<Account> accountList = fundsSvc.getAllAccounts();
        Transfer transfer = new Transfer();
        transfer.setFromAccount("");
        transfer.setToAccount("");
        model.addAttribute("transfer", transfer);
        model.addAttribute("accountList", accountList);
        return "view0";
    }

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String initTransfer(@Valid Transfer transfer, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            // System.err.println(binding.getAllErrors().get(0).getDefaultMessage().toString());
            List<Account> accountList = fundsSvc.getAllAccounts();
            model.addAttribute("transfer", transfer);
            model.addAttribute("accountList", accountList);
            return "view0";
        }
        System.out
                .println("NEW TRANSACTION FROM %s TO %s".formatted(transfer.getFromAccount(), transfer.getToAccount()));
        // extract IDs
        String fromAccountId = transfer.getFromAccount();
        System.out.println(fromAccountId);
        // .split(" ")[1].replace("(", "").replace(")", "");
        String toAccountId = transfer.getToAccount();
        System.out.println(toAccountId);
        // .split(" ")[1].replace("(", "").replace(")", "");

        List<ObjectError> errorList = new LinkedList<>();
        // C0
        if (!fundsSvc.isAccountExist(fromAccountId)) {
            ObjectError error = new ObjectError("fromAccount", "From account does not exist in database");
            errorList.add(error);
        }
        if (!fundsSvc.isAccountExist(toAccountId)) {
            ObjectError error = new ObjectError("toAccount", "To account does not exist in database");
            errorList.add(error);
        }
        // C1
        if (fromAccountId.length() != 10) {
            ObjectError error = new ObjectError("fromAccount", "From account ID must be 10 characters");
            errorList.add(error);
        }
        if (toAccountId.length() != 10) {
            ObjectError error = new ObjectError("toAccount", "To account ID must be 10 characters");
            errorList.add(error);
        }
        // C2
        if (fromAccountId == toAccountId) {
            ObjectError error = new ObjectError("toAccount", "Cannot transfer back to same account");
            errorList.add(error);
        }
        // C5
        if (transfer.getAmount() >= fundsSvc.getBalanceById(toAccountId)) {
            ObjectError error = new ObjectError("amount", "Insufficient funds in account");
            errorList.add(error);
        }

        // collated errors
        if (!errorList.isEmpty()) {
            for (ObjectError err : errorList) {
                binding.addError(err);
                System.err.println(binding.getAllErrors());
            }
            List<Account> accountList = fundsSvc.getAllAccounts();
            model.addAttribute("transfer", transfer);
            model.addAttribute("accountList", accountList);
            return "view0";
        }
        // PASS VALIDATION
        try {
            transfer = fundsSvc.transferFunds(transfer);
        } catch (TransferException e) {
            System.err.println(e);
            ObjectError oe= new ObjectError("error", e.getMessage());
            binding.addError(oe);
            List<Account> accountList = fundsSvc.getAllAccounts();
            model.addAttribute("transfer", transfer);
            model.addAttribute("accountList", accountList);
            return "view0";
        }
        model.addAttribute("transfer", transfer);
        return "view1";
    }

}
