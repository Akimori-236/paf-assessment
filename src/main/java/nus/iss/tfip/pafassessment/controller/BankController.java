package nus.iss.tfip.pafassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import nus.iss.tfip.pafassessment.model.Account;
import nus.iss.tfip.pafassessment.model.Transfer;
import nus.iss.tfip.pafassessment.service.SqlService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class BankController {

    @Autowired
    private SqlService sqlSvc;

    @GetMapping(path = { "/", "index.html" })
    public String landingPage(Model model) {
        List<Account> accountList = sqlSvc.getAllAccounts();
        Transfer transfer = new Transfer();
        model.addAttribute("transfer", transfer);
        model.addAttribute("accountList", accountList);
        return "view0";
    }

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String initTransfer(@Valid Transfer transfer, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            System.err.println(binding.getAllErrors().get(0).getDefaultMessage().toString());
            List<Account> accountList = sqlSvc.getAllAccounts();
            model.addAttribute("transfer", transfer);
            model.addAttribute("accountList", accountList);
            return "view0";
        }
        System.out
                .println("NEW TRANSACTION FROM %s TO %s".formatted(transfer.getFromAccount(), transfer.getToAccount()));
        // TODO: submit into repo and get generated uuid, pass into model
        return "view1";
    }

}
