package com.example.quiz.models;

import javax.persistence.Entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=2, message = "At least 5 symbols")
    private String username;
    
    @Size(min=2, message = "At least 4 symbols")
    private String password;
    
    @Transient
    private String passwordConfirm;
    
    @Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

	@ManyToMany()
    @JoinTable(name="users_role",
    		joinColumns = @JoinColumn(name = "user_id"),
    		inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    
    @OneToMany(fetch=FetchType.EAGER,mappedBy="user")
    private List<QuizResult> quizResults;
    
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void setQuizResults(List<QuizResult> quizResults) {
		this.quizResults = quizResults;
	}

	public List<QuizResult> getQuizResults() {
    	return quizResults;
    }
}