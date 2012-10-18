package com.oleewere.springtree.ws;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oleewere.springtree.jsonsupport.JSONHelper;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.nodeservices.NodeComparator;
import com.oleewere.springtree.nodeservices.NodeHelper;


@Controller
@Slf4j
public class BinarySearchTreeWebService {
    @Autowired
    private NodeComparator<Integer> comp;
    @Autowired
    private NodeHelper nodeHelper;
    @Autowired
    private JSONHelper jsonHelper;
	
	@RequestMapping(value = "/tree.ws", method = RequestMethod.POST, headers = "Accept=application/json,text/html,application/xhtml+xml,application/xml" )
	public @ResponseBody
	String restService(@RequestBody final WrappedList list) {
		log.info("{}",list);
		final List<Integer> datas = list;
		final String result = jsonHelper.NodeToJson(nodeHelper.buildBinarySearchTree(datas, comp));
        log.info("küldés: {}",result);
        return result;
	}
	
}
