package com.share.portfolio.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="portfolio")
public class SharePortfolioEntity implements Serializable{	

	private static final long serialVersionUID = -587207269553992649L;
	
	@Id
	private String id;
    private String portfolio;
	@Column(nullable = false)
    private String email;
}
