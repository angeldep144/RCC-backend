package com.revature.project3backend.exceptions;

public class UnauthorizedException extends Exception {
	public UnauthorizedException () {
		super ("Error! Unauthorized");
	}
}
