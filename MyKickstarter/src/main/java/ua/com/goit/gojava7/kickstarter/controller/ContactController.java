package ua.com.goit.gojava7.kickstarter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.com.goit.gojava7.kickstarter.database.contract.CategoryDao;
import ua.com.goit.gojava7.kickstarter.domains.Category;

@Controller
public class ContactController {
	private static final Logger log = LoggerFactory.getLogger(ContactController.class);
	private static final String EMAIL = "smiranton2013@gmail.com";
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private CategoryDao categoryDao;

	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public ModelAndView showContacts() {
		ModelAndView modelAndView = new ModelAndView("contacts");

		List<Category> categories = categoryDao.getAll();
		log.info("All categories: {}", categories);

		modelAndView.addObject("categories", categories);
		return modelAndView;
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public String addPayment(@RequestParam(name = "name") String name,
			@RequestParam(name = "emailAddress") String emailAddress,
			@RequestParam(name = "phoneNumber") String phoneNumber,
			@RequestParam(name = "messageText") String messageText) {

		log.info("Username: {}, email: {}, phoneNumber: {}, messageText: {}", name, emailAddress, phoneNumber,
				messageText);

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(EMAIL);
		email.setTo(EMAIL);
		email.setText(new StringBuilder()
				.append("name: ").append(name).append("\n")
				.append("emailadress: ").append(emailAddress).append("\n")
				.append("phone: ").append(phoneNumber).append("\n")
				.append("message: ").append(messageText).toString());

		mailSender.send(email);
		log.info("email has sent");

		return "redirect:./contacts";
	}
}
