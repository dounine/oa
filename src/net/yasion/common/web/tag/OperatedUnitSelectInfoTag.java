package net.yasion.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class OperatedUnitSelectInfoTag extends TagSupport {

	private static final long serialVersionUID = 8427607555146423493L;

	private Boolean isSurroundTable = true;

	private String selectStyle = "width: 206px;";

	private String unitPropertyName = "operatedUnitId";

	private String value;

	public Boolean getIsSurroundTable() {
		return isSurroundTable;
	}

	public void setIsSurroundTable(Boolean isSurroundTable) {
		this.isSurroundTable = isSurroundTable;
	}

	public String getSelectStyle() {
		return selectStyle;
	}

	public void setSelectStyle(String selectStyle) {
		this.selectStyle = selectStyle;
	}

	public String getUnitPropertyName() {
		return unitPropertyName;
	}

	public void setUnitPropertyName(String unitPropertyName) {
		this.unitPropertyName = unitPropertyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doEndTag() throws JspException {
		TbUser user = UserUtils.getCurrentUser();
		JspWriter out = this.pageContext.getOut();
		if (null != user) {
			try {
				if (!(user instanceof TbSuperUser)) {
					// if (0 < user.getTbUserUnitRelatings().size()) {
					// TbUserUnitRelating[] relatings = user.getTbUserUnitRelatings().toArray(new TbUserUnitRelating[0]);
					// if (1 < relatings.length) {
					// if (this.getIsSurroundTable()) {
					// out.write("<tr>");
					// out.write("<td colspan=\"2\">");
					// }
					// out.write("<label class=\"sr-only\" for=\"operatedUnitId\">内容所属单位</label>");
					// out.write("<select class=\"form-control\" id=\"" + this.getUnitPropertyName() + "\" name=\"" + this.getUnitPropertyName() + "\" style=\"" + this.getSelectStyle() + "\" default=\"true\" notSelectDefault=\"true\" value=\""
					// + (StringUtils.isNotBlank(this.getValue()) ? this.getValue() : "") + "\">");
					// for (int i = 0, len = relatings.length; i < len; i++) {
					// out.write("<option value=\"" + StringEscapeUtils.escapeXml(relatings[i].getTbUnit().getId()) + "\" " + ((0 == i) ? "default selected" : "") + ">" + StringEscapeUtils.escapeXml(relatings[i].getTbUnit().getName()) + "</option>");
					// }
					// out.write("</select>");
					// if (this.getIsSurroundTable()) {
					// out.write("</td>");
					// out.write("</tr>");
					// }
					// } else {
					// if (0 < relatings.length) {
					// out.write("<input type=\"hidden\" name=\"" + this.getUnitPropertyName() + "\" value=\"" + StringEscapeUtils.escapeXml(relatings[0].getTbUnit().getId()) + "\" set=\"false\" />");
					// }
					// }
					// }
					if (null != user.getTbUnit()) {
						TbUnit[] relatings = new TbUnit[] { user.getTbUnit() };
						if (1 < relatings.length) {
							if (this.getIsSurroundTable()) {
								out.write("<tr>");
								out.write("<td colspan=\"2\">");
							}
							out.write("<label class=\"sr-only\" for=\"operatedUnitId\">内容所属单位</label>");
							out.write("<select class=\"form-control\" id=\"" + this.getUnitPropertyName() + "\" name=\"" + this.getUnitPropertyName() + "\" style=\"" + this.getSelectStyle() + "\" default=\"true\" notSelectDefault=\"true\" value=\""
									+ (StringUtils.isNotBlank(this.getValue()) ? this.getValue() : "") + "\">");
							for (int i = 0, len = relatings.length; i < len; i++) {
								out.write("<option value=\"" + StringEscapeUtils.escapeXml(relatings[i].getId()) + "\" " + ((0 == i) ? "default selected" : "") + ">" + StringEscapeUtils.escapeXml(relatings[i].getName()) + "</option>");
							}
							out.write("</select>");
							if (this.getIsSurroundTable()) {
								out.write("</td>");
								out.write("</tr>");
							}
						} else {
							if (0 < relatings.length) {
								out.write("<input type=\"hidden\" name=\"" + this.getUnitPropertyName() + "\" value=\"" + StringEscapeUtils.escapeXml(relatings[0].getId()) + "\" set=\"false\" />");
							}
						}
					} else {
						out.write("<input type=\"hidden\" name=\"" + this.getUnitPropertyName() + "\" value=\"\" set=\"false\" />");
					}
				} else {
					out.write("<input type=\"hidden\" name=\"" + this.getUnitPropertyName() + "\" value=\"" + CommonConstants.ADMIN_UNIT_ID + "\" set=\"false\" />");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				out.write("<input type=\"hidden\" name=\"" + this.getUnitPropertyName() + "\" value=\"\" set=\"false\" />");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}
}