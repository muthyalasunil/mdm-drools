package com.example.drools.decision.corpactions.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.drools.decision.corpactions.model.MatchingScore;
import com.example.drools.decision.corpactions.model.OrderRequest;

import java.util.LinkedHashSet;
import java.util.Set;


@Service
public class OrderDiscountService {
 
    @Autowired
    private KieContainer kieContainer;

    static Set<OrderRequest> oldOrders = new LinkedHashSet<OrderRequest>();
    public Set<OrderRequest> getMatches(Set<OrderRequest> orderRequests) {

        oldOrders.addAll(orderRequests);

        for (OrderRequest orderRequest : orderRequests) {

            for (OrderRequest olderRequest : oldOrders) {
                KieSession kieSession = kieContainer.newKieSession();
                kieSession.insert(orderRequest);

                MatchingScore matchingScore = new MatchingScore();
                kieSession.setGlobal("oldRequest", olderRequest);
                kieSession.setGlobal("matchingScore", matchingScore);
                kieSession.fireAllRules();
                if (matchingScore.getScore() > 0 ) {
                    olderRequest.setScore(matchingScore.getScore());
                    orderRequest.getMatches().add(olderRequest.toString());
                }
                kieSession.dispose();

            }

        }

        return orderRequests;
    }
}