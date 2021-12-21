package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	public String memberContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	public String memberDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	public String memberInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	public String memberUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	public String memberRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
}
