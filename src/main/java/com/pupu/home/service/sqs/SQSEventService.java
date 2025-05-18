package com.pupu.home.service.sqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pupu.home.dto.Member;
import com.pupu.home.utils.MemberDataLoadConstants;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry;


@Service
public class SQSEventService {
	private static Logger log = LoggerFactory.getLogger(SQSEventService.class); 
	
	private SqsClient sqsClient = SqsClient.builder()
			.region(Region.of(System.getenv(MemberDataLoadConstants.UM_AWS_REGION)))
			.build();
	
	public void sendMessageToSqs(List<Member> memberList) {
		log.info("SQSEventService.sendMessageToSqs() :: STARTED");
		
		if(CollectionUtils.isNotEmpty(memberList)) {
			
			Map<String, MessageAttributeValue> sqsMessageAttributes = new HashMap<String, MessageAttributeValue>();
			
			MessageAttributeValue attributeValue = MessageAttributeValue.builder()
					.dataType("String")
					.stringValue("stringValue")
					.build();
			
			sqsMessageAttributes.put("ATTRIBUTE1", attributeValue);
			
			SendMessageBatchRequestEntry batchEntry = SendMessageBatchRequestEntry.builder()
					.id(UUID.randomUUID().toString())
					.messageBody("JSON_BODY")
					.messageGroupId("groupId")
					.messageDeduplicationId(UUID.randomUUID().toString())
					.messageAttributes(sqsMessageAttributes)
					.build();
			
			List<SendMessageBatchRequestEntry> sqsBatchEntries = new ArrayList<SendMessageBatchRequestEntry>();
			sqsBatchEntries.add(batchEntry);
			
			SendMessageBatchRequest batchRequest = SendMessageBatchRequest.builder()
					.queueUrl(System.getenv(MemberDataLoadConstants.SQS_URL))
					.entries(sqsBatchEntries)
					.build();
			
			log.info("SQSEventService.sendMessageToSqs() :: sending SQS BATCH");
			sqsClient.sendMessageBatch(batchRequest);
		}
		
	}
}
