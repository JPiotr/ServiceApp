package com.oblitus.serviceApp.Security;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.w3c.dom.Element;

public class PasswordMeneger extends PasswordEncoderParser {
    public PasswordMeneger(Element element, ParserContext parserContext) {
        super(element, parserContext);
    }

    @Override
    public BeanMetadataElement getPasswordEncoder() {
        return super.getPasswordEncoder();
    }
}
