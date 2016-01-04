package com.iidooo.core.tag.entity;

import java.util.ArrayList;

public class TreeNode {
	private String code;
	private TreeNode parent;
	private String name;
	private ArrayList<TreeNode> children;
	private String icon;
	private String url;
	
	// 保存实例对象
	private Object tag;

	public TreeNode(TreeNode parentNode) {
		children = new ArrayList<TreeNode>();
		if (parentNode != null) {
			this.parent = parentNode;
			this.parent.children.add(this);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
		this.parent.children.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
	public String toString(){

		StringBuilder sb = new StringBuilder();
		sb.append("<li class='tree-node expandable'>");
		sb.append("<div class='hitarea expandable-hitarea'></div>");
		sb.append("<input id='code' type='hidden' value='" + this.code + "'>");
		sb.append("<input id='name' type='hidden' value='" + this.name + "'>");
		sb.append("<input id='url' type='hidden' value='" + this.url + "'>");
		sb.append("<span class='tree-nod'>" + this.name + "</span>");
		sb.append("</li>");

		return sb.toString();
	}

}
