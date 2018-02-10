package com.modular.build.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebResourcesController {

	/**
	 * Enable HTML 5 forwarding style.
	 * See: http://www.jhipster.tech/tips/010_tip_configuring_html_5_mode.html
	 */
	@RequestMapping(value = "/**/{id:[^\\.]*}")
	public String html5Forwarding() {
	    return "forward:/index.html";
	}

}
