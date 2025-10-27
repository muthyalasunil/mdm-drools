package com.example.drools.decision.corpactions.service;

import com.example.drools.decision.corpactions.model.CorpAction;
import com.example.drools.decision.corpactions.model.MatchingScore;
import lombok.Getter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class RulesMatcher implements  Runnable {

    private List<CorpAction> gsublist;
    private List<CorpAction> lsublist;

    private List<String> matches;
    private String name;

    @Autowired
    private KieContainer kieContainer;

    public static RulesMatcher newRulesMatcher(KieContainer kieContainer, String name, List<CorpAction> glist, List<CorpAction> llist) {
        RulesMatcher trunner = new RulesMatcher();
        trunner.name = name;
        trunner.gsublist = glist;
        trunner.lsublist = llist;
        trunner.matches = new ArrayList<>();
        trunner.kieContainer = kieContainer;
        return trunner;
    }


    @Override
    public void run() {

        int processedCount = 0;

        for (CorpAction loadedObject : lsublist) {
            processedCount++;

            for (CorpAction cachedObject : gsublist) {
                KieSession kieSession = kieContainer.newKieSession();
                kieSession.insert(loadedObject);

                MatchingScore matchingScore = new MatchingScore();
                kieSession.setGlobal("swiftAction", cachedObject);
                kieSession.setGlobal("matchingScore", matchingScore);
                String msgString = loadedObject.toString() + " - " + cachedObject.toString();
                kieSession.fireAllRules();
                if (matchingScore.getScore() > 0) {
                    cachedObject.get_matches().add(loadedObject.toString()+" * "+matchingScore.getScore());
                    matches.add(msgString + " * " + matchingScore.getScore());
                }

                kieSession.dispose();

            }
            if (processedCount % 1000 == 0) {
                System.out.println(name + " processed :" + processedCount);
            }
        }
        System.out.println(name + " completed."+ processedCount +":"+matches.size());

    }
}