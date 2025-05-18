package com.pupu.home.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;

import com.pupu.home.dto.Member;

public class PersonItemReader implements ItemReader<Member> {
	
	private ItemReader<Member> delegate;
	
	@Override
	public Member read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
		
		String variable1 = System.getenv("MY_VARIABLE_1");
		String variable2 = System.getenv("MY_VARIABLE_2");
		
		System.out.println("\n\n\nvariable1: " + variable1 + "; variable2: " + variable2 + "\n\n\n");
		
		String filePath = "data2.csv";
		
		List<Member> memberList = new ArrayList<>();
		
		ClassLoader classLoader = PersonItemReader.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(filePath);
             InputStreamReader streamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(streamReader)) {

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
