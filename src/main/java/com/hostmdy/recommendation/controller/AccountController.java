package com.hostmdy.recommendation.controller;

import java.io.IOException;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.MovieDAO;
import com.hostmdy.recommendation.model.UserDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account")
public class AccountController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;
	
	private UserDAO userDAO;
    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(dataSource);
        movieDAO = new MovieDAO(dataSource);
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
