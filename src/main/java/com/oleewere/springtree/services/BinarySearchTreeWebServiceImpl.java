package com.oleewere.springtree.services;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.jsonsupport.WrappedList;

@Service
@Slf4j
public class BinarySearchTreeWebServiceImpl implements BinarySearchTreeService {
	private RestTemplate restTemplate;

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
		restTemplate = new RestTemplate();
		final List<HttpMessageConverter<?>> messageConverters = setUpConverter();
		restTemplate.setMessageConverters(messageConverters);
        final WrappedList wl = fillWrappedListWithIntegers(numbers);
		final String resp = restTemplate.postForObject("http://" + host + ":"
				+ port + "/" + context + "/" + service, wl, String.class);
		log.info("visszatérés: {}", resp);
		
		return convertFromJsonToNode(resp);
	}
	
	@SuppressWarnings("unchecked")
	private Node<Integer> fromJsonToNode(String json) throws JsonParseException,
			JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, Node.class);
	}
	
	private Node<Integer> convertFromJsonToNode(final String resp) {
		try {
			final Node<Integer> veg = fromJsonToNode(resp);
			return veg;
		} catch (Exception e) {
			log.error("Json beolvasási hiba: {}", e.getStackTrace());
			return null;
		}
	}

	private List<HttpMessageConverter<?>> setUpConverter() {
		final List<HttpMessageConverter<?>> messageConverters = restTemplate
				.getMessageConverters();
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		return messageConverters;
	}

	private WrappedList fillWrappedListWithIntegers(List<Integer> numbers) {
		WrappedList wl = new WrappedList();
		for (Integer n : numbers) {
			wl.add(n);
		}
		return wl;
	}


}
