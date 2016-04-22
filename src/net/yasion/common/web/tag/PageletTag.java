package net.yasion.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.yasion.common.support.common.dao.interfaces.IResultSet;

public class PageletTag extends TagSupport {

	private static final long serialVersionUID = 7446476523353610321L;

	private String url;

	private Boolean showOnOne = true;

	private Boolean script = true;

	private IResultSet<?> resultSet;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getShowOnOne() {
		return showOnOne;
	}

	public void setShowOnOne(Boolean showOnOne) {
		this.showOnOne = showOnOne;
	}

	public Boolean getScript() {
		return script;
	}

	public void setScript(Boolean script) {
		this.script = script;
	}

	public IResultSet<?> getResultSet() {
		return resultSet;
	}

	public void setResultSet(IResultSet<?> resultSet) {
		this.resultSet = resultSet;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if (null != resultSet && ((1 < resultSet.getTotalPageCount()) || this.showOnOne)) {
				out.write("分页：<select id=\"page\" style=\"width: 100px;\" url=\"" + this.url + "\"><option></option>");
				for (int i = 1, len = resultSet.getTotalPageCount(); i <= len; i++) {
					out.write("<option value=\"" + i + "\" " + ((i == resultSet.getPageNumber() ? "selected" : "")) + ">" + i + "</option>");
				}
				out.write("</select>&nbsp;&nbsp;" + resultSet.getPageNumber() + "/" + resultSet.getTotalPageCount() + "");
				out.write("");
				out.write("");
				if (this.script) {
					out.write("<script type=\"text/javascript\">");
					out.write("function pageChangeInit() {");
					out.write("var pageObj = $(\"#page\");");
					out.write("if (0 < pageObj.length) {");
					out.write("pageObj.on(\"change\", function(e) {");
					out.write("var pageStr = pageObj.attr(\"url\") + \"&pageNumber=\" + e.val;");
					out.write("var searchBtn = $(\"#searchDialog #searchBtn\");");
					out.write("if (0 < searchBtn.length) {");
					out.write("pageStr = $(\"#searchDialog #backupForm\").attr(\"action\");");
					out.write("pageStr += (\"&pageNumber=\" + e.val);");
					out.write("}");
					out.write("var backupForm = $(\"#searchDialog #backupForm\");");
					out.write("backupForm.attr(\"action\", pageStr);");
					out.write("backupForm.submit();");
					out.write("});");
					out.write("}");
					out.write("}");
					out.write("</script>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}