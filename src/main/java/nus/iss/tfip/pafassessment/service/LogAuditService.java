package nus.iss.tfip.pafassessment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.pafassessment.model.Transfer;
import nus.iss.tfip.pafassessment.repository.RedisRepository;
import nus.iss.tfip.pafassessment.MyConstants;

@Service
public class LogAuditService implements MyConstants {

    @Autowired
    private RedisRepository redisRepo;

    public Boolean logTransaction(Transfer transfer) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(ldt);
        // convert transfer to JsonObject
        JSONObject jObj = new JSONObject()
                .put(FIELD_TRANSACTIONID, transfer.getId())
                .put(FIELD_DATE, date)
                .put(FIELD_FROM_ACCOUNT, transfer.getFromAccount())
                .put(FIELD_TO_ACCOUNT, transfer.getToAccount())
                .put(FIELD_AMOUNT, transfer.getAmount());
        return redisRepo.logTransaction(jObj);
    }

}
