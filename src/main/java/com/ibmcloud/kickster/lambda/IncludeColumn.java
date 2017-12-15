package com.ibmcloud.kickster.lambda;

import org.apache.commons.lang.StringUtils;
import org.trimou.lambda.SpecCompliantLambda;

public class IncludeColumn extends SpecCompliantLambda {

	@Override
	public String invoke(String text) {
		return String.format("@Column(name=%s)", StringUtils.upperCase(text));
	}

}
