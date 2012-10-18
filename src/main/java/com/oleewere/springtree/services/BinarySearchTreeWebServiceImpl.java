package com.oleewere.springtree.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.jsonsupport.JSONHelper;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.wssupport.WsSupport;

@Service
@Slf4j
public class BinarySearchTreeWebServiceImpl implements BinarySearchTreeService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JSONHelper jsonHelper;
	@Autowired
	private WrappedList wl;
	@Autowired
	private WsSupport wsSupport;
	
	@Value("${ws.host}")
	private String host;
	@Value("${ws.context}")
	private String context;
	@Value("${ws.port}")
	private String port;
	@Value("${ws.service}")
	private String service;

	@Override
	public Node<Integer> getTreeFromList(List<Integer> numbers) {
		restTemplate = wsSupport.setUpConverter(restTemplate);
		wl = wsSupport.fillWrappedListWithIntegers(wl, numbers);
		final String resp = restTemplate.postForObject("http://" + host + ":"
				+ port + "/" + context + "/" + service, wl, String.class);
		log.info("visszatérés: {}", resp);
		return jsonHelper.fromJsonToNode(resp);
	}


}
