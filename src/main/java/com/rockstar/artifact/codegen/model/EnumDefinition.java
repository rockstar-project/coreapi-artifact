package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EnumDefinition {
	
	private String name;
	private List<Item> items;
	
	public EnumDefinition() {
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void addItem(String code, String label) {
		this.addItem(code, label, this.items.size());
	}

	public void addItem(String code, String label, Integer order) {
		this.getItems().add(new Item(code, label, order));
	}
	
	public List<Item> getItems() {
		if (this.items == null) {
			this.items = new ArrayList<Item> ();
		}
		return this.items;
	}
	
	public class Item {
		
		private String code;
		private String displayLabel;
		private Integer order;
		
		public Item(String code, String displayLabel) {
			this(code, displayLabel, null);
		}
		
		public Item(String code, String displayLabel, Integer order) {
			this.code = code;
			this.displayLabel = displayLabel;
		}
		
		public String getCode() {
			return this.code;
		}
		
		public String getDisplayLabel() {
			return this.displayLabel;
		}
		
		public Integer getOrder() {
			return this.order;
		}
		
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}

	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
