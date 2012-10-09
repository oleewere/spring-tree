package com.epam.springtree.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epam.springtree.domain.Node;
import com.epam.springtree.jsonsupport.WrappedList;

import flexjson.JSONDeserializer;

@Service
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
		
    	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    	messageConverters.add(new MappingJacksonHttpMessageConverter());
    	messageConverters.add(new StringHttpMessageConverter());
    	messageConverters.add(new FormHttpMessageConverter());
    	restTemplate.setMessageConverters(messageConverters);
    	
    	WrappedList wl = new WrappedList();
    	for (Integer n : numbers) {
			wl.add(n);
		}
    	
		System.out.println(wl);
		//HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_JSON);
		//HttpEntity<String> entity = new HttpEntity<String>(wl,headers);		
		//restTemplate.put("http://"+host+":"+port+"/"+context+"/"+service, entity);

	    String resp = restTemplate.getForObject("http://"+host+":"+port+
			"/"+context+"/"+service, String.class, wl);
		
		Node<Integer> veg = fromJsonArrayToNode(resp);
		
		System.out.println("visszatérés: "+veg);
		return veg;
	}
	
	public Node<Integer> fromJsonArrayToNode(String json) {
        return new JSONDeserializer<Node<Integer>>().use(null, Node.class).deserialize(json);
    }

}
