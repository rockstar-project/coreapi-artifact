package com.rockstar.artifact.codegen.model;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchDefinition {
	
	private Collection<ParamDefinition> params;
	private Collection<ParamDefinition> sorts;
	
	public SearchDefinition() {
	}

	public Collection<ParamDefinition> getParams() {
		return params;
	}

	public void setParams(Collection<ParamDefinition> params) {
		this.params = params;
	}

	public Collection<ParamDefinition> getSorts() {
		return sorts;
	}

	public void setSorts(Collection<ParamDefinition> sorts) {
		this.sorts = sorts;
	}

	public boolean hasSortFields() {
		return (this.getSorts() != null && !this.getSorts().isEmpty());
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
