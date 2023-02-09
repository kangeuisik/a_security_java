package com.jafa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthVO {
	private String memberId; 
	private MemberType memberType;
	private int ordinal;
	
	public AuthVO(String memberId, MemberType memberType) {
		this.memberId = memberId;
		this.memberType = memberType;
	}
}
