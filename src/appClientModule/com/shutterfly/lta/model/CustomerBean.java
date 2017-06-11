package com.shutterfly.lta.model;


/*
 * 
 * 
 * Bean class for customer database model
 */
public class CustomerBean {
	
	private String type;
	private String verb;
	private String key;
	private String lastName;
	private String eventTime;
	private String adrCity;
	private String adrState;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getAdrCity() {
		return adrCity;
	}
	public void setAdrCity(String adrCity) {
		this.adrCity = adrCity;
	}
	public String getAdrState() {
		return adrState;
	}
	public void setAdrState(String adrState) {
		this.adrState = adrState;
	}
	@Override
	public String toString() {
		return "CustomerBean [type=" + type + ", verb=" + verb + ", key=" + key + ", lastName=" + lastName
				+ ", eventTime=" + eventTime + ", adrCity=" + adrCity + ", adrState=" + adrState + "]";
	}
}
