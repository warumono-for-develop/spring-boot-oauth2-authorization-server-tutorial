package com.warumono.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
@Table(name = "CLIENTS", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@Entity
public class AppClient
{
	@Id
	private String id;
	
	@JsonIgnore
	@NotNull
	private String secret;
	
	@NotNull
	private String scopes;
	
	@NotNull
	private String grantTypes;

	public Collection<String> getScopes()
	{
		if(StringUtils.isEmpty(scopes))
		{
			return null;
		}

		return Arrays.asList(scopes.split(","));
	}

	public Collection<String> getGrantTypes()
	{
		if(StringUtils.isEmpty(grantTypes))
		{
			return null;
		}
		
		return Arrays.asList(grantTypes.split(","));
	}
}
