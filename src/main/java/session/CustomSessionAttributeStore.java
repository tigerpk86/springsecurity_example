package session;

import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

public class CustomSessionAttributeStore extends DefaultSessionAttributeStore {

    @Override
    public void storeAttribute(WebRequest request, String attributeName,
                               Object attributeValue) {
        System.out.println("storeAttribute : " + attributeName + "attributeValue :" + attributeValue);
        super.storeAttribute(request, getCustomAttributeName(request, attributeName), attributeValue);
    }

    @Override
    public Object retrieveAttribute(WebRequest request, String attributeName) {
        System.out.println("retrieveAttribute : " + attributeName);
        return super.retrieveAttribute(request, getCustomAttributeName(request, attributeName));
    }

    @Override
    public void cleanupAttribute(WebRequest request, String attributeName) {
        System.out.println("cleanupAttribute :" + attributeName);
        super.cleanupAttribute(request, getCustomAttributeName(request, attributeName));
    }

    public String getCustomAttributeName(WebRequest request, String attributeName) {

        String result;
        if(request instanceof ServletWebRequest) {
            result = ((HttpServletRequest)((ServletWebRequest)request).getNativeRequest()).getRequestURI() + attributeName;
            System.out.println("getCustomAttributeName : " + result);
        }
        else {
            result = attributeName;
            System.out.println("getCustomAttributeName : " + result);
        }
        return result;
    }

}
