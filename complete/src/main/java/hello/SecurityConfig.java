package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("player1").password("player1").roles("USER").and()
				.withUser("player2").password("player2").roles("USER").and()
				.withUser("player3").password("player3").roles("USER").and()
				.withUser("player4").password("player4").roles("USER").and()
				.withUser("player5").password("player5").roles("USER").and()
				.withUser("player6").password("player6").roles("USER").and()
				.withUser("player7").password("player7").roles("USER").and()
				.withUser("player8").password("player8").roles("USER").and()
				.withUser("observer").password("observer").roles("USER");
	}
}