package com.rockstar.artifact.converter.openapi;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Contact;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.rockstar.artifact.codegen.model.GeneralDefinition;

@Component
public class OpenApiInfoToGeneralDefinition implements Converter<Info, GeneralDefinition> {

	public GeneralDefinition convert(Info info) {
		GeneralDefinition generalDefinition = null;
		Contact contact = null;
		
		if (info != null) {
			generalDefinition = new GeneralDefinition();
			generalDefinition.setName(info.getTitle());
			generalDefinition.setVersion(info.getVersion());
			contact = info.getContact();
			if (contact != null) {
				generalDefinition.setOwnerEmail(contact.getEmail());
			}
		}
		return generalDefinition;
	}

}
