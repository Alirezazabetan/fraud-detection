package com.surepay.frauddetection.service.batch;

import com.surepay.frauddetection.model.TransactionCollection;
import com.surepay.frauddetection.service.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemProcessor implements ItemProcessor<TransactionDTO, TransactionDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionItemProcessor.class);
    
    @Autowired
    private TransactionCollection transactionCollection;

    /*There are two validations:

        all transaction references should be unique

        per record the end balance needs to be correct given the start balance and mutation*/

    @Override
    public TransactionDTO process( TransactionDTO transactionDTO) throws Exception {
        TransactionDTO trans = transactionCollection.getMap().get(transactionDTO.getReference());
        if(trans != null){
            if (trans.isStored()){
                return transactionDTO;
            }else {
                trans.setStored(true);
                transactionCollection.getMap().put(transactionDTO.getReference(), trans);
                transactionDTO.setTransactionDTO(trans);
                return transactionDTO;
            }

        }else if (hasNotValidBalance(transactionDTO)) {
            return transactionDTO;
        }
        transactionCollection.getMap().put(transactionDTO.getReference(), transactionDTO);
        LOGGER.info("Converting ( {} ) into ( {} )", transactionDTO, transactionDTO);
        return null;
    }

    private boolean hasNotValidBalance(TransactionDTO transactionDTO){
        return transactionDTO.getStartBalance().add(transactionDTO.getMutation()).compareTo(transactionDTO.getEndBalance()) != 0 ? true : false;
    }

}
