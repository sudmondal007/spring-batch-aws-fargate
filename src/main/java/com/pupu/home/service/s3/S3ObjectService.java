package com.pupu.home.service.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pupu.home.utils.MemberDataLoadConstants;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
public class S3ObjectService {
	private static Logger log = LoggerFactory.getLogger(S3ObjectService.class);
	
	public ResponseBytes<GetObjectResponse> processAndGetS3Object(String bucket, String objectKey) {
		log.info("S3ObjectService.processAndGetS3Object() :: STARTED");
		ResponseBytes<GetObjectResponse> objectBytes = null;
		
		GetObjectRequest objectRequest = GetObjectRequest.builder().key(objectKey).bucket(bucket).build();
		
		try {
			log.info("S3ObjectService.processAndGetS3Object() :: creating S3 object");
			S3Client s3Client = S3Client.builder()
										.region(Region.of(System.getenv(MemberDataLoadConstants.AWS_REGION)))
										.build();
										
			objectBytes = s3Client.getObjectAsBytes(objectRequest);
		} catch(Exception ex) {
			log.error("S3ObjectService.processAndGetS3Object() :: error", ex);
		}
		
		return objectBytes;
	}
	
}
