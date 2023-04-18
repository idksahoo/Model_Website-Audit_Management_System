package com.cts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@FeignClient(name = "authorization-service", url = "${AuthClient.url}")
public interface AuthClient {
	@GetMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "tokenValidation", notes = "returns boolean after validating JWT", httpMethod = "GET", response = ResponseEntity.class)
	public boolean validatingAuthorizationToken(
			@ApiParam(name = "JWT-token", value = "JWT generated for current customer present") @RequestHeader(name = "Authorization") String tokenDup);
}
