package com.pupu.home.processor;


import org.springframework.batch.item.ItemProcessor;

import com.pupu.home.dto.Member;

public class PersonItemProcessor implements ItemProcessor<Member, Member> {
	@Override
    public Member process(Member person) throws Exception {
		System.out.println("\n\n\nProcessing person: " + person.getFirstName() + " " + person.getLastName());
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();
        final Member transformedPerson = new Member();
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);
        return transformedPerson;
    }
}
