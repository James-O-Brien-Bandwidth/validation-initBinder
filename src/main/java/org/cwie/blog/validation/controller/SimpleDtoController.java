package org.cwie.blog.validation.controller;

import javax.validation.Valid;

import org.cwie.blog.validation.exceptions.NotFoundException;
import org.cwie.blog.validation.model.SimpleDto;
import org.cwie.blog.validation.model.SimpleDtoIdParam;
import org.cwie.blog.validation.service.SimpleDtoService;
import org.cwie.blog.validation.validators.SpringSimpleDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simpledto")
public class SimpleDtoController {

	//Guide: https://phoenixnap.com/kb/spring-boot-validation-for-rest-services

	//InitBinder hides the validation logic
	//1 Code will not move into controller logic until the springSimpleDtoValidator binder is passed
	//Example in trunk:
		//@InitBinder("regulationAddressDidIdLinkSearch")

	//Both solutions are great for business logic validations

	@Autowired
	private SimpleDtoService simpleDtoService;

	@Autowired
	private SpringSimpleDtoValidator springSimpleDtoValidator;
//So we could add as many validators as we require
	@InitBinder("simpleDto")
	public void initMerchantOnlyBinder(WebDataBinder binder) {
		binder.addValidators(springSimpleDtoValidator);
	}

	@RequestMapping(path = "/{simpleDtoId}", method = RequestMethod.GET, produces = "application/json")
	public SimpleDto getSimpleDto(
			@Valid SimpleDtoIdParam simpleDtoIdParam) {

		SimpleDto result = simpleDtoService.findById(simpleDtoIdParam.getSimpleDtoId());

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}

	@RequestMapping(path = "", method = RequestMethod.POST, produces = "application/json")
	public SimpleDto createSimpleDto(
			@Valid @RequestBody SimpleDto simpleDto) {

		SimpleDto result = simpleDtoService.save(simpleDto);


		return result; 
	}

}
