package com.epam.springtree.ws;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.springtree.domain.Node;
import com.epam.springtree.domain.NodeComparator;
import com.epam.springtree.jsonsupport.WrappedList;

import flexjson.JSONSerializer;

@Controller
@Slf4j
public class BinarySearchTreeWebService {

	@RequestMapping(value = "/tree.ws", method = RequestMethod.POST, headers = "Accept=application/json,text/html,application/xhtml+xml,application/xml" )
	public @ResponseBody
	String restService(@RequestBody final WrappedList list) {
		log.info("{}",list);
		final List<Integer> datas = list;
		final NodeComparator<Integer> comp = new NodeComparator<Integer>();
		String result = NodeToJson(Node.buildBinarySearchTree(datas, comp));
        log.info("küldés: {}",result);
        return result;
	}

	private String NodeToJson(Node<Integer> node) {
		return new JSONSerializer().exclude("*.class").serialize(node);
	}
	
}
