package ufbp.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;

import ufbp.bean.UserBean;
import ufbp.service.UserMangerService;

/**
 * �ỰУ�������
 * 
 * @author С����
 *
 */
public class SessionCheckFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = -422260424774981742L;
	private Log log = LogFactory.getLog(SessionCheckFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// HttpSession session = request.getSession();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String url = request.getServletPath();
		String contextPath = request.getContextPath();
		if (url.equals(""))
			url += "/";
		/**
		 * �Է��ʵĵ�ַ���й��ˣ������ַ���ǵ�¼��������Ҫ��session����У��
		 */
		if ((url.startsWith("/") && url.endsWith("html") && !url.startsWith("/login"))) {
			UserBean user = session.get(UserMangerService.USER) == null ? null
					: (UserBean) session.get(UserMangerService.USER);
			if (user != null) {
				filterChain.doFilter(req, res);
			} else {
				if (log.isErrorEnabled()) {
					log.error("��ǰδ��¼,��ת����¼����");
				}
				response.sendRedirect(contextPath + "/login.html");
			}
		} else {
			filterChain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
