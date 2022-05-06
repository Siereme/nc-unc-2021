package com.app.service.mail.job;

import com.app.model.emailInfo.NewEmail;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class RecordFieldSetMapper implements FieldSetMapper<NewEmail> {

    public NewEmail mapFieldSet(FieldSet fieldSet) throws BindException {
        //        String type =
        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyy");
        //        NewEmail mail = new NewEmail();
        //
        //        transaction.setUsername(fieldSet.readString("username"));
        //        transaction.setUserId(fieldSet.readInt(1));
        //        transaction.setAmount(fieldSet.readDouble(3));
        //        String dateString = fieldSet.readString(2);
        //        transaction.setTransactionDate(LocalDate.parse(dateString, formatter).atStartOfDay());
        //        return transaction;
        return null;
    }
}