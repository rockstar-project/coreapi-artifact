package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;

public class ConstraintDefinition {
	
	private List<Constraint> constraints;
	
	public ConstraintDefinition() {
		this.constraints = new ArrayList<Constraint> ();
	}
	
	public List<Constraint> getConstraints() {
		return this.constraints;
	}
	
	public void addConstraint(ConstraintType type) {
		Constraint constraint = new Constraint();
		constraint.setType(type);
		this.constraints.add(constraint);
	}
	
	public void addConstraint(ConstraintType type, String message, Map<String, Object> arguments) {
		Constraint constraint = new Constraint();
		constraint.setType(type);
		constraint.setMessage(message);
		constraint.getArguments().putAll(arguments);
		this.constraints.add(constraint);
	}
	
	public void addConstraint(ConstraintType type, Map<String, Object> arguments) {
		Constraint constraint = new Constraint();
		constraint.setType(type);
		constraint.getArguments().putAll(arguments);
		this.constraints.add(constraint);
	}
	
	public boolean hasConstraint(ConstraintType type) {
		return (this.getConstraint(type) != null ? true : false);
	}
	
	public ConstraintDefinition.Constraint getConstraint(ConstraintType type) {
		Constraint constraint = null;
		for (Constraint currentConstraint : this.getConstraints()) {
			if (currentConstraint.isConstraint(type)) {
				constraint = currentConstraint;
				break;
			}
		}
		return constraint;
	}
	
	public class Constraint {
		private String name;
		private ConstraintType type;
		private String message;
		private Map<String, Object> arguments;

		public Constraint() {
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ConstraintType getType() {
			return this.type;
		}
		
		public void setType(ConstraintType type) {
			this.type = type;
		}
		
		public Map<String, Object> getArguments() {
			if (this.arguments == null) {
				this.arguments = new HashMap<String, Object> ();
			}
			return this.arguments;
		}
		
		public void addArgument(String argumentName, Boolean argumentValue) {
			this.getArguments().put(argumentName, argumentValue);
		}
		
		public void addArgument(String argumentName, Integer argumentValue) {
			this.getArguments().put(argumentName, argumentValue);
		}
		
		public void addArgument(String argumentName, String argumentValue) {
			this.getArguments().put(argumentName, argumentValue);
		}
		
		public String getArgs() {
			String argumentStr = null;
			if (this.arguments != null && !this.arguments.isEmpty()) {
				argumentStr = Joiner.on(",").withKeyValueSeparator("=").join(this.arguments);
				if (!StringUtils.isEmpty(argumentStr)) {
					argumentStr = "(" + argumentStr + ")";
				}
			}
			return argumentStr;
		}
		
		public boolean isConstraint(ConstraintType type) {
			boolean isConstraint = false;
			
			if (this.type.equals(type)) {
				isConstraint = true;
			}
			return isConstraint;
		}
		
		public boolean isSizeConstraint() {
			return this.isConstraint(ConstraintType.Size);
		}
		
		public boolean isNotNullConstraint() {
			return this.isConstraint(ConstraintType.NotNull);
		}
		
		public boolean isNotEmptyConstraint() {
			return this.isConstraint(ConstraintType.NotEmpty);
		}
		
		public boolean isEmailConstraint() {
			return this.isConstraint(ConstraintType.Email);
		}
		
		public boolean isURLConstraint() {
			return this.isConstraint(ConstraintType.URL);
		}
		
		public boolean isPatternConstraint() {
			return this.isConstraint(ConstraintType.Pattern);
		}
		
		public boolean isValidConstraint() {
			return this.isConstraint(ConstraintType.Valid);
		}
		
		public boolean isValidEnumConstraint() {
			return this.isConstraint(ConstraintType.ValidEnum);
		}
		
		public String toString() {
			StringBuilder constraintBuilder = new StringBuilder();
			
			constraintBuilder.append("@");
			switch (this.type) {
				case ValidEnum:
					constraintBuilder.append("Valid");
					constraintBuilder.append(this.getName());
					break;
				default:
					constraintBuilder.append(this.type.name());
					if (this.hasConstraintParameters()) {
						constraintBuilder.append("(");
						constraintBuilder.append(this.toConstraintParametersString());
						constraintBuilder.append(")");
					}
					break;
			}
			return constraintBuilder.toString();
		}
		
		private boolean hasConstraintParameters() {
			boolean hasConstraintParameters = false;
			Map<String, Object> constraintParameters = this.getConstraintParameters();
			
			if (constraintParameters != null && !constraintParameters.isEmpty()) {
				hasConstraintParameters = true;
			}
			return hasConstraintParameters;
		}
		
		private Map<String, Object> getConstraintParameters() {
			Map<String, Object> constraintParameters = new HashMap<String, Object> ();
			
			if (StringUtils.hasText(this.message)) {
				constraintParameters.put("message", this.message);
			}
			if (this.arguments != null && !this.arguments.isEmpty()) {
				constraintParameters.putAll(arguments);
			}
			return constraintParameters;
		}
		
		private String toConstraintParametersString() {
			StringBuilder argumentsBuilder = new StringBuilder();
			Map<String, Object> constraintParameters = getConstraintParameters();
			if (constraintParameters != null && !constraintParameters.isEmpty()) {
				String separator = "";
				for (Entry<String, Object> currentEntry : constraintParameters.entrySet()) {
					argumentsBuilder.append(separator);
					argumentsBuilder.append(currentEntry.getKey());
					argumentsBuilder.append("=");
					argumentsBuilder.append(currentEntry.getValue());
					separator = ",";
				}
			}
			return argumentsBuilder.toString();
		}
	}
	
	public String toString() {
		StringBuilder constraintsStringBuilder = new StringBuilder();
		String argumentSeparator = null;
		for (Constraint current : this.constraints) {
			argumentSeparator = "";
			constraintsStringBuilder.append(" - ");
			constraintsStringBuilder.append(current.type.name());
			if (current.getArguments() != null && !current.getArguments().isEmpty()) {
				constraintsStringBuilder.append(" [");
				for (Entry<String, Object> argumentEntry : current.getArguments().entrySet()) {
					constraintsStringBuilder.append(argumentSeparator);
					constraintsStringBuilder.append(argumentEntry.getKey());
					constraintsStringBuilder.append("=");
					constraintsStringBuilder.append(argumentEntry.getValue());
					argumentSeparator = ",";
				}
				constraintsStringBuilder.append("]");
			}
			constraintsStringBuilder.append("\n");
		}
		return constraintsStringBuilder.toString();
	}
}
