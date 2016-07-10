package com.iidooo.core.model.vo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String text;
    
    private String href;
    
    private List<String> tags;
    
    private List<TreeNode> nodes;
    
    private Object data;
    
    public TreeNode(){
        this.tags = new ArrayList<String>();
        this.nodes = new ArrayList<TreeNode>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    
    
    
}
