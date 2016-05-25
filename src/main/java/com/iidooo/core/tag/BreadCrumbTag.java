package com.iidooo.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class BreadCrumbTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(BreadCrumbTag.class);

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = null;
        JspWriter out = null;
        try {
            pageContext = (PageContext) getJspContext();
            out = pageContext.getOut();

            ServletContext sc = pageContext.getServletContext();

//            List<SecurityResDto> breadCrumbList = this.getBreadCrumbList(sc);
//
//            out.println("<div class='bread_crumb'>");
//            out.println("<span class='bread_crumb_title'>当前的位置：</span>");
//            for (int i = 0; i < breadCrumbList.size(); i++) {
//                SecurityResDto resource = breadCrumbList.get(i);
//                out.println("<span class='bread_crumb_item'>");
//                out.println("<a href='" + resource.getResURL() + "'>");
//                out.println(resource.getResName());
//                // The last item should not out put the -
//                if (i < (breadCrumbList.size() - 1)) {
//                    out.println("&nbsp;-&nbsp;");
//                }
//                out.println("</a>");
//                out.println("<span>");
//            }
            out.println("</span>");

            out.println(" </div>");
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

//    private List<SecurityResDto> getBreadCrumbList(ServletContext sc) {
//        List<SecurityResDto> result = new ArrayList<SecurityResDto>();
//        try {
//            @SuppressWarnings("unchecked")
//            Map<Integer, SecurityResDto> resourceMap = (Map<Integer, SecurityResDto>) sc.getAttribute(SessionConstant.RESOURCE_ID_MAP);
//
//            SecurityResDto currentResource = (SecurityResDto) sc.getAttribute(SessionConstant.CURRENT_RESOURCE);
//            SecurityResDto parentResource = resourceMap.get(currentResource.getParentID());

//            result.add(0, currentResource);
//            while (parentResource != null) {
//                result.add(0, parentResource);
//                parentResource = resourceMap.get(parentResource.getParentID());
//            }

//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.fatal(e);
//        }
//        return null;
//    }
}
