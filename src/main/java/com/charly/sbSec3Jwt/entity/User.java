package com.charly.sbSec3Jwt.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true) 
	private String email;									
															
	private String password;								
															
															
															
	@Enumerated(EnumType.ORDINAL)
	private Role role;
	
	@Override
	public String getPassword() { 
		return password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { 
		return List.of(new SimpleGrantedAuthority(role.name())); 
	}
	@Override
	public String getUsername() {
		return email; 
	}
	@Override
	public boolean isAccountNonExpired() {
		return true; 
	}
	@Override
	public boolean isAccountNonLocked() {
		return true; 
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true; 
	}
	@Override
	public boolean isEnabled() {
		return true; 
	}				
					
	
}
