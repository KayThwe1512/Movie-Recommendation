package com.hostmdy.recommendation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.Favorite;
import com.hostmdy.recommendation.model.FavoriteDAO;
import com.hostmdy.recommendation.model.History;
import com.hostmdy.recommendation.model.HistoryDAO;
import com.hostmdy.recommendation.model.Mode;
import com.hostmdy.recommendation.model.Movie;
import com.hostmdy.recommendation.model.MovieDAO;
import com.hostmdy.recommendation.model.User;
import com.hostmdy.recommendation.model.UserDAO;
import com.hostmdy.recommendation.model.WatchLater;
import com.hostmdy.recommendation.model.WatchLaterDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
@WebServlet("/user")
public class UserController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name ="jdbc/recommend_app")
	private DataSource dataSource;
	
	
	private UserDAO userDAO;
	private MovieDAO movieDAO;
	private FavoriteDAO favoriteDAO;
	private HistoryDAO historyDAO;
	private WatchLaterDAO watchLaterDAO;
	private User user;
	
	@Override 
	public void init() throws ServletException { 
		movieDAO = new MovieDAO(dataSource);
		userDAO = new UserDAO(dataSource);
		favoriteDAO = new FavoriteDAO(dataSource);
		historyDAO = new HistoryDAO(dataSource);
		watchLaterDAO = new WatchLaterDAO(dataSource);
	}
	 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null) {
			mode = Mode.SIGNUP_FORM;
		}else {
			mode = Mode.valueOf(param);
		}
		
		switch (mode) {
		case SIGNUP_FORM:
			showSignUpForm(req, resp);
			break;
		case SIGNUP:
			signUp(req,resp);
			break;
		case FAVORITE:
            showAllUserFavorites(req, resp);
            break;
        case PROFILE:
            showProfile(req, resp);
            break;
        case SETTING:
        	showSetting(req,resp);
        	break;
        case UPDATE_PROFILE_FORM:
        	updateProfileForm(req,resp);
        	break;
        case UPDATE_PROFILE:
            updateProfile(req, resp);
            break;
        case CHANGE_PASSWORD_FORM:
        	changePasswordForm(req,resp);
        	break;
        case CHANGE_PASSWORD:
        	try {
				changePassword(req, resp);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        case HISTORY:
            showUserHistory(req, resp);
            break;
        case WATCH_LATER:
            showUserWatchLaterList(req, resp);
            break;
        case UPLOAD_PICTURE:
        	uploadPicture(req);
        	break;
        case DELETE_USER:
        	deleteUserAcc(req, resp);
        	break;
		default:
			showSignUpForm(req, resp);
			break;
		}
	}


	private void showSetting(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		Long userId = Long.parseLong(req.getParameter("userId"));
		user = userDAO.getUserById(userId);
		if (user != null) {
	        user = userDAO.getUserProfileById(userId);
	        req.setAttribute("user", user);
	    }
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/setting.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	private boolean uploadPicture(HttpServletRequest req) throws IOException, ServletException {
	    HttpSession session = req.getSession();
	    User user = (User) session.getAttribute("user");

	    if (user != null) {
	        Long id = Long.parseLong(req.getParameter("userId"));
	        Part profile_pic = req.getPart("profile_pic");
	        if (profile_pic != null && profile_pic.getSize() > 0) {
	            String picFile = profile_pic.getSubmittedFileName();
	            String uploadPath = getServletContext().getRealPath("/template/image/") + File.separator + picFile;
	            System.out.println("Upload path: "+uploadPath);
	            try (FileOutputStream outputStream = new FileOutputStream(uploadPath);
	                 InputStream inputStream = profile_pic.getInputStream()) {
	                
	                byte[] data = new byte[inputStream.available()];
	                inputStream.read(data);
	                outputStream.write(data);
	                System.out.println("File written successfully to: " + uploadPath);
	            } catch (Exception e) {
	                e.printStackTrace();  
	                return false;  
	            }
	            
	            return userDAO.uploadPicture(id, picFile);
	        }
	    }
	    return false;
	}


	private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	    	Long id = Long.parseLong(req.getParameter("userId"));
	    	String firstname = req.getParameter("firstname");
	    	String lastname = req.getParameter("lastname");
		    String username = req.getParameter("username");
	        String email = req.getParameter("email");
	        String biography = req.getParameter("biography");
	        String gender = req.getParameter("gender");
	        String dateOfBirthStr = req.getParameter("dateOfBirth");
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateOfBirth = null;
			dateOfBirth = LocalDate.parse(dateOfBirthStr, formatter);
	        User user = new User(id, firstname, lastname, username, email, biography, gender, dateOfBirth, dateOfBirthStr);
	        boolean updateOk = userDAO.updateUserProfile(new User(id,firstname, lastname, username, email, biography, gender, dateOfBirth));
	        boolean pictureUpdated = uploadPicture(req);
	        if (updateOk || pictureUpdated) {
	            resp.sendRedirect("user?mode=SETTING&userId=" + user.getId());
	        } else {
	            req.setAttribute("updateOk", false);
	            RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/setting.jsp");
	            dispatcher.forward(req, resp);
	        }
	    
	}

	private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		String currentPassword = req.getParameter("currentPassword");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");
		System.out.println("currentPassword : "+currentPassword);
		System.out.println("newPassword : "+newPassword);
		System.out.println("confirmPassword : "+confirmPassword);
		if (!newPassword.equals(confirmPassword)) {
			req.getSession().setAttribute("notification", "New Password and Confirm Password do not match!");
            req.getRequestDispatcher("user?mode=CHANGE_PASSWORD_FORM&userId="+userId).forward(req, resp);
        } else if (!userDAO.validatePassword(userId, currentPassword)) {
        	req.getSession().setAttribute("notification", "Current Password is incorrect!");
        	req.getRequestDispatcher("user?mode=CHANGE_PASSWORD_FORM&userId="+userId).forward(req, resp);
        } else {
        	boolean changeOk = userDAO.changePassword(userId, newPassword);
            if (changeOk) {
            	req.setAttribute("changeOk", changeOk); 
            	req.getSession().setAttribute("notification", "Password changed successfully!");
            } else {
            	req.setAttribute("changeOk", false);
            	req.getSession().setAttribute("notification", "Password change failed. Please try again!");
            }
            req.getRequestDispatcher("user?mode=CHANGE_PASSWORD_FORM&userId="+userId).forward(req, resp);
        }
	}
	
	private void changePasswordForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		req.setAttribute("mode", mode);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/setting.jsp");
		dispatcher.forward(req, resp);	
	}


	private void updateProfileForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		req.setAttribute("mode", mode);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/setting.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void showUserWatchLaterList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 Long userId = Long.parseLong(req.getParameter("userId"));
		    List<WatchLater> watchlaterList = watchLaterDAO.getUserWatchLaterListById(userId);
		    List<Movie> movieDetailsList = new ArrayList<>();
			for (WatchLater watchLater : watchlaterList) {
		        Movie movie = movieDAO.getMovieById(watchLater.getMovieId());
		        movieDetailsList.add(movie);
		    }
		    
		    System.out.println("Watch Later List Size: " + watchlaterList.size());
		    System.out.println(watchlaterList.toString());
		    
		    req.setAttribute("movieDetailsList", movieDetailsList);
		    req.setAttribute("watchlaterList", watchlaterList);
		    String mode = req.getParameter("mode");
		    req.setAttribute("mode", mode);
		    RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
		    dispatcher.forward(req, resp);
		
	}


	private void showUserHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    Long userId = Long.parseLong(req.getParameter("userId"));
	    List<History> histories = historyDAO.getAllHistory(userId);
	    List<Movie> movieDetailsList = movieDAO.getMoviesByHistory(histories);
	    
	    System.out.println("History List Size: " + histories.size());
	    System.out.println(histories.toString());
	    
	    req.setAttribute("movieDetailsList", movieDetailsList);
	    req.setAttribute("histories", histories);
	    String mode = req.getParameter("mode");
	    req.setAttribute("mode", mode);
	    RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
	    dispatcher.forward(req, resp);
	}


	private void showAllUserFavorites(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		System.out.println(userId);
		List<Favorite> favoriteList = favoriteDAO.getUserFavoriteById(userId);
		List<Movie> movieDetailsList = new ArrayList<>();
		for (Favorite favorite : favoriteList) {
	        Movie movie = movieDAO.getMovieById(favorite.getMovieId());
	        movieDetailsList.add(movie);
	    }
		System.out.println("Favorite List Size: " + favoriteList.size());
		req.setAttribute("movieDetailsList", movieDetailsList);
		req.setAttribute("favoriteList", favoriteList);
		String mode = req.getParameter("mode");
	    req.setAttribute("mode", mode);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
		dispatcher.forward(req, resp);
	}
	
	
	private void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String biography = req.getParameter("biography");
		String gender = req.getParameter("gender");
		
		System.out.println(firstname);
		System.out.println(password);
				
		boolean signupOk = userDAO.createUser(new User(firstname, lastname, username, email, password, "user",biography,gender));
		if (signupOk) {
			User user = userDAO.getUserByUsernameOrEmail(username).get();
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			Long userid = user.getId();
			resp.sendRedirect("movie?userId=" + userid);
		} else {
			req.setAttribute("signupOk", signupOk);
			resp.sendRedirect("movie");
		}
	}
	
	private void deleteUserAcc(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
			boolean deleteOk = userDAO.deleteUser(user);
			if (deleteOk) {
				session.invalidate(); 
				resp.sendRedirect("movie");
			}else {
                req.setAttribute("errorMessage", "Failed to delete account. Please try again.");
                req.getRequestDispatcher("movie").forward(req, resp);
            }
		}else {
			resp.sendRedirect("movie");
        }
	}
	
	private void showProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect("movie"); 
        }
    }


	private static void showSignUpForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/sign-up.jsp");
		dispatcher.forward(req, resp);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
