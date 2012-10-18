package com.oleewere.springtree.jsonsupport;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class WrappedList extends ArrayList<Integer> {
	private static final long serialVersionUID = -69845384061204254L;

	public WrappedList() {
		super();
	}
}
