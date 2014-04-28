package org.cirrostratus.sequoia.servicelocator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.cirrostratus.sequoia.servicelocator.Constants.Strategy;
import org.springframework.beans.factory.FactoryBean;

import com.google.common.collect.Lists;

public class ServiceLocatorFactory implements FactoryBean<ServiceLocator> {

	private static List<String> LIST = Lists.newArrayList("1", "2", "3") ;

	private boolean disabled = false ;
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public ServiceLocator getObject() throws Exception {
		
		if (disabled) {

			ServiceLocator locator = mock(ServiceLocator.class) ;
			when(locator.blockingGetAllHostsByService(anyString())).thenReturn(LIST) ;
			when(locator.getAllEnvironments()).thenReturn(LIST) ;
			when(locator.getAllHostsByService(anyString())).thenReturn(LIST) ;
			when(locator.getAllHostsByService(anyString(), anyString())).thenReturn(LIST) ;
			when(locator.getAllHostsByService(anyString(), any(Strategy.class))).thenReturn(LIST) ;
			when(locator.getAllServices()).thenReturn(LIST) ;
			when(locator.getNextHostForService(anyString())).thenReturn("9") ;
			when(locator.getZonesByService(anyString())).thenReturn(Collections.EMPTY_MAP) ;
			when(locator.isInitialized()).thenReturn(true) ;
			return locator ;
			
		} else {
			return new ServiceLocator(false, false) ;
		}
	}
	
	public Class<?> getObjectType() {
		return ServiceLocator.class;
	}
	
	public boolean isSingleton() {
		return true;
	}
}
