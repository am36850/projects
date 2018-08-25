package com.project.sharemarket.mapper;

import com.project.sharemarket.domains.NsePerDayData;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.math.BigDecimal;

@Component
public class NsePerDayDataMapper implements FieldSetMapper<NsePerDayData> {

    @Override
    public NsePerDayData mapFieldSet(FieldSet fieldSet) throws BindException {
        NsePerDayData nsePerDayData = new NsePerDayData();
        nsePerDayData.setTicker(fieldSet.readString(0));
        nsePerDayData.setDate(fieldSet.readDate(1,"yyyyMMdd"));
        nsePerDayData.setOpen(!fieldSet.readString(2).equalsIgnoreCase("-") ? fieldSet.readBigDecimal(2) : new BigDecimal(0));
        nsePerDayData.setHigh(!fieldSet.readString(3).equalsIgnoreCase("-") ? fieldSet.readBigDecimal(2) : new BigDecimal(0));
        nsePerDayData.setLow(!fieldSet.readString(4).equalsIgnoreCase("-") ? fieldSet.readBigDecimal(2) : new BigDecimal(0));
        nsePerDayData.setClose(!fieldSet.readString(5).equalsIgnoreCase("-") ? fieldSet.readBigDecimal(2) : new BigDecimal(0));
        nsePerDayData.setVolume(!fieldSet.readString(6).equalsIgnoreCase("-") ? fieldSet.readLong(2) : new Long(0));
        nsePerDayData.setOi(fieldSet.readInt(7));
        return nsePerDayData;
    }

}
