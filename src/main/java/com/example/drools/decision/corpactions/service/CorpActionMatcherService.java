package com.example.drools.decision.corpactions.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import org.kie.api.runtime.KieContainer;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.drools.decision.corpactions.model.*;

@Service
public class CorpActionMatcherService {
    @Autowired
    private KieContainer kieContainer;

    @Singleton
    private List<CorpAction> _goldenCopy = new ArrayList<CorpAction>();

    public Map<String, Map<Boolean, Long>> getCount(String srcName) {

        return _goldenCopy.stream().collect(Collectors.groupingBy(CorpAction::getVCA_SOURCE,
                Collectors.groupingBy(CorpAction::isActive, Collectors.counting())));

    }

    public List<CorpAction> getRecords(String[] key) {
        if (key != null && key.length > 0) {
            switch (key.length) {
                case 1:
                    return _goldenCopy.stream()
                            .filter(corpAction -> corpAction.getVCA_TICKER().equals(key[0]))
                            .collect(Collectors.toList());

                case 2:
                    return _goldenCopy.stream()
                        .filter(corpAction -> corpAction.getVCA_TICKER().equals(key[0])
                                && corpAction.getVCA_EX_DATE().contains(key[1]))
                            .collect(Collectors.toList());
                case 3:
                    return _goldenCopy.stream()
                            .filter(corpAction -> corpAction.getVCA_TICKER().equals(key[0])
                                    && corpAction.getVCA_EX_DATE().contains(key[1])
                                    && corpAction.getVCA_SOURCE().equals(key[2]))
                            .collect(Collectors.toList());
                case 4:
                    return _goldenCopy.stream()
                            .filter(corpAction -> corpAction.getVCA_TICKER().equals(key[0])
                                    && corpAction.getVCA_EX_DATE().contains(key[1])
                                    && corpAction.getVCA_SOURCE().equals(key[2])
                                    && corpAction.getVCA_CA_TYPE().equals(key[3]))
                            .collect(Collectors.toList());

                default:
                    return null;
            }
        } else {
            return _goldenCopy;
        }
    }


    public List<CorpAction> parseCorpActions(String filePath) throws IOException {
        List<CorpAction> myCorpActions = new ArrayList<>();
        if (myCorpActions.size() == 0) {
            try (Reader reader = new FileReader(filePath)) {
                CsvToBean<CorpAction> csvToBean = new CsvToBeanBuilder<CorpAction>(reader)
                        .withType(CorpAction.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                myCorpActions = csvToBean.parse();

            }
            System.out.println("Corp Actons :" + myCorpActions.size());
        }
        return myCorpActions.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getMatches(String file1) {

        List<CorpAction> loadedData;
        List<String> matches = new ArrayList<>();
        int processedCount = 0;

        try {
            if (_goldenCopy.size() == 0) {
                _goldenCopy.addAll(filterCorpActions(parseCorpActions(file1)));
                return Arrays.asList(new String[]{"created golden copy list"});
            }

            loadedData = filterCorpActions(parseCorpActions(file1));
            System.out.println("total objects:" + loadedData.size());

            Map<String, List<CorpAction>> gmap = _goldenCopy.stream().collect(
                    Collectors.groupingBy(CorpAction::hashCodeString, Collectors.toList()));
            Map<String, List<CorpAction>> lmap = loadedData.stream().collect(
                    Collectors.groupingBy(CorpAction::hashCodeString, Collectors.toList()));

            List threadList = new ArrayList();
            for (Map.Entry<String, List<CorpAction>> entry : lmap.entrySet()) {
                if (gmap.containsKey(entry.getKey())){
                    RulesMatcher newRulesMatcher =  RulesMatcher.newRulesMatcher(kieContainer, entry.getKey(),
                            gmap.get(entry.getKey()), entry.getValue());
                    Thread trunner = new Thread(newRulesMatcher);
                    trunner.start();
                    threadList.add(trunner);
                }
            }
            for (Object tr : threadList) {
                ((Thread) tr).join();
            }

            System.out.println("******* golden copy size:" + _goldenCopy.size());

            _goldenCopy.removeAll(_goldenCopy.stream()
                    .filter(CorpAction::isDuplicate).collect(Collectors.toList()));
            _goldenCopy.addAll(loadedData.stream()
                    .filter(corpAction -> !corpAction.isDuplicate()).collect(Collectors.toList()));

            System.out.println(getCount(""));
            //writeGoldenRecords(file1, swiftActions, swiftData2);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return matches;
    }

    private List filterCorpActions(List<CorpAction> fullist) {

        for(CorpAction obj :fullist){
            if (obj.getVCA_EX_DATE().trim().length()>0)
                obj.setVCA_EX_DATE(obj.getVCA_EX_DATE().split(" ")[0].replace("/","-"));
        }

        return fullist.stream()
                .filter(corpAction -> corpAction.getVCA_EX_DATE().trim().length() > 0
                        || corpAction.getVCA_PAYMENT_DATE().trim().length() > 0
                        || corpAction.getVCA_RECORD_DATE().trim().length() > 0
                        || corpAction.getVCA_EFFECTIVE_DATE().trim().length() > 0)
                .collect(Collectors.toList());
    }

    private void writeGoldenRecords(String file1, List<CorpAction> mergedList, List<CorpAction> srcList) {

        System.out.println("total objects srcList:" + srcList.size());
        System.out.println("total objects mergedList :" + mergedList.size());

        mergedList.addAll(srcList.stream()
                .filter(CorpAction::isActive).collect(Collectors.toList()));

        try (FileWriter writer = new FileWriter(file1.replace(".csv", "_M.csv"))) {
            StatefulBeanToCsv<CorpAction> beanToCsv = new StatefulBeanToCsvBuilder<CorpAction>(writer)
                    .build();
            beanToCsv.write(mergedList);
            System.out.println("total objects golden record:" + mergedList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String[] exdate = new String[]{"3/28/2025 12:00:00 AM","12-02-2024  00:00:00"};
        for (String dtstr : exdate) {
            dtstr = dtstr.split(" ")[0].replace("/", "-");
            System.out.println(dtstr);
        }
    }
}