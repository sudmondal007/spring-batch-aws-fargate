package com.pupu.home.service.fileprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pupu.home.dto.Member;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
public class S3FileProcessorService {
	private static Logger log = LoggerFactory.getLogger(S3FileProcessorService.class);
	
	public List<Member> processS3File(ResponseBytes<GetObjectResponse> objectBytes) {
		log.info("S3FileProcessorService.processS3File() :: STARTED");
		List<Member> memberList = null;
		
		if(objectBytes == null) {
			log.info("S3FileProcessorService.processS3File() :: objectBytes is empty or null");
			return null;
		}
		
		memberList = new ArrayList<Member>();
		
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(objectBytes.asInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	String[] k = line.split(",");
				memberList.add(new Member(k[0], k[1]));
            }
        } catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return memberList;
	}
}
