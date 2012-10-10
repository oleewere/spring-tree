package com.oleewere.springtree.ws;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.domain.NodeComparator;
import com.oleewere.springtree.jsonsupport.WrappedList;

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
		/*WrapNode wn = new WrapNode();
		wn.setNode(Node.buildBinarySearchTree(datas, comp));
		return new ResponseEntity<WrapNode>(wn, HttpStatus.OK);*/
        return result;
	}

	private String NodeToJson(Node<Integer> node) {
		return new JSONSerializer().exclude("*.class").serialize(node);
	}
	
}