package com.warumono.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@Entity
public class AppUser
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(max = 50)
	@Email
	@NotNull
	private String username;
	
	@JsonIgnore
	@NotNull
	private String password;
	
	@NotNull
	private String authorities;

	public Collection<GrantedAuthority> getAuthorities()
	{
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		if(StringUtils.isEmpty(grantedAuthorities))
		{
			String[] _authorities = authorities.split(",");
			
			for(String authority : _authorities)
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(authority));
			}
		}
		
		if(CollectionUtils.isEmpty(grantedAuthorities))
		{
			grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		}
		
		return grantedAuthorities;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o){ return true; }
		if(!(o instanceof AppUser)){ return false; }
		
		return Objects.nonNull(getId()) && Objects.equals(getId(), ((AppUser)o).getId());
	}
}
