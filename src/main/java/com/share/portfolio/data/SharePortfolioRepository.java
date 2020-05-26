package com.share.portfolio.data;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface SharePortfolioRepository extends JpaRepository<SharePortfolioEntity, String> {

	 //@Query("SELECT m FROM portfolio m WHERE LOWER(m.email) = LOWER(:email)")
	 public Optional<List<SharePortfolioEntity>> findByEmail(@PathVariable("email") String email);
}