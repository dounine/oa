package net.yasion.demo.action;

import net.yasion.common.support.common.processor.CustomReturnPageProcessor;
import net.yasion.common.web.action.BaseAction;
import net.yasion.demo.model.Group;
import net.yasion.demo.model.IdCard;
import net.yasion.demo.model.Orders;
import net.yasion.demo.model.User;
import net.yasion.demo.service.IGroupService;
import net.yasion.demo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoUserAct extends BaseAction {

	private IUserService demoUserService;

	private IGroupService demoGroupService;

	public IUserService getDemoUserService() {
		return demoUserService;
	}

	@Autowired
	public void setDemoUserService(IUserService demoUserService) {
		this.demoUserService = demoUserService;
	}

	public IGroupService getDemoGroupService() {
		return demoGroupService;
	}

	@Autowired
	public void setDemoGroupService(IGroupService demoGroupService) {
		this.demoGroupService = demoGroupService;
	}

	@RequestMapping("/helloworld.do")
	public String load(ModelMap modelMap) {
		User user = new User();
		user.setName("helloworld");
		user = demoUserService.insert(user);
		modelMap.addAttribute("user", user);
		return new CustomReturnPageProcessor("helloworld", "demo").returnViewName();
	}

	@RequestMapping("/test.do")
	public String test(ModelMap modelMap) {
		IdCard idCard = new IdCard("serial", "info");
		// 声明一个用户
		User user = new User("mayj");
		// 声明二订单
		Orders order1 = new Orders("order1");
		Orders order2 = new Orders("order2");
		// 订单关联用户
		order1.setUser(user);
		order2.setUser(user);
		// 用户关联订单
		user.getOrdersSet().add(order1);
		user.getOrdersSet().add(order2);
		user.setIdCard(idCard);
		idCard.setUser(user);
		// save
		// order1.setUser(null);
		// order2.setUser(null);
		this.demoUserService.insert(user);// 我们仅仅保存了user!
		// user.getOrdersSet().clear();
		// this.userService.delete(user);
		modelMap.addAttribute("user", user);
		return new CustomReturnPageProcessor("helloworld", "demo").returnViewName();
	}

	@RequestMapping("/test2.do")
	public String test2(ModelMap modelMap) {

		// Group g = this.groupService.find("2c9f878d42c25d0e0142c25dd7b60004");
		Group g = new Group("g1");
		User user = new User("mayj");
		user.setGroup(g);
		IdCard idCard = new IdCard("serial", "info");
		user.setIdCard(idCard);
		idCard.setUser(user);
		g.getUserSet().add(user);
		Orders order1 = new Orders("order1");
		Orders order2 = new Orders("order2");
		// 订单关联用户
		order1.setUser(user);
		order2.setUser(user);
		// 用户关联订单
		user.getOrdersSet().add(order1);
		user.getOrdersSet().add(order2);
		user.setIdCard(idCard);
		idCard.setUser(user);
		// this.userService.insert(user);
		// Group g = this.groupService.find("402881e342c2cada0142c2cdbbb30004");
		// User u = this.userService.find("402881e342c2cada0142c2cdbbb30003");
		this.demoGroupService.insert(g);
		System.out.println("----------------------------------");
		// User u2 = this.userService.find("402881e342c2cada0142c2ce1aeb0005");
		// Group g = this.groupService.find("2c9f878d42c0ce560142c0d0ba580005");
		// System.out.println(u.getIdCard());
		// System.out.println(g.getUserSet());
		// modelMap.addAttribute("user", u);
		// modelMap.addAttribute("user", u2);
		return new CustomReturnPageProcessor("helloworld", "demo").returnViewName();
	}

	@RequestMapping("/test3.do")
	public String test3(ModelMap modelMap, Integer id, String name) {
		System.out.println(name + " " + id);
		return new CustomReturnPageProcessor("helloworld", "demo").returnViewName();
	}

	@RequestMapping("/test5.do")
	public String test5(ModelMap modelMap, Integer id, String name) {
		this.demoGroupService.findOfDTO(null);
		return new CustomReturnPageProcessor("helloworld", "demo").returnViewName();
	}
}