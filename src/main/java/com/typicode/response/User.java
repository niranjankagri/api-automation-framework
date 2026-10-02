package com.typicode.response;

/**
 * A user from GET /users, mapped from JSON by ResponseBuilder.
 */
public class User {

	// Id of the user
	private int id;
	// Full name
	private String name;
	// Login name, used to search for the user
	private String username;
	// Email address
	private String email;
	// Postal address
	private Address address;
	// Phone number
	private String phone;
	// Website
	private String website;
	// Company the user works for
	private Company company;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Postal address of a user.
	 */
	public static class Address {

		// Street name
		private String street;
		// Apartment or suite
		private String suite;
		// City
		private String city;
		// Postal code
		private String zipcode;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getSuite() {
			return suite;
		}

		public void setSuite(String suite) {
			this.suite = suite;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		/**
		 * Geo coordinates of an address.
		 * Note: Address has no geo field, so this class is not filled from the JSON yet.
		 */
		public static class Geo {

			// Latitude
			private double lat;
			// Longitude
			private double lng;

			public double getLat() {
				return lat;
			}

			public void setLat(double lat) {
				this.lat = lat;
			}

			public double getLng() {
				return lng;
			}

			public void setLng(double lng) {
				this.lng = lng;
			}

		}

	}

	/**
	 * Company a user works for.
	 */
	public static class Company {

		// Company name
		private String name;
		// Company slogan
		private String catchPhrase;
		// Business description
		private String bs;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCatchPhrase() {
			return catchPhrase;
		}

		public void setCatchPhrase(String catchPhrase) {
			this.catchPhrase = catchPhrase;
		}

		public String getBs() {
			return bs;
		}

		public void setBs(String bs) {
			this.bs = bs;
		}

	}

}
