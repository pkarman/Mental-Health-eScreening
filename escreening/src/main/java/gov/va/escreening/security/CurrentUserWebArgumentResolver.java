package gov.va.escreening.security;

import java.lang.annotation.Annotation;
import java.security.Principal;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class CurrentUserWebArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {

        Annotation[] annotations = methodParameter.getParameterAnnotations();

        if (methodParameter.getParameterType().equals(EscreenUser.class))
        {
            for (Annotation annotation : annotations)
            {
                if (CurrentUser.class.isInstance(annotation))
                {
                    Principal principal = webRequest.getUserPrincipal();
                    if (principal != null) {
                        return (EscreenUser) ((Authentication) principal).getPrincipal();
                    }
                    else {
                        return null;
                    }
                }
            }
        }

        return WebArgumentResolver.UNRESOLVED;
    }

}
