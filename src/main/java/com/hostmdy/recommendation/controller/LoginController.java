package com.hostmdy.recommendation.controller;

import java.io.IOException;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.Mode;
import com.hostmdy.recommendation.model.User;
import com.hostmdy.recommendation.model.UserDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	
	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null) {
			mode = Mode.LOGIN_FORM;
		}else {
			mode = Mode.valueOf(param);
		}
		
		switch (mode) {
		case LOGIN_FORM:
			showLoginForm(req, resp);
			break;
		case LOGIN:
			login(req, resp);
			break;
		case LOGOUT:
			logout(req, resp);
			break;
		default:
			showLoginForm(req, resp);
			break;
		}
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		session.invalidate();
		//session.removeAttribute("user");
		resp.sendRedirect("movie");
		
	}

	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(dataSource);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(password);
		boolean loginOk = userDAO.authenticate(username, password);
		System.out.println(loginOk);
		if (loginOk) {
			User user = userDAO.getUserByUsernameOrEmail(username).get();
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			Long userid = user.getId();
			resp.sendRedirect("movie?userId=" + userid);
		} else {
			req.setAttribute("loginOk", loginOk);
			showLoginForm(req, resp);
		}
	}

	private void showLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/sign-in.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
