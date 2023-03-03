package nus.iss.tfip.pafassessment.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafassessment.MyConstants;

@Repository
public class RedisRepository implements MyConstants {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public Boolean logTransaction(JSONObject jObj) {
        redisTemplate.opsForValue().set(jObj.getString(FIELD_TRANSACTIONID), jObj);
        // check for txn that was just put in
        return redisTemplate.hasKey(jObj.getString(FIELD_TRANSACTIONID));
    }
}
