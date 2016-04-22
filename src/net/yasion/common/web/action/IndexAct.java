package net.yasion.common.web.action;

import net.yasion.common.support.common.processor.FrameworkReturnPageProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexAct extends BaseAction {

	@RequestMapping("/main/index.do")
	public String index() {
		return new FrameworkReturnPageProcessor("index").returnViewName();
	}

	@RequestMapping("/main/left.do")
	public String left() {
		return new FrameworkReturnPageProcessor("left").returnViewName();
	}

	@RequestMapping("/main/top.do")
	public String top() {
		return new FrameworkReturnPageProcessor("top").returnViewName();
	}

	@RequestMapping("/main/main.do")
	public String main() {
		return new FrameworkReturnPageProcessor("main").returnViewName();
	}
}