package com.epam.springtree.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epam.springtree.domain.Node;
import com.epam.springtree.jsonsupport.WrappedList;

import flexjson.JSONDeserializer;

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
    	
		final List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    	messageConverters.add(new MappingJacksonHttpMessageConverter());
    	messageConverters.add(new FormHttpMessageConverter());
    	
    	restTemplate.setMessageConverters(messageConverters);	
    	
    	WrappedList wl = new WrappedList();
    	for (Integer n : numbers) {
			wl.add(n);
		}	
	    
		final String resp = restTemplate.postForObject("http://"+host+":"+port+
			"/"+context+"/"+service, wl, String.class);
	    
	    log.info("visszatérés: {}",resp);
        final Node<Integer> veg = fromJsonToNode(resp);
        log.info("Node-ok: {}",veg);
		
		return veg;
	}
	
	public Node<Integer> fromJsonToNode(String json) {
        return new JSONDeserializer<Node<Integer>>().use(null, Node.class).deserialize(json);
    }

}
