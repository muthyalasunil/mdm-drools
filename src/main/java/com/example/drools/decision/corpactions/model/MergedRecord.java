package com.example.drools.decision.corpactions.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
class MergedRecord {
    String source;
    Date sourceTimestamp;
    Date timestamp;

    public MergedRecord(CorpAction corpAction){
        this.source = corpAction.toString();
        this.sourceTimestamp = corpAction.get_load_date();
        this.timestamp = new Date();
    }
}

