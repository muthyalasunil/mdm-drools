package com.example.drools.decision.corpactions.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;

import lombok.Setter;
import org.drools.core.util.DateUtils;

@Getter
@Setter
public class CorpAction {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorpAction that = (CorpAction) o;
        return Objects.equals(VCA_TICKER, that.VCA_TICKER) && Objects.equals(VCA_SOURCE, that.VCA_SOURCE) && Objects.equals(VCA_MSG_FUNC, that.VCA_MSG_FUNC) && Objects.equals(VCA_CA_TYPE, that.VCA_CA_TYPE) && Objects.equals(VCA_EX_DATE, that.VCA_EX_DATE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(VCA_TICKER, VCA_SOURCE, VCA_MSG_FUNC, VCA_CA_TYPE, VCA_EX_DATE);
    }
    public String hashCodeString() {
        return ""+ Objects.hash(VCA_EX_DATE);
    }

    private Map _updates = new HashMap();
    private List _merges = new ArrayList();
    private List _matches = new ArrayList();
    private Date _load_date = new Date();

    public void merge(CorpAction obj, String srcOrder, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        int myOrder = srcOrder.indexOf(this.getVCA_SOURCE());
        int objOrder = srcOrder.indexOf(obj.getVCA_SOURCE());
        //System.out.println(this.getVCA_SOURCE()+" merge field...."+ myOrder +" ... "+ objOrder+" "+obj.getVCA_SOURCE());

        Field nameField = this.getClass().getDeclaredField(fieldName);
        nameField.setAccessible(true);
        if (myOrder > objOrder) {
            _updates.put(fieldName, new MergedRecord(obj));
            nameField.set(this, nameField.get(obj));
        }
    }

    public void merge(CorpAction obj, String srcOrder) {
        int myOrder = srcOrder.indexOf(this.getVCA_SOURCE());
        int objOrder = srcOrder.indexOf(obj.getVCA_SOURCE());

        if (myOrder > objOrder) {
            //mergeRecords(this, obj);
            //System.out.println(this.toString() + " merged into :" + obj.toString());
            obj.isActive = true;
            this.isActive = false;
            obj._merges.add(new MergedRecord(this));
        } else if (myOrder < objOrder) {
            //mergeRecords(obj, this);
            //System.out.println(obj.toString() + " merged into :" + this.toString());
            this.isActive = true;
            obj.isActive = false;
            this._merges.add(new MergedRecord(obj));
        } else {
            //System.out.println(this.toString() + " is same :" + obj.toString());
            this.isDuplicate = true;
            obj.isActive = this.isActive;
            obj._merges.add(new MergedRecord(this));

        }

    }

