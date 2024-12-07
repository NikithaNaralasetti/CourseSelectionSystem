package com.klef.jfsd.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfilePicture {

	@Id
	private Long profileid;
	public Long getProfileid() {
		return profileid;
	}
	public void setProfileid(Long profileid) {
		this.profileid = profileid;
	}
	public byte[] getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(byte[] profileimage) {
		this.profileimage = profileimage;
	}
	@Lob
	@Column(columnDefinition="LONGBLOB")
	private byte[] profileimage;
}
