package com.example.drools.decision.corpactions.controller;

import com.example.drools.decision.corpactions.model.CorpAction;
import com.example.drools.decision.corpactions.service.CorpActionMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.drools.decision.corpactions.model.OrderRequest;
import com.example.drools.decision.corpactions.service.OrderDiscountService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class CorpActionsController {

    @Autowired
    private OrderDiscountService orderDiscountService;

    @Autowired
    private CorpActionMatcherService matcherService;

    @PostMapping("/get-discount")
    public ResponseEntity<Set<OrderRequest>> getDiscount(@RequestBody Set<OrderRequest> orderRequests) {
        System.out.println("-------------------------------------------------------------");
        orderRequests = orderDiscountService.getMatches(orderRequests);
        return new ResponseEntity<>(orderRequests, HttpStatus.OK);
    }

    //C:/Users/sunil_kumar_muthyala/Downloads/SIX_CA_and_SWIFT_CA1.csv
    @PostMapping("/load-file")
    public ResponseEntity<List<String>> matchFile(@RequestBody String fileName) throws IOException {
        System.out.println("File name:"+fileName);
        List<String> matches = matcherService.getMatches(fileName);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    @PostMapping("/get-count")
    public ResponseEntity<Map<String, Map<Boolean, Long>>> getCount(@RequestBody String sourceName) {
        Map<String, Map<Boolean, Long>> retval = matcherService.getCount(sourceName);
        System.out.println(retval);
        return new ResponseEntity<>(retval, HttpStatus.OK);
    }
    @PostMapping("/get-entity")
    public ResponseEntity<List<CorpAction>> getEntity(@RequestBody String key) {
        List<CorpAction> list = new ArrayList<>();
        if (key.indexOf(":") > 0 )
            list = matcherService.getRecords(key.split(":"));
        else
            list = matcherService.getRecords(new String[]{key});

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
