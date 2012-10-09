package com.epam.springtree.ws;

import java.util.List;

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
public class BinarySearchTreeWebService {

	@RequestMapping(value = "/tree.ws", method = RequestMethod.GET, consumes = "application/json" )
	public @ResponseBody
	String restService(@RequestBody final WrappedList list) {

		System.out.println(list);
		List<Integer> datas = list;
		
		final NodeComparator<Integer> comp = new NodeComparator<Integer>();

		String result = NodeToJson(Node.buildBinarySearchTree(datas, comp));
        System.out.println("küldés: "+result);
        
        //HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.TEXT_PLAIN);
		//return new ResponseEntity<String>(result, headers, HttpStatus.OK);
        return result;
	}

	public String NodeToJson(Node<Integer> node) {
		return new JSONSerializer().exclude("*.class").serialize(node);
	}
	
}