    private void mergeRecords(CorpAction src, CorpAction target) {

        target.VCA_T3_STOCK_KEY = src.VCA_T3_STOCK_KEY;
        target.VCA_RIC = src.VCA_RIC;
        //target.VCA_TICKER = src.VCA_TICKER;
        target.VCA_T3_INDEX = src.VCA_T3_INDEX;
        target.VCA_T3_ALL_ACTIVE = src.VCA_T3_ALL_ACTIVE;
        target.VCA_T3_HPI = src.VCA_T3_HPI;
        target.VCA_I6_INDEX = src.VCA_I6_INDEX;
        target.VCA_REGION = src.VCA_REGION;
        //target.VCA_SOURCE = src.VCA_SOURCE;
        target.VCA_CORP = src.VCA_CORP;
        //target.VCA_MSG_FUNC = src.VCA_MSG_FUNC;
        target.VCA_ASSET_TYPE = src.VCA_ASSET_TYPE;
        target.VCA_SECURITY_TYPE = src.VCA_SECURITY_TYPE;
        target.VCA_CA_CLASSIFICATION = src.VCA_CA_CLASSIFICATION;
        target.VCA_CA_CATEGORY = src.VCA_CA_CATEGORY;
        //target.VCA_CA_TYPE = src.VCA_CA_TYPE;
        target.VCA_SOURCE_CA_TYPE = src.VCA_SOURCE_CA_TYPE;
        target.VCA_STATUS = src.VCA_STATUS;
        target.VCA_SECURITY_NAME = src.VCA_SECURITY_NAME;
        target.VCA_ISIN = src.VCA_ISIN;
        target.VCA_LOCAL_EXCHANGE_TICKER = src.VCA_LOCAL_EXCHANGE_TICKER;
        target.VCA_SEDOL = src.VCA_SEDOL;
        target.VCA_PLIS = src.VCA_PLIS;
        target.VCA_CFI_CODE = src.VCA_CFI_CODE;
        target.VCA_COUNTRY = src.VCA_COUNTRY;
        target.VCA_CRNCY = src.VCA_CRNCY;
        target.VCA_ANNOUNCEMENT_DATE = src.VCA_ANNOUNCEMENT_DATE;
        //target.VCA_EX_DATE = src.VCA_EX_DATE;
        target.VCA_EFFECTIVE_DATE = src.VCA_EFFECTIVE_DATE;
        target.VCA_RECORD_DATE = src.VCA_RECORD_DATE;
        target.VCA_PAYMENT_DATE = src.VCA_PAYMENT_DATE;
        target.VCA_LOI_SEC = src.VCA_LOI_SEC;
        target.VCA_EDM_SEC_ID = src.VCA_EDM_SEC_ID;
        target.VCA_InActive = src.VCA_InActive;
        target.SCA_SOURCE = src.SCA_SOURCE;
        target.SCA_MESSAGE_ID = src.SCA_MESSAGE_ID;
        target.SCA_MSG_TYPE = src.SCA_MSG_TYPE;
        target.SCA_CORP = src.SCA_CORP;
        target.SCA_MSG_FUNC = src.SCA_MSG_FUNC;
        target.SCA_CA_CLASSIFICATION = src.SCA_CA_CLASSIFICATION;
        target.SCA_CA_CATEGORY = src.SCA_CA_CATEGORY;
        target.SCA_CA_TYPE = src.SCA_CA_TYPE;
        target.SCA_SOURCE_CA_TYPE = src.SCA_SOURCE_CA_TYPE;
        target.SCA_SEME = src.SCA_SEME;
        target.SCA_COAF = src.SCA_COAF;
        target.SCA_STATUS = src.SCA_STATUS;
        target.SCA_SECURITY_NAME = src.SCA_SECURITY_NAME;
        target.SCA_MSG_PREP_DATE = src.SCA_MSG_PREP_DATE;
        target.SCA_ISIN = src.SCA_ISIN;
        target.SCA_LOCAL_EXCHANGE_TICKER = src.SCA_LOCAL_EXCHANGE_TICKER;
        target.SCA_SEDOL = src.SCA_SEDOL;
        target.SCA_PLIS = src.SCA_PLIS;
        target.SCA_CFI_CODE = src.SCA_CFI_CODE;
        target.SCA_COUNTRY = src.SCA_COUNTRY;
        target.SCA_CRNCY = src.SCA_CRNCY;
        target.SCA_EDM_SEC_ID = src.SCA_EDM_SEC_ID;
        target.SCA_ASSET_TYPE = src.SCA_ASSET_TYPE;
        target.SCA_SECURITY_TYPE = src.SCA_SECURITY_TYPE;
        target.SCA_EDA_RULE_ID = src.SCA_EDA_RULE_ID;
        target.SCA_LE_RULE_ID = src.SCA_LE_RULE_ID;
        target.SCA_EDM_CA_ID = src.SCA_EDM_CA_ID;
        target.SCA_ACCEPT_CANC = src.SCA_ACCEPT_CANC;
        target.SCA_AUTO_CANC = src.SCA_AUTO_CANC;
        target.SCA_ACCEPT_WITH = src.SCA_ACCEPT_WITH;
        target.SCA_InActive = src.SCA_InActive;
        target.CADIS_SYSTEM_INSERTED = src.CADIS_SYSTEM_INSERTED;
        target.SCA_CADIS_SYSTEM_UPDATED = src.SCA_CADIS_SYSTEM_UPDATED;
        target.SCA_CADIS_SYSTEM_CHANGEDBY = src.SCA_CADIS_SYSTEM_CHANGEDBY;
        target.SCA_CADIS_SYSTEM_TIMESTAMP = src.SCA_CADIS_SYSTEM_TIMESTAMP;

    }

