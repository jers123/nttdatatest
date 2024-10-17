package org.nttdatatest.test.annotation;

import static org.nttdatatest.test.utils.SystemConstants.LOCAL_ORIGIN_PATH;
import static org.nttdatatest.test.utils.SystemConstants.PUBLIC_ORIGIN_PATH;
import static org.nttdatatest.test.utils.SystemConstants.CLIENTE_PATH;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@CrossOrigin(origins = {LOCAL_ORIGIN_PATH
		,PUBLIC_ORIGIN_PATH
		//,"*"
	}
	,methods = {RequestMethod.GET}
)
@RequestMapping(path = CLIENTE_PATH)
public @interface RestApi {
	
}