package com.rockstar.artifact.codegen.model;

public class MySqlColumnDefinition implements Definition {
	
	private static final Integer DEFAULT_MAX_LENGTH = 255;
	
	private String name;
	private MySqlType type;
	private Object defaultValue;
	private Integer maximumLength;
	private Boolean nullable;
	private Boolean isParentId;
	private Boolean foreignKey;
	
	public MySqlColumnDefinition() {
		this.nullable = Boolean.TRUE;
		this.foreignKey = Boolean.FALSE;
		this.isParentId = Boolean.FALSE;
		this.maximumLength = DEFAULT_MAX_LENGTH;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaximumLength() {
		return maximumLength;
	}
	
	public void setMaximumLength(Integer maximumLength) {
		if (maximumLength != null) {
			this.maximumLength = maximumLength;
		}
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public MySqlType getType() {
		return type;
	}

	public void setType(MySqlType type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean isParentId() {
		return isParentId;
	}

	public void setIsParentId(Boolean isParentId) {
		this.isParentId = isParentId;
	}

	public Boolean isForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(Boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public Boolean isNull() {
		return this.nullable;
	}
	
	public boolean isType(MySqlType type) {
		return (this.type != null && this.type.equals(type));
	}
	
	public boolean isShortType() {
		return (this.type != null && this.type.equals(MySqlType.SmallInteger));
	}
	
	public boolean isIntegerType() {
		return (this.type != null && this.type.equals(MySqlType.Integer));
	}
	
	public boolean isLongType() {
		return (this.type != null && this.type.equals(MySqlType.BigInteger));
	}
	
	public boolean isStringType() {
		return (this.type != null && this.type.equals(MySqlType.String));
	}
	
	public boolean isBooleanType() {
		return (this.type != null && this.type.equals(MySqlType.Boolean));
	}
	
	public boolean isDoubleType() {
		return (this.type != null && this.type.equals(MySqlType.Double));
	}
	
	public boolean isDateType() {
		return (this.type != null && this.type.equals(MySqlType.Date));
	}
	
	public boolean isDateTimeType() {
		return (this.type != null && this.type.equals(MySqlType.DateTime));
	}
	
	public String toString() {
		return "MySql Column Definition [name=" + name + ", type=" + type + ", defaultValue=" + defaultValue
				+ ", maximumLength=" + maximumLength + ", nullable=" + nullable + ", isParentId=" + isParentId
				+ ", foreignKey=" + foreignKey + "]";
	}

}
