package com.pupu.home.readers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.pupu.home.dto.Member;
import com.pupu.home.service.fileprocess.S3FileProcessorService;
import com.pupu.home.service.s3.S3ObjectService;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class PersonItemReader implements ItemReader<Member> {
	private static Logger log = LoggerFactory.getLogger(PersonItemReader.class);
	
	@Autowired
	private S3ObjectService s3ObjectService; 
	
	@Autowired
	private S3FileProcessorService s3FileProcessorService;
	
	private ItemReader<Member> delegate;
	
	@Override
	public Member read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		log.info("PersonItemReader.read() :: STARTED");
		if(delegate == null) {
			delegate = new IteratorItemReader<Member>(getMemberDetails());
		}
		return delegate.read();
	}
	
	private List<Member> getMemberDetails() {
		List<Member> memberList = readDataFromFile();
		
		return memberList;
	}
	
	private List<Member> readDataFromFile() {
		log.info("PersonItemReader.readDataFromFile() :: STARTED");
		String bucket_name = System.getenv("bucket_name");
		String object_key = System.getenv("object_key");
		
		log.info("\n\n\nbucket_name: " + bucket_name + "; object_key: " + object_key + "\n\n\n");
		
		log.info("PersonItemReader.readDataFromFile() :: reading S3 object");
		ResponseBytes<GetObjectResponse> objectBytes = s3ObjectService.processAndGetS3Object(bucket_name, object_key);
		
		log.info("PersonItemReader.readDataFromFile() :: reading S3 file");
		List<Member> memberList = s3FileProcessorService.processS3File(objectBytes);
		
		return memberList;
	}
}
