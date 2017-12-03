package model.dataobjects;

import java.io.Serializable;

public class Adres implements Serializable{

	public Adres(String street, String number, int postalCode, String city, String country) {
		super();
		this.street = street;
		this.number = number;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}
	private int id;
	private String street;
	private String number;
	private int postalCode;
	private String city;
	private String country;

	/** Getter & Setter for 'id' **/
	public int getId() {
		return id; }
	public void setId(int id) {
		this.id = id; }

	/** Getter & Setter for 'street' **/
	public String getStreet() {
		return street; }
	public void setStreet(String street) {
		this.street = street; }

	/** Getter & Setter for 'number' **/
	public String getNumber() {
		return number; }
	public void setNumber(String number) {
		this.number = number; }

	/** Getter & Setter for 'postalCode' **/
	public int getPostalCode() {
		return postalCode; }
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode; }

	/** Getter & Setter for 'city' **/
	public String getCity() {
		return city; }
	public void setCity(String city) {
		this.city = city; }

	/** Getter & Setter for 'country' **/
	public String getCountry() {
		return country; }
	public void setCountry(String country) {
		this.country = country; }
}
