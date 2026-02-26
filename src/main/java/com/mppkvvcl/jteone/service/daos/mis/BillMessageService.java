package com.mppkvvcl.jteone.service.daos.mis;

import com.mppkvvcl.jteone.utility.GlobalConstant;
import com.mppkvvcl.misdao.daos.BillMessageDAO;
import com.mppkvvcl.misdao.interfaces.BillMessageInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillMessageService {
    private static final Logger log = LoggerFactory.getLogger(BillMessageService.class);
    private static final Map<String, String> billMessageByName = new HashMap<>();
    private static final Map<String, List<BillMessageInterface>> billMessagesNameByHeader = new HashMap<>();

    @Autowired
    private BillMessageDAO billMessageDAO;

    public List<BillMessageInterface> getByHeader(final String header) {
        if (StringUtils.isEmpty(header)) return null;

        return billMessageDAO.getByHeader(header);
    }

    public BillMessageInterface getByNameAndLanguage(String name, String language) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(language)) return null;

        return billMessageDAO.getByNameAndLanguage(name, language);
    }

    public String getMessageOne(final String language, final Object... args) {
        if (language == null) return null;
        if (!billMessagesNameByHeader.containsKey(BillMessageInterface.HEADER_MESSAGE_ONE)) {
            final List<BillMessageInterface> billMessages = getByHeader(BillMessageInterface.HEADER_MESSAGE_ONE);
            if (billMessages != null && !billMessages.isEmpty()) {
                billMessagesNameByHeader.put(BillMessageInterface.HEADER_MESSAGE_ONE, billMessages);
                for (BillMessageInterface billMessage : billMessages) {
                    billMessageByName.put(billMessage.getName() + "_" + billMessage.getLanguage(), billMessage.getTemplate());
                }
            }
        }

        final String name = billMessagesNameByHeader.get(BillMessageInterface.HEADER_MESSAGE_ONE) != null ?
                billMessagesNameByHeader.get(BillMessageInterface.HEADER_MESSAGE_ONE).getFirst().getName() : null;
        return getMessage(name, language, args);
    }

    public String getMessageTwo(final String language, final Object... args) {
        if (language == null) return null;
        if (!billMessagesNameByHeader.containsKey(BillMessageInterface.HEADER_MESSAGE_TWO)) {
            final List<BillMessageInterface> billMessages = getByHeader(BillMessageInterface.HEADER_MESSAGE_TWO);
            if (billMessages != null && !billMessages.isEmpty()) {
                billMessagesNameByHeader.put(BillMessageInterface.HEADER_MESSAGE_TWO, billMessages);
                for (BillMessageInterface billMessage : billMessages) {
                    billMessageByName.put(billMessage.getName() + "_" + billMessage.getLanguage(), billMessage.getTemplate());
                }
            }
        }

        return getOTSMessage(language, args);
    }

    public String getMessageThree(final String language, final Object... args) {
        if (language == null) return null;
        if (!billMessagesNameByHeader.containsKey(BillMessageInterface.HEADER_MESSAGE_THREE)) {
            final List<BillMessageInterface> billMessages = getByHeader(BillMessageInterface.HEADER_MESSAGE_THREE);
            if (billMessages != null && !billMessages.isEmpty()) {
                billMessagesNameByHeader.put(BillMessageInterface.HEADER_MESSAGE_THREE, billMessages);
                for (BillMessageInterface billMessage : billMessages) {
                    billMessageByName.put(billMessage.getName() + "_" + billMessage.getLanguage(), billMessage.getTemplate());
                }
            }
        }

        final String name = billMessagesNameByHeader.get(BillMessageInterface.HEADER_MESSAGE_THREE) != null ?
                billMessagesNameByHeader.get(BillMessageInterface.HEADER_MESSAGE_THREE).getFirst().getName() : null;
        return getMessage(name, language, args);
    }

    private String getMessage(String messageName, final String language, Object[] args) {
        if (messageName == null) return null;
        String key = messageName + "_" + language;

        if (!billMessageByName.containsKey(key)) {
            final BillMessageInterface billMessage = getByNameAndLanguage(messageName, language);
            if (billMessage != null) {
                billMessageByName.put(key, billMessage.getTemplate());
            }
        }
        final String template = billMessageByName.get(key);
        if (StringUtils.isEmpty(template)) return null;
        return String.format(template, args);
    }

    private String getOTSMessage(final String language, final Object... args) {
        if (language == null || args == null || args.length == 0) return null;
        String message = null;
        if (GlobalConstant.OTS_OPTION_LUMPSUM.equals(args[0])) {
            message = getMessage(BillMessageInterface.MESSAGE_NAME_OTS_MATURED, language, args);
        } else if (GlobalConstant.OTS_OPTION_INSTALLMENT.equals(args[0])) {
            message = getMessage(BillMessageInterface.MESSAGE_NAME_OTS_INSTALLMENT, language, args);
        } else if (GlobalConstant.DEFAULTER.equals(args[0])) {
            message = getMessage(BillMessageInterface.MESSAGE_NAME_OTS_DEFAULTER, language, args);
        }
        return message;
    }
}
