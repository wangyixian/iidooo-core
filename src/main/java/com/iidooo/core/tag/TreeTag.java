package com.iidooo.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import com.iidooo.core.tag.entity.TreeNode;
import com.iidooo.core.util.StringUtil;

/**
 * 这是一个树型标签
 * 
 * @author wangyixian
 *
 */
public class TreeTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(TreeTag.class);

    private final String FILE_TREE_NODE = "<span class='file'><a href={0}>{1}</a></span>";

    private final String FOLD_TREE_NODE = "<span class='folder'><a href={0}>{1}</a></span>";

    private final String HTML_UL = "<ul  id='{0}' class='{1}'>";

    private String id;
    private String cssClass;
    private TreeNode root;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = null;
        JspWriter out = null;
        try {
            pageContext = (PageContext) getJspContext();
            out = pageContext.getOut();

            out.println(StringUtil.replace(HTML_UL, id, cssClass));

            String folder = StringUtil.replace(FOLD_TREE_NODE, root.getUrl(), root.getName());
            out.println("<li>" + folder);

            if (root.getChildren().size() > 0) {
                out.println("<ul>");
                for (TreeNode item : root.getChildren()) {
                    printHTML(out, item);
                }
                out.println("</ul>");
            }
            out.println("</li>");

            out.println("</ul>");

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        } 
    }

    private void printHTML(JspWriter out, TreeNode treeNode) throws JspException, IOException {

        try {
            if (treeNode.getChildren().size() > 0) {
                String folder = StringUtil.replace(FOLD_TREE_NODE, treeNode.getUrl(), treeNode.getName());
                out.println("<li>" + folder);

                List<TreeNode> children = treeNode.getChildren();
                out.println("<ul>");
                for (TreeNode child : children) {
                    this.printHTML(out, child);
                }
                out.println("</ul>");

                out.println(" </li>");
            } else {
                String file = StringUtil.replace(FILE_TREE_NODE, treeNode.getUrl(), treeNode.getName());
                out.println("<li>" + file + "</li>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

}
