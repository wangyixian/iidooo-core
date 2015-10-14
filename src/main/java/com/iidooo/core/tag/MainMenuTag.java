package com.iidooo.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dto.extend.SecurityResDto;

public class MainMenuTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(MainMenuTag.class);

    int level = 1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = null;
        JspWriter out = null;
        try {
            pageContext = (PageContext) getJspContext();
            out = pageContext.getOut();

            ServletContext sc = pageContext.getServletContext();

            @SuppressWarnings("unchecked")
            List<SecurityResDto> resourceList = (List<SecurityResDto>) sc.getAttribute(SessionConstant.RESOURCE_LIST);
            out.println("<div id='mainMenu' class='main_menu'>");
            out.println("<ul>");
            for (SecurityResDto item : resourceList) {
                
                // If the resource was set as invisible, it should not be shown in the menu.
                if (item.getInvisible() != 0) {
                    continue;
                }
                
                // If the menu level was set as 1, the resource's children should not be displayed.
                if (this.level == 1 && item.getParentID() > 0) {
                    continue;
                }
                out.println(" <li>");
                if (item.getIsSelected()) {
                    out.println("<a href='" + item.getResURL() + "'" + " class='focus'>");
                    out.println(item.getResName());
                    out.println("</a>");
                } else {
                    out.println("<a href='" + item.getResURL() + "'>");
                    out.println(item.getResName());
                    out.println("</a>");
                }
                out.println("</li>");
            }

            out.println("</ul>");
            out.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
