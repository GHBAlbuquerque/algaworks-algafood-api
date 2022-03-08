package com.algaworks.algafood.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class ResourceUriHelper {

    public static void addUriInResponseHeader(Object resourceId){
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        var response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }
}
