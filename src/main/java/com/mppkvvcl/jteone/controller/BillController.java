package com.mppkvvcl.jteone.controller;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.service.impl.BillServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/bill")
public class BillController {

    private static final Logger log = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillServiceImpl billServiceImpl;

    @GetMapping(value = "download/id/{billId}/f/{fileFormat}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadBill(@PathVariable("billId") long billId, @PathVariable("fileFormat") String fileFormat) {
        log.info("API Call /bill/download/id/{}/f/{}", billId, fileFormat);
        final MessageDTO messageDTO = new MessageDTO();
        if (StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }
        if (!"PDF".equalsIgnoreCase(fileFormat) && !"HTML".equalsIgnoreCase(fileFormat)) {
            messageDTO.setMessage("Invalid file format: ".concat(fileFormat));
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }

        byte[] out = billServiceImpl.downloadBill(billId, fileFormat, messageDTO);

        if (out == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) {
                messageDTO.setMessage("Unexpected scenario occurred while preparing bill");
                log.error(messageDTO.getMessage());
            }
            return ResponseEntity.badRequest().body(messageDTO);
        }

        return ResponseEntity.ok().body(out);
    }

    @GetMapping(value = "download/c/{consumerNo}/f/{fileFormat}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadLatestBill(@PathVariable("consumerNo") String consumerNo, @PathVariable("fileFormat") String fileFormat) {
        log.info("API Call /bill/download/c/{}/f/{}", consumerNo, fileFormat);
        final MessageDTO messageDTO = new MessageDTO();
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }
        if (!"PDF".equalsIgnoreCase(fileFormat) && !"HTML".equalsIgnoreCase(fileFormat)) {
            messageDTO.setMessage("Invalid file format: ".concat(fileFormat));
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }
        if (consumerNo.startsWith("N")) consumerNo = consumerNo.substring(1);

        byte[] out = billServiceImpl.downloadLatestBill(consumerNo, fileFormat, messageDTO);

        if (out == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) {
                messageDTO.setMessage("Unexpected scenario occurred while preparing bill");
                log.error(messageDTO.getMessage());
            }
            return ResponseEntity.badRequest().body(messageDTO);
        }

        return ResponseEntity.ok().body(out);
    }

    @GetMapping(value = "download/c/{consumerNo}/bm/{billMonth}/f/{fileFormat}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> downloadBill(@PathVariable("consumerNo") String consumerNo, @PathVariable("billMonth") String billMonth, @PathVariable("fileFormat") String fileFormat) {
        log.info("API Call /bill/download/c/{}/bm/{}/f/{}", consumerNo, billMonth, fileFormat);

        final MessageDTO messageDTO = new MessageDTO();
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(billMonth) || StringUtils.isEmpty(fileFormat)) {
            messageDTO.setMessage("Input param(s) is null");
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }
        if (!"PDF".equalsIgnoreCase(fileFormat) && !"HTML".equalsIgnoreCase(fileFormat)) {
            messageDTO.setMessage("Invalid file format: ".concat(fileFormat));
            log.info(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }
        if (consumerNo.startsWith("N")) consumerNo = consumerNo.substring(1);

        byte[] out = billServiceImpl.downloadBill(consumerNo, billMonth, fileFormat, messageDTO);

        if (out == null) {
            if (StringUtils.isEmpty(messageDTO.getMessage())) {
                messageDTO.setMessage("Unexpected scenario occurred while preparing bill");
            }
            log.error(messageDTO.getMessage());
            return ResponseEntity.badRequest().body(messageDTO);
        }

        return ResponseEntity.ok().body(out);
    }
}
