package com.figsinc.app.settings.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user")
@Expose
private User user;
@SerializedName("Title")
@Expose
private Object title;
@SerializedName("Gender")
@Expose
private String gender;
@SerializedName("DateOfBirth")
@Expose
private String dateOfBirth;
@SerializedName("Position")
@Expose
private String position;
@SerializedName("Facebook")
@Expose
private String facebook;
@SerializedName("Twitter")
@Expose
private String twitter;
@SerializedName("Phone")
@Expose
private String phone;
@SerializedName("Mobile")
@Expose
private String mobile;
@SerializedName("Status")
@Expose
private String status;
@SerializedName("FavoriteQuote")
@Expose
private String favoriteQuote;
@SerializedName("previous_login")
@Expose
private String previousLogin;
@SerializedName("PersonAdmired")
@Expose
private String personAdmired;
@SerializedName("Avatar")
@Expose
private String avatar;
@SerializedName("years_of_experience")
@Expose
private String yearsOfExperience;
@SerializedName("Country")
@Expose
private String country;
@SerializedName("Language")
@Expose
private Integer language;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}

public Object getTitle() {
return title;
}

public void setTitle(Object title) {
this.title = title;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getDateOfBirth() {
return dateOfBirth;
}

public void setDateOfBirth(String dateOfBirth) {
this.dateOfBirth = dateOfBirth;
}

public String getPosition() {
return position;
}

public void setPosition(String position) {
this.position = position;
}

public String getFacebook() {
return facebook;
}

public void setFacebook(String facebook) {
this.facebook = facebook;
}

public String getTwitter() {
return twitter;
}

public void setTwitter(String twitter) {
this.twitter = twitter;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getFavoriteQuote() {
return favoriteQuote;
}

public void setFavoriteQuote(String favoriteQuote) {
this.favoriteQuote = favoriteQuote;
}

public String getPreviousLogin() {
return previousLogin;
}

public void setPreviousLogin(String previousLogin) {
this.previousLogin = previousLogin;
}

public String getPersonAdmired() {
return personAdmired;
}

public void setPersonAdmired(String personAdmired) {
this.personAdmired = personAdmired;
}

public String getAvatar() {
return avatar;
}

public void setAvatar(String avatar) {
this.avatar = avatar;
}

public String getYearsOfExperience() {
return yearsOfExperience;
}

public void setYearsOfExperience(String yearsOfExperience) {
this.yearsOfExperience = yearsOfExperience;
}

public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

public Integer getLanguage() {
return language;
}

public void setLanguage(Integer language) {
this.language = language;
}

}