package com.pupu.home.writers;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.pupu.home.dto.Member;

public class PersonItemWriter implements ItemWriter<Member> {

	@Override
	public void write(List<? extends Member> items) throws Exception {
		System.out.println("\n\n\nWriting chunk of size: " + items.size() + "\n\n\n");

	}

}