    private boolean isActive = true;
    private boolean isDuplicate = false;

    @Override
    public String toString() {
        return "(" + VCA_SOURCE + ") " + VCA_TICKER + " : " + VCA_CA_TYPE + " : " + VCA_EX_DATE + " : " + VCA_MSG_FUNC + ":" + isActive;
    }

    @CsvBindByName(column = "VCA_T3_STOCK_KEY")
    private String VCA_T3_STOCK_KEY;
    @CsvBindByName(column = "VCA_RIC")
    private String VCA_RIC;
    @CsvBindByName(column = "VCA_TICKER")
    private String VCA_TICKER;
    @CsvBindByName(column = "VCA_T3_INDEX")
    private String VCA_T3_INDEX;
    @CsvBindByName(column = "VCA_T3_ALL_ACTIVE")
    private String VCA_T3_ALL_ACTIVE;
    @CsvBindByName(column = "VCA_T3_HPI")
    private String VCA_T3_HPI;
    @CsvBindByName(column = "VCA_I6_INDEX")
    private String VCA_I6_INDEX;
    @CsvBindByName(column = "VCA_REGION")
    private String VCA_REGION;
    @CsvBindByName(column = "VCA_SOURCE")
    private String VCA_SOURCE;
    @CsvBindByName(column = "VCA_CORP")
    private String VCA_CORP;
    @CsvBindByName(column = "VCA_MSG_FUNC")
    private String VCA_MSG_FUNC;
    @CsvBindByName(column = "VCA_ASSET_TYPE")
    private String VCA_ASSET_TYPE;
    @CsvBindByName(column = "VCA_SECURITY_TYPE")
    private String VCA_SECURITY_TYPE;
    @CsvBindByName(column = "VCA_CA_CLASSIFICATION")
    private String VCA_CA_CLASSIFICATION;
    @CsvBindByName(column = "VCA_CA_CATEGORY")
    private String VCA_CA_CATEGORY;
    @CsvBindByName(column = "VCA_CA_TYPE")
    private String VCA_CA_TYPE;
    @CsvBindByName(column = "VCA_SOURCE_CA_TYPE")
    private String VCA_SOURCE_CA_TYPE;
    @CsvBindByName(column = "VCA_STATUS")
    private String VCA_STATUS;
    @CsvBindByName(column = "VCA_SECURITY_NAME")
    private String VCA_SECURITY_NAME;
    @CsvBindByName(column = "VCA_ISIN")
    private String VCA_ISIN;
    @CsvBindByName(column = "VCA_LOCAL_EXCHANGE_TICKER")
    private String VCA_LOCAL_EXCHANGE_TICKER;
    @CsvBindByName(column = "VCA_SEDOL")
    private String VCA_SEDOL;
    @CsvBindByName(column = "VCA_PLIS")
    private String VCA_PLIS;
    @CsvBindByName(column = "VCA_CFI_CODE")
    private String VCA_CFI_CODE;
    @CsvBindByName(column = "VCA_COUNTRY")
    private String VCA_COUNTRY;
    @CsvBindByName(column = "VCA_CRNCY")
    private String VCA_CRNCY;
    @CsvBindByName(column = "VCA_ANNOUNCEMENT_DATE")
    private String VCA_ANNOUNCEMENT_DATE;
    @CsvBindByName(column = "VCA_EX_DATE")
    private String VCA_EX_DATE;
    @CsvBindByName(column = "VCA_EFFECTIVE_DATE")
    private String VCA_EFFECTIVE_DATE;
    @CsvBindByName(column = "VCA_RECORD_DATE")
    private String VCA_RECORD_DATE;
    @CsvBindByName(column = "VCA_PAYMENT_DATE")
    private String VCA_PAYMENT_DATE;
    @CsvBindByName(column = "VCA_LOI_SEC")
    private String VCA_LOI_SEC;
    @CsvBindByName(column = "VCA_EDM_SEC_ID")
    private String VCA_EDM_SEC_ID;
    @CsvBindByName(column = "VCA_InActive")
    private String VCA_InActive;
    @CsvBindByName(column = "SCA_SOURCE")
    private String SCA_SOURCE;
    @CsvBindByName(column = "SCA_MESSAGE_ID")
    private String SCA_MESSAGE_ID;
    @CsvBindByName(column = "SCA_MSG_TYPE")
    private String SCA_MSG_TYPE;
    @CsvBindByName(column = "SCA_CORP")
    private String SCA_CORP;
    @CsvBindByName(column = "SCA_MSG_FUNC")
    private String SCA_MSG_FUNC;
    @CsvBindByName(column = "SCA_CA_CLASSIFICATION")
    private String SCA_CA_CLASSIFICATION;
    @CsvBindByName(column = "SCA_CA_CATEGORY")
    private String SCA_CA_CATEGORY;
    @CsvBindByName(column = "SCA_CA_TYPE")
    private String SCA_CA_TYPE;
    @CsvBindByName(column = "SCA_SOURCE_CA_TYPE")
    private String SCA_SOURCE_CA_TYPE;
    @CsvBindByName(column = "SCA_SEME")
    private String SCA_SEME;
    @CsvBindByName(column = "SCA_COAF")
    private String SCA_COAF;
    @CsvBindByName(column = "SCA_STATUS")
    private String SCA_STATUS;
    @CsvBindByName(column = "SCA_SECURITY_NAME")
    private String SCA_SECURITY_NAME;
    @CsvBindByName(column = "SCA_MSG_PREP_DATE")
    private String SCA_MSG_PREP_DATE;
    @CsvBindByName(column = "SCA_ISIN")
    private String SCA_ISIN;
    @CsvBindByName(column = "SCA_LOCAL_EXCHANGE_TICKER")
    private String SCA_LOCAL_EXCHANGE_TICKER;
    @CsvBindByName(column = "SCA_SEDOL")
    private String SCA_SEDOL;
    @CsvBindByName(column = "SCA_PLIS")
    private String SCA_PLIS;
    @CsvBindByName(column = "SCA_CFI_CODE")
    private String SCA_CFI_CODE;
    @CsvBindByName(column = "SCA_COUNTRY")
    private String SCA_COUNTRY;
    @CsvBindByName(column = "SCA_CRNCY")
    private String SCA_CRNCY;
    @CsvBindByName(column = "SCA_EDM_SEC_ID")
    private String SCA_EDM_SEC_ID;
    @CsvBindByName(column = "SCA_ASSET_TYPE")
    private String SCA_ASSET_TYPE;
    @CsvBindByName(column = "SCA_SECURITY_TYPE")
    private String SCA_SECURITY_TYPE;
    @CsvBindByName(column = "SCA_EDA_RULE_ID")
    private String SCA_EDA_RULE_ID;
    @CsvBindByName(column = "SCA_LE_RULE_ID")
    private String SCA_LE_RULE_ID;
    @CsvBindByName(column = "SCA_EDM_CA_ID")
    private String SCA_EDM_CA_ID;
    @CsvBindByName(column = "SCA_ACCEPT_CANC")
    private String SCA_ACCEPT_CANC;
    @CsvBindByName(column = "SCA_AUTO_CANC")
    private String SCA_AUTO_CANC;
    @CsvBindByName(column = "SCA_ACCEPT_WITH")
    private String SCA_ACCEPT_WITH;
    @CsvBindByName(column = "SCA_InActive")
    private String SCA_InActive;
    @CsvBindByName(column = "CADIS_SYSTEM_INSERTED")
    private String CADIS_SYSTEM_INSERTED;
    @CsvBindByName(column = "SCA_CADIS_SYSTEM_UPDATED")
    private String SCA_CADIS_SYSTEM_UPDATED;
    @CsvBindByName(column = "SCA_CADIS_SYSTEM_CHANGEDBY")
    private String SCA_CADIS_SYSTEM_CHANGEDBY;
    @CsvBindByName(column = "SCA_CADIS_SYSTEM_TIMESTAMP")
    private String SCA_CADIS_SYSTEM_TIMESTAMP;


}