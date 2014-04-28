package org.cirrostratus.samples.java;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cirrostratus.sequoia.circuitbreaker.CircuitBreaker;
import org.cirrostratus.sequoia.circuitbreaker.model.ErrorNo;
import org.cirrostratus.sequoia.circuitbreaker.model.ReturnValue;
import org.cirrostratus.sequoia.servicelocator.ServiceLocator;
import org.cirrostratus.sequoia.structuredlogging.DestinationCategory;
import org.cirrostratus.sequoia.structuredlogging.IStructuredLogger;
import org.cirrostratus.sequoia.structuredlogging.StructuredLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController{
	/**
	 * KAFKA logging
	 */
	private static final IStructuredLogger logger = StructuredLoggerFactory.getLogger(MainController.class,
								DestinationCategory.DEVELOPMENT);

	@Autowired
	private ServiceLocator serviceLocator ;	
	
	/*
	 * This method was added to return a view that shows all apps and instances by calling 
	 * Controller's methods.
	 * For now, index.jsp has the code to show all that.
	 * 
	 * @RequestMapping(value = "/showApps")
	public ModelAndView showApps(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
 
		logger.info("SeqStarter app: showApps");
		ModelAndView view = new ModelAndView();
		view.setViewName("apps");
		List<String> appsList = findAllApplications();
		view.addObject("appsList", appsList);
 
		return view;
	}*/
	
	/** 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws Exception
	 *  
	 * Calling this URL would return COULD_NOT_CONNECT 3 times and then returns CIRCUIT_BREAKER_TRIPPED
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/cb", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String callCircuitBreakerMethod(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
 
		logger.info("SeqStarter app: circuitBreakerTest");
		return methodTestMinVolume().getError().name();
	}
	
	/**
	 * This method demonstrates Circuit Breaker functionality with minimim volume threshold = 3.
	 * @return
	 */
	@CircuitBreaker(name="methodTestMinVolume", minimumVolumeThreshold=3)
	public ReturnValue methodTestMinVolume(){
		return new ReturnValue().withError(ErrorNo.COULD_NOT_CONNECT);
	}
	
	 
	/**
	 * Following methods reused from simple-java app
	 * 
	 */
	
	@RequestMapping(value = "/self-service-locatable", method = RequestMethod.GET)
	@ResponseBody public boolean isSelfServiceLocatable() {
		logger.info("Am I service locatable: " + serviceLocator.isSelfServiceLocatable()) ;
		return serviceLocator.isSelfServiceLocatable() ;
	}

	@RequestMapping(value = "/eureka/status", method = RequestMethod.GET)
	@ResponseBody public Map<String, String> getEuerkaStatus() {
		logger.info("Getting eureka status") ;
		return serviceLocator.getEurekaClient().getStatus() ;
	}
	
	@RequestMapping(value = "/apps/{name}/instances/next", method = RequestMethod.GET)
	@ResponseBody public String findNextInstance(@PathVariable String name) {
		logger.info("Locating next instance for app: App=" + name) ;
		return serviceLocator.getNextHostForService(name) ;
	}

	@RequestMapping(value = "/apps/{name}/instances", method = RequestMethod.GET)
	@ResponseBody public List<String> findAllInstances(@PathVariable String name, @RequestParam (value="blocking", required=false, defaultValue="false") boolean blocking) {
		logger.info("Locating all instances for app: App=" + name + " Blocking=" + blocking) ;
		if (blocking)
			return serviceLocator.blockingGetAllHostsByService(name) ;
		else
			return serviceLocator.getAllHostsByService(name) ;
	}

	@RequestMapping(value = "/apps", method = RequestMethod.GET)
	@ResponseBody public List<String> findAllApplications() {
		logger.info("Locating all apps") ;
		return serviceLocator.getAllServices() ;
	}
	
	@RequestMapping(value = "/debug", method = RequestMethod.GET)
	@ResponseBody public Map<String, Object> debug(HttpServletRequest req) throws Exception {
		HashMap<String, Object> outer = new HashMap<String, Object>() ;

		outer.put("date", new Date().toString()) ;
		outer.put("hostname", java.net.InetAddress.getLocalHost().getHostName()) ;
		outer.put("params", req.getParameterMap()) ;

		HashMap<String, Object> map = new HashMap<String, Object>() ;
		outer.put("headers", map) ;

		Enumeration<String> names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			map.put(name, req.getHeader(name));
		}
		return outer ;
	}
}
